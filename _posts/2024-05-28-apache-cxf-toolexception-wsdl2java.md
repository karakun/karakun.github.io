---
layout: post
title: 'Apache CXF ToolException "Tools plugin provider jaxb context init failed" when using wsdl2java '
authors: ['markus']
featuredImage: gitlab-ci-pipelines
excerpt: "Build a multiproject spanning Pipeline triggered by Tags in Gitlab CI"
permalink: '/2024/05/28/cxf-jaxb-toolexception-wsdl2java.html'
categories: [CXF, JAXB, wsdl2java, XML, SOAP, Code Generation, Gradle]
header:
  text: Apache CXF ToolException with JAXB / wsdl2java 
  image: post
---

Imagine, in a project you are asked to consume a SOAP webservice. Yes, SOAP is aged as stone BUT it is also very percisely specified as a interface, which make it still a common choice when it comes to intergrate systems. And only integrated systems deliver great benefit on the one hand as well as (old) legacy system have proven their business case which is why they are worth to integrate. (There is *gold* in legacy.) 

So while integrating that SOAP webservice, you try to generate some Java classes from the XML files provided and suddently, you see:

```
org.apache.cxf.tools.common.ToolException: Tools plugin provider jaxb context init failed
```

Welcome to the club, we found us in the very same situation.

While our sample project with an isolated setup and the following `build.gradle` was working fine, integrating it in our multi-project gradle build brought up the given error.

`build.gradle` of isolated, working, sample:

```
plugins {
	id 'java'
	id 'war'
	id 'org.springframework.boot' version '3.2.1'
	id 'io.spring.dependency-management' version '1.1.4'
	id 'com.github.seanrl.jaxb' version '2.5.1'
	id 'com.github.bjornvester.wsdl2java' version '2.0.2'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'

java {
	sourceCompatibility = '17'
}

repositories {
	mavenCentral()
}

ext {
	jaxbApiVersion = "2.2.7" //be careful with Jaxb version changes, xjc relies on it
	jaxbVersion = "2.3.5"
}

dependencies {
	implementation group: 'org.apache.httpcomponents', name: 'httpclient', version: '4.5.14'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-web-services'
	providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'

	jaxb "com.sun.xml.bind:jaxb-xjc:$jaxbVersion"
	jaxb "com.sun.xml.bind:jaxb-impl:$jaxbVersion"
	jaxb "javax.xml.bind:jaxb-api:$jaxbApiVersion"
}

tasks.named('test') {
	useJUnitPlatform()
}

wsdl2java {
	cxfVersion.set("4.0.2")
	markGenerated.set(true)
	useJakarta.set(true)
	wsdlDir.set(layout.projectDirectory.dir("src/main/resources/service-schema/"))
	generatedSourceDir.set(layout.projectDirectory.dir("build/generated-sources/wsdl2java/"))
}

task copyGeneratedFilesToSourceFolder(type: Copy) {
	dependsOn 'wsdl2java'
	from "${project.buildDir}/generated-sources/wsdl2java"
	include "**/*.java"
	into "${project.projectDir}/src/main/java/"
}
```

But as i transferred this into the lager project, I got the error mentioned above:

```
org.apache.cxf.tools.common.ToolException: Tools plugin provider jaxb context init failed
```

To get more information, try running my build with the `--stacktrace`flag, but it does not give a more detailled output or more hints than the errormessage above.

Going one step further with `--debug` , one finds himself flooded with output - but having a closer look and goind through it pay off. We find 

```
[DEBUG] [jakarta.xml.bind] Unable to find from OSGi: [jakarta.xml.bind.JAXBContextFactory]
java.lang.ClassNotFoundException: org.glassfish.hk2.osgiresourcelocator.ServiceLoader
        at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:445)
```

Using some search-skills on this output, I add 
`jaxb 'org.glassfish.hk2:hk2-api:3.1.0'`
to my `build.gradle` and get to a new output:

` org.apache.cxf.tools.common.ToolException: Could not find jaxb databinding within classpath`

which in turn leads me to the requirement of adding these to my dependencies:

```
    wsdl2java "org.apache.cxf:cxf-tools-wsdlto-frontend-jaxws:4.0.4"
    wsdl2java "org.apache.cxf:cxf-tools-wsdlto-databinding-jaxb:4.0.4"
```	

and suddently i can see the output I was longing for: 

`BUILD SUCCESSFUL in 2s`

Now, why did the transform from the simple sample project not work as expected? We found that the `jaxb` configuration in the gradle file above clashed with the things we previously had in our project. We replace the whole block with the newer glassfish dependencies:

```
    jaxb "org.glassfish.jaxb.core:4.0.5"
    jaxb 'org.glassfish.jaxb:jaxb-runtime:4.0.5'
    jaxb 'org.glassfish.jaxb:jaxb-xjc:4.0.5'
    jaxb 'org.glassfish.hk2:hk2-api:3.1.0'
```

To in essence, there is not enough tool support to make sure the dependency versions are compatible to each other and that might cause quite some headache.

Our receipt is, to build a minimal solution to the the desire feature working and then expande that working example step by step to make sure to only bring compatible things together.

Do you find this article helpful or have feedback or  questions? Feel free to send me a question, comment or request via [email](mailto:markus.schlichting+devhub-jaxb@karakun.com) or on [Mastodon](https://jit.social/@madmas).
