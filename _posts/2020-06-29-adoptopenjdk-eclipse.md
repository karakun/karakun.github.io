---
layout: post
title:  'AdoptOpenJDK becomes Eclipse Adoptium'
author: hendrik
featuredImage: comment
excerpt: 'AdoptOpenJDK officially announced its accession to the Eclipse Foundation and the future name "Eclipse Adoptium". A necessary and sensible move? A comment by Hendrik Ebbers, member of the Technical Steering Committee of AdoptOpenJDK.' 
categories: [Java, OpenJDK, Eclipse, Adoptium]
header:
  text: AdoptOpenJDK becomes <span class="my-karakun">Eclipse Adoptium</span>
  image: post
---

AdoptOpenJDK has become one of the biggest Java respectively OpenJDK distributions with [more than 170M downloads](https://dash-v2.adoptopenjdk.net/). As a member of the Technical Steering Committee (TSC) of AdoptOpenJDK, I did not only follow the project in the last couple of months but was at least in pieces a vital part of it.

## Why AdoptOpenJDK?

If I was asked 5 years ago about where to download Java I definitely would have referred to the download pages of the Oracle JDK. But in the last couple of years, things have changed. Thanks to OpenJDK, in theory everyone can build its own Java distribution. AND, licensing of Oracle JDK has changed - [it is no longer free to use](/java/2018/06/25/java-releases.html). This is why more and more Java distributions came up and it is sometimes not easy to get a clear overview of this JDK jungle.

![3 Duke Suspects](/assets/images/pages/jdks/3duke_suspects.png)

In contrast to OpenJDK distributions by companies like Oracle, IBM, Azul or Bellsoft, [AdoptOpenJDK](https://adoptopenjdk.net/) is a community-based project providing Java binaries for a large number of platforms. Besides the common binaries for Windows x64, Mac x64 and Linux x64 there are also builds for AIX and ARM.

![Platforms supported by AdoptOpenJDK](/assets/posts/2020-07-13-adoptium/platforms.png)

One of the maybe biggest advantages of the provided builds is the [Long-Term-Support (LTS)](https://adoptopenjdk.net/support.html#roadmap). Unlike some commercial providers of Java binaries, AdoptOpenJDK delivers free updates with important security fixes for Java LTS versions (e.g. Java 8 and 11) even 6 months later.

AdoptOpenJDK does not only build Java binaries but also runs tests with its own [test toolkit AQA](/2020/02/26/OpenJDK-builds.html). Every build passes tests in different categories like unit tests, performance or integration tests. In addition to the tests defined in OpenJDK, Technology Compatibility Kits (TCK) of various Eclipse microprofile specifications or the complete testsuite of Apache Tomcat are run against the JDK. This means millions and millions of tests for every release and nightly build and guaranteed compatibility with established applications.

![testing](/assets/posts/2020-07-13-adoptium/adopt-3-1536x543.png)

## And why do you move to Eclipse?
All this (plus a few not mentioned things) has led to a strong growth in a pretty short time and existing structures and organizations reached their limits. Thus, the Technical Steering Committee (TSC) of AdoptOpenJDK investigated several options for moving AdoptOpenJDK to a software foundation or similar structure. We researched many alternatives, and ultimately the Eclipse Foundation came out as the clear front runner. 

Joining the Eclipse Foundation allows AdoptOpenJDK to continue to grow and to focus on our mission. Furthermore, becoming part of the Eclipse family strengthens our vendor independence while maintaining a strong relationship with existing sponsors and the Java community as a whole. And, the Eclipse Foundation has a deep history with the Java ecosystem. It provides proven structures for strong, vendor-neutral project governance, IP flow protection, as well as world-class marketing, legal, and hosting support.

## Ok, and why a new project name?
As part of the move to the Eclipse Foundation, we will undergo a project rename. As OpenJDK is a registered trademark of Oracle, this step was somehow overdue. Of course, we respect existing trademarks, and while the project would retain a close association with the work at OpenJDK, we don’t want anyone to be confused by our current name similarity.

Adoptium is a compound word of “AdoptOpenJDK” and the Latin suffix “-ium”. Today already, we often hear "Adopt" when people are talking about the project. Thus, "Adoptium" is in my opinion a very good name.

## Meet us at the AdoptOpenJDK Virtual Roadshow
If you’re curious to understand the difference between OpenJDK, Oracle Java, AdoptOpenJDK and all the other distributions, then you should join us at a JUG near you! This talk will cover how we build on over 15 different platforms, execute over 87 million tests and distribute OpenJDK binaries to millions of users. We will also cover how AdoptOpenJDK binaries compare against the Java binaries that you use today.

![Speaker of Virtual Roadshow](/assets/posts/2020-07-13-adoptium/speakers.png)

George Adam (Microsoft) and myself are presenting this talk. If you want to check the dates and JUGs we're presenting at, please go to [https://blog.adoptopenjdk.net/2020/07/adoptopenjdk-virtual-roadshow/](https://blog.adoptopenjdk.net/2020/07/adoptopenjdk-virtual-roadshow/). We're looking forward to seeing you virtually...

## Need a guide through the JDK jungle?
We at [Karakun](https://karakun.com) offer consultancy services to companies to select the right JDK, Vendor and update strategy. For assistance, either contact [me](/people/hendrik) or write an email to [info@karakun.com](mailto:info@karakun.com).