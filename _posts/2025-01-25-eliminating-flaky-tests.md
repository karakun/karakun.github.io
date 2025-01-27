---
layout: post
title: 'Eliminating Flaky Tests to End World Hunger'
authors: ['francois']
featuredImage: eliminating-flaky-tests
excerpt: "We keep re-running tests that sometimes fail and then pass again without explanation, wasting time, money, and eroding trust in our CI pipeline. Let's explore why tests become flaky and how to fix them before they pile up like clutter in a junk drawer."
permalink: '/2025/01/25/eliminating-flaky-tests.html'
categories: [ Testing ]
header:
  text: 'Eliminating Flaky Tests to End World Hunger'
  image: post
---

Einstein's so-called "Parable of Quantum Insanity" says:

> **Insanity** is doing the same thing over and over and expecting different results.

However, everyone has at least once experienced a test that failed and then passed in the next run without changing the code or the environment. In software engineering, we don't call it insanity; we call such unpredictable failures **flaky tests**. At first, they may seem like minor problems, but like clutter in a junk drawer, they become worse over time if you never take care of them.

What if we lived in a world where we could solve massive global problems like world hunger by eliminating flaky tests in software development? While it may seem exaggerated, it is closer to the truth than you might think, and it highlights the enormous cost and resources that flaky tests drain in development teams worldwide.

---

## Why Do Flaky Tests Matter?

