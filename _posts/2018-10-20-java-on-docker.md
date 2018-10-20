---
layout: post
title:  'Java in Docker'
author: hendrik
featuredImage: container
excerpt: 'TODO'
categories: [Java, Docker, Cloud]
header:
  image: post
---

If you develop micro services for the cloud using (Docker) containers is the de facto standard. But even if you have a simple Java backend that is installed on a linux server the use of a container is very helpfull. Based on this it makes sense to have a deeper look at Docker containers and how Java based applications can be hosted in such containers.

##What is Docker
TODO

##Running Java applications in Docker
To run a Java based application in docker the easiest starting point are the official [Java images for Docker] that are hosted at Docker hub. The sources for this Docker images [can be found at GitHub](https://github.com/docker-library/openjdk).

TODO: Welche Versionen gibt es
TODO: Kurztes Beispiel

Fazit: Cool, aber große Images

##Shrinking the size of the images

TODO: Option 1 - JLINK
TODO: Option 2 - Alpine Linux



Alpine Linux: https://www.alpinelinux.org

Open JDK on Alpine / Project Portola: https://openjdk.java.net/projects/portola/

Optimizations to make image smaller - Like remove src.zip from JDK: https://github.com/timbru31/docker-alpine-java-maven/blob/master/Dockerfile#L23

Use JLink to create custom JRE for your app: https://blog.dekstroza.io/building-minimal-docker-containers-with-java-9/

Graph of sizes
![diagramm]({{ "/assets/posts/2018-10-20-java-on-docker/diagram-1.png" | absolute_url }})



