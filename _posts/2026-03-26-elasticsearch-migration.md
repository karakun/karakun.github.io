---
layout: post
title: 'Migrating from Elasticsearch 7.17 to 8.19: A Practical Guide'
seo_title: 'Elasticsearch 7.17 to 8.x Migration Guide'
description: 'Practical guide to migrating from Elasticsearch 7.17 to 8.x. Learn how to replace HLRC, adopt the Java API Client, update security settings, and handle breaking changes.'
authors: [ 'jatin' ]
featuredImage: 'elastic'
excerpt: 'Migrating from Elasticsearch 7.17 to 8.x requires more than a version upgrade. This guide explains how to replace HLRC with the Java API Client, adapt to security defaults, and handle breaking changes in production systems.'
permalink: '/2026/03/26/elasticsearch-7-to-8-migration-guide.html'
categories: [ Elasticsearch, Search ]
header:
  image: 'books'
---

Migrating from Elasticsearch 7.17 to 8.x introduces significant changes in client APIs, security defaults, and index management. 
This article provides a practical migration guide, covering the transition from HLRC to the Java API Client, structured error handling, composable index templates, and production-ready testing strategies.

---

## Table of Contents

* [Why Upgrade to Elasticsearch 8.x Now?](#why-upgrade-elasticsearch-8)
* [Elasticsearch Migration Overview](#migration-overview)
* [Replacing HLRC with the Elasticsearch Java API Client](#replacing-hlrc)
* [Structured Error Handling in Elasticsearch Java Client](#structured-error-handling)
* [Elasticsearch 8 Index Templates and Mappings Changes](#elasticsearch-8-index-templates-mapping-changes)
* [Bulk Operations and Response Handling in Elasticsearch 8 Java Client](#bulk-operations-response-handling)
* [Testing Elasticsearch 8 with Testcontainers](#elasticsearch-8-testing-testcontainers)
* [Spring Boot Elasticsearch Health Indicator Migration](#spring-boot-health-indicator)
* [Administrative Operations](#administrative-operations)
* [Elasticsearch Migration Checklist (7.17 to 8.x)](#elasticsearch-migration-checklist)
* [Key Lessons from the Migration](#lessons-learned-from-migration)
* [Elasticsearch Migration Resources](#elasticsearch-migration-resources)
* [Let's connect](#cta)

---

Elasticsearch 7.x has reached end-of-life, with maintenance ending in April 2025 and support ending in January 2026, prompting many teams to migrate to version 8.x. 
This migration is more than a simple version bump—it requires rethinking how your Java application interacts with Elasticsearch. 
The Java High Level REST Client (HLRC), the primary client library for ES 7.x, is now deprecated in favor of a completely redesigned Java API Client that embraces modern patterns such as builders, functional composition, and strong typing.

This article documents our journey migrating a production Spring Boot application from Elasticsearch 7.17 to 8.19.3, covering the key technical challenges, code transformations, and lessons learned along the way.

## <a name="why-upgrade-elasticsearch-8"></a> Why Upgrade Now?

Beyond the requirement to remain on a supported version, Elasticsearch 8.19 brings:

* **Security by default:** TLS and basic authentication are now enabled out of the box
* **Performance improvements:** Leveraging Lucene 9.12.2 with numerous bug fixes and optimizations
* **Modern API design:** The new Java client offers type-safe requests and responses, reducing runtime errors
* **Future-proofing:** Access to vector search, inference APIs, and other 8.x-exclusive features

Most importantly, continuing with HLRC means living with a frozen, unmaintained codebase while the ecosystem moves forward.

## <a name="migration-overview"></a> The Migration Landscape

Our migration touched four major areas:

1. **Client library replacement:** Swapping HLRC for the new typed Java API Client
2. **Security configuration:** Adapting to Elasticsearch's security-first defaults
3. **Index templates and mappings:** Updating to composable templates and changed analyzer semantics
4. **Error handling:** Reworking exception handling for the new client's error model

## <a name="replacing-hlrc"></a> Part 1: Replacing the Java Client

### Dependency Updates

The first step was updating our Gradle dependencies:

```gradle
// Old (ES 7.17)
implementation "org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.0"

// New (ES 8.19)
implementation "org.elasticsearch.client:elasticsearch-rest-client:8.19.3"
implementation "co.elastic.clients:elasticsearch-java:8.19.3"
implementation "jakarta.json:jakarta.json-api:2.1.1"
```

Note that the new client requires a JSON-P implementation. 
We chose Jackson's JSON-P mapper for seamless integration with our existing Jackson setup.

### Client Initialization

The old HLRC used a simple builder pattern:

```java
// ES 7.17 approach
RestHighLevelClient client = new RestHighLevelClient(
    RestClient.builder(
        new HttpHost("localhost", 9200, "http")
    )
);
```

The new client separates concerns between transport and the client itself:

```java
// ES 8.19 approach
RestClient restClient = RestClient.builder(
    new HttpHost("localhost", 9200, "http")
).build();

ElasticsearchTransport transport = new RestClientTransport(
    restClient, 
    new JacksonJsonpMapper()
);

ElasticsearchClient client = new ElasticsearchClient(transport);
```

### Authentication and Security

Elasticsearch 8.x enables security by default. 
For production environments, we added basic authentication:

```java
RestClientBuilder builder = RestClient.builder(httpHosts)
    .setRequestConfigCallback(cfg -> 
        cfg.setSocketTimeout(timeoutInSeconds * 1000)
    );

if (authEnabled) {
    CredentialsProvider credentials = new BasicCredentialsProvider();
    credentials.setCredentials(
        AuthScope.ANY,
        new UsernamePasswordCredentials(username, password)
    );
    builder.setHttpClientConfigCallback(cb -> 
        cb.setDefaultCredentialsProvider(credentials)
    );
}
```

For local development and temporary flexibility, we have the option to disable the security in `docker-compose.yml`:

```yaml
elasticsearch:
  image: docker.elastic.co/elasticsearch/elasticsearch:8.19.3
  environment:
    - xpack.security.enabled=false
    - discovery.type=single-node
  ports:
    - "9200:9200"
```

### Request/Response Pattern Changes

The new client's biggest shift is from generic maps to strongly-typed builders and response objects.

**Old search operation (ES 7.17):**

```java
SearchRequest request = new SearchRequest(indexName);
SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
sourceBuilder.query(QueryBuilders.matchQuery("field", "value"));
request.source(sourceBuilder);

SearchResponse response = client.search(request, RequestOptions.DEFAULT);
SearchHit[] hits = response.getHits().getHits();
```

**New search operation (ES 8.19):**

```java
SearchResponse<MyDocument> response = client.search(s -> s
    .index(indexName)
    .query(q -> q
        .match(m -> m
            .field("field")
            .query("value")
        )
    ),
    MyDocument.class
);

List<Hit<MyDocument>> hits = response.hits().hits();
for (Hit<MyDocument> hit : hits) {
    MyDocument doc = hit.source();
    // Strongly typed access to your document
}
```

The functional builder pattern takes some getting used to, but it eliminates entire categories of errors by enforcing type safety at compile time.

### Handling Field Values

One subtle change: the new client introduces `FieldValue` as a wrapper for all dynamic values in queries, aggregations, and scripts.

**Search-after tokens** must now be explicitly converted:

```java
// Old
searchAfter(Arrays.asList("value1", 123, timestamp))

// New
searchAfter(Arrays.asList(
    FieldValue.of("value1"),
    FieldValue.of(123),
    FieldValue.of(timestamp.toEpochMilli())
))
```

**Script parameters** need similar wrapping when passing variables to Painless scripts in aggregations or updates:

```java
// New
Map<String, JsonData> params = Map.of(
    "boost", JsonData.of(1.5),
    "field_value", JsonData.of("some_text")
);
```

## <a name="structured-error-handling"></a> Part 2: Structured Error Handling

The HLRC threw generic `ElasticsearchException` instances that required parsing error messages as strings. 
The new client provides structured error information through `ErrorCause`.

We created an enum to classify error types systematically:

```java
public enum ElasticsearchErrorKind {
    INDEX_NOT_FOUND("index_not_found_exception", false),
    INDEX_CLOSED("index_closed_exception", false),
    CLUSTER_BLOCK("cluster_block_exception", true),
    VERSION_CONFLICT("version_conflict_engine_exception", true),
    ES_REJECTED_EXECUTION("es_rejected_execution_exception", true),
    TIMEOUT("timeout_exception", true),
    UNKNOWN("_unknown", false);

    private final String type;
    private final boolean recoverable;

    // Constructor and methods...

    public static ElasticsearchErrorKind fromErrorCause(ErrorCause cause) {
        if (cause == null) return UNKNOWN;
        String type = cause.type();
        
        // Check nested root causes
        List<ErrorCause> rootCause = cause.rootCause();
        if (rootCause != null && !rootCause.isEmpty()) {
            type = rootCause.get(0).type();
        }
        
        return fromType(type);
    }
}
```

This allowed us to build intelligent retry logic:

```java
try {
    return operation.execute();
} catch (ElasticsearchException e) {
    ElasticsearchErrorKind kind = 
        ElasticsearchErrorKind.fromErrorCause(e.error());
    
    if (kind.isRecoverable() && retryCount < maxRetries) {
        Thread.sleep(backoffMs);
        return retryOperation(operation, retryCount + 1);
    }
    throw e;
}
```

## <a name="elasticsearch-8-index-templates-mapping-changes"></a> Part 3: Index Templates and Mappings

Elasticsearch 8.x introduces **composable index templates**, replacing the legacy template format. 
While our templates were relatively straightforward, we had to:

1. **Update analyzer configurations**: Some token filters changed names (e.g., `french_elision` syntax)
2. **Switch to explicit normalizers**: Keyword fields now use explicit normalizer definitions
3. **Fix deprecated syntax**: Date histogram intervals like `1M` must now be spelled out as `month`

Example template structure for ES 8.19:

```json
{
  "index_patterns": ["my-index-*"],
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 1,
      "refresh_interval": "1s",
      "analysis": {
        "normalizer": {
          "lowercase_normalizer": {
            "type": "custom",
            "filter": ["lowercase", "asciifolding"]
          }
        },
        "analyzer": {
          "custom_analyzer": {
            "type": "custom",
            "tokenizer": "standard",
            "filter": ["lowercase", "stop", "synonym_graph"]
          }
        }
      }
    },
    "mappings": {
      "properties": {
        "title": {
          "type": "text",
          "analyzer": "custom_analyzer"
        },
        "status": {
          "type": "keyword",
          "normalizer": "lowercase_normalizer"
        }
      }
    }
  },
  "version": 1
}
```

### Template Versioning Strategy

We implemented automatic template updates by tracking versions. 
While template versioning existed in ES 7.x, the API for accessing version metadata changed with the new client.

**Old approach (ES 7.17 with HLRC):**

```java
GetIndexTemplatesResponse response = client.indices()
    .getIndexTemplate(request, RequestOptions.DEFAULT);

Long remoteVersion = response.getIndexTemplates().get(0).version();
```
**New approach (ES 8.19 with new client):**

```java
GetIndexTemplateResponse response = client.indices()
        .getIndexTemplate(request);

Long remoteVersion = response.indexTemplates().stream()
        .findFirst()
        .map(t -> t.indexTemplate().version())  // Strongly typed access
        .orElse(0L);

long localVersion = extractVersionFromTemplate(templateContent);

if (localVersion > remoteVersion) {
    client.indices().putIndexTemplate(t -> t
        .name(templateName)
        .indexPatterns(patterns)
        .template(templateBody)
    );
}
```

## <a name="bulk-operations-response-handling"></a> Part 4: Bulk Operations and Response Handling

Bulk indexing saw significant API changes. 
The new client provides cleaner separation between successful and failed operations:

```java
BulkResponse response = client.bulk(b -> {
    for (Document doc : documents) {
        b.operations(op -> op
            .index(idx -> idx
                .index(indexName)
                .id(doc.getId())
                .document(doc)
            )
        );
    }
    return b;
});

if (response.errors()) {
    for (BulkResponseItem item : response.items()) {
        if (item.error() != null) {
            ElasticsearchErrorKind kind = 
                ElasticsearchErrorKind.fromErrorCause(item.error());
            
            if (kind == ElasticsearchErrorKind.VERSION_CONFLICT) {
                // Handle version conflict specifically
            } else {
                logger.error("Bulk operation failed for {}: {}", 
                    item.id(), item.error().reason());
            }
        }
    }
}
```

## <a name="elasticsearch-8-testing-testcontainers"></a> Part 5: Testing Infrastructure

We updated our testing stack to use Elasticsearch 8 Testcontainers:

```java
@Container
static ElasticsearchContainer elasticsearchContainer = 
    new ElasticsearchContainer(
        "docker.elastic.co/elasticsearch/elasticsearch:8.19.3"
    )
    .withEnv("xpack.security.enabled", "false")
    .withEnv("ES_JAVA_OPTS", "-Xms512m -Xmx512m");

@DynamicPropertySource
static void elasticsearchProperties(DynamicPropertyRegistry registry) {
    registry.add("elasticsearch.nodes[0].host", 
        elasticsearchContainer::getHost);
    registry.add("elasticsearch.nodes[0].port", 
        elasticsearchContainer::getFirstMappedPort);
}
```

### Testing Pitfall: Wildcard Deletes

Elasticsearch 8 rejects wildcard index deletions by default. Our test cleanup code needed updating:

```java
// Old approach (fails in ES 8)
client.indices().delete(d -> d.index("test-*"));

// New approach
GetIndexResponse indices = client.indices().get(g -> g.index("test-*"));
for (String indexName : indices.result().keySet()) {
    client.indices().delete(d -> d.index(indexName));
}
```

## <a name="spring-boot-health-indicator"></a> Part 6: Health Indicator Updates

Spring Boot's default `ElasticsearchHealthIndicator` still relies on HLRC. 
We replaced it with a custom implementation:

```java
@Component("elasticsearch")
public class CustomElasticsearchHealthIndicator implements HealthIndicator {
    
    private final ElasticsearchClient client;
    
    @Override
    public Health health() {
        try {
            if (!client.ping().value()) {
                return Health.down()
                    .withDetail("error", "Ping failed")
                    .build();
            }
            
            RestClient lowLevel = 
                ((RestClientTransport) client._transport()).restClient();
            Request req = new Request("GET", "/_cluster/health");
            Response resp = lowLevel.performRequest(req);
            
            JsonNode json = mapper.readTree(resp.getEntity().getContent());
            String status = json.path("status").asText("red");
            
            boolean up = "green".equalsIgnoreCase(status) || 
                        "yellow".equalsIgnoreCase(status);
            
            return (up ? Health.up() : Health.down())
                .withDetail("status", status)
                .build();
                
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
```

Don't forget to disable the default indicator in `application.yml`:

```yaml
management:
  health:
    elasticsearch:
      enabled: false
```

## <a name="administrative-operations"></a> Part 7: Administrative Operations

A critical change in ES 8 involves the `ignore_unavailable` parameter. 
Previously, setting this to `true` for admin operations would silently succeed even if indices didn't exist—useful for idempotent cleanup scripts but dangerous for user-triggered actions.

We now explicitly set `ignore_unavailable=false` for user-facing operations:

```java
client.indices().delete(d -> d
    .index(indexName)
    .ignoreUnavailable(false)  // Fail loudly if index doesn't exist
);
```

This surfaces proper errors to the UI when users attempt invalid operations.

## <a name="elasticsearch-migration-checklist"></a> Migration Checklist

Based on our experience, here's a practical checklist for teams undertaking this migration:

### Pre-Migration
- [ ] Audit all usages of `RestHighLevelClient` in your codebase
- [ ] Document custom analyzer and token filter configurations
- [ ] Review security requirements (TLS certificates, authentication)
- [ ] Plan for breaking changes in REST API responses

### Code Changes
- [ ] Update Gradle/Maven dependencies to ES 8.19.3
- [ ] Replace `RestHighLevelClient` with `ElasticsearchClient`
- [ ] Refactor all search operations to use fluent builders
- [ ] Wrap dynamic values with `FieldValue` or `JsonData`
- [ ] Update bulk operation handling for new response structure
- [ ] Implement structured error classification
- [ ] Replace Spring Boot's default Elasticsearch health indicator

### Configuration
- [ ] Update index templates to composable format
- [ ] Validate and update analyzer configurations
- [ ] Configure authentication for production environments
- [ ] Disable security for local development (if appropriate)
- [ ] Set explicit `ignore_unavailable` values for admin operations

### Testing
- [ ] Upgrade Testcontainers to use Elasticsearch 8.19.3
- [ ] Fix test cleanup to avoid wildcard deletes
- [ ] Add tests for highlighting, aggregations, and spellcheck
- [ ] Verify security configuration in integration tests
- [ ] Test error handling for all error kinds

### Deployment
- [ ] Update Docker Compose files for local development
- [ ] Plan production rollout (rolling restart vs. reindex)
- [ ] Monitor cluster health during initial deployment
- [ ] Verify application logs for migration-related warnings

## <a name="lessons-learned-from-migration"></a> Lessons Learned

1. **Strong typing prevents runtime surprises**: While the functional builder syntax felt verbose initially, it caught numerous bugs at compile time that would have been production incidents.

2. **Error handling needs a strategy**: Don't treat all Elasticsearch exceptions the same. Classify them, log context-rich messages, and implement smart retry logic for recoverable errors.

3. **Security isn't optional anymore**: ES 8's security-first approach is the right move, but it requires thoughtful configuration management across environments.

4. **Test with the real version**: Don't rely on in-memory fake implementations. Use Testcontainers with the exact Elasticsearch version you'll run in production.

5. **Index templates matter**: Small changes in analyzer behavior can subtly break search quality. Diff your templates carefully and test with production-like data volumes.

## Looking Forward

With Elasticsearch 8.19 in place, we're positioned to explore capabilities that were painful or impossible in 7.x:

- **Vector search** for semantic similarity
- **Inference endpoints** for ML-powered features
- **Runtime fields** for schema flexibility
- **Improved aggregation performance** for analytics workloads

This type of migration is substantial, typically touching 50-150+ files depending on your codebase size. 
But the result is a more maintainable, type-safe, and future-proof integration with Elasticsearch.

## <a name="cta"></a> Let's Connect!

Do you have questions about our migration to Elasticsearch 8.19? 
Or would you like to discuss the best migration path for your installation?
Did you already migrate and experienced other pitfalls?
[Feel free to reach out.](/people/jatin) 
I’m always happy to exchange knowledge, ideas, and experiences.


## <a name="elasticsearch-migration-resources"></a> Resources

- [Official ES 8.19 Migration Guide](https://www.elastic.co/guide/en/elasticsearch/reference/8.19/migrating-8.19.html){:target="_blank"}
- [Java API Client Documentation](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.19/index.html){:target="_blank"}
- [Migrating from HLRC](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.19/migrate-hlrc.html){:target="_blank"}
