---
layout: post
title:  'How to submit a Bug to OpenJDK - An Unexpected Journey'
authors: ['hendrik']
featuredImage: bugs
excerpt: "In this article, I will have a look at how developers can create issues for Java (the openjdk project) or comment on existing issues.
Creating issues is one of the simplest actions that a developer can do to help open source projects.
By doing so the maintainers of such project will get a good overview of the users, their problems and wishes.
Let's have a look at how such use cases are defined at the openjdk."
permalink: '/rico/2021/02/22/openjdk-bug-tracker.html'
categories: [Java, OpenJDK]
header:
  text: How to submit a Bug to OpenJDK
  image: post
---

Maybe you have heard that the sources of the OpenJDK are currently moving to GitHub.
This move is planned and coordinated as part of the OpenJDK [Project Skara](https://wiki.openjdk.java.net/display/SKARA).
Many consider this to be a major step for the future of the OpenJDK.
By using GitHub and its workflows, developers around the world can participate in the development of OpenJDK.
In the past, it was difficult for a regular developer to contribute to the OpenJDK.
This was partly due to the VCS (Mercurial) which was used to manage the OpenJDK sources.
Also, the workflows (e.g. the internal progress of code reviews) were quite complex and hard to understand.
Git and GitHub are widely used today, and most developers are familiar with them.
This lowers the bar for people to contribute for the first time.
While you still need to do all the paperwork to become an official committer, submitting a change request (i.e. a pull request) is now much simpler.
If you are interested you can find an example of a pull request [here](https://github.com/openjdk/jdk/pull/1465).

## The OpenJDK Bug Tracker

While this is certainly a leap forward, there are still some workflows and tools in the OpenJDK environment that need an update.
The topic I want to discuss today is a perfect example of a workflow where the OpenJDK must evolve:
Let's have a look at how to submit a bug to the OpenJDK.

The OpenJDK provides a bug database that is based on the tool Jira.
The database is called [JDK Bug System (JBS)](https://bugs.openjdk.java.net).
If you are familiar with Jira the navigation and search within the database will work for you.
Issues are tagged by "components", "sub-components" and "labels".
Also fields like "affected versions" and "fixed versions" are filled for the issues in the database.
Based on this you can easily check what AWT specific issues have been solved in java 1.4, for example.
Additional information about the structure of the bug database can be found in the [OpenJDK wiki](https://wiki.openjdk.java.net/display/general/JBS+Overview)

![OpenJDK bugtracker issue](/assets/posts/2021-02-22-openjdk-bug-tracker/openjdk-jira.png)

On the JBS starting page you can find the following information:

> Everyone with OpenJDK Author status or above has a JBS account which may be used to create and edit bugs.
> Those without accounts can view bugs anonymously.

The good news is, searching and browsing for issues can be done without having an account in the bug database.
If you want to report a bug, on the other hand, you need to have a login i.e. you need to be an OpenJDK Author.
To make a long story short: getting the OpenJDK Author status is not that easy and definitely nothing you want to do to simply file a bug.
Sadly the front-page of the Jira does not contain any information on how somebody can submit a bug who is not an OpenJDK Author.
There is only a vague hint to the wiki:

> See the [OpenJDK wiki](https://wiki.openjdk.java.net/display/general/JBS+Overview) for information on using the system.

![Duke and Bugs illustration](/assets/posts/2021-02-22-openjdk-bug-tracker/duke_bugs_2.jpg)

## The Oracle Java Bug Database

Oracle provides a frontend for the OpenJDK bug database called [Oracle Java Bug Database](http://bugs.java.com/).
At the "Oracle Java Bug Database" you can use a simple search and have the possibility to display the main information of an issue.
The following image shows the above mentioned issue in the Oracle Bug Database.

![OpenJDK bugtracker issue](/assets/posts/2021-02-22-openjdk-bug-tracker/oracle-bug.png)

The information available at "Oracle Java Bug Database" is only a subset of the ones in JBS Jira.
This can be helpful when trying to get an overview of an issue.
What is missing is a link to the issue in the JDK Bug System (JBS) where all the details are visible.
Like in the JBS you do not have any possibility to add comments to an issue within this view in the "Oracle Java Bug Database".
The search for issues is also much harder.
Therefore, I will strongly suggest to always use the JBS when searching for issues.

## Reporting a Bug - for the rest of us

Fortunately, there is a way how the rest of us can open an issue in the OpenJDK.
But there is no explanation of the process in the JBS, so you have to google such information.
Additionally, the place to report bugs is not within the OpenJDK or the JBS but in the Oracle Java Bug Database.

In contrast to JBS, the start page of the Oracle Java Bug Database contains a lot of information.
It lists links or short descriptions on how to

- Report an Issue
- Suggest an Enhancement
- Submit a Code Fix or Test Case
- How to find the Frequently asked Questions

Well, this sounds promising.
Let's have a look at the paragraph about reporting an issue.
It asks us to search the existing database before opening an issue to avoid duplicated reports.
It links to a guide on which information to provide with an issue and how to collect it.
Finally, it points out that if you really need a fix for the bug it is best to get paid support from Oracle.

The guide is extremely comprehensive so plan some time to read it.
Having all these options and steps explained is very helpful but wait - the guide is for Java 10.
With a little URL fiddling (simply change the version in the URL) we can get the guide of the version we want to report the bug for.

That's it, we are ready to open our issue.

## I'm going on an Adventure

It is already confusing that the place to report a bug is not part of OpenJDK, rather it is hidden away in an Oracle mirror of it.
Surely, this has some historical or organizational reason and simply is the way it is because it always has been this way.

This is a topic for another article.
We want to stay focused.
Let's have a look at how to submit a bug.
The entry point to submit a bug is [https://bugreport.java.com/](https://bugreport.java.com/).

__But wait!__
This is not a form to report an issue.
The page looks very similar to the start page.

The very first paragraph starts with the following statement:

> This page is for reporting bugs in implementations of the **Oracle Java Standard Edition** and related products...

Does this mean that we cannot submit a bug if we use an OpenJDK distribution other than the Oracle JDK?
Ok, there is no other place to report bugs, so we will just ignore this and continue.
Luckily it gets better, and the next sentence mentions OpenJDK:

> This site is not for development support, but for developers to contribute to and be involved with OpenJDK engineers in fixing and improving Java products.

Does that mean that I am back to square one because only people working on OpenJDK can submit a bug?
Well, this complete description might frighten 99% of all Java developers, but we are reckless adventurers and won't be stopped by such (dis-)information.
The rest of the page once again asks us to double-check that the bug has not been reported before and is not fixed in an upcoming release of the next Java version.
Also, the guide from the start page is linked again (this time to the Java 15 version) and a FAQ page for submitting issues.
At the bottom of the page, you can find the "Start a new Report" button. Before you can click that button you just need to check a small checkbox...

![Oracle checkbox](/assets/posts/2021-02-22-openjdk-bug-tracker/checkbox.png)

Well, if the initial text on the page has not banished all developers that wanted to report a bug that they have found in a Java runtime then this checkbox will definitely do it.
Looks like Oracle wants to discourage the creation of new bug reports by all means.
Not us, we are brave souls and will not surrender.
We will check that silly checkbox and continue to go down that rabbit hole.

![Duke and Bugs illustration](/assets/posts/2021-02-22-openjdk-bug-tracker/duke_bugs_1.jpg)

Hooray, we are finally on the form to create a bug report.
As you can see in the following picture the form really looks like many other bug tracker forms.
You find all the fields for reporting the issue, the expected and actual behavior, and much more.
You can and should also provide code to reproduce the bug.
Adding information in these text areas is really helpful for solving the issue.
Frankly speaking, if you do not provide sufficient information your issue may simply not make it to the OpenJDK Bug Tracker.
If you have them at hand you can also add additional metadata like error logs or core dumps.
Oracle provides a quite good overview of all the information that should be added to an issue [in the guide](https://docs.oracle.com/en/java/javase/15/troubleshoot/submit-bug-report.html#GUID-3933BFE1-0193-403E-8D72-2E0DC6639EE8).

![Oracle bugtracker form](/assets/posts/2021-02-22-openjdk-bug-tracker/oracle-bug-form.png)

After submitting a new bug by using the Oracle form the issue won't be directly created in the Java Bug Database.
It looks like the report will be reviewed and checked by Oracle before it will be added to the JBS Jira.
Within a "short" time (maximum a few days), you will normally receive a mail from Oracle that your issue has been created.
Issues that are considered spam will just be ignored.
I tested this by creating an issue about how hard it is to create an issue.
I never received any answer to this request until today.

Anyway, normally you will receive a mail from Oracle (`Bug-Report-Daemon_WW@ORACLE.COM`) that informs you about the created issue.
As you can see in the screenshot the mail directly contains a link to the issue (in the "Oracle Java Bug Database", not in the JBS) and a link to add comments to the issue.
That's handy, especially if you are asked to provide additional information about the issue.

![Mail by Oracle](/assets/posts/2021-02-22-openjdk-bug-tracker/oracle-mail-2.png)

When clicking on the "provide more information about this issue" you are directed to another form of the "Oracle Java Bug Database".
You can now enter the additional information.
Again, your data will be reviewed by Oracle before it will be added as a comment to the issue.
Even if this workflow is horrible we at least have a way to create issues for OpenJDK and comment on "our issues".
Sadly the workflow contains some problems.
To describe a problem you normally want to add an example.
Maybe you want to highlight specific words like class names in the description of an issue.
Let's assume you want to add the following sentence to the description or comment of an issue:

> The problem is in the \`some.package.Class\` of the \`xyz\` module.

Many developers are used to working with issue trackers which support markdown to add some minimal formatting to an issue.
Well, I tried this for an OpenJDK issue which I reported some time ago.
You can see the result in issue [JDK-8248122](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8248122).

![Bad encoding sample](/assets/posts/2021-02-22-openjdk-bug-tracker/wrong-encoding.png)

If you read the text of the issue and ask yourself why JavaFX is mentioned here in a Java SE 11 Issue:
Well, believe it or not, the 'java.base' module still contains references to JavaFX!
But this should not be our problem for today.
As you can see in the description the workflow to migrate issues from the "Oracle Java Bug Database" to JBS has some encoding issues.
It's really sad to see such problems since the "Oracle Java Bug Database" already exists for quite a while.

While we found a way to create OpenJDK bugs and comment on our bugs there is no real solution to comment on a bug that has not been reported by yourself.
Sadly the link to add a comment to an issue by using the "Oracle Java Bug Database" form does not contain the issue id and therefore you can not rebuild the URL to comment on a specific bug.
At this point, we can be really happy with the manual review process at Oracle.
As already mentioned all submitted issues at the "Oracle Java Bug Database" will be reviewed by Oracle manually.
This is not a process that you would expect in 2021, and it leads to a time delay until an issue is created or updated.
But you can take advantage of this manual step if you want to comment on an issue.
Simply go to the form for reporting an issue, but instead of opening a new one ask to add additional information to an existing one.
To do this you can use the __synopsis__ field and give it the value: "Comment for issue JDK-xxxxxxxxx"
After the manual review process your comment will be added to the corresponding JDK issue.
I tested this for an issue, and it worked well (let's ignore the encoding problems).

## The mailing list as a service road

What I totally ignored in this article are the OpenJDK mailing lists.
In theory, you can discuss any encountered issue on the mailing list and maybe a person with the OpenJDK Author status will add that issue for you.
From my point of view, this workflow is even more complex than the one above since you need to understand to which OpenJDK mailing list you need to send such requests.

Hint: [there are many of them](https://mail.openjdk.java.net/mailman/listinfo).

It is even harder to find the correct mailing list than it is to find the "Oracle Java Bug Database".
And honestly, in the year 2021 most developers do not want to start a mailing list discussion when they found a bug.
It is even hard enough to encourage people to report a bug.
The reporting must be as easy as possible.
Otherwise, many people simply will not do it.

![Bugs illustration](/assets/posts/2021-02-22-openjdk-bug-tracker/bugs.png)

## The journey comes to an end

Well, this was really an adventure but fortunately, we found all the answers that we need at the end of our journey to the OpenJDK bug tracker.
But the user experience is miles away from what we all expect from an open-source project today.
The Jira-based JBS is really a good fit for a project of the size of OpenJDK.
It is also very helpful that all the old issues from Sun and Oracle (e.g. from Java 1.2) have been migrated to the JBS to show a complete history.
On the other hand, it is clumsy that you cannot create an account and report issues directly in the bug database. Again, only JDK Authors are allowed to do this.

Oracle provides a tedious solution that is half bug tracker and half point of sale for Oracle support.
There might be some good reasons to disallow creating issues directly at the JBS (spam or security).
But having the frontend hosted by Oracle instead of OpenJDK is confusing and sometimes also misleading.
If it was provided by the OpenJDK maybe we would not see several mentions of paid Oracle support or checkboxes telling you that this is not a place to get support.

There is still a lot of potential to make the workflow of bug reporting for the OpenJDK better.
While researching for this article I found many answers to the questions I encountered.
They are scattered in the wiki, the help pages, and different guides.
A low-hanging fruit would be to collect all these bits and pieces and summarize them in a short step-by-step tutorial.
Then put this tutorial prominently on the start page of the JBS, the Github repositories, and the Oracle Java Bug Database.

Or maybe [Eclipse Adoptium](https://blog.adoptopenjdk.net/2020/06/adoptopenjdk-to-join-the-eclipse-foundation/) will be able to provide an ad-free and user-friendly frontend to report bugs for the OpenJDK and comment on them.
The Adoptium working group is in the process of getting formed as I'm writing this article.
But I already feel that there will be a lot of potential in this project to make Java simply better and more open.
