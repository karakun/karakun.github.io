///usr/bin/env jbang "$0" "$@" ; exit $?

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;

public class SubmitIndexNow {

    private static final Pattern INDEXNOW_KEY_FILE = Pattern.compile("^[a-fA-F0-9]{32}\\.txt$");
    private static final Pattern LOC_PATTERN = Pattern.compile("<loc>(.*?)</loc>");
    private static final Pattern FRONT_MATTER_PERMALINK = Pattern.compile("(?m)^permalink:\\s*[\"']?([^\"'\\r\\n]+)[\"']?\\s*$");
    private static final Pattern POST_FILE = Pattern.compile("^_posts/(\\d{4})-(\\d{2})-(\\d{2})-(.+)\\.md$");
    private static final Set<String> SHARED_RENDERING_FILES = Set.of(
            "_config.yml",
            "Gemfile",
            "Gemfile.lock",
            "sitemap.xml",
            "assets/main.scss"
    );

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public static void main(String[] args) throws Exception {
        new SubmitIndexNow().run();
    }

    private void run() throws Exception {
        Optional<String> key = resolveIndexNowKey();
        if (key.isEmpty()) {
            System.out.println("Skipping IndexNow notification because no INDEXNOW_KEY or committed key file was found.");
            return;
        }

        URI siteUrl = resolveSiteUrl();
        List<String> changedFiles = determineChangedFiles();
        System.out.printf("Detected %d changed file(s).%n", changedFiles.size());

        LinkedHashSet<String> sitemapUrls = fetchSitemapUrls(siteUrl);
        boolean submitAll = changedFiles.isEmpty() || changedFiles.stream().anyMatch(this::isSharedRenderingFile);

        LinkedHashSet<String> urlsToSubmit = new LinkedHashSet<>();
        if (submitAll) {
            urlsToSubmit.addAll(sitemapUrls);
            System.out.printf("Submitting all %d sitemap URL(s).%n", urlsToSubmit.size());
        } else {
            for (String changedFile : changedFiles) {
                mapChangedFileToUrl(changedFile, siteUrl)
                        .filter(url -> sitemapUrls.isEmpty() || sitemapUrls.contains(url))
                        .ifPresent(urlsToSubmit::add);
            }
            System.out.printf("Mapped %d URL(s) from changed files.%n", urlsToSubmit.size());
        }

        if (urlsToSubmit.isEmpty()) {
            System.out.println("No public URLs to submit to IndexNow.");
            return;
        }

        if (Boolean.parseBoolean(env("DRY_RUN"))) {
            System.out.printf("DRY_RUN enabled; would submit %d URL(s) to IndexNow.%n", urlsToSubmit.size());
            urlsToSubmit.forEach(url -> System.out.println("  " + url));
            return;
        }

        submitIndexNow(siteUrl, key.get(), urlsToSubmit);
    }

