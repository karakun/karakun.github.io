---
layout: post
title:  'Why a native bundle can not always replace Web Start based applications'
author: hendrik
featuredImage: jar-2
excerpt: "Starting with the release of Java 11 Web Start won't be anymore part of the Oracle JDK. Based on this several
companies migrate away from Web Start to the build of native bundles that provide a native executable which contains the
specific application and a JDK. While this is a very good approach in general the Web Start technology contains some
important features for enterprise that can't easily be replaced by a native bundle."
tags: [Java, WebStart, Native, Build]
headerImage: post
---

As you might already know Oracle removed Java Web Start from Java string with version 11. For companies that develop or 
maintain desktop applications that are distributed by Web Start this announcement created a big issue that needs to be 
solved within the next months since Java 11 will be released in September 2018 and the free Java 8 support will end in 
January 2019.

Next to this you might have noticed that we at Karakun try to create [an open source sucessor of Web Start]({{ site.baseurl }}{% link webstart.md %}). 
While using such a product would be a good strategie to ensure the operationality of a software that uses Web Start there 
are some other possible solutions that can be used. On of this solutions is the distribution of a native bundle that 
contains a JRE and the application. In this article we want to have show how a native bundle can be credated and what 
benefits and downsides native bundles have against the use of Oracle Web Start or our future product.

From our point of view there are currently 2 different popular approach to create a native bundle: using the javafxpackager of the JDK or using the commercial software install4J. Let's have a look at both of them.

## Creating a native bundle by using JDK features
Todo

## Creating a native bundle with install4J
Todo

## Benefits of a native bundle
Todo

## Downsides of native bundles
Todo

## Conclusion
Todo