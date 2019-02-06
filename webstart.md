---
layout: article
title: Open WebStart
order: '16'
headerText:
permalink: /webstart/
header:
  image: webstart
  text: Run <span class="my-karakun">Web Start</span> based application after the release of <span class="my-karakun">Java 11</span>
nav:
  bottom: false
---
## Java Web Start will die!
Java Web Start (JWS) was deprecated in Java 9 and starting with Java 11 Oracle will remove JWS from their JDK distributions.
All this happened in September 2018.
From this time on, it will be impossible to use JWS-based applications on clients, that have the latest version of Java installed.
Since the public support of Java 8 ends in the 4 quarter of 2019 companies won't get any updates and security fixes for WebStart anymore.
Based on this we at Karakun decided to create Open WebStart as an open source reimplementation of the WebStart technology.
The replacement should provide all general features of WebStart and will reuse the JNLP standard so that customers can continue to use applications based on Webstart and JNLP without any changes.

For more information about the removal of Java WebStart you can take a look at these documents:

* [Official Java desktop roadmap from Oracle](http://www.oracle.com/technetwork/java/javase/javaclientroadmapupdate2018mar-4414431.pdf)
* [DZone article about the consequences for Java desktop applications](https://dzone.com/articles/what-the-future-java-releases-will-mean-for-legacy)
* [Do I need to pay for Java now?]({{ site.baseurl }}{% link _posts/2018-06-25-java-releases.md %})

### Technical details
Open WebStart will be based on the JNLP-specification defined in JSR-56 and will implement the most commonly used features of Java Web Start.
It will be able to handle any typical JWS-based application and will support all future versions of Java starting from Java 11.
The following topics describe several aspects of the tool in more detail.


#### Installation and administration
Open WebStart will come in 2 different version:

- An installer with update functionality
- An executable bundle

If you use Webstart for several small customers or on your own the installer will be perfect.
By using a native installer (for Windows, Mac or Linux) Open WebStart will be installed on your System and is directly ready to use.
Next to this Open WebStart will automatically check if an update is available.
The tool contains an Updater component that will update Open WebStart without any needed complete user interaction.

If you or your customers are companies with its own IT department the executable bundle can be used to rollout Open WebStart on several client machines.
In that case the update functionality of the tool is deactivated and the IT department can plan and handle the rollout of new versions based on internal workflows.


#### General modules and functionallities
The main focus of Open WebStart is the execution of JNLP based applications.
Next to this the tool contains 4 modules with additional functionality that helps to simplify the WebStart workflows and let you configure Open WebStart just for your needs.

- The App Manager will manage the versions of any JNLP based application that was executed by Open WebStart
- The JVM Manager will manage Java versions and its updates on the client
- The Control Panel provides a graphical user interface to configure Open WebStart
- The Updater can automatically download and install new versions of Open WebStart

#### JNLP support
Open WebStart will provide exactly the same JNLP based workflow as Java Web Start, therefore nothing will change from the point of view of your users:

- A JNLP-file that describes the application is downloaded to the client, typically either by clicking a link on a webpage or by an automated provisioning process.
- Open WebStart registers itself by default for the JNLP-file-extension and the MIME-type "application/x-java-jnlp-file", therefore by double clicking the JNLP-file, Open WebStart launches.
- It parses the JNLP-file, downloads all needed resources (JARs, native libraries and images) and stores them in a cache.
- Once all resources are available, the application starts.

Any JNLP based application that was downloaded and started via Open WebStart will be managed by the internal App Manager. The App Manager will automatically check if an update is provided for any managed applications and downloads such update automatically.

#### JDK/JRE management
One feature that has always been missing with Oracle WebStart was JVM (Java Virtual Machine) version management.
When deploying a JNLP based application a developer can not really define what exact Java version will be used to run the application.
Open WebStart from Karakun will add such functionality by the integrated JVM Manager.
The JVM Manager can download Java versions from a dedicated server and manage such versions internally.
By doing so a developer can easily define a specific Java version that should be used to run the provided JNLP based application.

By default Open WebStart will download OpenJDK based Java version from a dedicated server.
The server can be configured by hand in the Control Center of the WebStart tool.

#### Configuration of the WebStart
The well known Java Control Panel that was installed in addition to any Oracle Java installation was part of WebStart.
Therefore it will be removed from Oracle Java along with WebStart.
Open WebStart provides its own Control Center.
One feature of that Control Center will be the configuration of the workflow to start JNLP based applications by hand.

#### Customer specific build
If you want to evaluate the functionality of Open WebStart without registering the tool for the JNLP MIME-type, we can provide a build that is configured to use a custom "JNLPX" MIME-type instead.
Based on this, an easy migration from Oracle WebStart to the replacement will be possible.
Next to this the server URL for JDK downloads can be configured.
By doing so you can easily provide JDKs for your clients and customers on a custom server.
If you want to use JDK with commercial support that should not be reachable in public Open WebStart can be configured to download JDKs from a secure server by using authentication.
All this configurations can be easily done for a customer that bought commercial support at Karakun. The free to use Open WebStart builds are all configured in a default way.

### Roadmap
Currently we are looking for funding.
Once funding is secured, we will start development.
Beta tests might be availabe in Q2 2019.
The following diagram shows the roadmap:

#### Interested?
If you want to learn more or if you would like to discuss your specific needs, please contact us at openwebstart@karakun.com or subscribe to our newsletter.
