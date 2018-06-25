---
layout: post
title:  "How WebComponents can help to create Microservices"
author: hendrik
featuredImage: sweets
excerpt: "This post gives an overview of different patterns that can be used to define 
frontends for microservices. In additon the post shows how you can make use of
 web components together with a microservice based archicture to reduce the duplication
 of code and provide a constinent ui / ux for a complete application landscape"
tags: [architecture, microservice, webcomponents]
headerImage: post
---


If you plan a new project and decide to use a [Microservice architecture](http://martinfowler.com/articles/microservices.html)   you need to define how your system should be structured. For the backend several good tutorials and overviews can be found online. In addition frameworks like [Spring Boot](https://projects.spring.io/spring-boot/), [MicroProfile](https://microprofile.io) or [Spark](http://sparkjava.com) provides functionality to create small and standalone services that encapsulate one of you business domains. Based on this it’s quite easy to create server side microservices. But if you plan your project based on microservices you will come to the point where you need to decide how your fronted should be structured. The following article should give some ideas how modern frontends for a microservice bases architecture might be defined.

## Defining Microservices
As an example I will use a web store. The store application that should be splitted in several services that handle parts of the business model. To define this example as simple as it could we will split the application in 3 microservices:

- A **product service** that handles the products of the store. This service provides all information of a product like the price, the description, images and the availability.
- A **shopping cart service** that handles the products a customer want to buy.
- A **payment service** that provides all functionality for payment

![Services]({{ "/assets/posts/web-components-and-microservices/services.png" | absolute_url }})

This solution will and you can for example simply create a [AngularJS](https://angularjs.org/) based Frontend for you application as a [single page app](https://en.wikipedia.org/wiki/Single-page_application). Because all views / pages of the application are part of the same module it’s easy to create a consistent user experience by for example using a global stylesheet. But on the other side this approach has several downsides. To understand the problems I will go a step back and discuss why microservices are such a famous architecture style. When creating an monolithic application you will get several problems. Let’s have a look at the problems that most developers that worked on monolithic applications in the last years will:

- Mostly the deployment is very hard. Changing the whole application in a productive system will take some time and the deployment process will become very complex when the application is growing.
- Whenever you fix a bug you need to deploy the complete application. So maybe fixing a typo in the title of a button will result in a deploy process that might take several hours
- When your applications grows it will become more complex and you will have more code that must be maintained. This ends in a bigger / growing team. You could split the developers that are working on the same big application in several teams to more flexible and agil but I don’t think that this will solve the real problem.
- Developers normally need to know the complete application since they are responsible for the complete code. By doing so it’s very hard to become an expert of a specific segment. In addition you will always have complexe parts that are only understood by a small group of people but maintained by all developers. This will end in bugs

In the shown solution with microservices in the backend and a monolithic frontend you will have the mentioned problems in the frontend development. A frontend developer needs to know all the endpoints of the backends services and whenever you change something in the frontend or in an  public backend endpoint you need to redeploy the complete frontend. By doing so the complete application can’t be accessed while the deployment is running. So maybe we need to find a better solution

### Vertical decomposition
Another approach is to create a frontend for each microservice. The following pciture shows how such an approach might look:

![Vertical decomposition]({{ "/assets/posts/web-components-and-microservices/vertical.png" | absolute_url }})

By using this approach you can work with several teams where each team is responsible for a single microservice and it’s frontend. Here the “products” team for example don’t need to know anything of the payment server. Whenever a developer changes code only one microservice (and it’s frontend) must be redeployed. In addition the complexity of the code that a developer must maintain won’t grow like when working on a monolithic application.

So some of the mentioned problems are solved by this approach. One point that will still be problematic is a consitent user experience. In this approach the UI of the application is developed by several teams that by definition work independent.

### Composite Frontend
TODO