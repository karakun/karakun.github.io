---
layout: post
title:  'Do I need to pay for Java now?'
author: hendrik
featuredImage: tip-jar
excerpt: 'This post gives an overview of the new Java release train as it was announced by Oracle. Next to
 this the article provides some important information and hints how you should handle new Java releases in
 future and helps you to decide if you need to buy commercial Java support in future.'
tags: [Java]
headerImage: post
---

This year many changes are introduced to the Java release and support model that is provided by Oracle.
Based on this several Java developers have questions on how this changes will affect their future work and 
what strategy should be used when thinking about Java version updates in applications. One thing that is 
often discussed but never really ends in a clear answer is if companies will need to pay for Java in near future. 
This article will give an overview of the new Java release train and the commercial support for Java. Based on 
this several solutions and workflows for supporting and managing Java versions for Java based applications will 
be discussed.

## The Java release train
Last year Oracle announced a new release train for Java and this new schedule really changes how companies 
will work with Java in the (near) future. Before we have a deeper look at the new release model we will look back 
and see how Java releases were done and used in the past and even today. 

Until now it was (for most developers) fairly simple to build and run an application on a supported version of
Java. Till Java 8 all version had a quite long lifetime and free updates were still provided after the next 
version was released. Based on this the period in that a company could safely update Java to the newest 
version was quite long. The following diagram shows a time graph of the last Java releases and the time of 
its official support by oracle.

![Java release train]({{ "/assets/posts/java-release-train/old-roadmap.png" | absolute_url }})

As you can see in the diagram the free support of a Java version was quite long and the timespan in which 2 
versions were supported in parallel was long enough to plan and handle a migration of your software to the 
newest Java version. Even if this period was to short based on some constraints it was possible to extend it 
by buying commercial support for a Java version.

As already said all this will change in the future based on the new release train for Java. The 
following image shows the release train as it was announced by Oracle.

![Future Java release train]({{ "/assets/posts/java-release-train/roadmap-java.png" | absolute_url }})

Starting with Java 9 most Java version will only have a lifetime of 6 month. After this period no more 
updates will be provided for the version. Even if you have a commercial contract with Oracle you won’t get 
any additional updates for Java 9 or 10 after this 6 months. Next to this all 3 years a LTS (long term 
support) version of Java will be released. The first version with LTS support will be Java 11 that is 
scheduled for fall 2018. This LTS version will provide a commercial support with updates for 8 years. The 
most important fact is that updates for LTS version can only be accessed and used by people that buy 
commercial support at Oracle. Based on my daily work I would say that most companies that create software 
based on Java do not have commercial support today since the release train of Java was quite „friendly“ and 
an adoption of the newest version was easily possible.

