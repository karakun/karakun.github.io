---
layout: post
title: 'Groovy 6: Writing Less Code and Saying More. Concurrency, streaming, and contracts for modern JVM development.'
seo_title: 'Groovy 6: Concurrency, Streaming, Contracts, and AI-Friendly Code'
description: 'Groovy 6 makes asynchronous workflows, streaming data and program contracts easier to express, while giving both developers and AI coding agents more explicit information to work with.'
authors: [ 'jochen' ]
featuredImage: 'Groovy6-Writing-Less-Code'
excerpt: 'Groovy 6 makes asynchronous workflows, streaming data and program contracts easier to express, while giving both developers and AI coding agents more explicit information to work with.'
permalink: '/2026/09/24/Groovy-6-Writing-Less-Code.html'
categories: [ Groovy, Java, JVM, Development, AI ]
header:
  text: 'Groovy 6: Writing Less Code and Saying More. Concurrency, streaming, and contracts for modern JVM development.'
  image: 'post'
---
**TL;DR:** Groovy is a JVM language that combines Java interoperability with an expressive syntax, supporting both static and dynamic typing. Groovy 6 builds on that foundation with modern concurrency, streaming, HTTP clients, contracts and additional compiler support. This article looks at those capabilities through one small example — a service that assembles a customer report — and at why making program semantics explicit can help both developers and AI coding agents.

Groovy 6 brings together several changes that make everyday code easier to write, but some of the most interesting improvements are not about saving keystrokes. They are about making the structure and guarantees of a program more explicit.

Consider a small service that assembles a customer report. It needs customer details, orders, incidents, and usage data from four backend APIs.

```groovy
CustomerReport createReport(String customerId) {
    def customer  = customerApi.get(customerId)
    def orders    = orderApi.findFor(customerId)
    def incidents = incidentApi.findFor(customerId)
    def usage     = usageApi.events(customerId)

    new CustomerReport(
        customer: customer,
        orders: orders,
        incidents: incidents,
        usage: usage
    )
}
```

There is nothing particularly Groovy 6-specific here. It is ordinary application code. The interesting question is how much ceremony is needed when the APIs are asynchronous, the usage data is large, and the resulting objects have rules that callers should not violate.

## Making the backend APIs concrete

Groovy 6 includes a new HTTP Builder module. Its declarative client lets an API be described as an interface:

```groovy
@HttpBuilderClient('https://customers.example')
interface CustomerApi {
    @Get('/customers/{id}')
    Customer get(String id)
}
```

The interface describes the endpoint, path and return type. There is no client implementation in the example.

The same API can return a `CompletableFuture` when the report should fetch several independent resources concurrently:

```groovy
@HttpBuilderClient('https://customers.example')
interface CustomerApi {
    @Get('/customers/{id}')
    CompletableFuture<Customer> get(String id)
}
```

That changes the report method without changing the API description itself.

## Running the calls concurrently

The four backend calls do not depend on one another. Running them one after another means the total time is dominated by the sum of their latencies.

Groovy 6's `async`/`await` lets the code express the concurrency without turning the method into a future-composition exercise:

```groovy
CustomerReport createReport(String customerId) {
    def customer  = async { customerApi.get(customerId) }
    def orders    = async { orderApi.findFor(customerId) }
    def incidents = async { incidentApi.findFor(customerId) }
    def usage     = async { calculateUsage(usageApi.events(customerId)) }

    def (customerData, orderData, incidentData, usageData) =
        await(customer, orders, incidents, usage)

    new CustomerReport(
        customer: customerData,
        orders: orderData,
        incidents: incidentData,
        usage: usageData
    )
}
```

The four operations start independently, and `await` waits for all of them before constructing the report. There is no need to turn the report into a chain of `thenApply`, `thenCompose` and `join` calls, or to make its control flow follow the mechanics of `CompletableFuture`.

`groovy.concurrent` also provides agents, actors, dataflow variables, channels and parallel collection operations. Developers familiar with GPars will recognise many of these concepts; Groovy 6 brings them into its own concurrency toolkit.

## Consuming large results as they arrive

