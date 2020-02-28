---
layout: post
title:  'How AdoptOpenJDK provides enterprise ready OpenJDK builds'
author: hendrik
featuredImage: adoptopenjdk
excerpt: "With the new licence of the Oracle JDK a lot of companies need to switch to a new JDK vendor. With AdoptOpenJDK we have a community based alternative that provides free LTS JDK builds. In this post I will have a deeper look at the infrastructure of the AdoptOpenJDK and how each build is tested to offer production ready quality."
categories: [Java, OpenJDK]
header:
  text: AdoptOpenJDK test suite
  image: post
---
<div class="notification">
   This post was originally posted at <a href="https://guigarage.com">guigarage.com</a>.
</div>

While in theory everybody can build it’s own JDK based on the OpenJDK sources, you normally don’t want to do that. Since several years, people downloaded JDK builds from Oracle but based on the [new Licence for Oracle JDKs](https://dev.karakun.com/java/2018/06/25/java-releases.html) this isn’t that easy anymore. If you need an up-to-date Oracle JDK for production you need to buy commercial support from Oracle since last year.

A lot of Linux users do not really care about that problem since they used OpenJDK builds that were downloaded from an APT repository, for example. On linux distributions like ubuntu, you only need the following command to add a ready-to-use JDK installation to your system:

{% highlight java %}
sudo apt-get install openjdk
{% endhighlight %}

While this sounds like a really easy solution there are several points that you normally cannot answer based on this approach:

* You do not know who has build this JDK binary
* You do not know if these binaries were build on the latest OpenJDK sources / tag
* You do not know if and how these binaries were tested

While you can find answers to some of the questions, it is not a trivial task for a regular user. Especially the last question can normally not be answered.

## AdoptOpenJDK for the rescue

With [AdoptOpenJDK](https://adoptopenjdk.net/) there is an open source project out there that has the main goal to solve all the mentioned problems by providing open, free and well tested builds of the OpenJDK. Since last year I’m part of the [AdoptOpenJDK technical steering committee (TSC)](https://github.com/AdoptOpenJDK/TSC#the-tsc). I’m really happy to be part of the team and with way over **150.000.000 downloads** I can say that the project is a great success and brings a really important benefit to the Java community. With AdoptOpenJDK binaries, you have a really good alternative next to downloading (and paying for) Oracle Java. If you have more questions about AdoptOpenOpenJDK just ping me or ask us directly in the [AdoptOpenJDK Slack](https://adoptopenjdk.slack.com/).

![AdoptOpenJDK](/assets/posts/2020-02-26-OpenJDK-builds/adopt-logo.png)

In this article I will give an overview about the test infrastructure of AdoptOpenJDK and describe the different types of tests that will be executed for each release. All this ends in a really well tested distribution that is ready to use for the enterprise.

As the world of testing at AdoptOpenJDK is evolving and improving quickly, some information and descriptions of this article may fall behind the march of progress. The most updated documentation can always be found in the [AdoptOpenJDK github repo](https://github.com/AdoptOpenJDK/openjdk-tests).

## Guide to the test jobs at AdoptOpenJDK

For all nightly and release builds, there are test jobs running as part of the [AdoptOpenJDK continuous delivery pipelines](https://ci.adoptopenjdk.net). For the test step of a build, all tests are grouped by their types. Currently, the tests of AdoptOpenJDK are split in 6 different groups / types. When running the test phase on our CI servers, the defined groups will be executed in parallel. All these tests are defined in the open source test framework [**AQA** (AdoptOpenJDK Quality Assurance)](https://blog.adoptopenjdk.net/2019/07/the-first-drop-introducing-adoptopenjdk-quality-assurance-aqa-v1-0) that is provided by AdoptOpenJDK and can be found [here](https://github.com/AdoptOpenJDK/openjdk-tests). The following image shows the simplified pipeline of the AdoptOpenJDK builds and all the different test groups that are part of the test step in each build:

![Pipeline of the AdoptOpenJDK builds](/assets/posts/2020-02-26-OpenJDK-builds/ci-pipeline.png)

### Test groups

As already mentioned all tests of AdoptOpenJDK are grouped based on their types in 6 different groups. The following table gives a first overview about the different test types:

{:.table}
| Name        | Type 							| Description | 
| ------------| -------------- 					| -------------- |
| openjdk     | OpenJDK regression tests        | Tests from OpenJDK           |
| system      | System and load tests           | Tests from the AdoptOpenJDK/openjdk-systemtest repo           |
| external    | 3rd party application tests     | Test suites from a variety of applications, along with microprofile TCKs, run in Docker containers           |
| perf        | Performance benchmark suites    | Performance benchmark tests (both full suites and microbenches) from different open-source projects such as Acme-Air and AdoptOpenJDK/bumblebench           |
| functional  | Unit and functional tests       | Functional tests not originating from the openjdk regression suite, that include locale/language tests and a subset of implementation agnostic tests from the openj9 project.           |
| jck         | Compliance texts          		| TCK tests (under the OpenJDK Community TCK License Agreement), in compliance with the license agreement. While this test material is currently not run at the AdoptOpenJDK project (see the [support statement](https://adoptopenjdk.net/support.html#jck) for details), those with their own OCTLA agreements may use the AdoptOpenJDK test automation infrastructure to execute their TCK test material in their own private Jenkins servers.           |

As you can see next to the OpenJDK tests we added a lot of additional tests. The OpenJDK regression tests are a great start to test a JDK, but eventually you may want to be able to test the performance of your code and whether some 3rd party applications still work. Then, all the other test types come into play. This does not only add performance tests and additional general unit tests. Next to those, the test framework contains test suites from several big players from the Java ecosystem to directly check the accurate functionality of important frameworks with the AdoptOpenJDK builds. AQA for example executes tests from [11+ popular Java applications](https://github.com/AdoptOpenJDK/openjdk-tests/tree/master/external) including all tests from [Apache Tomcat](http://tomcat.apache.org/) project and all TCKs (Test Compatibility Kit) of the [Eclipse MicroProfile](https://microprofile.io/) on the popular frameworks available (OpenLiberty, Payara, Tomee and Thorntail).

### Building all tests with AQA
While this first looks like an impressive mix of several tests that will be executed individually for each build the underlying AQA platform does much more. Since we do not want to run test suites one after another and end in several different test reports AQA provides a thin platform. This platform provides thin wrappers around the different test modules and assembles all in a single huge test suite.

By using AQA, new tests or complete suites of tests can easily be added to the project and since AQA is open source any other project can easily adopt it. To be true some JDK vendors already use AQA today to test the builds of their JDK distributions. Some of them even integrate the Java TCK in AQA. While AdoptOpenJDK currently does not run the Java TCK and has [no agreement with Oracle](https://adoptopenjdk.net/quality.html#jck), AQA already provides everything to simply integrate the TCK for those who wish to include it and have an OCLTA license for those closed test materials.

As you can see, every AdoptOpenJDK build runs through a ‘we test the hell out of it’ job. ;) And the project won’t stop here. More and more tests will be added to make AQA the perfect test suite for any JDK build. In general, you can define the benefits of AQA like this:

* better, more flexible tests with the ability to apply certain types of testing to different builds
* a common way to easily add, edit, group, include, exclude and execute tests on AdoptOpenJDK builds
* the latitude to use a variety of tests that use many different test frameworks
* test results to have a common look & feel for easier viewing and comparison
* easily run all types of tests via make targets in various CI environments

## Guide to running the tests yourself
Thanks to AQA, you can even run all the tests by yourself on your own system and test any OpenJDK distribution. As already mentioned, several other companies that provide OpenJDK distributions already use AQA to get a better test coverage and quality for their commercial OpenJDK distributions. For more details on how to run the same tests as AdoptOpenJDK on your laptop or in your build farm, please consult the [official user guide](https://github.com/AdoptOpenJDK/openjdk-tests/blob/master/doc/userGuide.md).


 


