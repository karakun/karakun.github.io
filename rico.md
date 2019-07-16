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

Implement applications while leveraging state-of-the art technologies, patterns and practices.

## Overview

Rico is an application framework that provides several functionalities and APIs to create enterprise applications. 
This includes features like tracing or monitoring that are critical in developing applications, escpecially in modern cloud based and distributed application landscapes. 
Thus, Rico provides generic APIs with specific implementation for the mostly used frameworks and toolkits in modern application development.

Rico originates from the _Canoo Dolphin Platform_ which appears to be discontinued. Rico is an (originally) API compatible fork the we continue to develop and improve.

### Rationale

[//]: # ( #### Motivation for Rico )

The motivation for us to create Rico is to avoid code for standard use-cases to be written over-and-over again. Thus, we created it as an extension to commonly used frameworks like Spring and Jakarta EE (Java EE) that is easily adoptable.

The outcome is a framework that is build on top of well-known frameworks combined with commonly used components and prepared to be used with a very short warmup. 
Rico helps you to build your applications in a short time letting you focus on the business requirements as it has standard solutions for common technical use cases like _Data Access_, _Logging_, _Monitoring_ and _Security_ *already build in* and well prepared to get *integrated in your environment*.

To achieve this, Rico provides clean APIs to seperate complexity coming from the set of technologies required to build your solution from your actual code required to provide your business solution.

Also, Rico provides a remoting layer that helps you to seperate UI framework specific code so that is a lot easier to switch UI implementations. As we see a very rapidly changing environment in UI technologies, this speration helps to protect your investment put into the solution build with Rico as it will be less dependent on a concrete UI technology.


[//]: # (### Introduction )

### Getting started

Currently, the best point to get started is our [samples project repository](https://github.com/rico-projects/rico-samples). Please refer to the README in each project for a description how to get started with the sample:

* [Rico To-Do list application]() Application both for Spring and Jave EE with frontends in Angular, AngularJS, Polymer and JavaFx to demonstrate _Rico Remoting_ and an _EventBus_ to connect between different clients implemented in various UI technolgies.
* [Rico HTTP rest security](https://github.com/rico-projects/rico-samples/tree/master/rest-security) - use Rico integration for Keycloak to secure your applications.
* [Rico HTTP client](https://github.com/rico-projects/rico-samples/tree/master/http-client) - show usage of Rico HTTP client to consume web services over HTTP 

Our main repository as well as all our subprojects can be found at our [GitHub organisation](https://github.com/rico-projects/).

## Who is using Rico

Users of Rico we know of and who have agreed to be listed here as references:

* [Sprouts GmbH](https://www.sprouts.aero/)
* [IRIX Software Engineering AG](https://www.irix.ch/)

If you are using Rico and would be happy to see your company listed here, please [contact us](mailto:info@karakun.com)

## Outlook 

Next steps planned for development of Rico are:

* Release [Rico Projector](https://github.com/rico-projects/rico-projector)
* Improve Rico Remoting Protocol
* Upgrade to Java 11
* Migrate [Rico JavaScript](https://github.com/rico-projects/rico-js) to TypeScript for better maintainablility
* Upgrade to recent Spring Boot Version (2.2)
* Extend [Rico JavaScript](https://github.com/rico-projects/rico-js) to support more Rico features (currently just remoting)
* Upgrade the [Angular Adapter](https://github.com/rico-projects/rico-angular) to Angular 8
* Upgrade support for WebComponents (currently outdated [Polymer Adapter](https://github.com/rico-projects/rico-polymer))

Rico is a open-source project funded by the projects creating the solutions it helps to build. If you want to influence the timeline or prioritize the next steps in Rico development or add your own items to the list, please [contact us](mailto:info@karakun.com) .
