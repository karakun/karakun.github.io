---
layout: rico
title: Rico
order: '16'
headerText:
permalink: /rico/
header:
  image: rico
  text: Rico by Karakun
nav:
  bottom: false
---

NOTE: Rico has been put 'on-hold'. Find more details what that mean in the corresponding [blog post](/).

Implement applications while leveraging state-of-the art technologies, patterns and practices.

![Rico Logo]({{ "/assets/images/rico/rico-logo.png" | absolute_url }})

## Overview

Rico is an application framework that provides several functionalities and APIs to create enterprise applications. 
This includes features like tracing or monitoring that are critical in developing applications, especially in modern cloud based and distributed application landscapes. 
Thus, Rico provides generic APIs with specific implementation for the mostly used frameworks and toolkits in modern application development.

Rico originates from the [_Dolphin Platform_](https://github.com/canoo/dolphin-platform/) which appears to be discontinued. Rico is an (originally) API compatible fork that we continue to develop and improve.

### Rationale

The motivation for us to create Rico is to avoid code for standard use-cases to be written over and over again. Thus, we created it as an extension to commonly used frameworks like Spring and Jakarta EE (Java EE) that is easily adoptable.

The outcome is a framework that is built on top of well-known frameworks combined with commonly used components and prepared to be used with a very short warmup. 
Rico helps you to build your applications in a short time while letting you focus on the business requirements. It offers standard solutions for common technical use cases like _Data Access_, _Logging_, _Monitoring_ and _Security_ *already built in* and is well prepared to get *integrated in your environment*.

To achieve this, Rico provides clean APIs to separate complexity coming from the set of technologies required to build your solution from your actual code.

Also, Rico provides a remoting layer that helps you to separate UI framework specific code so that is a lot easier to switch UI implementations.
It also enables you to choose different technologies for every target platform. 
As we see a very rapidly changing environment in UI technologies, this separation helps to protect your investment put into the solution built with Rico as it will be less dependent on a concrete UI technology.

### Getting started

Currently, the best point to get started is our [documentation](https://rico-projects.github.io/rico/) in combination with the [samples project repository](https://github.com/rico-projects/rico-samples). Please refer to the README in each project for a description how to get started with the sample:

* [Rico To-Do list application](https://github.com/rico-projects/rico-samples/tree/master/todo-list) Application both for Spring and Java EE with frontends in Angular, AngularJS, Polymer and JavaFx to demonstrate _Rico Remoting_ and an _EventBus_ to connect between different clients implemented in various UI technologies.
* [Rico HTTP rest security](https://github.com/rico-projects/rico-samples/tree/master/rest-security) - use Rico integration for Keycloak to secure your applications.
* [Rico HTTP client](https://github.com/rico-projects/rico-samples/tree/master/http-client) - show usage of Rico HTTP client to consume web services over HTTP.
* [Rico Logging Sample](https://github.com/rico-projects/rico-samples/tree/master/logging-sample) - shows how the logging appender of Rico can be used to add useful metadata to application logging and store it in a central place.
* [Rico Metrics Sample](https://github.com/rico-projects/rico-samples/tree/master/metrics-sample) - collect metrics and make use of them with Prometheus and Grafana.

Our main repository as well as all our sub-projects can be found at our [GitHub organisation](https://github.com/rico-projects/).

## Who is using Rico

Users of Rico we know of and who have agreed to be listed here as references:

* [Sprouts GmbH](https://www.sprouts.aero/)
* [IRIX Software Engineering AG](https://www.irix.ch/)

If you are using Rico and would be happy to see your company listed here, please [contact us](mailto:rico@karakun.com).

## Outlook 

Rico is an open-source framework mainly driven by the projects already using it in production. The is not concrete timeline for further development planned at the moment. More details can be found in [the blog post](/)

If you want to influence the timeline, prioritize the next steps in Rico development or add your own items to the list, please [contact us](mailto:rico@karakun.com).

![Rico Open Source]({{ "/assets/images/rico/rico-opensource.png" | absolute_url }})