    private Optional<String> resolveIndexNowKey() throws IOException {
        String envKey = env("INDEXNOW_KEY");
        if (!envKey.isBlank()) {
            return Optional.of(envKey);
        }

        try (Stream<Path> files = Files.list(Path.of("."))) {
            return files
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> INDEXNOW_KEY_FILE.matcher(name).matches())
                    .map(name -> name.substring(0, name.length() - ".txt".length()))
                    .sorted()
                    .findFirst();
        }
    }

    private URI resolveSiteUrl() throws IOException {
        String siteUrl = env("SITE_URL");
        if (siteUrl.isBlank()) {
            siteUrl = readConfigUrl().orElseThrow(() ->
                    new IllegalStateException("SITE_URL is missing and no url value was found in _config.yml."));
        }

        URI uri = URI.create(stripTrailingSlash(siteUrl));
        if (uri.getScheme() == null || uri.getHost() == null) {
            throw new IllegalStateException("SITE_URL must be an absolute URL: " + siteUrl);
        }
        return uri;
    }

    private Optional<String> readConfigUrl() throws IOException {
        Path config = Path.of("_config.yml");
        if (!Files.exists(config)) {
            return Optional.empty();
        }
        for (String line : Files.readAllLines(config)) {
            String trimmed = line.trim();
            if (trimmed.startsWith("url:")) {
                return Optional.of(cleanYamlScalar(trimmed.substring("url:".length())));
            }
        }
        return Optional.empty();
    }

    private String cleanYamlScalar(String value) {
        String trimmed = value.trim();
        int commentStart = trimmed.indexOf(" #");
        if (commentStart >= 0) {
            trimmed = trimmed.substring(0, commentStart).trim();
        }
        if ((trimmed.startsWith("\"") && trimmed.endsWith("\""))
                || (trimmed.startsWith("'") && trimmed.endsWith("'"))) {
            return trimmed.substring(1, trimmed.length() - 1);
        }
        return trimmed;
    }

    private List<String> determineChangedFiles() throws IOException, InterruptedException {
        if ("schedule".equals(env("EVENT_NAME"))) {
            System.out.println("Scheduled build detected; falling back to all sitemap URLs.");
            return List.of();
        }

        String before = env("BEFORE_SHA");
        String after = env("AFTER_SHA");
        if (after.isBlank()) {
            after = "HEAD";
        }
        if (before.isBlank() || before.matches("^0+$")) {
            System.out.println("No usable BEFORE_SHA found; falling back to all sitemap URLs.");
            return List.of();
        }

        Process process = new ProcessBuilder("git", "diff", "--name-only", before, after)
                .redirectErrorStream(true)
                .start();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        int exit = process.waitFor();
        if (exit != 0) {
            System.out.printf("git diff failed with exit code %d; falling back to all sitemap URLs.%n%s%n", exit, output);
            return List.of();
        }
        return output.lines()
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .sorted()
                .toList();
    }

    private LinkedHashSet<String> fetchSitemapUrls(URI siteUrl) throws Exception {
        URI sitemap = siteUrl.resolve("/sitemap.xml");
        HttpRequest request = HttpRequest.newBuilder(sitemap)
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("Could not fetch sitemap " + sitemap + ": HTTP " + response.statusCode());
        }

        LinkedHashSet<String> urls = parseSitemapWithXml(response.body());
        if (urls.isEmpty()) {
            urls = parseSitemapWithRegex(response.body());
        }
        System.out.printf("Fetched %d URL(s) from %s.%n", urls.size(), sitemap);
        return urls;
    }

    private LinkedHashSet<String> parseSitemapWithXml(String body) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        var document = factory.newDocumentBuilder()
                .parse(new java.io.ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
        var locs = document.getElementsByTagNameNS("*", "loc");
        LinkedHashSet<String> urls = new LinkedHashSet<>();
        for (int i = 0; i < locs.getLength(); i++) {
            urls.add(normalizeUrl(locs.item(i).getTextContent()));
        }
        return urls;
    }

    private LinkedHashSet<String> parseSitemapWithRegex(String body) {
        LinkedHashSet<String> urls = new LinkedHashSet<>();
        Matcher matcher = LOC_PATTERN.matcher(body);
        while (matcher.find()) {
            urls.add(normalizeUrl(matcher.group(1)));
        }
        return urls;
    }

    private boolean isSharedRenderingFile(String changedFile) {
        return SHARED_RENDERING_FILES.contains(changedFile)
                || changedFile.startsWith("_includes/")
                || changedFile.startsWith("_layouts/")
                || changedFile.startsWith("_sass/");
    }

    private Optional<String> mapChangedFileToUrl(String changedFile, URI siteUrl) throws IOException {
        if (changedFile.startsWith("_posts/")) {
            return mapPost(changedFile, siteUrl);
        }
        if (changedFile.startsWith("_talks/")) {
            return mapCollectionFile(changedFile, "_talks/", "/talks/", siteUrl);
        }
        if (changedFile.startsWith("_people/")) {
            return mapCollectionFile(changedFile, "_people/", "/people/", siteUrl);
        }
        if (isRootPage(changedFile)) {
            return mapRootPage(changedFile, siteUrl);
        }
        return Optional.empty();
    }

    private Optional<String> mapPost(String changedFile, URI siteUrl) throws IOException {
        Optional<String> permalink = readPermalink(Path.of(changedFile));
        if (permalink.isPresent()) {
            return Optional.of(toAbsoluteUrl(siteUrl, permalink.get()));
        }

        Matcher matcher = POST_FILE.matcher(changedFile);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        return Optional.of(toAbsoluteUrl(siteUrl, "/" + matcher.group(1) + "/" + matcher.group(2) + "/" + matcher.group(3)
                + "/" + matcher.group(4) + ".html"));
    }

    private Optional<String> mapCollectionFile(String changedFile, String prefix, String urlPrefix, URI siteUrl) throws IOException {
        Optional<String> permalink = readPermalink(Path.of(changedFile));
        if (permalink.isPresent()) {
            return Optional.of(toAbsoluteUrl(siteUrl, permalink.get()));
        }

        String slug = changedFile.substring(prefix.length()).replaceFirst("\\.md$", "");
        return Optional.of(toAbsoluteUrl(siteUrl, urlPrefix + slug + ".html"));
    }

    private Optional<String> mapRootPage(String changedFile, URI siteUrl) throws IOException {
        Path path = Path.of(changedFile);
        if (!Files.exists(path) || !hasFrontMatter(path)) {
            return Optional.empty();
        }

        Optional<String> permalink = readPermalink(path);
        if (permalink.isPresent()) {
            return Optional.of(toAbsoluteUrl(siteUrl, permalink.get()));
        }

        String fileName = path.getFileName().toString();
        if ("index.html".equals(fileName)) {
            return Optional.of(toAbsoluteUrl(siteUrl, "/"));
        }
        String stem = fileName.replaceFirst("\\.(md|html)$", "");
        return Optional.of(toAbsoluteUrl(siteUrl, "/" + stem + ".html"));
    }

    private boolean isRootPage(String changedFile) {
        return !changedFile.contains("/")
                && (changedFile.endsWith(".md") || changedFile.endsWith(".html"))
                && !changedFile.equals("README.md");
    }

    private Optional<String> readPermalink(Path path) throws IOException {
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        String content = Files.readString(path);
        Matcher matcher = FRONT_MATTER_PERMALINK.matcher(content);
        if (matcher.find()) {
            return Optional.of(matcher.group(1).trim());
        }
        return Optional.empty();
    }

    private boolean hasFrontMatter(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.findFirst().orElse("").trim().equals("---");
        }
    }

    private void submitIndexNow(URI siteUrl, String key, LinkedHashSet<String> urlsToSubmit) throws IOException, InterruptedException {
        String host = siteUrl.getHost();
        String keyLocation = toAbsoluteUrl(siteUrl, "/" + key + ".txt");
        String payload = """
                {
                  "host": "%s",
                  "key": "%s",
                  "keyLocation": "%s",
                  "urlList": [%s]
                }
                """.formatted(
                jsonEscape(host),
                jsonEscape(key),
                jsonEscape(keyLocation),
                toJsonArrayValues(urlsToSubmit)
        );

        HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.bing.com/indexnow"))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        int status = response.statusCode();
        System.out.printf("IndexNow response: HTTP %d for %d URL(s).%n", status, urlsToSubmit.size());
        if (status == 200 || status == 202) {
            return;
        }
        if (status == 429) {
            throw new IllegalStateException("IndexNow rate limit reached: HTTP 429.");
        }
        throw new IllegalStateException("IndexNow submission failed with HTTP " + status + ": " + response.body());
    }

    private String toJsonArrayValues(Set<String> urls) {
        return urls.stream()
                .map(url -> "\"" + jsonEscape(url) + "\"")
                .reduce((left, right) -> left + ", " + right)
                .orElse("");
    }

    private String toAbsoluteUrl(URI siteUrl, String pathOrUrl) {
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            return normalizeUrl(pathOrUrl);
        }
        String path = pathOrUrl.startsWith("/") ? pathOrUrl : "/" + pathOrUrl;
        return normalizeUrl(siteUrl.resolve(path).toString());
    }

    private String normalizeUrl(String url) {
        return url.trim().replace(" ", "%20");
    }

    private String stripTrailingSlash(String value) {
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

    private String jsonEscape(String value) {
        StringBuilder escaped = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"' -> escaped.append("\\\"");
                case '\\' -> escaped.append("\\\\");
                case '\b' -> escaped.append("\\b");
                case '\f' -> escaped.append("\\f");
                case '\n' -> escaped.append("\\n");
                case '\r' -> escaped.append("\\r");
                case '\t' -> escaped.append("\\t");
                default -> {
                    if (c < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) c));
                    } else {
                        escaped.append(c);
                    }
                }
            }
        }
        return escaped.toString();
    }

    private String env(String name) {
        return Optional.ofNullable(System.getenv(name)).orElse("").trim();
    }
}
