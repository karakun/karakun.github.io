---
layout: article
title: OpenWebStart
order: '16'
headerText:
permalink: /webstart/
header:
  image: webstart-2
  text: Run <span class="my-karakun">Web Start</span> based applications after the release of <span class="my-karakun">Java 11</span>
nav:
  bottom: false
---
## Java Web Start will die!
Java Web Start (JWS) was deprecated in Java 9 and starting with Java 11 Oracle removed JWS from their JDK distributions.
From this time on, it became impossible to use JWS-based applications on clients, that had the latest version of Java installed.
Since public support of Java 8 ended in Q4/2019, companies do not get any updates and security fixes for Java Web Start anymore.

For this reason we decided to create <span class="text-highlight">Open<span>WebStart</span></span>, an open source reimplementation of the Java Web Start technology.
The replacement will provide the most commonly used features of Java Web Start and the JNLP standard so that customers can continue to use applications based on Java Web Start and JNLP without any changes.

![roadmap]({{ "/assets/images/webstart/divider-2.png" | absolute_url }})

For more information about the removal of Java Web Start you can take a look at these documents:

* [Official Java desktop roadmap from Oracle](http://www.oracle.com/technetwork/java/javase/javaclientroadmapupdate2018mar-4414431.pdf)
* [DZone article about the consequences for Java desktop applications](https://dzone.com/articles/what-the-future-java-releases-will-mean-for-legacy)
* [Do I need to pay for Java now?](/java/2018/06/25/java-releases.html)

### Technical details
<span class="text-highlight">Open<span>WebStart</span></span> will be based on the JNLP-specification defined in JSR-56 and will implement the most commonly used features of Java Web Start.
It will be able to handle any typical JWS-based application and we plan to support all future versions of Java starting with Java 11. Next to Java 11 we will support Java 8 directly with the first release of <span class="text-highlight">Open<span>WebStart</span></span>.
The following topics describe several aspects of the tool in more detail.


#### Installation and administration
<span class="text-highlight">Open<span>WebStart</span></span> will come in 2 different version:

- An installer with update functionality
- An executable bundle

If you use Web Start for several small customers or on your own the installer will be perfect.
By using a native installer (for Windows, Mac or Linux) <span class="text-highlight">Open<span>WebStart</span></span> will be installed on your System and is directly ready to use.
Next to this <span class="text-highlight">Open<span>WebStart</span></span> will automatically check if an update is available.
The tool contains an Updater component that will update <span class="text-highlight">Open<span>WebStart</span></span> without any needed complete user interaction.

If you or your customers are companies with its own IT department the executable bundle can be used to rollout <span class="text-highlight">Open<span>WebStart</span></span> on several client machines.
In that case the update functionality of the tool is deactivated and the IT department can plan and handle the rollout of new versions based on internal workflows.

![roadmap]({{ "/assets/images/webstart/rollout.png" | absolute_url }})

#### General modules and functionallities
The main focus of <span class="text-highlight">Open<span>WebStart</span></span> is the execution of JNLP based applications.
Next to this the tool contains 4 modules with additional functionality that helps to simplify the Web Start workflows and let you configure <span class="text-highlight">Open<span>WebStart</span></span> just for your needs.

- The **App Manager** will manage the versions of any JNLP based application that was executed by <span class="text-highlight">Open<span>WebStart</span></span>
- The **JVM Manager** will manage Java versions and its updates on the client
- The **Control Panel** provides a graphical user interface to configure <span class="text-highlight">Open<span>WebStart</span></span>
- The **Updater** can automatically download and install new versions of <span class="text-highlight">Open<span>WebStart</span></span>

#### JNLP support
<span class="text-highlight">Open<span>WebStart</span></span> will provide exactly the same JNLP based workflow as Java Web Start, therefore nothing will change from the point of view of your users:

- A JNLP-file that describes the application is downloaded to the client, typically either by clicking a link on a webpage or by an automated provisioning process.
- <span class="text-highlight">Open<span>WebStart</span></span> registers itself by default for the JNLP-file-extension and the MIME-type `application/x-java-jnlp-file`, therefore by double clicking the JNLP-file, <span class="text-highlight">Open<span>WebStart</span></span> launches.
- It parses the JNLP-file, downloads all needed resources (JARs, native libraries and images) and stores them in a cache.
- Once all resources are available, the application starts.

![roadmap]({{ "/assets/images/webstart/manage-applications.png" | absolute_url }})

Any JNLP based application that was downloaded and started via <span class="text-highlight">Open<span>WebStart</span></span> will be managed by the internal App Manager. The App Manager will automatically check if an update is provided for any managed applications and downloads such update automatically.

#### JDK/JRE management
One feature that has always been missing with Oracle Web Start was JVM (Java Virtual Machine) version management.
When deploying a JNLP based application a developer can not really define what exact Java version will be used to run the application.
<span class="text-highlight">Open<span>WebStart</span></span> from Karakun will add such functionality by the integrated JVM Manager.
The JVM Manager can download Java versions from a dedicated server and manage such versions internally.
By doing so a developer can easily define a specific Java version that should be used to run the provided JNLP based application.

![roadmap]({{ "/assets/images/webstart/manage-java-version.png" | absolute_url }})

By default <span class="text-highlight">Open<span>WebStart</span></span> will download OpenJDK based Java version from a dedicated server.
The server can be configured by hand in the Control Center of the Web Start tool.

![roadmap]({{ "/assets/images/webstart/download-jvm.png" | absolute_url }})

#### Configuration of OpenWebStart
The well known Java Control Panel that was installed in addition to any Oracle Java installation was part of Web Start.
Therefore it will be removed from Oracle Java along with Web Start.
<span class="text-highlight">Open<span>WebStart</span></span> provides its own Control Center.
One feature of that Control Center will be the configuration of the workflow to start JNLP based applications by hand.

#### Customer specific build
If you want to evaluate the functionality of <span class="text-highlight">Open<span>WebStart</span></span> without registering the tool for the JNLP MIME-type, we can provide a build that is configured to use a custom "JNLPX" MIME-type instead.
Based on this, an easy migration from Oracle Web Start to the replacement will be possible.
Next to this the server URL for JDK downloads can be configured.
By doing so you can easily provide JDKs for your clients and customers on a custom server.
If you want to use JDK with commercial support that should not be reachable in public <span class="text-highlight">Open<span>WebStart</span></span> can be configured to download JDKs from a secure server by using authentication.
All this configurations can be easily done for a customer that bought commercial support at Karakun. The free to use <span class="text-highlight">Open<span>WebStart</span></span> builds are all configured in a default way.

### Roadmap
We have been able to secure a good part of the required funding. With our sponsors we started to discuss the list of features that we are going to implement.
Our goal is to have beta tests and a release candidate ready in Q3/2019.
The following diagram shows the roadmap:

![roadmap]({{ "/assets/images/webstart/roadmap-karakun.png" | absolute_url }})

#### Interested in becoming a sponsor?
We are still looking for sponsors.
As a sponsor you can influence the list of features that we are going to implement.
Plus we will run regular tests with your applications to guarantee that your application will work with OpenWebStart.
If that sounds interesting, please contact us at [OpenWebStart@karakun.com](mailto:openwebstart@karakun.com).

#### How can I stay informed?
If you want to learn more and stay up-to-date, please [subscribe to our newsletter](/subscribe/).

![roadmap]({{ "/assets/images/webstart/rocket.png" | absolute_url }})
