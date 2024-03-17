---
layout: post
title: 'Why Software Testing Is a Waste of Time: Unpacking the Paradox'
authors: [ 'francois' ]
featuredImage: why-software-testing-is-a-waste-of-time
excerpt: "The provocative truth behind 'Why Software Testing Is a Waste of Time' and how revolutionizing your approach can transform testing from a chore to a cornerstone of software excellence."
permalink: '/2024/03/18/why-software-testing-is-a-waste-of-time-unpacked.html'
categories: [ Testing ]
header:
  text: 'Why Software Testing Is a Waste of Time: Unpacking the Paradox'
  image: post
---

Software testing is often perceived as a time-consuming and sometimes unnecessary step in the development process.
However, what if the real waste of time isn't the act of testing itself but how we approach it? This is the provocative
premise I explored in my
talk ["Why Software Testing Is a Waste of Time" which I first held at JCON Europe 2023](https://www.youtube.com/watch?v=qQVhb3z244Q).

## Misconceptions about Software Testing

At first glance, the statement of software testing being a waste of time seems unrealistic to those who have seen
firsthand the disasters and uncertainty that can result from inadequately tested software. When examining the reasons
behind some developers’ view of software testing being unproductive, I consistently encountered a pattern of
inefficient, outdated, and poorly implemented testing strategies. As I’ve guided teams into improving those aspects,
I’ve noticed testing becoming an indispensable part of ensuring software quality and reliability to them. The goal of my
talk, and the insights that follow, is to empower you with the tools and knowledge to replicate similar enhancements
within your projects.

## Inefficiencies in Common Testing Practices

The real issue lies not in the testing itself but in the approach, many organizations take toward it:

* **Overreliance on reactive measures over prevention:** Systems that detect and roll back bugs in production address
  issues only after they've impacted users, rather than preventing them upfront. This results in bad user experience and
  having to fix bugs after a change was deployed, instead of getting instant feedback when testing during development.

* **Neglecting Test-driven Development (TDD):** Skipping TDD leads to missing out on benefits, such as less
  overengineering, inherently testable code, and improved architecture.

* **Misapplication of the Test Pyramid:** Straying from
  the [test pyramid](https://martinfowler.com/articles/practical-test-pyramid.html)'s guidance results in
  a [slow and fragile suite of tests, prioritizing end-to-end tests over unit tests](https://www.youtube.com/watch?v=CI8KX6tr4vM&t=368s).

* **Excessive Manual Testing:** Manual testing is not only slow and prone to errors but also reduces release flexibility
  and delays feedback. By automating repetitive manual tests, teams gain the freedom to focus on exploratory testing.
  This shift from tedious, routine checks to dynamic, critical thinking tasks not only enhances engagement. It also
  encourages a more thorough and inventive approach to quality assurance. It allows every team member and stakeholder to
  contribute to a culture of innovation and continuous improvement in the testing process.

* **Over-automation of tests:** Automating every test, regardless of its frequency or predictability, wastes resources
  on rarely used or unpredictable tests.

* **Irrelevant test execution:** Running tests unaffected by code changes is inefficient and requires developers to wait
  longer for tests to complete. This often results in running tests less frequently.
  Some tools have a built-in feature for this, like [Jest's `--changedSince`](https://jestjs.io/docs/cli),
  [nx affected](https://nx.dev/concepts/affected),
  or [Gradle Enterprise's Predictive Test Selection](https://gradle.com/gradle-enterprise-solutions/predictive-test-selection/).
  If you separate your test types or long-running tests into multiple Gradle tasks, you can also
  use [Gradle's Build Cache](https://docs.gradle.org/current/userguide/build_cache.html) to have each task only run when
  necessary.

* **Infrequent test execution:** Executing tests with every code change quickly isolates failures; in contrast, testing less frequently, such as daily, blurs the cause—especially in teams with a high volume of changes—requiring tedious and slow troubleshooting.

* **Flaky tests:** Tests that fail intermittently without clear reasons can lead to developers losing trust in the tests
  and their outcomes, often necessitating repetitive, time-consuming, and extensive investigations.

## Strategies for Efficient Testing

Adopting efficient testing practices can mitigate these issues:

* **Data-Driven Testing:** Utilizing tools
  like [JUnit's @CsvSource, @MethodSource](https://junit.org/junit5/docs/current/user-guide/),
  or [Jest's `.each`](https://jestjs.io/docs/api) makes tests more comprehensive, readable, and concise.

* **Reducing the complexity of logic within tests:** Simplifying the logic within tests and using assertion libraries
  like [AssertJ](https://assertj.github.io/doc/) or [assertpy](https://github.com/assertpy/assertpy) enhances
  maintainability and clarity.

* **Appropriate use of BDD tools:** BDD tools should be
  used [for collaboration with the appropriate training](https://cucumber.io/blog/collaboration/the-worlds-most-misunderstood-collaboration-tool/)
  and [by using the right level of abstraction](https://lizkeogh.com/2011/03/04/step-away-from-the-tools/), to retain
  their value in aligning development with business needs.

* **Test data management:** Effectively manage test data by setting up unique data for individual tests and cleanly
  removing it afterward to preserve the integrity of the results.

* **Efficient end-to-end (E2E) testing:**. Using deep links and ensuring tests can run independently and quickly are
  crucial for efficient E2E testing.

* **Parallel execution of tests:** Running tests in parallel, either on the same machine or across multiple machines,
  significantly accelerates the testing process and enforces tests to be independent. Setting up parallelization on the
  same machine is usually very straightforward. For example, [WebdriverIO](https://webdriver.io/docs/organizingsuites/)
  does this by
  default, [JUnit](https://junit.org/junit5/docs/5.3.0-M1/user-guide/index.html#writing-tests-parallel-execution)
  and [Gradle](https://docs.gradle.org/current/userguide/performance.html#parallel_execution) can be easily configured
  to run in parallel.

* **Detailed failure reporting:** Comprehensive failure reports, facilitated by tools
  like [Allure](https://qameta.io/allure-report/), aid in quickly diagnosing and resolving issues.

For more details, you can watch my
talk ["Why Software Testing Is a Waste of Time"](https://www.youtube.com/watch?v=qQVhb3z244Q),
reach out to me via [email](mailto:francois.martin@karakun.com) or send me a message
on [LinkedIn](https://linkedin.com/in/françoismartin).
