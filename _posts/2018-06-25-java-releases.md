---
layout: post
title:  "Do I need to pay for Java now?“
author: hendrik
featuredImage: travis
excerpt: „This post gives an overview of the new Java release train as it was announced by Oracle. Next to
 this the article provides some important information and hints how you should handle new Java releases in
 future and helps you to decide if you need to buy commercial Java support in future.“
tags: [Java]
headerImage: post
---

This year several changes are introduced to the Java release and support model that is provided by Oracle.
Based on this several Java developers have questions on how this changes will affect their future work and 
what strategy should be used when thinking about Java version updates in applications. One think that is 
often discussed but never really end in a clear answer is if companies need to pay for Java in near future. 
This article will give an overview of the new Java release train, the commercial support for Java. Based on 
this several solutions and workflows for supporting Java in Java based applications will b e discussed.

## The Java release train
Last year Oracle announced a new release train for Java and this new schedule really changes how companies 
will work with Java in (near) future. Before we have a deeper look at the new release model we will look back 
and see how Java releases were done and used in the past and even today. 

The last years it was more or less easy for an application developer to depend on a supported version of 
Java. Till Java 8 all version had a quite long lifetime and free updates were still provided after the next 
version was released. Based on this the periode in that a company could savely update Java to the newest 
version was quite long. The following diagram shows a time graph of the last Java releases and the time of 
it’s official support by oracle.

XXXXXX BILD XXXXXXXX roadmap-java.png

As you can see in the diagram the free support of a Java version was quite long and the timespan in that 2 
versions were supported in parallel was long enough to plan and handle a migration of your software to the 
newest Java version. Even if this period was to short based on some constraints it was possible to extend it 
by buying commercial support for a Java version.

As already said all this points will change in future based on that the new release train for Java. The 
following image shows the release train as it was announced by Oracle.

XXXXXX BILD XXXXXXXX

Starting with Java 9 mostly all Java version will only have a lifetime of 6 month. After this periode no more 
updates will be provided for the version. Even if you have a commercial contract with Oracle you won’t get 
any additional updates for Java 9 or 10 after this 6 months. Next to this all XX years a LTS ( long term 
support) version of Java will be released. The first version with LTS support will be Java 11 that is 
scheduled for this autumn. This LTS version will provide a commercial support with updates for XX years. The 
most important fact is that updates for LTS version can only be accessed and used by people that buy 
commercial support at Oracle. Based on my daily work I would say that most companies that create software 
based on Java do not have commercial support today since the release train of Java was quite „friendly“ and 
an adoption of the newest version was easily possible.

## Why is Oracle doing this?
TODO: ASChneller neue Features, Geld verdienen

## What does the new release train mean to my company?
If we concentrate on the Oracle JDK the answer to that question is quite easy and you can choose between 3 
options:
- Update the Java version all 6 month. By doing so you will always depend on the supported Java version and 
will automatically get all important feature and security updates.
- Buy commercial support from Oracle and migrate only from one LTS version to the next LTS version. This 
would mean that you migrate from Java 8 to Java 11 maybe within the next year and than from Java 11 to Java 
XX in XXXX.
- Stay on a Java version that is not supported anymore. Once the free support of a Java version ends nobody 
forbids you to use the unsupported version in future. By doing so you do not need to pay commercial support 
or get into a maybe stressfull migration all 6 month. While you can now easily decide on your own when you 
want to migrate to a newer Java version you will miss security updates and bugfixes. While a bugfix is not 
really critical as long as the bug do not affect your software, open security issues can end in horrible 
problems.
While this option provides several strategies and mostly at least one of them can be adopted to the general 
workflow of a developer team you should have one important point in mind: One of the main reasons why Oracle 
has chosen this new release train is to offer new languages features faster. When generics, annotations or 
lambdas were introduced in Java the developer community had quite a lot of time to understand and adopt that 
new features. Next to this frameworks in general stayed updated on one of the 2 last Java versions. Today you 
are in a good position if your application depends on Java 8 for example. In this case you can use mostly all 
Java dependencies in your software and had a lot of time to maker yourself familiar with lambdas. This points 
will change in near future. While today you often hear that Java 11 will be the next „big release“ were 
libraries and application will depend on you need to keep in mind that even this version will only have a 6 
month lifetime if you do not buy commercial support. Today nobody knows what features will be introduced in 
the JDK after version 11. Maybe Java 13 will introduce something were framework developers will depend on. 
Based on that such dependencies can not be used in your application anymore if you stay on an older Java 
version. So even if you buy commercial support or skip Java releases for your software it will be more 
important than before to update to a current Java version.
