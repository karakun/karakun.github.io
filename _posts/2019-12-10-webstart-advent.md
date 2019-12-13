---
layout: post
title:  'How open source saved WebStart – a christmas carol'
author: hendrik
featuredImage: winter
excerpt: 'Everyone who has developed Java desktop applications within the last 15 years used WebStart or at least heard about it. This post gives an overview how the technology evolves as an open source project after Oracle announced its removal from the Oracle JDK.'
categories: [Java, OpenWebStart]
header:
  text: How open source saved <span class="my-karakun">WebStart<span>
  image: post
---
<div class="notification">
   This post was originally posted at <a href="https://www.javaadvent.com">www.javaadvent.com</a>.
</div>

Everyone who has developed Java desktop applications within the last 15 years used WebStart or at least heard about it. After WebStart was added to the Sun (and later Oracle) Java JREs and JDK in 2002 a lot of applications have been based on this technology. WebStart allows to install, update and run Java based applications on desktop machine by providing the definition of the application in a so called JNLP file (containing an XML description of the application). This file and all resources (like jar-files or icons) that form the application can be stored on a sever. Whenever a client starts a WebStart application, new or updated resources will automatically be downloaded from the server.

While WebStart has been used a lot to provide Java Desktop application most developers missed one important point: While there is a JSR that specifies the [JNLP standard](https://jcp.org/en/jsr/detail?id=56) Oracle never provided an open source reference implementation. Next to this the WebStart functionally that has been provided by Oracle was never part of the OpenJDK. In spring 2018 Oracle published a roadmap for Java on the desktop and announced that WebStart will be removed in the future. Half a year later Java 11 was released as the first LTS version of Java which does no longer include WebStart. Next to this the last free-to-use version of Java 8 was released at the beginning of 2019.

![webstart timeline]({{ "/assets/posts/2019-12-10-webstart-advent/ws-timeline.png" | absolute_url }})

Based on this a lot of companies that use WebStart as a foundation for large applications were faced with a problematic situation within the last 2 years. In general there were the following options to choose from:

* Continue to use an outdated (and in maybe insecure) Java 8 version
* Sign a contract with Oracle to receive commercial LTS version of Java 8 in future
* Create a replacement for WebStart

As you can see no option looks favorable. While it is desirable that companies pay for LTS support, the vendor lock-in to the Oracle JDK is not. And as there was no alternative to WebStart companies started to to find custom solutions.

In that period several companies contacted Karakun AG and ask if we can help to create a custom workaround / solution to handle the WebStart issue. Instead of trying to create several customer specific projects we decide that a general project would be much more sustainable and could help all developers.

## Finding partners for the future of WebStart

As you can imagine other companies have already worked on alternatives to Oracle WebStart in the past. With [IcedTeaWeb](https://icedtea.classpath.org/wiki/IcedTea-Web) RedHat created a JNLP client which provides several of the features of Oracle WebStart. The scope of IcedTeaWeb was limited to Linux (and some minor Windows support) and not all features of WebStart were implemented. All in all it was just not ready to be used for most WebStart users. Also the main work on the project happened several years ago and in recent years it was mainly neglected. The sources of IcedTeaWeb were hosted in a hard to find mercurial repository and the code style was mainly based on Java 1.4. Fortunately we have some good connections to RedHat and were able to get in contact with the people working on IcedTeaWeb. Around the same time the [AdoptOpenJDK](https://adoptopenjdk.net/) community reacted on the discontinuation of WebStart and contacted RedHat, too.

![companies]({{ "/assets/posts/2019-12-10-webstart-advent/companies.png" | absolute_url }})

Luckily all three organisations really care about open source and therefore it was possible to define a roadmap: IcedTeaWeb will be transferred and [hosted on GitHub](https://github.com/AdoptOpenJDK/IcedTea-Web) under the umbrella of the AdoptOpenJDK community. After moving the code and making it more visible the big task of refactoring the code was started. The goal was to make it ready for Java 8 and 11 LTS support on all major operation systems. In this process we found out that integrating with one or the other operation system can create quite some interesting issues.

## How to pay for all of this?

As none of the partners was willing or capable of financing all the work required to add the missing features to IcedTeaWeb a search of sponsors was started. The goal was to find companies which were willing to sponsor part of the development coast. It was not always easy to convince finance departments why they should be paying for a software that a) is not ready to use and b) is licensed under GPLv2 and therefore free for everybody to use. So for our sponsors it was a prisoners dilemma. Why should they invest for something which is free. But on the other hand if nobody invested the project would not be realized and then they would need to find a custom solution for their existing applications which depend on WebStart.

# OpenWebStart

While analyzing the features of IcedTeaWeb and comparing them with the needs of the companies sponsoring the work we noticed a gap. IcedTeaWeb was mostly designed with a normal end user in mind. But companies have very different requirements. E.g. companies want a simple mechanism to roll out an application to many computers or they want to control which application can be started on a computer. As a result of this we decided to start a second project on top of IcedTeaWeb: and so was [OpenWebStart](https://openwebstart.com/) born.

IcedTeaWeb is the very core and most of the development time we spent here. But there are also a few mentionable features which are only available in OpenWebStart:

* Unattended installation and autoupdate: These were requirements from the sponsors. OpenWebStart delegates this task to install4j which is a commercial product but is available for free to open source projects.
* MacOS support: install4j also includes an installer for MacOS. Therefore we decided to limit MacOS support to OpenWebStart. This may be added to IcedTeaWeb later
* JVM Manager: IcedTeaWeb uses a JRE to run. Also all applications are started with exactly the same JRE. To support different vendors and JRE version on the same computer OpenWebStart introduces a JVM Manager. The JVM Manager can use locally installed JREs or download and update JREs from a central server. This allows to have different JREs for different applications. As a consequence of this, a user does not need to have Java installed in order to start a JNLP application.

![JVM manager]({{ "/assets/posts/2019-12-10-webstart-advent/manage-java-version.png" | absolute_url }})

With the 1.0 release of OpenWebStart some weeks ago developers can now choose if they want to bundle an IcedTeaWeb installation with a given JVM to execute JNLP applications or use OpenWebStart and delegate the JVM handling to this new tool. In both cases the JNLP handling and workflows are the same since OpenWebStart uses IcedTeaWeb internally as the JNLP client.

Thanks to [the sponsors](https://openwebstart.com/sponsors/) that liked the idea of a solid open source replacement of Oracle WebStar we were able to polish the IcedTeaWeb code and shift it’s quality and feature-set to a new level next to implementing new native features in OpenWebStart. With IcedTeaWeb and OpenWebStart user now have 2 open source solutions that allow them to continue the use of JNLP based applications. While some features are still in development we already received a lot of positive feedback from the developer community.

# The future of WebStart

Don’t get this wrong. Nobody is trying to sell WebStart as the next big thing in the Java ecosystem. For us it was important to support companies and developers in doing a planed and controlled migration away from WebStart to other technologies. By having a working open source solution that will stay for the next years, companies will have time to find solutions and workflow to install, update and execute Java desktop integration. With the JavaPackager the OpenJDK will contain a tool to create native applications from Java in the near future. While this tool dos not provide all functionalities that WebStart offers – and that are needed for desktop integration from our point of view – the Java community has won some time to create a real successor that might help to bring more Java applications to the desktop.

# Our learning

Besides all the technical details and Java specific quirks we had two very insightful learning:

* Visibility on the internet and user base do not necessarily correlate. If you look at hot topics in the Java community you will most likely not stumble upon WebStart. Never the less there is a large number of applications, users and developers which are using this technology. Most of these applications are custom software used only in a single company. And companies tend to not brag about their internal software which might give them an advantage over their competitors. WebStart (and other Java on the desktop technologies) form kind of an iceberg. Below the few bits an pieces visible on the internet lies a much greater user base.
* It is possible to find money for open source software. While the only profit we draw from OpenWebStart is the numerous experiences we made in the process of writing the code. We were able to find companies willing to support us financially in making this project come to live. Specially around Christmas time it is comforting to know that even in today’s culture of free services there are still some companies out there willing to pay for a piece of software.

At the end I’m really happy that – together with awesome partners – we finished the 1.0 release of this interesting open source project just before the end of 2019.

![XMAS]({{ "/assets/posts/2019-12-10-webstart-advent/christmas-rocket.png" | absolute_url }})