1. **Waste of Time and Money:**  
   When a test fails, it can be difficult to tell at first glance if there is a real bug or if the test is just flaky. Developers often have to rerun tests, sift through logs, and add extra debugging code to confirm the issue is genuine. Over time, these continued efforts add up to an enormous cost. Brian Demers and I estimated in our talk ["Testing on Thin Ice: Chipping Away at Test Unpredictability"](https://www.youtube.com/watch?v=vJyY7x69p0Y) that **$36 billion** are wasted every year due to flaky tests worldwide—shockingly close to the **$40 billion** it would take to **[end world hunger by 2030](https://www.wfpusa.org/articles/how-much-would-it-cost-to-end-world-hunger/)**.

2. **Erosion of Trust:**  
   When more test failures are due to flakiness than actual issues in the code, the team loses confidence in the test suite. As a result, teams start to ignore test failures in general, which means that genuine test failures also get ignored. This means that bugs can slip through to production and cause more severe and expensive problems in the future.

3. **Slowed Development Process:**  
   Flaky tests can slow down the CI pipeline, especially when developers must rerun it multiple times to get it to pass. In one project, our test suite took about an hour per run, but because of the flaky tests, we had to rerun it at least four or five times per merge request. The worst thing is that when someone else merged new code to the main branch, we had to rebase and try getting the pipeline to pass again. In practice, we would spend most of the day watching the pipeline to get a single change merged.

Like a junk drawer that becomes more daunting with each new item you toss in, allowing flaky tests to accumulate makes it more likely that you'll put off fixing them.

---

## Common Causes of Flaky Tests

To effectively address flaky tests, you have to understand why tests become flaky in the first place. The most common reasons include:

1. **Timing Issues or Hard-Coded Delays:**  
   Using fixed waits, like `Thread.sleep(500)` instead of waiting for conditions to be met, will lead to unpredictable failures. The test may pass or fail if the system is faster or slower than expected.

2. **UI Race Conditions:**  
   In essence, this is another timing issue. The test may fail if you interact with a UI element in an end-to-end test before it is fully loaded. When you have hard-coded delays or do not wait, you will likely run into this issue on slower runs.

3. **Unreliable or Shared Test Data:**  
   Using the same data across multiple tests can make tests unreliable. For instance, creating a user with a fixed username in one test causes another test to fail when trying to create the same user. Randomly generated data may pass the first time but fail the second time if it violates a validation rule. For example, if a form field restricts the maximum length to 20 characters, the test succeeds if the first randomly generated string is 8 characters long. However, the test will fail if the next run generates a text with 22 characters.

4. **Unstable Environments:**  
   Tests that rely on external services or shared resources can break if the services are slow, unavailable, or unreliable. This is especially problematic in CI environments, where the resource allocation is usually constrained and can differ from one run to the next.

5. **Test Order Dependencies:**  
   If one test relies on the state left behind by another test, parallel or out-of-order execution can lead to failures. Each test should be able to run independently.

---

## Strategies to Keep Tests Reliable

### 1. Awareness of Flaky Tests

You can't eliminate flaky tests if you don't know they exist. Every time you come across one, ensure you mark it using a consistent comment like:

```java
// Flaky Test
@Test
void testSomething() {
    //...
}
```

Encourage everyone on the team to do this. The consistent keyword makes it easy to find all flaky tests in the code and gives you a quick overview of how many you have and where they are located.

### 2. Fix One Flaky Test Each Sprint

Adopt the following rule for your team: **For each sprint, fix at least one flaky test before starting a new task or feature**.

Think of it as putting away one item from your junk drawer each week. Over time, the drawer (your flaky test backlog) will be empty. This practice is easy to justify to management ("it's just one test per sprint") but makes a considerable difference cumulatively.

Some teams go further by dedicating a day (like "Flaky Test Fridays") to address these issues systematically. This can be especially effective if your project has many flaky tests.

### 3. Use New Test Data

Make sure each test starts with a clean slate:

- **Isolate Test Data**: To prevent collisions, use unique data for each test. Tools like [Testcontainers](https://www.testcontainers.org/) allow you to spin up disposable containers for databases and other services quickly.
- **Setup and Teardown**: In frameworks like JUnit, use `@BeforeEach` to set up fresh data and `@AfterEach` to remove it:

```java
@BeforeEach
void setUpData() {
    // Insert test data or start a container
}

@AfterEach
void cleanUpData() {
    // Remove test data or stop the container
}
```

### 4. Wait for Conditions to Be Met

Avoid hard-coded delays by waiting for specific events or conditions. For various popular end-to-end testing frameworks:

- **Selenium**: Use `WebDriverWait` to wait until an element is present or clickable:

    ```java
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='submit-button']")));
    ```

- **Cypress**: Utilize built-in commands like `.should('be.visible')` to [wait for elements](https://learn.cypress.io/cypress-fundamentals/waiting-and-retry-ability).

- **Playwright**: Use Playwright's [auto-retrying assertions](https://playwright.dev/docs/test-assertions), like `toHaveText` and `toBeVisible`. They wait for conditions to be satisfied and will fail if not met within a certain time (timeout). For example, when you click a button and expect an element's text to change:

    ```js
    // ❌ Flaky: checks the text immediately; may fail if the text hasn't updated yet
    expect(await this.textarea.textContent()).toBe('expected text');
    
    // ✅ Reliable: waits until the element has the expected text
    await expect(this.textarea).toHaveText('expected text');
    ```

- **WebdriverIO**: Similar to Playwright, [built-in assertions](https://webdriver.io/docs/bestpractices/#use-the-built-in-assertions) automatically wait for conditions to be met within a configurable timeout:

    ```js
    // ❌ Flaky: immediately checks if the button is displayed; fails if it isn't ready
    expect(await $('[data-testid="submit-button"]').isDisplayed()).toBe(true);
    
    // ✅ Reliable: waits until the button is displayed
    await expect($('[data-testid="submit-button"]')).toBeDisplayed();
    ```

This approach makes tests more resilient to variations in system performance and load.

### 5. Run Tests in Parallel

Parallel execution speeds up testing and uncovers hidden dependencies:

- **Gradle**: Enable [parallel execution of test tasks](https://docs.gradle.org/current/userguide/performance.html#parallel_execution) with the `--parallel` flag.
- **JUnit 5**: Set `junit.jupiter.execution.parallel.enabled` to `true` to run [tests in parallel](https://junit.org/junit5/docs/5.3.0-M1/user-guide/index.html#writing-tests-parallel-execution).
- **WebdriverIO and Playwright**: They run tests in parallel with different files by default. You can also configure Playwright to [parallelize tests within a file](https://playwright.dev/docs/test-parallel#introduction).

If tests fail when run in parallel, they may be relying on a shared state.

### 6. Temporarily Quarantine Flaky Tests

When a flaky test that is not part of your change fails and blocks progress, quarantine it so it doesn't prevent you from merging:

```java
// JUnit
@Disabled("quarantine: reason or link to issue here")
@Test
void flakyTest() {
  //...
}
```

```js
// Jest
// quarantine: reason or link to issue here
it.skip('should throw an error', () => {
  expect(response).toThrowError(expected_error);
});
```

Use a consistent prefix (e.g., `quarantine:`) to easily find and track these tests. However, **do not** let quarantined tests remain ignored forever—make fixing them a priority.

### 7. Split Up End-to-End and Integration Tests

End-to-end and integration tests are often slower and more prone to flakiness because they involve multiple components. Evaluate whether each test is at the right level of abstraction:

- **Integration Tests**: Verify how your component behaves when interacting with other components (e.g., between your code and a database) without using the UI.
- **Unit Tests**: Verify the behavior of individual functions or classes in isolation.

Teams often default to adding new integration tests because it *seems* more straightforward: fewer, broader tests can cover more code. However, this approach leads to larger, slower test suites that are harder to maintain. One practical approach to address this issue is to refactor complex, multi-purpose methods so each method has a specific purpose. This simplifies methods and makes them easier to test with unit tests. For instance, in one project, I took a monolithic integration test suite that ran for **five minutes** and restructured it so that most logic was covered by unit tests instead. This ended up reducing the total runtime to **just 11 seconds**.

---

## Building a Reliable Test Suite: A Cultural Shift

Eliminating flaky tests isn't just a technical challenge. It requires a cultural shift within your team:

- **Educate the Team**: Explain the impact and costs of flaky tests. Share examples and encourage everyone to treat flaky tests as a priority.
- **Track Flaky Tests**: Keep a list or dashboard of known flaky tests, their status, and assigned owners. Bring them up in daily standups so they aren't forgotten.
- **Set Clear Goals**: Commit to fixing a specific number or percentage of flaky tests within a set timeframe.
- **Celebrate progress**: Recognize and appreciate those who fix flaky tests. Positive feedback motivates the team to continue improving the test suite.

---

## Conclusion

Flaky tests are not just minor annoyances. They waste valuable time, break your team's trust, and slow development. The good news is you don't have to tackle everything at once. Start with one of these ideas: fix a single flaky test each sprint, ensure your tests use isolated data, wait for specific conditions instead of using fixed delays, run tests in parallel, quarantine problematic tests, or replace extensive end-to-end tests with smaller, more focused tests. Adopting just one is a big step toward making your test suite more reliable.

Think of your test suite like that junk drawer: if you clean it up regularly, it stays helpful and easy to manage. Pick one flaky test that causes frequent trouble and fix it first. That small success will motivate your team and make your testing smoother.

For more details, you can watch my talk ["Testing on Thin Ice: Chipping Away at Test Unpredictability"](https://www.youtube.com/watch?v=vJyY7x69p0Y). If you'd like to share your own experiences or need specific advice, feel free to reach out via [email](mailto:francois.martin@karakun.com) or connect with me on [LinkedIn](https://linkedin.com/in/françoismartin). Let's work together to build more dependable and trustworthy test suites!
