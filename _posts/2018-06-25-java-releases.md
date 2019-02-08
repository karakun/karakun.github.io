---
layout: post
title:  'Do I need to pay for Java now?'
author: hendrik
featuredImage: tip-jar
excerpt: 'This post gives an overview of the new Java release train as it was announced by Oracle. Plus, the article provides some important 
information and hints how you should handle new Java releases in the future and helps you to decide if you need to buy commercial Java support 
or not.'
permalink: '/java/2018/06/25/java-releases.html'
categories: [Java]
header:
  text: Do I need to pay for <span class="my-karakun">Java<span> now?
  image: post
---

This year many changes are introduced to the Java release and support model that is provided by Oracle.
Because of this many Java developers have questions on how this changes will affect their future work and
what strategy should be used when thinking about Java version updates in applications. One concern that is
often discussed but remains mostly unanswered is if companies will need to pay for Java in the near future.
This article will give an overview about the new Java release train and the commercial support for Java. Based on
this several solutions and workflows for supporting and managing Java versions for Java based applications will
be discussed.

## The Java release train
Last year Oracle announced a new release train for Java and this new schedule really changes how companies
will work with Java in the (near) future. Before we have a deep dive into the new release model we will take a look back
at how Java releases have been done and how they have been adopted in the past until today.

Until now it was (for most developers) fairly simple to build and run an application on a supported version of
Java. Until Java 8 all version had a quite long lifetime and free updates were still provided after the next
version was released. Thus, the period in which a company could safely update Java to the newest
version was quite long. The following diagram shows a time graph of the last Java releases and the time of
its official support by oracle.

![Java release train]({{ "/assets/posts/2018-06-25-java-releases/diagramm-past.png" | absolute_url }})

As you can see in the diagram the free support of a Java version was quite long and the timespan in which 2
versions were supported in parallel was long enough to plan and handle a migration of your software to the
newest Java version. Even if this period was to short based on some constraints it was possible to extend it
by buying commercial support for a Java version.

As already said all this will change in the future because of the new release train for Java. The
following image shows the release train as it was announced by Oracle.

![Future Java release train]({{ "/assets/posts/2018-06-25-java-releases/diagramm-oracle-1.png" | absolute_url }})

Starting with Java 9 most Java version will only have a lifetime of 6 months. Updates won't be provided for the version after this period. 
Even if you have a commercial contract with Oracle you won’t get any additional updates for Java 9 or 10 after these 6 months. 
Plus, all 3 years a LTS (long term support) version of Java will be released. The first version with LTS support will be Java 11 that is
scheduled for fall 2018. This LTS version will provide a commercial support with updates for 8 years. The
most important fact is that updates for LTS versions can only be accessed and used by people that buy
commercial support at Oracle. My experience is that today most companies that develop Java based software do not have commercial support 
since the release train of Java was quite „friendly“ and an adoption of the newest version was easily possible.

But there is another important change in the licence model of the Oracle JDK.
So far, most developers simply downloaded a single Java version from Oracle (JRE or JDK)
and installed it into all environments (development, testing and production).
Starting with Java 11, the Oracle JDK is restricted to development and testing environments.
Oracle JDKs may only be used in production if you buy the commercial support.
Instead, Oracle will provide Java builds based on OpenJDK for free which can be used in production.
But for the official Oracle JDK the real roadmap will look like this:

![Future Oracle JDK release train]({{ "/assets/posts/2018-06-25-java-releases/diagramm-oracle-2.png" | absolute_url }})

