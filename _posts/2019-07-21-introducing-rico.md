---
layout: post
title:  'Introducing Rico'
author: markus
featuredImage: 
excerpt: 'Introduction to Rico, the application framework.'
permalink: '/rico/2019/07/16/introducing-rico.html'
categories: [Java, Jakarta EE, Java EE, Spring, Rico, Remoting, Monitoring, Security, Angular, WebComponents, JavaFX, Projector]
header:
  text: Introducing <span class="my-karakun">Rico<span>
  image: post
---

[Rico](/rico) is an open-source application framework led by <span class="my-karakun">Karakun</span> to help building better solutions in less time.

Since [Hendrik blogged recently](/java/rico/2019/01/15/rico-server-timing.html) about a in-depth topic on [Rico](/rico), I thought we should give it an introduction post since it has not surfaced much so far....

## History

[Rico](/rico) descends from the _Dolphin Platform_ initiated by Canoo. 
Unfortunately, Canoo does not exist any longer and the original project is dead. As most of the _Dolphin Platform_ team is now working for Karakun and the company still sees value in it, we decided to fork it.
The result is the [Rico Application Platform](/rico).

## Overview

[Rico](/rico) is designed to be used on top of the most commonly used frameworks, Spring and Jakarta EE (formerly Java EE). Both are used to build applications based on Java technology nowadays.
It provides modules for common use cases that extend the range of functions you already get from the framework underneath.  (...) and simplify access and usage to functionality in question. 

![Rico Modules Overview]({{ "/assets/posts/2019-07-21-introducing-rico/rico-overview.png" | absolute_url }})
 
These are in regard to core functionalities:

* _Data Access_ -
* _Logging_ - 
* _Monitoring_ -
* _Security_ - 

(**TODO:** some word to each module)

Also, as providing a user interface is required for almost every solution in some sort, be it a admin UI or a UI to give users acess the core use case provided, Rico provides some modules to help in that regard.

We see the landscape of UI technologies changing much faster than that of core technology. So Rico provides with __[Rico](/rico) Remoting__ a module that helps to seperate business logic from UI technology and even from client technology (e.g. browser or desktop).
This separation protects the investment the is placed by building a solution as it makes it much cheaper to reimplement the UI with a new UI technology while keeping the core of the solution as it is (being in production and thus deeply validated by that time).
Client UI technologies currently supported by adapters are:

* JavaFX
* Angular
* AngularJS
* Polymer (WebComponents)

On top of _[Rico](/rico) Remoting_, there is __[Rico](/rico) Projector__. This module provides a server-side API to create client UIs (currently just JavaFx). 
It has been initiated and contributed by <a href="https://twitter.com/johado78">Jörg Hälker</a> at <a href="https://www.sprouts.aero/">sprouts GmbH</a> to whom we want to send a big THANK YOU.
This contribution to Rico is currently being finalized and will be released soon. The [__Projector__ repository](https://github.com/rico-projects/rico-projector) already contains an example application in case you want to have a look.
Creating the UI from a server side API reduces the effort the dev team has to take to build a solution as they do not need to care about UI technology.

To provide the maximum extend of flexibility, it is of course also still possible to implement interfaces provided over HTTP (SOAP, REST, GraphQL, ...) as it is the case for the frameworks build upon.
**TODO:** clarify if Rico provides something in addition....

In order to use these components for UIs efficiently, even when combined, [Rico](/rico) provides a __Client Scope__ module on the one hand and an __Event Bus__ module on the other hand.

__Client Scope__ is for example handy in cases when a browser UI is implemented (Angular or WebComponents/Polymer) and you need to handle several browser tabs being used in one session.

__Event Bus__ is used to communicate between sessions. So that data from one client session can be published to other session, e.g. to implement a collaboration use case or a chat client.



## Next steps

Now that you got an overview, it is a good time to lookups or [samples project repository](https://github.com/rico-projects/rico-samples). 
For each sample, you can find an README on how to run it and look into the code.

After this introduction, you can expect more blog posts on [Rico](/rico) explaining on how to get started and how to leverage from its features in short term. 

In the meantime, feel free to have a look on the new [Rico homepage](/rico) here on the DevHub for some more input on Rico.
