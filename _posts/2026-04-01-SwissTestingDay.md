---
layout: post
title: 'Swiss Testing Day 2026 – Reflections on Testing AI and Non-Deterministic Systems'
seo_title: 'AI Testing for Non-Deterministic Systems'
description: 'Insights from Swiss Testing Day 2026 on AI testing, non-deterministic systems, and strategies for ensuring reliability in modern software engineering.'
authors: [ 'mike' ]
featuredImage: 'swisstestingday'
excerpt: 'How do you test non-deterministic AI systems? Insights from Swiss Testing Day 2026 on AI testing, reliability, and probabilistic validation.'
permalink: '/2026/04/02/Swiss-Testing-Day.html'
categories: [ Testing, AI ]
header:
  text: Insights from Swiss Testing Day 2026 on AI testing, reliability, and probabilistic validation.
  image: 'books'
---

At Karakun, we are closely following how software engineering evolves in the age of AI – especially when it comes to testing and reliability.
The Swiss Testing Day 2026 brought together a range of perspectives on exactly this topic: from classical software verification to emerging approaches for testing non-deterministic systems.
I, [Mike Mannion](/people/Mike) attended the conference and captured a set of reflections and observations from selected talks.

---

## Table Of Contents
* [Opening Keynote: Software Verification in the Age of AI](#software-verification)
* [Good AI Testing Strategy / Bad AI Testing Strategy. The difference and why it matters](#good-bad-ai-testing-strategy)
* [Agentic testing in banking: From hype to governed practice](#agentic-testing-in-banking)
* [Why AI Is Useless for Compliance](#why-ai-is-useless-for-compliance)
* [Key Takeaways](#takeaways)
* [Karakun Perspective](#Karakun)
* [Let's Discuss](#cta)

---

## <a name="software-verification"></a> Opening Keynote: Software Verification in the Age of AI

**Bertrand Meyer – OO and Software Correctness Pioneer**

Bertrand is a personal hero of mine.
His work on software correctness shaped my profile as a software developer the moment I came in contact with it.
In this keynote he covers a wide range of issues, but comes back to the central idea: proving that the software does what it promises; a challenge which LLMs, with their always-present non-determinism, have only made more difficult.

![Betrand Meyer during his opening keynote at Swiss Testing Day 2026](/assets/posts/2026-04-01-Swiss-Testing-Day/1-keynote.jpeg "Betrand Meyer during his opening keynote at Swiss Testing Day 2026")

The second line of the following slide is absolutely crucial and a key aspect of the probabilistic testing framework [PUnit](https://javai.org){:target="_blank"}.

![B.A.D. - Bullshit Avoidance Discipline](/assets/posts/2026-04-01-Swiss-Testing-Day/2-Bullshit-Avoidance-Discipline.jpeg "B.A.D. - Bullshit Avoidance Discipline")

The performance of stochastic features – which especially includes LLMs – must be measured, because a simple correct/not correct is not sufficient to gauge performance.

But despite this observation, Bertrand does not go into detail about how to measure such systems.
Instead he reiterates the message, which he has been saying for decades: program correctness must be built into the software.

This was, in fact, the genius of his Eiffel language, which unfortunately was not adopted by any mainstream language that followed.
But the principle stands – even if it does not yet fully answer the question of performance of stochastic systems.

He ends on an optimistic note, stressing that the need for good software engineers will not disappear any time soon.
Generated code must still be verified, and human understanding of the code remains key.

## <a name="good-bad-ai-testing-strategy"></a> Good AI Testing Strategy / Bad AI Testing Strategy. The difference and why it matters

**Iosif Itkin**

A very philosophical but worthwhile talk. Iosif asks: What is strategy? What is it not?

He challenges us to think about this carefully, and reminds us not to confuse strategy with a list of goals.
It is also not QA; QA requires a different mindset than testing – and that mindset is critical.

He frequently references the book *Good Strategy/Bad Strategy*.

A useful distinction: QA is not testing.

Iosif is adamant that testing is about identifying bugs.
He does not explicitly include other outputs such as usability feedback, responsiveness data, or success rates for stochastic services.

When I asked him about this, he suggested that these aspects could also be interpreted as “bugs”.
I’m not sure I agree – a suggestion for improvement is not necessarily a bug, but a claim that needs to be evaluated and may evolve into a requirement.

Despite this difference of opinion, the talk is an important reminder: organisations need to think carefully about their testing strategy – not just their tooling.

## <a name="agentic-testing-in-banking"></a> Agentic testing in banking: From hype to governed practice

**J. Reitermayer, S. Baumberger, M. Hause**

Reitermayer presents an agent called “Avalon”, which generates synthetic test data.

He quickly dives into product details, which can be difficult to follow without prior context.
However, one thing is clear: this is a sophisticated agent-based system that delivers measurable productivity gains.

What stands out is the transparency of the system.
The execution is visualised in real time in the UI, exposing LLM interactions, tool calls, and decision steps.

## <a name="why-ai-is-useless-for-compliance"></a> Why AI Is Useless for Compliance

**Nick Gushchin – AI Transformation Manager**

![Opening slide of the talk "Why AI is useless for compliance"](/assets/posts/2026-04-01-Swiss-Testing-Day/3-why-ai-is-useless.jpeg "Opening Slide "Why AI is useless for Compliance"")

Nick structures risk into different levels, each requiring different types of tooling.

![Defining Roles and Responsibilities for AI in Organisation](/assets/posts/2026-04-01-Swiss-Testing-Day/4-roles-and-responsibilities.jpeg "Defining Roles and Responsibilities for AI in Organisation")

He presents a matrix using likelihood and impact to systematically assess risks associated with AI systems.
This is not a new concept – but its importance applies just as much in the context of AI.

![Cross-Industry Insight: AI Governance Through a Banking Risk Lens](/assets/posts/2026-04-01-Swiss-Testing-Day/5-matrix-ai-agents.jpeg "Cross-Industry Insight: AI Governance Through a Banking Risk Lens")

One open question remains: how do we quantify “likelihood” in non-deterministic systems?
Frameworks like [PUnit](https://javai.org){:target="_blank"} ([GitHub](https://github.com/javai-org/punit){:target="_blank"}) may offer part of the answer here.

This was an outstanding talk – highly practical, with no hype, and extremely relevant for anyone working on testing and reliability in AI-driven systems.

## <a name="takeaways"></a> Key Takeaways

Across the talks, a few recurring themes emerged:

* **Non-deterministic systems require new testing approaches**:
Traditional binary correctness is not sufficient for AI-based systems.

* **Measurement becomes critical**:
Observability, probabilities, and performance metrics are central to evaluating stochastic behaviour.

* **Testing is strategic, not just operational**:
Organisations need to actively design how they approach testing – not just execute it.


## <a name="Karakun"></a> Karakun Perspective

For us at Karakun, these discussions reinforce a key observation:
As AI systems become part of real-world engineering systems, testing can no longer rely on deterministic assumptions.

Instead, we need:
* new models for evaluating system behaviour
* transparent and explainable execution
* and engineering practices that integrate correctness and probabilistic performance

This is particularly relevant in domains such as automotive, aerospace, and other safety-critical environments – where reliability is non-negotiable.

## <a name="cta"></a> Let's discuss!

The Swiss Testing Day 2026 made one thing very clear:
AI does not eliminate the need for engineering discipline – it increases it.

What are your thoughts on AI and engineering discipline?
[Feel free to reach out](/people/Mike).
I’m always happy to exchange knowledge, ideas, and experiences.