Usage events may be numerous enough that collecting them all before calculating the summary is unnecessary. Groovy 6 adds `for await`, so an asynchronous sequence can be consumed as events arrive.

```groovy
def calculateUsage(events) {
    def total = 0
    def exports = 0

    for await (event in events) {
        total++

        if (event.type == 'export') {
            exports++
        }
    }

    new UsageSummary(total, exports)
}
```

The report still gets a `UsageSummary`, but the calculation does not require the complete event stream to be materialised first.

## Describing what the code guarantees

The report method has an assumption that is easy to miss from its implementation alone: `customerId` must identify a customer. A contract makes that assumption explicit.

```groovy
@Requires({ !customerId.empty })
CustomerReport createReport(String customerId) {
    ...
}
```

There are similar rules for the data produced by `calculateUsage`. A usage summary cannot contain negative counts, and the number of exports cannot exceed the total number of events.

```groovy
@Immutable
@Invariant({ total >= 0 && exports >= 0 && exports <= total })
class UsageSummary {
    int total
    int exports
}
```

Developers who have used GContracts will recognise the Design by Contract approach. GContracts provided this model as a separate Groovy project; `groovy-contracts` brings contracts into the Groovy ecosystem as a supported module.

These annotations are more than documentation. Groovy Contracts transforms them into assertions during compilation, so violations are checked when the resulting code runs. Groovy also provides type-checking extensions that can establish additional properties at compile time, including nullability, purity and allowed modifications.

Groovy is still often described simply as a dynamic language, which leaves out a substantial part of what the language offers. `@TypeChecked` and `@CompileStatic` have been available for years, allowing developers to opt into static type checking and static compilation where they want it. Groovy 6 extends the information available to the compiler without forcing the whole language into a single programming model. Developers can therefore choose how much of a program's semantics they want the compiler to establish.

## Less code to read, less code to reason about

Before changing `createReport`, a developer needs to know what callers are allowed to pass to it. Before changing `UsageSummary`, they need to know which states are valid. Without explicit contracts, those facts may be spread across implementations, callers, tests and documentation. Understanding the code means reconstructing the rules from those sources.

With contracts, some of that knowledge is part of the code itself:

```groovy
@Requires({ !customerId.empty })
CustomerReport createReport(String customerId) {
    ...
}

@Immutable
@Invariant({ total >= 0 && exports >= 0 && exports <= total })
class UsageSummary {
    ...
}
```

An AI coding agent faces the same problem when it enters an unfamiliar repository. Before making a change, it has to build a model of the surrounding code and its constraints. Explicit contracts provide some of that model without requiring the agent to inspect every implementation path from which the same facts could otherwise be inferred.

That can reduce the amount of source placed into context and the reasoning needed to recover its constraints. The Groovy project's article on AI-friendly code demonstrates this with an example that reduces 288 tokens of source to 83 tokens of verified specification, without opening the implementation bodies. The numbers are specific to that example rather than a general compression ratio, but they show the effect. [Groovy 6: AI-friendly Groovy](https://groovy.apache.org/blog/groovy6-ai-friendly)

The important difference between such a specification and a summary generated by an AI agent is that the specification is part of the program and can be checked against the implementation. Tools can consume the compact representation without losing that connection to the source.

## There is more in Groovy 6

The features used in our customer report cover only part of the Groovy 6 release.

Groovy 6 adds optional modules for HTTP, CSV and Markdown, along with integrations for Reactor and RxJava. GINQ has graduated from incubation, while the language gains features such as `val`, richer destructuring, compound-assignment operator overloading and nested `copyWith` support.

There is substantial work underneath the language as well, including improvements to AST transformations, compiler diagnostics, generated bytecode, invokedynamic dispatch and allocation behaviour.

The complete set of changes is covered in the [Groovy 6.0 release notes](https://groovy-lang.org/releasenotes/groovy-6.0.html).

Our customer report started as four ordinary backend calls. Groovy 6 lets us run them concurrently, consume a large result as a stream, and describe properties of the resulting code in a form the compiler can check.

Those properties no longer have to remain assumptions that a developer or a tool reconstructs from the implementation. They can become part of the program itself.

The result is not just less code. It is code that says more.
