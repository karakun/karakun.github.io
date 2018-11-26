---
layout: post
title:  'Amazon Corretto announced'
author: hendrik
featuredImage: icecream
excerpt: 'At the Devoxx conference 2 weeks ago, Amazon announced Corretto as a new player in the OpenJDK market. Next to companies like SAP, Oracle or Bellsoft, the cloud computing company now provides a custom OpenJDK build. On the website Amazon Corretto is described as 
"No-cost, multiplatform, production-ready distribution of OpenJDK". In this post I will have a deeper look at Corretto and explain why Amazon did this move.'
categories: [Java, OpenJDK]
header:
  image: post
---

Based on the changes that Oracle announced a year ago about [the new release train and licences of Java](/java/2018/06/25/java-releases.html) and the Oracle Java distributions it was completely obvious and only a matter of time 
when additional big players start to distribute custom OpenJDK builds. Some companies like RedHat or IBM did this already for some years to provide custom Java support 
for existing enterprise customers. The first big player that offered a more general OpenJDK build with custom support was Azul. With the new licence model of Oracle that 
only provides free updates for java version (even LTS versions) for a 6 month period, the door was open for new vendors. Especially cloud providers needed to find a 
solution for the new situation. If you have Java installed on millions of containers that are running in your cloud you do not want to buy commercial support at Oracle 
for all of them. But you do not want to use outdated Java versions in production either. Especially in a public cloud infrastructure you need security fixes as fast 
as possible. Based on this Microsoft already [announced a partnership with Azul](https://azure.microsoft.com/en-us/blog/microsoft-and-azul-systems-bring-free-java-lts-support-to-azure/) a month ago. Amazon decided to not cooperate with a given JDK vendor and provide its 
own distribution as they have announced at Devoxx 2018. The Java distribution of Amazon is named Corretto. [On the product website](https://aws.amazon.com/de/corretto/) Amazon describes Corretto as a "No-cost, multiplatform, production-ready distribution of OpenJDK". Let's have a deeper look at it.

![Free LTS]({{ "/assets/posts/2018-11-23-amazon-corretto/duke_ice.png" | absolute_url }})

## About multiplatform support

Currently a Java 8 based preview can be downloaded for Linux, Mac and Windows. Until now, Amazon provides only 64 bit version. Against some other distributions, the download 
contains an installer (at least for Windows and Mac) that will install the Java distribution on your machine. This is a huge benefit compared to some other vendors that 
only offer a packed (like tar or zip) folder of the JDK. Plus, Amazon already offers ready to use docker support. If you want to run a java application in a container by using the Corretto JDK you only need to build the open source image:

{% highlight shell %}
docker build -t amazon-corretto-8 github.com/corretto/corretto-8-docker
{% endhighlight %}

I am sure that Amazon will offer the images in the offical docker hub in the near future.

Once you have installed Corretto it is ready to use. By calling `java -version` the current preview build of Corretto will give you the following version information:

{% highlight shell %}
openjdk version "1.8.0_192"
OpenJDK Runtime Environment (build 1.8.0_192-amazon-corretto-preview-b12)
OpenJDK 64-Bit Server VM (build 25.192-b12, mixed mode)
{% endhighlight %}

## Is Corretto really a no-cost JDK?

For now, you can easily download a Java 11 version everywhere. You can choose between Oracle JDK, Oracle OpenJDK builds, AdoptOpenJDK builds and [many more](/jdks/). 
While all (except for the Oracle JDK build) can easily be used in production it will become much harder in some months when Java 12 hits the market and the 6 month period 
of Java 11 ends. Even Java 11 is a so called LTS release (Long Term Support) companies like Oracle or Azul will only offer updates on Java 11 if you pay 
commercial support to them. Amazon plans to offer a free support and will provide updates for Java 11 with patches (security fixes and bugfixes) for 
several years. On the FAQ page of Corretto Amazon describes the model like this:

> Corretto is distributed by Amazon under an Open Source license at no cost to you. It is licensed under the terms of the GNU 
> Public License version 2 with the Class Path Exception (GPLv2 with CPE). Amazon does not charge for its use or distribution.

> Amazon will provide security updates for Corretto 8 until at least June 2023. Updates are planned to be released quarterly.
> Corretto 11, corresponding to OpenJDK 11, will be available during the first half of 2019. Amazon will support Corretto 11 with quarterly updates until at least August 2024

Based on this you can definitelly say that Corretto will be free. To be true Amazon does not provide any commercial support for Corretto.
If this is positive or negative depends on your opionon about commercial support. In general, it is really positive to have a distributor that offers free LTS support for Java.

## Is Corretto production ready?

Since Corretto is based on the sources of OpenJDK and therefore has mostly no functional change against other distributions, this question can be answered in general with "yes". 
If you install the Java 8 preview of Corretto, you normally will have absolutely no problems. From my point of view a more important question is if Corretto will stay
production ready in the future. By providing long term support for Java versions Amazon needs to merge and implement fixes into the Corretto repositories since the work on
the OpenJDK branches for a specific version will stop after 6 month. When having a look at the OpenJDK contributors of the last year you will see that Amazon is already an
OpenJDK commiter. The following diagram gives an idea about the work that some JDK vendors did the last year on OpenJDK. As you can see Amazon was not that active as the
big vendors like SAP or Bellsoft:

![OpenJDK conributions]({{ "/assets/posts/2018-11-23-amazon-corretto/diagramm.png" | absolute_url }})

Based on this, only time will tell if Amazon can provide fast bugfixes and updates based on security issues and general bugs. I hope that they will increase the work on OpenJDK in near future and
provide a new and good version of Java 11 each quarter.

As already said Corretto is build on the same OpenJDK sources just like all other Java distribution. For sure the sources will change after 6 months once the LTS periode begins. At that moment companies
will continue to work on Java 11 in a closed branch. I do not like this workflow and plan to write an additional post about that topic in near future. Based on this Corretto contains everything that
you need to run a Java applications. By taking a closer look at Corretto you can find some differences. Since the current download is a preview we will see how this emerges in the future. Currently, I
can see some positive and some negative aspects about Corretto:

* Corretto contains JavaFX and can therefore be used to easily start a JavaFX based application. Several other vendors do not bundle JavaFX in the JDK artifacts
* Next to the JavaFX API the Amazon JDK contains the javafxpackager tool. This can be found in the bin folder and allows users to create native applications based on a Java application
* As all other JDK vendors (besides Oracle) no WebStart functionallity can be found in Corretto. An explanation of the WebStart absense in OpenJDK distributions can be found here

Based on all this, one can say that Amazon is on a really good way to offer a production ready OpenJDK distribution.

## Conclusion and additional thoughts

Considering all the given facts, Corretto looks really promissing. In March 2019, Java 12 will be released and Java 11 will go in LTS mode. So, somewhere in summer of 2019 we will
see if Amazon will release a free update of Corretto that is based on Java 11 containing all needed bugfixes and security fixes. Taking into account the size, power and
knowledge of Amazon, I do not expect any issue here. On the other hand we will never know when Amazon stops the work and support for specific Java versions. Since
Corretto is 100% free no one will have any contract about future support and therefore it will just be in Amazons hands to support the Java commmunity with future
updates.

Since Corretto is completely free I would really love to see that Amazon contributes some of the work (like bugfixes or security fixes) back to [AdoptOpenJDK](https://adoptopenjdk.net). With this
community we have a 100% free and open initiative to provide future open Java versions. If big vendors that work on OpenJDK and provide LTS support for Java would
contribute their changes to AdoptOpenJDK instead or next to a closed branch, the future of Java as an open source project would even be brigther. But this discussion
will contain more than enough content for a future post ;)

