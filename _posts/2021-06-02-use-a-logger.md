---
layout: post
title:  'System.out.println("USE A LOGGER")'
authors: ['stephanc']
featuredImage: logger
excerpt: "Logging is done in most applications but when did you last talk about it?
What to log, how to log and which benefits a logger provides are discussed before going into the topic of collecting and analyzing logs."
categories: [Java, Logging]
header:
  text: System.out.println("USE A LOGGER")
  image: post
---

In one of the recent tech talks at Karakun, we presented the benefits of using a dedicated logging framework.
A recording of this session is available on the [Karakun YouTube channel](https://youtube.com/c/karakun).

<iframe width="560" height="315" src="https://www.youtube.com/embed/hwyEanE6tX8" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

This article presents the main points and conclusions in case you do not have time to watch the full video (55 min).

# What should you log?

Before we examine how to log, we need to talk about what to log.
This question is not trivial and there is no black and white.
In fact, most developers need to answer this question multiple times a day.
Defining a logging concept for your project or product helps you to make decisions faster and more precisely.
A logging concept should at least touch the following points:

* the motivation why to log
* who is reading/monitoring the log
* what information to extract from the log
* what information NOT to extract
* the differences of logging in production/test/development

# What are the benefits of logging?

Loggers can be seen as a pattern to solve a common problem in software.
The following image provides an overview of the different parts a typical logging library contains.

![Logger Parts Diagram](/assets/posts/2021-06-02-use-a-logger/logger-detail.png)

All logging frameworks offer simple configurations of the main parts.
Most notably the following:

* **Logger**: which logger to send messages to which appender
* **Filter**: which messages to log and which to ignore
* **Layout**: how to represent the message
* **Appender**: where to store the message

All of this is possible because a log message is not a string but a complete object.
This allows to add and process metadata about the message.
This metadata is used in every step and may even be written in the final storage making it available for future analysis.

# Which logger is the right one for me?

Well, roughly speaking: it depends.
If you develop a small library with (almost) no dependencies using Java 9 or greater, you should have a look at the [System.Logger](https://docs.oracle.com/javase/9/docs/api/java/lang/System.Logger.html) which is part of the JDK since Java 9.
It is very limited, but it will not add a dependency to your library.

For most projects, we recommend using [Log4j 2](https://logging.apache.org/log4j/2.x/).
Similar to SLF4j, it is split into a facade (consisting of interfaces) and a logger implementation.
While SLF4j has not seen any major release since Java 6, Log4j 2 makes use of lambdas and other features introduced in recent Java versions.
This allows for smoother integration in modern applications.

Finally, if you like to be an oddball and support small project teams which offer an awesome niche product, have a look at [tinylog](https://tinylog.org/v2/).
It is very fast, simple, and clean to use but lacks some of the features big libraries offer.

# How to combine logs

In a typical modern application, log messages come from many places.
While some are sent from your business logic, others originate in a library you use.
Some come from a third-party application, or the environment your application is running in.
There are two main concepts to talk about that help you keep track of this flood of messages.
If you are currently not using both of them, it may just be the right time to take a closer look at the opportunities you are missing out on.

## Logging Facades

A logging facade captures the messages from different logging frameworks within your application.
All of these messages are then routed to a central appender and written to a common log for your application.

![Logging Facade Diagram](/assets/posts/2021-06-02-use-a-logger/logging-facade.png)

## Centralized Logging

With the rise of microservices, distributed applications, and containerized production environments, the need to see beyond the border of your application arose.
It is no longer sufficient to look at the log of an application in isolation.
All the logs from different sources need to be aggregated to get the complete picture.
Centralized logging solutions allow you to collect, store, browse, process and analyze log messages from everywhere.
Some of them even offer alerting, intelligent search, and other nifty features.

There are many products on the market.
Some are open-source others are proprietary cloud solutions.
The best recommendation we can give to you is: go out there and explore which one is the right tool for your needs.
Here are some popular ones to get you started:
* [ELK Stack](https://www.elastic.co/what-is/elk-stack)
* [Graylog](https://www.graylog.org/)
* [Splunk](https://www.splunk.com/)

![Logging Facade Diagram](/assets/posts/2021-06-02-use-a-logger/centralized-logging.png)
