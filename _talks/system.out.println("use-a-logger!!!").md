---
layout: talk
title: System.out.println("USE A LOGGER!!!");
speakers:
  - hendrik
  - stephanc
summary: >
  No matter if you are developing a small library, a complex framework or an
  application for end users - you will come to the point that you want to log
  information. The Java community offers a whole wealth of solutions: Besides
  java.util.logging there are libraries like tinylog, Log4J or Logback. Where
  are the differences and unique selling points of these frameworks? In
  addition, actually everyone says that only the use of a facade like slf4J is
  "best practice". But why should I use an abstraction when developing a
  concrete application? Or does a facade perhaps offer completely different
  advantages? And as if that weren't enough questions, with Java 9 a new logger,
  System.Logger, was quietly integrated into the OpenJDK.

  This talk gives an understandable guide through the logging jungle and shows a
  few practices that help to get the logging of libraries and applications under
  control. If you're on the verge of going back to System.out out of
  frustration, this session is for you.
lectures:
  - JavaLand 2021
  - JCON 2021
slides-link: 'https://speakerdeck.com/hendrikebbers/system-dot-out-dot-println-use-a-logger'
video-link: >-
  https://www.youtube.com/watch?v=hwyEanE6tX8&list=PL9r_sTu6nhXSFYknILieFl2YbFBrJIWRW&index=2
featuredImage: logger
index: 35

---

No matter if you are developing a small library, a complex framework or an application for end users - you will come to the point that you want to log information. The Java community offers a whole wealth of solutions: Besides java.util.logging there are libraries like tinylog, Log4J or Logback. Where are the differences and unique selling points of these frameworks? In addition, actually everyone says that only the use of a facade like slf4J is "best practice". But why should I use an abstraction when developing a concrete application? Or does a facade perhaps offer completely different advantages? And as if that weren't enough questions, with Java 9 a new logger, System.Logger, was quietly integrated into the OpenJDK.
This talk gives an understandable guide through the logging jungle and shows a few practices that help to get the logging of libraries and applications under control. If you're on the verge of going back to System.out out of frustration, this session is for you.
