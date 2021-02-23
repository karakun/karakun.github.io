---
layout: post
title:  'How to submit a Bug to OpenJDK - An Unexpected Journey'
author: hendrik
featuredImage: TODO
excerpt: 'TODO'
permalink: '/rico/2021/02/22/openjdk-bug-tracker.html'
categories: [Java, OpenJDK]
header:
  text: How to submit a Bug to OpenJDK
  image: post
---

As you might know all the sources of the OpenJDK are currently moving to GitHub.
This move was planed and defined as part of the OpenJDK project Skara.
This is a huge and important step for the future of the OpenJDK.
By using GitHub and its workflows and tools (like pull requests) developers around the world can much easier participate in the OpenJDK.
In the past it was quite hard for an individual developer to contribute to the OpenJDK since the used tools (Mercurial) and workflows (the internal progress of pull requests) were quite complex and hard to understand.
By using GitHub everybody who knows how to do a pull request can technically contribute.
While you still need to do all the paperwork to become a legal / allowed committer the technical workflow is way more easy.
An example of a pull request can be found [here](https://github.com/openjdk/jdk/pull/1465).

## The OpenJDK Bug Tracker

While this is a really good progress there are still some workflows and tools in the OpenJDK that need an update.
The topic that I want to discuss today is a perfect example for a workflow where the OpenJDK must evolve.
Let's have a look on how to submit a bug to the OpenJDK.
The OpenJDK provides a bug database that is based on the tool Jira.
The database is called Java Bug Database (JBS) and you can find the bug database [here](https://bugs.openjdk.java.net).
If you are familiar with Jira the navigation and search within the database will work for you. Issues are tagged by "components", "sub-components" and "labels".
Also fields like "affected versions" and "fixed versions" are filled for the issues in the database.
Based on this you can easily check what AWT specific issues have been solved in java 1.4, for example.
Additional information about the structure of the bug database can be found [here](https://wiki.openjdk.java.net/display/general/JBS+Overview)

![OpenJDK bugtracker issue](/assets/posts/2021-02-22-openjdk-bug-tracker/openjdk-jira.png)

While searching and browsing for issues can be done without having an account at the bug database, you need to be logged in if you want to create a new bug or add comments to a bug.
While several Jira instances for open source software have an easy way to sign up the OpenJDK bug database does not provide such option.
On the starting page you can find the following information: 
"Everyone with OpenJDK Author status or above has a JBS account which may be used to create and edit bugs. Those without accounts can view bugs anonymously.".
To make a long story short: getting the OpenJDK Author status is not that easy and definitely nothing you want to do just to file a bug.
Sadly the front-page of the Jira does not contain any information how a person who is not an OpenJDK Author can submit a bug.

![Duke and Bugs illustration](/assets/posts/2021-02-22-openjdk-bug-tracker/duke_bugs_2.jpg)

## OpenJDK Bugtracking for the rest of us

Happily there is a way how the rest of us can open an issue in OpenJDK.
Sadly you need to Google such information since there is no place in the JBS where this is explained.
Also the place to report bugs without being an OpenJDK Author is not part of the OpenJDK.
Oracle provides a frontend for the OpenJDK bug database at [http://bugs.java.com/](https://bugreport.java.com/).
At the "Oracle Java Bug Database" you can use a very limited search functionality and have the possibility to display most of the information of an issue.
The following image shows the same issue in the OpenJDK Jira instance and the Oracle frontend.

![OpenJDK bugtracker issue](/assets/posts/2021-02-22-openjdk-bug-tracker/oracle-bug.png)

As you can see in the screenshot, not all information which is available at JBS Jira is part of the information in the "Oracle Java Bug Database".
Also there is no link to the issue in the Java Bug Database (JBS).
Like in the JBS you do not have any possibility to add comments to an issue within this view in the "Oracle Java Bug Database".
The search for issues is also much harder.
Therefore I will strongly suggest to always use the JBS when searching for issues.

The "Oracle Java Bug Database" provides one big advantage compared to the JBS.
It offers a form to report a bugs.
Well, this sounds like the solution we are searching for.
Let's have a deeper look at that submission process.

## I'm going on an Adventure

It is more than confusing that the form to report a bug is not part of the OpenJDK but is hidden in some Oracle mirror of it.
But let us not focus on this organizational issue and instead have a look on how to submit a bug.
The entry point to submit a bug is [https://bugreport.java.com/](https://bugreport.java.com/).

__But wait!__
The description of the page starts with __"This page is for reporting bugs in implementations of the Oracle Java Standard Edition and related products..."__.
Does this mean that I can not submit a bug if I use a different OpenJDK distribution than the Oracle JDK?
Ok, there is no other place to report bugs so we will just ignore that and continue.
Luckily it get better and the next sentence sounds more open and mentions the OpenJDK: 
__"This site is not for development support, but for developers to contribute to and be involved with OpenJDK engineers in fixing and improving Java products."__
Does that mean that I am back to square one because only people working on OpenJDK can submit a bug?
Well, this complete description might frighten 99% of all Java developers but we are reckless adventurers and won't be stopped by such (mis-)information.
On the bottom of the page you can find the "Start a new Report" button but before you can click that button you just need to check a small checkbox...

![Oracle checkbox](/assets/posts/2021-02-22-openjdk-bug-tracker/checkbox.png)

Well, if the initial text on the page has not banished all developers that wanted to report a Bug that they have found in a Java runtime than this checkbox will definitely do it.
Looks like Oracle wants to prevent the creation of new bug reports with all means.
But we are brave souls and will not surrender.
We will check that silly checkbox and continue to go down that rabbit hole.

![Duke and Bugs illustration](/assets/posts/2021-02-22-openjdk-bug-tracker/duke_bugs_1.jpg)

Happily the following page looks more promising.
Finally we're on form to create a bug report.
As you can see in the following picture the form really looks promising and contains all the fields that you expect for a typical bug report.
Here you can provide all the information that is needed to understand and reproduce a bug.
Beside the general description you can add extra information that will help OpenJDK engineers to understand and reproduce the bug.
Adding information in this text areas is really helpful for solving the issue.
If you have them at hand you can also add additional metadata like error logs or a core dumps.
Oracle provides a quite good overview of all the information that should be added to an issue [here](https://docs.oracle.com/en/java/javase/15/troubleshoot/submit-bug-report.html#GUID-3933BFE1-0193-403E-8D72-2E0DC6639EE8).

![Oracle bugtracker form](/assets/posts/2021-02-22-openjdk-bug-tracker/oracle-bug-form.png)

After submitting a new bug by using the Oracle form the issue won't be directly created in the Java Bug Database.
It looks like the report will be reviewed and checked by Oracle before it will be added the JBS Jira.
Within a "short" period of time (maximum some days) you will normally receive a mail from Oracle that your issue has been created.
Issues that are considered spam will just be ignored.
I tested this by creating an issue about how hard it is to create an issue.
I never received any answer on this request till today.

Anyway, normally you will receive a mail from Oracle (`Bug-Report-Daemon_WW@ORACLE.COM`) that informs you about the created issue.
As you can see in the screenshot the mail directly contains a link to the issue (in the "Oracle Java Bug Database") and a link to add a comment to the issue.
That's really great.

![Mail by Oracle](/assets/posts/2021-02-22-openjdk-bug-tracker/oracle-mail-2.png)

When clicking on the "provide more information about this issue" link you can add a comment to the issue by using another form of the "Oracle Java Bug Database".
Again your data will be reviewed by Oracle before it will be added as a comment to the issue.
Even if this workflow is really horrible we at least have a way to create issues for OpenJDK and comment on "our issues".
Sadly the workflow contains some problems.
To describe a problem you normally want to add an example.
Maybe you event want to highlight specific words like class names in the description of an issue.
Let's assume you want to add the following sentence to the description or comment of an issue:
"The problem is in the `some.package.Class` of the `xyz` module".
Since I'm often work with issue trackers like the one that is part of GitHub I automatically add wrap code information with the '`' sign.
Well I did this for an OpenJDK issue that I reported some time ago and you can see the result in issue [JDK-8248122](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8248122).

![Bad encoding sample](/assets/posts/2021-02-22-openjdk-bug-tracker/wrong-encoding.png)

If you read the text of the issue and ask yourself why JavaFX is mentioned here in a Java SE 11 Issue:
Well, believe it or not but the 'java.base' module still contains references to JavaFX!
But this should not be our problem for today.
As you can see in the description the workflow to migrate issues from the "Oracle Java Bug Database" to JBS has some encoding issues.
It's really sad to see such problems since the "Oracle Java Bug Database" already exists for a while.

While we found workflows to create OpenJDK bugs and comment on our bugs there is no real solution to comment on a bug that has not been reported by yourself.
Sadly the link to add a comment to an issue by using the "Oracle Java Bug Database" form does not contain the issue id and therefore you can not rebuild the URL to comment to a specific bug.
At this point we can be really happy for the manual review process at Oracle.
As already mentioned all submitted issues at the "Oracle Java Bug Database" will be reviewed by Oracle manually.
While this is not process that you would expect in 2021 and based on this it can really take some time until the issue is in the JBS you can benefit from the manual review when you want to comment an issue.
In this case just go to the form to report a new issue and add something like this to the synopsis field: "Comment for issue JDK-8248122".
With some luck your comment will be added to the official issue.
I tested this at some issues and it worked out (let's ignore the encoding problems).

## The mailing list as a service road

One think that I totally ignored in this article are the OpenJDK mailinglists.
In theory you can discuss any found issue on the mailing list and maybe a person with the OpenJDK Author status will add that issue for you.
From my point of view this workflow is even more complexe than the given one since you need to understand to what OpenJDK mailinglist you need to send such request.
Hint: [there are some of them](https://mail.openjdk.java.net/mailman/listinfo).
It is even harder to find that information than finding the "Oracle Java Bug Database".
And to be true most developers do not want to start a discussion when they found a bug.
It is even hard enough to bring people to a point on that they are willing to report a bug.
The reporting must be as easy as possible.
Otherwise most people won't do it.

![Bugs illustration](/assets/posts/2021-02-22-openjdk-bug-tracker/bugs.png)

## The journey comes to an end

Well, this was really an adventure but happily we found all the answers that we need at the end of our journey to the OpenJDK bug tracker.
Sadly the experience is miles away from what you would expect from an open source project today.
The Jira based JBS is really a good choose as for a project as big as OpenJDK and I'm quite happy that even all the old issues from fro example Java 1.2 has been added to the JBS to show a consistent history.
On the other side it is horrible that a normal person is not allowed to report an issue.
Oracle provides a rudimentary solution that is 50% a bug tracker and 50% an Oracle commercial.
While I still do not understand why it is not possible to create issues directly at the JBS I ask myself why OpenJDK does not provide such a frontend instead of Oracle.
Maybe such frontend would not contain a phrase like "Check this box to indicate that you understand this is not a place to receive support"...

As you can see there is still a lot of potential to make the workflow of bug reporting for the OpenJDK better.
I'm just thinking loud but maybe [Eclipse Adoptium](https://blog.adoptopenjdk.net/2020/06/adoptopenjdk-to-join-the-eclipse-foundation/) will be able to provide an add free und user friendly frontend to report bugs for the OpenJDK and comment on them in future.
While the Adoptium WG just get formed while I'm writing this article I know that there will be a lot of potential in this project to make Java just better and more open.
