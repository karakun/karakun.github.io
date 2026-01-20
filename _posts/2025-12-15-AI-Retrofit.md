---
layout: post
title: 'Retrofitting an Existing Spring Application with AI Capabilities Using Spring AI'
authors: [ 'Hannes' ]
featuredImage: 'RetrofittingAI'
excerpt: 'Adding AI-powered capabilities to existing enterprise systems is often complex, especially when modernization or migration to new frameworks is not immediately feasible. However, it is possible to retrofit an application with a natural language interface while keeping the original business logic untouched.'
permalink: '/2025/12/15/Retrofitting-AI.html'
categories: [ Ai, Nlp, Java, Spring, Spring boot, Search ]
header:
  text: Retrofitting an Existing Spring Application with AI Capabilities Using Spring AI
  image: 'books'
---

Adding AI-powered capabilities to existing enterprise systems is often complex, especially when modernization or migration to new frameworks is not immediately feasible. However, it is possible to retrofit an application with a natural language interface while keeping the original business logic untouched. 


This post walks through how to integrate **AI-based request generation** for an existing search API, focusing on **structured outputs**, **tooling**, and **validation**, while discussing some real-world obstacles. This example represents a simpler case where tool calls are fast and have no side effects. It allows us to focus on the interaction between the LLM, the tools, and the structured output without introducing external dependencies, complex state handling, or repeated tool calls. Complex scenarios involving interactions with tools that have side effects will be covered in a follow-up article.

# Table of Contents

