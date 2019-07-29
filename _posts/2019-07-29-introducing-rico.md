---
layout: post
title:  'Introducing Rico'
author: markus
featuredImage: puffin
excerpt: 'Rico is the application framework initiated and maintained by Karakun to help building better enterprise solutions in less time. This article gives an introduction and points out the key functionalities your projects can easily benefit from.'
permalink: '/rico/2019/07/29/introducing-rico.html'
categories: [Java, Jakarta EE, Java EE, Spring, Rico, Remoting, Monitoring, Security, Angular, WebComponents, JavaFX, Projector]
header:
  text: Introducing <span class="my-karakun">Rico<span>
  image: post
---

[Rico](/rico) is an open-source application framework led by <span class="my-karakun">Karakun</span> to help building better solutions in less time.

![Rico Logo]({{ "/assets/images/rico/rico-logo.png" | absolute_url }})

Since [Hendrik blogged recently](/java/rico/2019/01/15/rico-server-timing.html) about a in-depth topic on [Rico](/rico), I thought we should give it an introduction post since it has not surfaced much so far...

## History

[Rico](/rico) descends from the _Dolphin Platform_ initiated by Canoo. 
Unfortunately, Canoo does not exist any longer and the original project is dead. As most of the _Dolphin Platform_ team is now working for Karakun and the company still sees value in it, we decided to fork it.
The result is the [Rico Application Platform](/rico).

## Overview

[Rico](/rico) is designed to be used on top of the most commonly used frameworks, Spring and Jakarta EE (formerly Java EE). Both are used to build applications based on Java technology nowadays.
It provides modules for common use cases that extend the range of functions you already get from the framework underneath. Besides those extended features, [Rico](/rico) simplifies access to and usage of needed functionality. 

![Rico Modules Overview]({{ "/assets/posts/2019-07-21-introducing-rico/rico-overview.png" | absolute_url }})
 
These are in regard to core functionalities:

* _Data Access_ - Rico provides helpers to enhance usage of your applications data layer. This includes using an event system for entity mutation to solve common problems in distributed systems. Since Rico builds on top of established frameworks (Spring Data or JPA) it integrates smoothly in your data layer.
* _Logging_ - Based on SLF4J, Rico provides logging features to serve typical uses-cases including distributed logging (painless GreyLog integration). Next to this, accessing the log entries is simplified by an In-Memory pipe of recent logentries and ready-to-use widgets to visualize logs (local & remote).
* _Monitoring_ - As one of the first frameworks, Rico implemented the [new ServerTiming standard](/java/rico/2019/01/15/rico-server-timing.html) and is prepared for distributed tracing.
* _Security_ - Generic interface to serve typical security use-cases (e.g. login, logout, session-management, ...) with integration of KeyCloak (popular access and identity management solution) as a first implementation. All security features are automatically integrated into Rico's Remoting / HTTP layers.

Providing a user interface is more or less required for almost every solution today - be it an admin UI or a UI to give users access the needed functionality. Rico also provides some modules to help in that regard.

We see the landscape of UI technologies changing much faster than core technology. This is why developed the __[Rico](/rico) Remoting__ module which helps separating business logic from UI technology and even from client technology (e.g. browser or desktop).
This separation protects the investment in an application as it makes it much cheaper to reimplement the UI with a new UI technology while keeping the core of the solution as it is (being in production and thus deeply validated by that time).
Client UI technologies currently supported by adapters are:

* JavaFX
* Angular
* AngularJS
* Polymer (WebComponents)

On top of _[Rico](/rico) Remoting_, there is __[Rico](/rico) Projector__. This module provides a server-side API to create client UIs (currently just JavaFX). 
It has been initiated and contributed by [Jörg Hälker](https://twitter.com/johado78) at [sprouts GmbH](https://www.sprouts.aero/) to whom we want to send a big THANK YOU.

This contribution to Rico is currently being finalized and will be released soon. The [__Projector__ repository](https://github.com/rico-projects/rico-projector) already contains an example application in case you want to have a look.
Creating the UI from a server side API reduces the effort for dev teams as they do not need to care about UI technology.

To provide maximum flexibility, it is of course also still possible to implement interfaces provided over HTTP (SOAP, REST, GraphQL, ...) as it is the case for the frameworks build upon.
  
Rico provides a fluent client API for HTTP(S) that automatically respects the threading rules of the given UI toolkit. 
Next to this, Rico has functionality to provide maximum convenience when handling utf-8 text, (large) binaries and serializable data.
Thanks to the plugin functionality of the HTTP client, _security features_ or the _Client Scope_ are injected automatically in each request.

In order to use these components for UIs efficiently, even when combined, [Rico](/rico) provides a __Client Scope__ module on the one hand and an __Event Bus__ module on the other hand.

__Client Scope__ is for example handy in cases when a browser UI is implemented (Angular or WebComponents/Polymer) and you need to handle several browser tabs being used in one session.

__Event Bus__ is used to communicate between sessions (even beyond server boundaries). With this module data from one client session can be published to another session, e.g. to implement a collaboration use case or a chat client.



## Next steps

Now that you got an overview, it is a good time to have a look at our [samples project repository](https://github.com/rico-projects/rico-samples). 
For each sample, you can find a README on how to run it and how to look into the code.

After this introduction, you can expect more blog posts on [Rico](/rico) explaining on how to get started and how to leverage from its features in short term. 

In the meantime, feel free to have a look at the new [Rico homepage](/rico) here on the DevHub for some more input on Rico.