## Why is Oracle doing this?
You might ask why Oracle changes the releases of Java in such a big way. To be true Oracle is not the only big company that
changed the release cycle of important products in the last years. Simply have a look at the release timeline of
[Chrome](https://www.chromestatus.com/features/schedule) or the [Go programming language](https://golang.org/doc/devel/release.html)
and you will see that more and more products try to shorten the periods between releases. While this is in general based on agile
methods and the workflow that we use in software development, we can also recognise 2 main benefits that are a direct
consequence of the new Java release train.

The most important point for faster releases of Java is the early availability of new features. Between the release of Java 7
and Java 8 was a period of 3 years and that was more or less the time we as developers needed to wait for new features like e.g.
lambdas. With the new release train, new features can easily be published every 6 month. To be true a feature like lambdas or
the Java module system needs much more time than 6 month to be implemented but once it's ready it can be added to the next version
that will be released at least 6 month later. In the past, Java releases were delayed since some important features simply
were not production ready at the code freeze. Since Oracle didn't want to move that features to next release the complete JDK
release was delayed. With a new release every 6 months skipping a feature and adding it to one of the next versions is no
problem anymore.

![Duke]({{ "/assets/posts/2018-06-25-java-releases/duke-11.jpg" | absolute_url }})

There is a second point that will provide a big benefit to Oracle. With the new schedule, the period in which Oracle needs to
provide free support for more than 1 Java version is completely gone. Therefore, companies need to plan migrating to new
Java versions in a much stricter way than before. Because of internal constraints, like resources, not all companies will be
able to update to a new Java version every 6 months. That's why Oracle provides an extended commercial support model for
companies to use the LTS version of Java much longer than 6 months and to receive bugfixes and security updates after the
end of the public support. Since the commercial support might become an important option for several companies in the near future
we will have a deeper look at its conditions and price now.

## What is Oracles price for commercial support of Java?
Based on the changed release train, a new commercial support model for Java was announced by Oracle. This
provides 2 different types of support subscriptions. One of them targets Java on the desktop and the other one Java
on the server and Java in general. If you do not use Java on the desktop the "Java SE Subscription" will be the right support
model for you. If you use Java on the desktop for client applications you need to pay support for each desktop user by
choosing the "Java SE Desktop Subscription". If your software uses a Java server and Java based clients you need to pay for
both subscriptions.

The following table shows the price for the "Java SE Subscription" (based on number of processors):

{:.table}
| Processor count | Monthly price per processor          |
| --------------- | ------------------------------------ |
| 1-99            | $25.00                               |
| 100-249         | $23.75                               |
| 250-499         | $22.50                               |
| 500-999         | $20.00                               |
| 1,000-2,999     | $17.50                               |
| 3,000-9,999     | $15.00                               |
| 10,000-19,999   | $12.50                               |
| 20,000+         | Oracle must be contacted for details |

When running Java based servers on bare metal, this price model is quite easy and you simply pay for the CPUs that your server
machines contain. If you work with a cloud based infrastructure things will become much harder to understand because the real
CPUs are shared between multiple virtual machines or containers (like in Docker and Kubernetes).

In addition to this model (for Java on the server), Oracle provides a special support for Java on the desktop. In this case, the 
price for the commercial support is based on the number of users / client terminals. The following table shows the cost for
the "Java SE Desktop Subscription" model:

{:.table}
| Users / client machines | Monthly price per user / client machine |
| ----------------------- | --------------------------------------- |
| 1-999                   | $2.50                                   |
| 1,000-2,999             | $2.00                                   |
| 3,000-9,999             | $1.75                                   |
| 10,000-19,999           | $1.50                                   |
| 20,000-49,999           | $1.25                                   |
| 50,000+                 | Oracle must be contacted for details    |

Commercial support for Java applications on the desktop might become quite important for some companies since Oracle will drop
several important desktop features from the JDK starting with Java version 11. If you are using Java on the desktop I highly
recommend to read [this article about the Java client roadmap that was announced by Oracle in 2018](https://dzone.com/articles/what-the-future-java-releases-will-mean-for-legacy).

If you need more information about the commercial support for Java that is offered by Oracle you should have a look at
[this official document](http://www.oracle.com/technetwork/java/javaseproducts/overview/javasesubscriptionfaq-4891443.html). If you
are using Java WebStart technology to distribute desktop clients you should take care about the current situation as fast as possible
since Oracle has removed WebStart from Java. You can find all the needed information [here]({{ site.baseurl }}{% link webstart.md %}).

## What does the new release train mean to my company?
Concentrating on the Oracle JDK, the answer to that question is quite easy and you can choose between 3
options:

- Update the Java version every 6 months. When doing this, you will always build on the supported Java version and
will automatically get all important feature and security updates.
- Buy commercial support from Oracle and migrate only from one LTS version to the next LTS version. This
would mean that you migrate from Java 8 to Java 11 maybe in the first half of 2019 and than from Java 11 to Java
17 in 2022.
- Stay on a Java version without updates and bugfixes from Oracle. Once the free support of a Java version ends
nobody forbids to keep on using the unsupported version. You avoid paying for commercial support
or getting into a maybe stressful migration every 6 months. You are free to decide when to migrate to a newer
Java version. On the downside, you will miss out all released bugfixes and security updates. While a bugfix is not
really critical as long as the bug does not affect your software, open security issues can end in horrible
problems.

The 3 options presented above outline different strategies. Which one to choose depends on many factors such as importance of
the project, impact of failure, budget, company policy and many more. This new release train brings a great change to the Java
environment and while many companies, ops and devs will struggle at first there is a reason behind this change. One of Oracles
designated goals with the new release trains is to deliver new language features faster.

When new language features are introduced in Java, they take a considerable amount of time until they are adopted and widely
used in the daily work. As an example, both generics (introduced with Java 5) as well as lambdas (introduced with Java 8)
have made their progress to the community very slowly. Part of this slow adoption speed is due to the countless open source
frameworks and libraries which are a core part of the Java ecosystem. A new language feature will not get adopted until it can
interoperate with the dependencies of an application.

This point will change in the near future. While today you often hear that Java 11 will be the next „big release“ where
libraries and applications will depend on your need you have to keep in mind that even this version will only have a 6
month lifetime if you do not buy commercial support. Today, nobody knows what features will be introduced in
the JDK after version 11. Maybe Java 13 will introduce a feature which framework developers will depend on.
Based on that such dependencies can not be used in your application anymore if you stay on an older Java
version. So even if you buy commercial support or skip Java releases for your software it will be more
important than before to update to a current Java version.

## Is Oracle our last hope?
Until now, all strategies and solutions to handle future Java versions are based on the new release train of Oracle. While
a large majority of all installed JDKs is coming from Oracle there are several other vendors on the market that provide
a JDK and a JVM. All these products have 1 important thing in common: They are built on top of OpenJDK.

The OpenJDK is the open source part of Java that is also used to build the Oracle JDK and JRE as a native binary that you normally
will install on your system. To be honest, most of the parts that are used by Oracle to build Java binaries are part of the
OpenJDK. Therefore, even the OpenJDK provides Java binaries. Those binaries can be downloaded for free and are often used as Java
installation in Linux distributions. All core parts of Java are part of the OpenJDK binaries. Only some Oracle specific tools
like "Java Flight Recorder" are missing.

Oracle and the OpenJDK itself are not the only players that provide native builds based on the open sources of Java. Some
IT companies provide specific JDK builds for their products. A good example is IBM that provides its own JDK for
AIX systems. Also the first companies with a business model based on native Java builds have recently shown up.
The biggest player is definitely Azul. With the new release model of Java and the need for commercial support in the near
future maybe more companies will emerge based on the business model of providing Java build artifacts for the different needs.
While this might be an interesting future for Java no one knows today what impact this will have in
IT companies. Therefore we should have a concrete look at the vendors that are already on the market today.

![Duke]({{ "/assets/posts/2018-06-25-java-releases/duke-logos.jpg" | absolute_url }})

### OpenJDK
Open JDK provides builds that are directly based on the open source part of Java. Builds of OpenJDK can be downloaded [here](https://adoptopenjdk.net/index.html).
The roadmap of the OpenJDK is more or less the same as the roadmap that Oracle has defined. The big difference is that OpenJDK
will provide LTS releases longer than 6 month but by far not as long as Oracle
will do with the commercial support model. While Oracle will provide commercial support for Java 11 until September 2026, the
OpenJDK community will provide its last build for Java 11 in September 2022. This means that you will get free updates for Java
11 much longer by using OpenJDK than Oracle JDK but on the other hand there is just no commercial support for OpenJDK builds.
An overview about the planned roadmap of OpenJDK can be found [here](https://adoptopenjdk.net/support.html).

### Azul
It looks like Azul offers a really good support for all companies that do not want to skip all Java version between LTS releases
but can not switch to the newest version every 6 month. In addition to the support for all LTS releases - Azul provides 1 more year
of support than Oracle - Azul offers support for so called MTS (Medium Term Support) releases for the Zulu JDK. With this, companies can buy
commercial support for every second Java version. The support duration of those versions are different. Azul tries to provide a
good time range to prepare a migration to the next version and defines 3 different durations for support of Java versions.
The following image gives an overview of the future Java version and the period supported by Azul.

![Azul roadmap]({{ "/assets/posts/2018-06-25-java-releases/diagramm-azul.png" | absolute_url }})

Because Zulu is a Java distribution targeting server side applications, Azul does not provide any support model for Java
on the desktop. Unlike Oracle the commercial support of Zulu is not defined per CPU but based on the number of systems. A
system is defined as a physical or virtual server or a desktop PC. As you can see in the following table, the support model
does not really make sense for desktop applications but is quite interesting for server side applications.

{:.table}
| systems   | Price/year (Standard Support) | Price/year (Premium Support) |
| --------- | ----------------------------- | ---------------------------- |
| 1-25      | $12,000                       | not available                |
| 16-100    | $28,750                       | $34,500                      |
| 101-1000  | $86,250                       | $103,500                     |
| unlimited | $258,750                      | $310,500                     |

 The only difference between standard and premium support is the availability of the support. By buying premium support you can call 
 Azul 24x7. More details might be added by an update or comment to this article. Additional information of the Azul support model for Zulu can be found
 [here](https://www.azul.com/products/azul_support_roadmap/)
 and [here](https://www.azul.com/products/zulu-and-zulu-enterprise/zulu-enterprise-java-support-options/).

### IBM
To support its own hardware, IBM provides native JDK bundles for AIX, Linux, z/OS and IBM i. Until now, IBM offers free versions
of the build for download. For Java 7 and 8, IBM still provides security updates and bugfixes. A special commercial
support for this build is offered by IBM for its enterprise customers. Until today, IBM has not announced an end of life for the
Java 8 support but Java 7 support will end in September 2019. Based on the new Java release schedule, IBM has announced that
not all future Java versions will be supported and provided by IBM. Only for Java LTS versions the company will provide native
builds. Based on this, Java 11 will be the next Java version after Java 8 for which IBM will provide native builds. More
information about the JDK by IBM can be found [here](https://developer.ibm.com/javasdk).

### RedHat
Like IBM RedHat won't provide Java 9 and 10 releases. The next distribution that RedHat plans to release is OpenJDK 11 for
Red Hat Enterprise Linux 7. Currently Java 8 is the supported release for Red Hat Enterprise Linux and the company will
support it until 2020. A general information from RedHat can be found [here](https://access.redhat.com/articles/1299013).

## Conclusion
By defining the new release train of Java, Oracle created a big challenge for enterprise companies.
Not all of them will be able to update their application to the newest Java version every 6 months.
Especially companies with many or large Java based applications need to rethink their migration strategy.
This could create a new market for companies to offer extended support for specific Java version and/or features.
The following table gives an overview of future Java releases and the support which will be offered by the biggest vendors as of today.

{:.table}
| Java version | free 6 month support  | free MTS support | commercial MTS support | commercial LTS support |
| ------------ | --------------------- | ---------------- | ---------------------- | ---------------------- |
| 8            | all                   | all              | all                    | all                    |
| 9            | OpenJDK, Oracle, Azul | -                | Azul                   | -                      |
| 10           | OpenJDK, Oracle, Azul | -                | -                      | -                      |
| 11           | all                   | OpenJDK          | all                    | all                    |
| 12           | OpenJDK, Oracle, Azul | -                | -                      | -                      |
| 13           | OpenJDK, Oracle, Azul | -                | Azul                   | -                      |
