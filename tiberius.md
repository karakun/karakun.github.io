---
layout: article
title: "Tiberius – Security Testing for LLM Applications in Java"
seo_title: "Tiberius – Security Testing for LLM Applications in Java"
description: "Tiberius is an open-source Java library built on JUnit 5 and PUnit for probabilistic security testing of LLM applications: prompt injection, jailbreaks, guardrail validation, and bias testing."
order: "18"
headerText:
permalink: /tiberius/
header:
  image: tiberius
  text: Making <span class="my-karakun">LLM security</span> measurable and testable
nav:
  title: Tiberius
  bottom: false
---

## Security Testing for LLM Applications

Your LLM can pass every functional test and still write a phishing email.

Tiberius is an open-source Java library for testing LLM applications against prompt injection attacks, jailbreaks, and adversarial inputs.
It runs inside your existing JUnit 5 test suite.
No new frameworks.
No separate toolchains.
Just tests.

[View on GitHub](https://github.com/tiberius-security/tiberius){:target="_blank"}

---

## The Problem

LLM-based applications are customer-facing, embedded in enterprise Java systems, and work with sensitive user data.

Their attack surface is linguistic.
An attacker doesn't need a technical vulnerability.
Role-play framing, encoding, multi-turn escalation, or simply switching to an underrepresented language can be enough to bypass a system prompt or a guardrail.

At the same time, LLMs are non-deterministic by design.
The same attack may fail once and succeed on the next run.

A single pass/fail check like:

```java
assertEquals("safe", llm.respond(attack));
```

tells you what happened once, not what the system reliably does.

> How do you write a regression test for a system that is non-deterministic by design?

---

## What is Tiberius?

Tiberius brings security and safety testing for LLM applications into the standard Java testing workflow.

It is built on [PUnit](/punit/), the probabilistic testing extension for JUnit 5, and expresses security requirements as statistically grounded assertions instead of single-run verdicts.
Its attack datasets are inspired by and sourced from Praetorian's open-source LLM vulnerability scanner [Augustus](https://github.com/praetorian-inc/augustus){:target="_blank"}.

Tiberius is built around a few core ideas:

### Scan

Run 200+ adversarial probes across nine attack categories, mapped to the [OWASP Top 10 for LLM Applications](https://owasp.org/www-project-top-10-for-large-language-model-applications/){:target="_blank"}, against your model.

### Capture

Serialize the scan results into versioned JSON fixtures.
Attacks that got through become your stable ground truth for downstream regression tests, without re-querying the model.

### Validate

Test your guardrails against real attacks from those fixtures, tracking both missed attacks and falsely blocked legitimate requests.

### Assert Statistically

Run each probe many times with PUnit and fail the build when the attack success rate exceeds your threshold.

---

## Features

- **Probabilistic security contracts** – multi-trial scans and statistical assertions powered by PUnit
- **Fixture-based regression testing** – scan results stored as plain, shareable JSON
- **Guardrail testing** – including LangChain4J guardrails
- **Bias testing** – detect systematic skew across demographic or contextual variants
- **Model fingerprinting** – check whether your deployment leaks which model it runs on
- **Buff mutations** – encoding, rephrasing, and fictional framing to test evasion resistance; custom buffs included
- **Attack categories** – jailbreaks, prompt injection, encoding, data extraction, multi-turn attacks, format exploits, context manipulation, adversarial tokens, and evasion
- **Providers** – Ollama (local), OpenAI, Anthropic, and any OpenAI-compatible REST API
- **Spring Boot** – auto-configuration via `@Import(TiberiusAutoConfiguration.class)`

---

## Example

A probabilistic security contract runs every attack probe multiple times and asserts on the aggregated result:

```java
@Test
void probabilisticSecurityContract(TiberiusScanner scanner) {
    scanner.setGenerator(new OllamaGenerator("llama3.2"));

    ScanReport report = scanner.multiTrialScan()
        .samples(35)     // each attack probe executed 35 times
        .execute();

    // 0.0 means: across all 35 trials per probe, not a single attack got through
    assertThat(report.successRate()).isEqualTo(0.0);
}
```

Recording a scan as a reusable fixture:

```java
@ExtendWith({TiberiusExtension.class, FixtureExtension.class})
@CreateFixture("fixtures/baseline-scan.json")
class LLMSecurityScan {

    @Test
    void scanForVulnerabilities(TiberiusScanner scanner, FixtureContext fixture) {
        scanner.setGenerator(new OllamaGenerator("llama3.2"));
        ScanReport report = scanner.scan();
        fixture.record(report);
    }
}
```

Attacks that bypass your model don't just register as failures.
They become fixtures and feed directly back into guardrail validation.
The system becomes harder to break with every breach.

---

## Getting Started

Tiberius is available on Maven Central:

```xml
<dependency>
    <groupId>io.github.tiberius-security</groupId>
    <artifactId>tiberius</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

Requirements: Java 21+, JUnit 5.11+, Maven 3.8+ or Gradle 8+.

---

## Learn More

- [Testing the Untestable: Your LLM passes all Tests and Just Wrote a Phishing Email](/2026/07/20/llm-security-testing-java-tiberius.html) – in-depth article on the DevHub
- [Tiberius: A Security Testing Framework for LLM Applications in Java](https://foojay.io/today/tiberius-a-security-testing-framework-for-llm-applications-in-java/){:target="_blank"} – on foojay.io
- [Tiberius on GitHub](https://github.com/tiberius-security/tiberius){:target="_blank"}
- [Security Testing Guide](https://github.com/tiberius-security/tiberius/blob/main/docs/SECURITY_TESTING_GUIDE.md){:target="_blank"} · [Guardrails Testing](https://github.com/tiberius-security/tiberius/blob/main/docs/guardrails.md){:target="_blank"} · [LangChain4J Integration](https://github.com/tiberius-security/tiberius/blob/main/docs/langchain4j-guardrail-testing.md){:target="_blank"}
- [PUnit](/punit/) – the probabilistic testing foundation of Tiberius

---

## About the Project

Tiberius is an open-source project licensed under Apache 2.0, created by [Iryna Dohndorf](/people/iryna).

Contributions and domain-specific probe additions are very welcome.
The probe library improves with every real-world finding the community adds.
