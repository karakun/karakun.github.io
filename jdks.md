---
layout: article
title: OpenJDK
order: '15'
headerText: 
permalink: /jdks/
header:
  image: webstart
  text: Open<span class="my-karakun">JDK</span> downloads
nav:
  bottom: false
---
## What is the OpenJDK
As you might already know Java will change at the end of the year based on the new Java release train and
the new licence model of Oracle. Starting with Java 11 it is not allowed to use the Oracle JDK anymore in
production. Next to this Oracle won't provide any JRE builds for desktop clients anymore.

If you want to know more about that topics you should have a look at the following articles:

* [A general overview of future Java releases and Java licence models]({{ site.baseurl }}{% post_url 2018-06-25-java-releases %})
* [Overview of all changes regarding Java Desktop in future Java releases](https://dzone.com/articles/what-the-future-java-releases-will-mean-for-legacy)
* [Additional information about the end of Java WebStart]({{ site.baseurl }}{% link webstart.md %})

### Downloads

 Until now most of you have downloaded and used the __Oracle JDK__ until now. Especially on client machines this was
 a good practice since it was provided as an installer with auto update functionality. JRE and JDK versions
 of the Oracle Java builds until Java 10 can be downloaded at [the OTN (Oracle technical network)](http://www.oracle.com/technetwork/java/javase/downloads/index.html). 
Future versions of the Oracle JDK will be offered at the OTN, too. Sadly this versions are not allowed to 
be used in production anymore unless you pay commercial support to Oracle. The Oracle JDK builds are licenced under 
the _Oracle Binary Code License Agreement for Java SE_ that you need to accept when downloading such a Java build. 
How this licence will change in detail starting with Java 11 is not published until now.

Next to the commercial JDK builds Oracle provides OpenJDK builds that are licenced under the [GNU General Public License, version 2, with the Classpath Exception](http://openjdk.java.net/legal/gplv2+ce.html). You can download 
builds for Windows, Linux and Mac for Java 10 and early access builds for Java 11 at the moment. You can download the binaries at Oracle
for [Java 10](http://jdk.java.net/10/) and for [Java 11 early access](http://jdk.java.net/11/) or just click on a link below:

{:.table}
| OS            | Java version | link                             |
| ------------- | ------------ | -------------------------------- |
| Linux / x64   | 10.0.2       |[download](https://goo.gl/HeSiYQ) |
| macOS / x64   | 10.0.2       |[download](https://goo.gl/r84jeU) |
| Windows / x64 | 10.0.2       |[download](https://goo.gl/bVCg7R) |
| Linux / x64   | 11-ea+23     |[download](https://goo.gl/MCYuxJ) |
| macOS / x64   | 11-ea+23     |[download](https://goo.gl/nbDjmH) |
| Windows / x64 | 11-ea+23     |[download](https://goo.gl/fAeavW) |

Next to Oracle there is another option to download binaries that are based on OpenJDK. At [adoptopenjdk.net](https://adoptopenjdk.net) the
open source community around Java and the OpenJDK provides free builds of the OpenJDK. They even provide artifacts for more operation
systems than oracle does. You can download the artifacts directly at [adoptopenjdk.net](https://adoptopenjdk.net) or just click on a link below:

{:.table}
| OS             | Java version | link                             |
| -------------- | ------------ | -------------------------------- |
| Linux / x64    | 8u172        |[download](https://goo.gl/Sg1zMC) |
| macOS / x64    | 8u172        |[download](https://goo.gl/d2jCoH) |
| Windows / x64  | 8u172        |[download](https://goo.gl/1Mb4Fd) |
| Linux /s390x   | 8u172        |[download](https://goo.gl/v3urDM) |
| Linux /ppc64le | 8u172        |[download](https://goo.gl/PHm56n) |
| Linux /aarch64 | 8u172        |[download](https://goo.gl/Y5obNh) |
| AIX / ppc64    | 8u172        |[download](https://goo.gl/mw3uxR) |
| Linux / x64    | 10           |[download](https://goo.gl/D7Yt7C) |
| macOS / x64    | 10           |[download](https://goo.gl/xiBV4x) |
| Windows / x64  | 10           |[download](https://goo.gl/3AJ2dP) |
| Linux /ppc64le | 10           |[download](https://goo.gl/b7hRwT) |
| Linux /aarch64 | 10           |[download](https://goo.gl/zkqosn) |
| AIX / ppc64    | 10           |[download](https://goo.gl/EpSxqA) |

Next to Oracle [Azul](https://www.azul.com) provides free OpenJDK builds, too. The OpenJDK build that Azul
provides is named Zulu. You can download and use Zulu for free (see [terms of use](https://www.azul.com/products/zulu-and-zulu-enterprise/zulu-terms-of-use/)) at [Azul](https://www.azul.com/downloads/zulu/).
Next to this here is a table with direct links to all current versions:

{:.table}
| OS            | Java version | link                             |
| ------------- | ------------ | -------------------------------- |
| Linux / x64   | 10.0.1       |[download](https://goo.gl/DuUNie) |
| macOS / x64   | 10.0.1       |[download](https://goo.gl/yuG2zD) |
| Windows / x64 | 10.0.1       |[download](https://goo.gl/CCtdRf) |
| Linux / x64   | 9.0.7        |[download](https://goo.gl/8i4yUz) |
| macOS / x64   | 9.0.7        |[download](https://goo.gl/gTkkct) |
| Windows / x64 | 9.0.7        |[download](https://goo.gl/wJEFnC) |
| Linux / x64   | 8u172        |[download](https://goo.gl/v9pKwG) |
| macOS / x64   | 8u172        |[download](https://goo.gl/g68cNn) |
| Windows / x64 | 8u172        |[download](https://goo.gl/tX1wNF) |

Another player is [SAP](https://www.sap.com) that distributes (free OpenJDK builds)[https://sap.github.io/SapMachine/] next to a closed source SAP JVM. 
The free JDK at SAP is called "SapMachine" and at the moment the following free JDK builds can be downloaded:

{:.table}
| OS             | Java version     | link                             |
| -------------- | ---------------- | -------------------------------- |
| Linux / x64    | 10.0.2           |[download](https://goo.gl/7MnWk3) |
| Linux /ppc64   | 10.0.2           |[download](https://goo.gl/nr4UuW) |
| Linux /ppc64le | 10.0.2           |[download](https://goo.gl/nbT59G) |
| Linux / x64    | 11 (pre-release) |[download](https://goo.gl/jmDkad) |
| Linux /ppc64   | 11 (pre-release) |[download](https://goo.gl/GqAPmm) |
| Linux /ppc64le | 11 (pre-release) |[download](https://goo.gl/YSh54U) |
| Windows / x64  | 11 (pre-release) |[download](https://goo.gl/CZuQnt) |
| macOS / x64    | 11 (pre-release) |[download](https://goo.gl/wo2GXY) |
| Linux / x64    | 12 (pre-release) |[download](https://goo.gl/PwwErg) |
| Linux /ppc64   | 12 (pre-release) |[download](https://goo.gl/DJUVpW) |
| Linux /ppc64le | 12 (pre-release) |[download](https://goo.gl/6QvpSd) |
| Windows / x64  | 12 (pre-release) |[download](https://goo.gl/jeAdCS) |

Next to JDK builds SAP provides JRE builds for the versions that are listed in the table. Since SAP provides all builds as GitHub releases a good overview [can be found here](https://github.com/SAP/SapMachine/releases).

_We just added links to the artifacts that are provided by different vendors of Java binaries - we do not provide
any support for them._

### Why should I care?
Well If you already paying commercial support to Oracle you are fine. Since this is not the case
for most Java developers it really makes sense to download OpenJDK binaries already today
and get familar with them. To be true there is not that much difference between the commercial
Java builds from Oracle and the OpenJDK builds but it will make sense to download them
and check your Java appplications running on OpenJDK today.

![Duke]({{ "/assets/posts/2018-06-25-java-releases/duke-11.jpg" | absolute_url }})
