---
layout: article
title: "PUnit – Probabilistic Testing for Non-Deterministic AI Systems"
seo_title: "PUnit – Probabilistic Testing for Non-Deterministic AI Systems"
description: "PUnit is an open-source JUnit 5 extension for probabilistic testing of AI systems, LLM applications, and non-deterministic software behaviour."
order: "17"
headerText:
permalink: /punit/
header:
  image: punit
  text: Testing <span class="my-karakun">non-deterministic systems</span> with confidence
nav:
  title: PUnit
  bottom: false
---

## Testing Non-Deterministic Systems with Confidence

A conventional unit test pretends certainty: one run, one verdict.
AI systems do not behave that way.

PUnit is an experimentation and probabilistic testing framework for the regression testing of non-deterministic systems and services.

[View on GitHub](https://github.com/javai-org/punit){:target="_blank"}

---

## The Problem

Deterministic testing assumes that identical input always produces identical output.

Modern AI systems increasingly break this assumption.

LLM-based applications, recommendation engines, retrieval pipelines, and probabilistic models often behave differently between executions — even when the input remains unchanged.
LLM-based systems require statistical testing approaches instead of relying solely on deterministic assertions.

Traditional assertions like:

```java
assertEquals(expected, actual);
```

are often no longer sufficient.

The real question becomes:

> Is the system behaving acceptably within statistically defined boundaries?

PUnit helps teams answer exactly this question.

---

## What is PUnit?

PUnit is an experimentation and probabilistic testing framework for the regression testing of non-deterministic systems and services.

It extends JUnit 5 with statistical testing capabilities for AI systems, LLM applications, and stochastic software behaviour.

PUnit integrates directly into existing Java testing and CI/CD workflows.

PUnit is built around three core ideas:

### Measure

Assert statistical behaviour instead of relying solely on deterministic assertions.

### Regress

Detect statistically significant degradation before it reaches production.

### Trust

Generate measurable and reproducible evidence for AI-enabled systems.

## Example

In practice, probabilistic tests can look like this:


```java
  @ProbabilisticTest
      void serviceGreetsConsistently() {
          // sampling declares an SLA and a condition that must be satisified
          var greetingSampling =
                  Contract.<String, String>inline()
                          .returning(GreetingServiceTest::greet) // call the actual service
                          .passRate(0.95) // define minimum pass rate
                          .contractRef(SLA, "Greeting Service SLA v1.0 §2.1") // document SLA provenance
                          .satisfies("greeting is 'friendly'", GreetingServiceTest::greetingIsFriendly)
                          .sampling(50,  List.of("Mike", "Elke", "Helena")); // 50 samples; input selected round-robin style

          // a unit test expressed using the sampling
          PUnit.testing(greetingSampling)
                  .assertPasses(); // passes as long as ≥95% of greetings satisfy the 'friendly' test
      }
```


PUnit transforms repeated executions into statistical evidence instead of relying on single deterministic outcomes.

---

## Learn More

- [PUnit on GitHub](https://github.com/javai-org/punit){:target="_blank"}
- [Releases](https://github.com/javai-org/punit/releases){:target="_blank"}

---

## About the Project

PUnit is an open-source project exploring probabilistic approaches to software testing for non-deterministic systems.

The project is open source and actively evolving.