* [1. The Idea: Let the AI Build Your Request Objects](#Idea)
* [2. Technology Setup](#Setup)
  * [Gradle Dependencies](#Dependencies)
  * [Configuration](#Configuration)
* [3. Converting Prompts into Valid Search Requests](#Prompts)
* [4. Adding Domain-Specific Tool](#Tools)
  * [Why Tools Matter](#ToolsMatter)
* [5. Testing AI-Assisted Code](#Testing)
* [6. Business Value and Real-World Constraints](#Value)
  * [Practical Constraints](#Constraints)
* [7. Takeaways](#Takeaways)
* [8. We Are Here to Help](#Help)

# <a name="Idea"></a> 1. The Idea: Let the AI Build Your Request Objects

Existing systems often have well-defined APIs for search, analytics, or operations. They usually expect strongly typed input models, such as a `SearchRequestModel`. Retrofitting them for AI input means giving the user a natural language interface and letting the LLM create valid request objects automatically.

With **Spring AI**, this becomes practical through:
- **Structured output handling** – the LLM generates JSON matching a given class.
- **Tools** – annotated functions that the LLM can call to retrieve external data or validate intermediate results.

This approach bridges free-text prompts with typed data structures, enabling human-like queries while keeping the backend stable.

---

# <a name="Setup"></a> 2. Technology Setup

For this article, we use our own [HIBU platform](https://hibu-platform.com/en/home/) as an example. HIBU provides an API library that includes request and response classes annotated with OpenAPI metadata, which makes it well suited for generating structured outputs.

### <a name="Dependencies"></a> Gradle Dependencies

You can retrofit without heavy dependencies, provided your project already runs on **Spring Boot 3.x** (required for Spring AI) or you are maintaining this code in a separate module/project.

```groovy
dependencies {
    implementation platform("org.springframework.boot:spring-boot-dependencies:3.5.7")

    implementation 'org.springframework.boot:spring-boot-starter-web'

    implementation platform("org.springframework.ai:spring-ai-bom:1.0.3")
    implementation 'org.springframework.ai:spring-ai-starter-model-openai'

    implementation "com.karakun.hibu:hibu-api:3.6.1"

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.assertj:assertj-core'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

### <a name="Configuration"></a> Configuration

```yaml
spring:
  ai:
    openai:
      api-key: add your key
      chat:
        options:
          model: gpt-4.1-mini
          temperature: 0
```
A low temperature ensures "deterministic" output for testing and validation.

# <a name="Prompts"></a> 3. Converting Prompts into Valid Search Requests

The main service delegates prompt interpretation to the LLM. The `ChatClient` and your own `@Tool` definitions drive this.

```java
package com.karakun.hibu.promtassistance;

@Service
public class AiPromptAssistanceService {
    private final ChatClient chatClient;
    private final HibuTools hibuTools;

    public AiPromptAssistanceService(ChatClient chatClient, HibuTools hibuTools) {
        this.chatClient = chatClient;
        this.hibuTools = hibuTools;
    }

    public SearchRequestModel getRequestFromPrompt(String prompt, List<String> facetFields, String container) {
        return chatClient.prompt()
            .system(u -> u.text("""
                    Task: Given a user prompt, produce a SearchRequest JSON for our search API.
                    Use synonyms and simple_query_string syntax for "query".
                    Allowed filter fields: {filterFields}
                    Use the tools to fetch keyword filter values and validate the result object.
                    """
                )
                .param("filterFields", String.join(",", facetFields))
                .param("container", container)
            )
            .tools(hibuTools)
            .user(u -> u.text(prompt))
            .call()
            .entity(SearchRequestModel.class);
    }
}
```

This example uses the Spring AI fluent API. The LLM receives a system prompt describing how to construct a valid `SearchRequestModel`.
The `.entity(SearchRequestModel.class)` call ensures the response is automatically deserialized and validated against the record definition. 
For this, Spring AI processes existing annotations like `@Nullable`, `@Schema`, `@JsonProperty`, and many more. 

**Note:** The actual system prompt most likely contains many more instructions and restrictions for the LLM, such as “DO NOT invent or change filter values.” I have kept it brief for this article.

# <a name="Tools"></a> 4. Adding Domain-Specific Tools

The `@Tool` annotation turns normal Spring beans into callable LLM functions. In this example, two tools support validation and controlled value selection.

```java
package com.karakun.hibu.promtassistance;

@Service
public class HibuTools {
    private final RestClient client;
    private final String filtersUrl;
    private final Validator validator;

    public HibuTools(Validator validator, RestClient.Builder builder,
                     @Value("${tools.hibu.fetchAvailableFilterValuesUrl}") String fetchUrl) {
        this.filtersUrl = fetchUrl;
        this.validator = validator;
        this.client = builder.build();
    }

    @Tool(name = "isValidSearchRequestModel",
          description = "Validate JSON against SearchRequestModel class.")
    public String isValidSearchRequestModel(String searchRequestModel) {
        try {
            SearchRequestModel model = new ObjectMapper().readValue(searchRequestModel, SearchRequestModel.class);
            Set<ConstraintViolation<SearchRequestModel>> violations = validator.validate(model);
            if (!violations.isEmpty()) {
                return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
            }
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
        return "true";
    }

    @Tool(name = "fetchAvailableFilterValues",
          description = "Fetches available filter values for a given keyword-based field.")
    public List<String> fetchAvailableFilterValues(@NotNull String container, @NotNull String fieldName) {
        // Query the existing API
        SearchRequest request = new SearchRequest(container, "", List.of(), null, Map.of(), 0, 0, null, false, null, List.of(fieldName));
        var type = new ParameterizedTypeReference<SearchResponse<ObjectMapCustomData>>() {};
        var resp = client.post().uri(filtersUrl).body(request).retrieve().body(type);
        if (resp == null || resp.getFacets() == null) return List.of();
        return resp.getFacets().stream()
            .filter(f -> f.getFieldName().equals(fieldName))
            .flatMap(f -> f.getValues().stream().map(FacetValue::getValue))
            .toList();
    }
}
```

### <a name="ToolsMatter"></a> Why Tools Matter

* The validation tool (isValidSearchRequestModel) allows the LLM to self-correct invalid JSON by reattempting generation. 
* The fetch tool limits the model to known keyword values, avoiding invented filters and producing robust output.

These patterns greatly reduce runtime errors and make the integration resilient to AI hallucinations.

# <a name="Testing"></a> 5. Testing AI-Assisted Code

LLMs like ChatGPT do not guarantee deterministic replay, so testing expectations is essential.
You can use mocks to isolate behavior and assert that generated requests meet certain structural and semantic criteria.

```java
package com.karakun.hibu.promtassistance;

public class AiPromptAssistanceServiceTest extends SpringBaseTest {

    @Autowired
    private AiPromptAssistanceService service;

    @MockitoBean
    private HibuTools mockedHibuTools;

    @Test
    public void getRequestFromPrompt() {
        when(mockedHibuTools.fetchAvailableFilterValues(any(), any()))
            .thenReturn(List.of("Karakun AG", "Another AG"));

        SearchRequestModel result = service.getRequestFromPrompt(
            "Search for all company presentations of Karakun created in the last three months of each of the last five years.",
            List.of("metadata.creation_date", "metadata.companyName_string"),
            "foo");

        verify(mockedHibuTools).fetchAvailableFilterValues("foo", "metadata.companyName_string");
        assertThat(result.query()).contains("presentation");
        assertThat(result.filters()).containsKey("metadata.creation_date");
        assertThat(result.filters().get("metadata.creation_date")).hasSize(5);
    }
}
```

**Tip:** Always verify tool calls and expected key fields.
This ensures your prompt and model configuration are aligned with predictable outcomes.

# <a name="Value"></a> 6. Business Value and Real-World Constraints

Adding AI features on top of existing systems provides several advantages:

* **Faster experimentation** – you can test AI-driven interfaces without refactoring the core logic.
* **Lower risk** – tools isolate the AI layer, so failures do not affect critical paths.
* **Improved UX** – users interact in natural language while the backend remains unchanged.

### <a name="Constraints"></a> Practical Constraints

* Spring Boot 3.x required: Spring AI only supports applications running on the latest generation. Legacy projects may require upgrade work before integration or maintain such a retrofitting component in a separate module/project.
* Validation tools improve reliability: Without them, structured output tends to break on minor syntax issues.
* Model selection and cost: Smaller models like gpt-4.1-mini often suffice. Larger ones may be cost-prohibitive for frequent use.
* Testing discipline: Because LLMs behave probabilistically, regression tests are critical to detect subtle prompt changes or API behavior shifts. At Karakun, we are building an infrastructure that enables consistent testing across multiple models and helps us curate a maintainable collection of prompt patterns and best practices.

# <a name="Takeaways"></a> 7. Takeaways

* Retrofitting an existing Spring application with AI features is possible and often valuable. 
* Spring AI’s Tools and Structured Output simplify controlled AI integration.
* Custom validation tools make AI-generated structures robust and retryable. 
* Expect some migration effort to Spring Boot 3.x and ensure your LLM configuration is "deterministic" for repeatable tests. 
* Test, observe, and iterate - AI integration is not a one-time setup but a living process.

By combining Spring AI, careful tool definitions, and disciplined validation, teams can extend legacy systems with intelligent interfaces while maintaining technical and business stability.

# <a name="Help"></a> 8. We Are Here to Help

While this example keeps things simple with side-effect-free tool calls, real-world applications often involve more complex integrations.
Integrating AI into existing software ecosystems requires more than technical know-how. It takes experience in balancing architecture, maintainability, and business goals.  


At [karakun.com](https://karakun.com), we help organizations analyze their current solutions and design the best way to integrate AI - whether that means lightweight retrofitting, full-stack modernization, or targeted use of AI capabilities.

If you are exploring how to bring intelligent features into your existing systems, reach out to us. Together we can identify where AI delivers real value without disrupting what already works.
