---
layout: post
title:  'Missing xcrun - Mac OS update killed my JavaScript builds'
author: hendrik
featuredImage: tip-jar
excerpt: 'At the weekend I did the Mac OS update to the newest version "Mojave". While first everything looked
good I needed to build a JavaScript project today. At Karakun we always try to set up JavaScript projects as friendly
as possible for Java developers and stupid people like me, the Mojave update destroyed something on my machine and
the build ends with a "Missing xcrun" error message.'
categories: [Java]
header:
  image: post
---

One might say the problems already started when I got my hands at JavaScript ;) - but to be fair as a Java developer today
it is very helpfull for your job to now about JS and how a web frontend can be added to a Java based server application.



[INFO] bower paper-item#^2.0.0                  cached https://github.com/PolymerElements/paper-item.git#2.1.1
[INFO] bower paper-item#^2.0.0                validate 2.1.1 against https://github.com/PolymerElements/paper-item.git#^2.0.0
[INFO] bower polymer#^2.1.1                     cached https://github.com/Polymer/polymer.git#2.6.0
[INFO] bower polymer#^2.1.1                   validate 2.6.0 against https://github.com/Polymer/polymer.git#^2.1.1




[ERROR] bower font-roboto#^1.0.1                           ECMDERR Failed to execute "git ls-remote --tags --heads https://github.com/PolymerElements/font-roboto-local.git", exit code of #1 xcrun: error: invalid active developer path (/Library/Developer/CommandLineTools), missing xcrun at: /Library/Developer/CommandLineTools/usr/bin/xcrun
[ERROR] 
[ERROR] Additional error details:
[ERROR] xcrun: error: invalid active developer path (/Library/Developer/CommandLineTools), missing xcrun at: /Library/Developer/CommandLineTools/usr/bin/xcrun




xcode-select --install