## Why is Oracle doing this?
You might ask why Oracle changes the releases of Java in such an big way. To be true Oracle is not the only big company that 
changed the release cycle of important products in the last years. Simply have a look at the release timeline of 
[Chrome](https://www.chromestatus.com/features/schedule) or the [Go programming language](https://golang.org/doc/devel/release.html)
and you will see that more and more products try to shorten the periods between releases. While this is in general based on agile
methods and the workflow that we use in software development we can also recognise 2 main benefits that are an
outcome of the new Java release train.

The most important point for faster releases of Java is the early availability of new features. Between the release of Java 7 
and Java 8 was a period of 3 years and that was more or less the time we as developers needed to wait for new features like
lambdas. With the new release train new features can easily be published every 6 month. To be true a feature like lambdas or 
the Java module system needs much more time than 6 month to be implemented but once it's ready it can be added to the next version
that will be released at least 6 month later. In the past Java releases were delayed since some specific features simply
were not production ready at the code freeze. Since Oracle didn't want to move that features to next release the complete JDK 
release was delayed. With a new release every 6 month skipping a feature and adding it to one of the next versions is no
problem anymore.

Next to the there is a second point that will provide a big benefit to Oracle. With the new schedule a period in that Oracle
provide free support for more than 1 Java version is completely gone. Based on this companies need to plan migration to new
Java versions in a much stricter way than before. Based on internal constraints like resources it won't be possible for all
companies to always update to a new Java version every 6 months. Here Oracle provides an updated commercial support model for
companies to use the LTS version of Java much longer than 6 month and receive bugfixes and security updates long after the 
end of the public support. Since the commercial support might become an important option for several companies in near future 
we will have a deeper look at its conditions and price now.

## What is Oracles price for commercial support of Java?
Based on the changed release train a new commercial support model for Java was announced by Oracle. This 
provides 2 different types of support subscriptions. One of them targets Java on the desktop and the other one Java 
on the server and Java in general. If you do not use Java on the desktop the "Java SE Subscription" will be the right support
model for you. Iy you use Java on the desktop for client applications you need to pay support for each desktop user by 
choosing the "Java SE Desktop Subscription". If your software uses a Java server and Java based clients you need to pay for 
both subscriptions.

The following table shows the price for the "Java SE Subscription" that cost is based on the processor count:

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

When running Java based servers on bare metal this price model is quite easy and you simply pay for the CPUs that your server 
machines contain. If you work with a cloud based infrastructure things will become much harder to understand because the real 
CPUs are shared between multiple virtual machines or containers (like in Docker and Kubernetes).

Next to this model that is usable for Java on the server Oracle provides a special support for Java on the desktop. Here the
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
use Java WebStart technology to provide desktop clients you should take care about the current situation as fast as possible 
since Oracle has removed WebStart from Java. You can find all the needed information [here]({{ site.baseurl }}{% link webstart.md %}).

## What does the new release train mean to my company?
If we concentrate on the Oracle JDK the answer to that question is quite easy and you can choose between 3 
options:

- Update the Java version every 6 month. By doing so you will always build on the supported Java version and 
will automatically get all important feature and security updates.
- Buy commercial support from Oracle and migrate only from one LTS version to the next LTS version. This 
would mean that you migrate from Java 8 to Java 11 maybe in the first half of 2019 and than from Java 11 to Java 
17 in 2022.
- Stay on a Java version without updates and bugfixes from Oracle. Once the free support of a Java version ends 
nobody forbids you to use the unsupported version in future. By doing so you do not need to pay commercial support 
or get into a maybe stressful migration every 6 months. With this approach you can freely decide when to migrate to a newer 
Java version. On the downside you will miss out on newly released bugfixes and security updates. While a bugfix is not 
really critical as long as the bug does not affect your software, open security issues can end in horrible 
problems.

The 3 options presented above outline different strategies. Which one to choose depends on many factors such as importance of 
project, impact of failure, budget, company policy and many more. This new release train brings a great change to the Java 
environment and while many companies, ops and devs will struggle at first there is a reason behind this change. One of Oracles 
designated goals with the new release trains is to deliver new language features faster. 
When new language features are introduced in Java they take a considerable amount of time until they are adopted and widely 
used in the daily work. As an example both the generic (introduced with Java 5) as well as lambdas (introduced with Java 8) 
have only slowly made their progress to the community. Part of this slow adoption speed is due to the countless open source 
frameworks and libraries which are a core part of the Java ecosystem. A new language feature will not get adopted until it can 
inter operate with the dependencies of an application.

This points will change in near future. While today you often hear that Java 11 will be the next „big release“ were 
libraries and application will depend on you need to keep in mind that even this version will only have a 6 
month lifetime if you do not buy commercial support. Today nobody knows what features will be introduced in 
the JDK after version 11. Maybe Java 13 will introduce something were framework developers will depend on. 
Based on that such dependencies can not be used in your application anymore if you stay on an older Java 
version. So even if you buy commercial support or skip Java releases for your software it will be more 
important than before to update to a current Java version.

## Is Oracle our last hope?
Until now all strategies and solutions to handle future Java versions are based on the new release train of Oracle. While
a large majority of all installed JDKs is coming from Oracle there are several other vendors on the market that provide
a JDK and a JVM. All this products have one important thing in common: They all are built on top of OpenJDK. 

The OpenJDK is the open source part of Java that is used to build the Oracle JDK and JRE as a native binary that you normally
will install on your system. To be honest most of the parts that are used by Oracle to build Java binaries are part opf the 
OpenJDK. Therefore even the OpenJDK provides Java binaries. This binaries can be downloaded for free and often used as Java
installation in Linux distributions. All core parts of Java are part of the OpenJDK binaries. Only some Oracle specific tools 
like "Java Flight Recorder" are missing.

Oracle and the OpenJDK itself are not the only players that provide native builds based on the open sources of Java. Some
IT companies provide specific JDK builds for their products. A good example in this area is IBM that provides its own JDK for
AIX systems. Next to the first companies that create a business model based on native Java builds shown up the last years.
Here the biggest player is definitely Azul. With the new release model of Java and the need for commercial support in near 
future maybe more companies will be created based on this business to provide Java build artifacts for the different needs 
of all the customers. While this might be an interesting future for Java no one knows today what impact this will have in 
IT companies. Therefore we should have a concrete look at the vendors that are already on the market today.

### OpenJDK
Open JDK provides builds that are directly based on the open source part of Java. Builds of OpenJDK can be downloaded [here](https://adoptopenjdk.net/index.html).
The roadmap of the OpenJDK is more or less the same as the roadmap that Oracle has defined. The big different is that OpenJDK 
will provide LTS releases longer than 6 month but by far not as long as Oracle
will do with the commercial support model. While Oracle will provide commercial support for Java 11 until September 2026 the
OpenJDK community will provide its last build for Java 11 in September 2022. This means that you will get free updates for Java
11 much longer by using OpenJDK than Oracle JDK but on the other hand there is just no commercial support for OpenJDK builds.
An overview about the planed roadmap of OpenJDK can be found [here](https://adoptopenjdk.net/support.html).

### Azul
https://www.azul.com/products/azul_support_roadmap/
https://www.azul.com/products/zulu-and-zulu-enterprise/zulu-enterprise-java-support-options/
![Azul roadmap]({{ "/assets/posts/java-release-train/roadmap-azul.png" | absolute_url }})

### IBM
To support its own hardware IBM provides native JDK bundles for AIX, Linux, z/OS and IBM i. Until now IBM offers free versions 
of the build for download. For Java 7 and 8 IBM still provides updates with security- and bugfixes. A special commercial 
support for this built is offered by IBM for its enterprise customers. Until now IBM has not announced an end of life for the 
Java 8 support but Java 7 support will end in September 2019. Based on the new Java release schedule IBM has announced that 
not all future Java versions will be supported and provided by IBM. Only for Java LTS versions the company will provide native 
builds. Based on this Java 11 will be the next Java version after Java 8 for that IBM will provide native builds. More 
information about the JDK by IBM can be found [here](https://developer.ibm.com/javasdk).

### RedHat
https://access.redhat.com/articles/1299013
https://access.redhat.com/articles/3409141

### OpenJ9
https://www.eclipse.org/openj9/

### SAP
https://sap.github.io/SapMachine/

## Conclusion
todo

Overview of future Java releases.

{:.table}
| Java version | free 6 month support  | free MTS support | commercial MTS support | commercial LTS support |
| ------------ | --------------------- | ---------------- | ---------------------- | ---------------------- |
| 8            | all                   | all              | all                    | all                    | 
| 9            | OpenJDK, Oracle, Azul | -                | Azul                   | -                      | 
| 10           | OpenJDK, Oracle, Azul | -                | -                      | -                      | 
| 11           | all                   | OpenJDK          | all                    | all                    |
| 12           | OpenJDK, Oracle, Azul | -                | -                      | -                      | 
| 13           | OpenJDK, Oracle, Azul | -                | Azul                   | -                      | 
