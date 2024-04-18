---
layout: post
title: 'JavaLand 2024: A Review'
authors: [ 'ixchel' ]
featuredImage: javaland-review
excerpt: "From the 9th to the 11th of April, JavaLand celebrated its tenth anniversary with a grand event at the iconic Nürburgring in the Eifel region of Germany. The event saw an impressive turnout of over 1,440 attendees, making it a truly memorable occasion for all in attendance."
permalink: '/2024/04/18/javaland24-review.html'
categories: [ Javaland, Conference ]
header:
  text: 'JavaLand 2024: A review'
  image: post
---

From the 9th to the 11th of April, [JavaLand](https://www.javaland.eu/) celebrated its tenth anniversary with a grand event at the iconic Nürburgring in the Eifel region of Germany. The event saw an impressive turnout of over 1,440 attendees, making it a truly memorable occasion for all in attendance.

## JavaLand Conference Agenda
The conference featured presentations from twelve different streams, providing attendees with a wealth of knowledge and insight. Renowned experts from the Java community joined the event, offering valuable perspectives and engaging discussions that kept attendees engaged throughout.

One of my favourite sessions was [Testcontainers: necessary, simple, powerful](https://meine.doag.org/events/javaland/2024/agenda/#agendaId.3785) by Kevin Wittek and Piotr Przybyl. They presented the new features that make testing with Testcontainers even easier than before! Another interesting point they make is the type of tests and the distribution of tests that we usually think is best for our projects. The traditional testing pyramid, where we spend less time on large and cumbersome integration tests, is not a limiting factor in their number or reach.

If you haven't used Testcontainers, you're doing a disservice to your peace of mind! [Testcontainers](https://testcontainers.com/) is a library that provides simple and lightweight APIs for bootstrapping local development and testing dependencies with real services wrapped in Docker containers. With Testcontainers, you can write tests that depend on the same services you use in production, without mocks or in-memory services.

Another session I enjoyed was [Jenseits von Kafka](https://meine.doag.org/events/javaland/2024/agenda/#agendaId.3714) by Jochen Mader. One of the reasons I wanted to attend this session was that I had an amazingly entertaining conversation with the speaker before his session. The motivation behind this session was very compelling! Sometimes the use case doesn't match the technology chosen to "solve" it. The talk provided a concise overview of different event systems, looking at their unique features, recommended applications and implications for inter-service communication, CQRS and streaming. It will also touch on the trade-offs that can arise when choices are limited.
 
## The Community
Another outstanding feature of JavaLand, in addition to its technical content, is its unique community atmosphere.

Community-driven technical conferences, such as JavaLand, offer a number of advantages over more corporate-sponsored events. One key advantage is the diverse content committee that shapes the conference agenda. Unlike corporate-driven events, community-led conferences bring together professionals from a variety of backgrounds and experiences. This diversity ensures that the conference programme covers a wide range of perspectives, topics and expertise. Attendees can expect a richer and fuller agenda, covering both mainstream and niche topics to meet the diverse interests of the community.

Passion is another feature of community-driven conferences like javaLand. Organised by practitioners deeply immersed in the Java ecosystem, these events are driven by a genuine enthusiasm for the technology and its applications. From the speakers to the volunteers, everyone involved is motivated by a shared passion for Java and its community. This enthusiasm creates a dynamic and engaging atmosphere where attendees can immerse themselves in the latest developments, best practices and real-world experiences shared by fellow enthusiasts. The result is an exciting environment that fosters learning, collaboration and innovation.

## The Commonhaus Foundation
One of the most interesting developments at JavaLand following this community spirit was the announcement of the Commonhaus Foundation (Building a forever home for open source projects), a notable event as open source organisations are paramount to the future, sustainability and health of open source projects. Commonhaus acts as a steward of transparency and custodian of project and community identity. Commonhaus provides guidance and support in a unique way, without imposing mandates. Through a collaborative approach, they empower project contributors to take ownership of their initiatives, fostering pride and commitment within the community. This approach not only respects the diverse voices and perspectives of stakeholders, but also ensures that decisions are made collectively, reflecting the true spirit of open source collaboration.

Transparency is fundamental to the way Commonhaus operates and permeates every aspect of its activities. They are committed to openness and accountability, from governance structures to financial disclosure. By keeping stakeholders informed of key decisions and processes, they build trust and foster mutual respect within the community. This transparent approach strengthens the projects' foundation and serves as a model for others wishing to embrace open source principles.

In addition to promoting transparency, Commonhaus emphasises long-term thinking as essential for sustainable project impact. By encouraging stakeholders to think beyond short-term gains and consider the broader implications of their actions, they foster a culture of strategic planning and foresight. This ensures that Commonhaus-supported projects are well equipped to overcome challenges and capitalise on opportunities for growth and innovation in the dynamic open source technology landscape. Through its unwavering commitment to transparency and long-term impact, Commonhaus sets a high standard for open source foundations, inspiring others to cultivate thriving and resilient communities of developers and contributors.

Among the project that already joined the foundation are:

* [**Hibernate**](https://hibernate.org/): The Hibernate projects offer a suite of powerful Java libraries to work with data. It is best known for Hibernate ORM, which provides 
relational persistence for Java models and is an implementation of the Jakarta Persistence specification.
	
* [**Jackson**](https://github.com/FasterXML/jackson): The go-to library for JSON processing in Java. Jackson offers fast and flexible parsing/generation of JSON for Java applications, 
enabling seamless data interchange.
* [**JBang**](https://www.jbang.dev/): Unlock Java's scripting potential. JBang makes it easy to run Java applications as scripts without the need for a project setup or build 
configuration. Ideal for quick experiments, prototypes, or utility scripts.
* [**JReleaser**](https://jreleaser.org/): Automate your Java project releases with ease. JReleaser streamlines packaging and distribution to multiple platforms, integrating with Maven, 
Gradle, and more. Simplify your release process, from changelogs to deployment. 
* [**Morphia**](https://morphia.dev/): Bridge the gap between Java and MongoDB. Morphia provides a lightweight type-safe mapping library to simplify working with MongoDB documents using 
Java.
* [**OpenRewrite**](https://docs.openrewrite.org/): Automate the refactoring of your Java codebase. OpenRewrite offers scalable, safe, and idempotent code transformations to modernize 
and maintain your applications.
 
JavaLand is organised by members of more than 40 Java User Groups in Germany, Austria and Switzerland, which are part of the [IJUG association](http://ijug.eu/).

![Overview Java User Groups in Germany, Austria and Switzerland - Source: iJUG](/assets/posts/2024-04-18-javaland/iJUG-JUGS.png)