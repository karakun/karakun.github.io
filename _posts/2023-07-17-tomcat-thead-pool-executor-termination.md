---
layout: post
title: 'Terminating custom scheduled tasks in Spring Framework properly to prevent memory leaks'
author: 'giorgi'
featuredImage: tip-jar
excerpt: "You may or may not have seen this error before but when you have a custom `ThreadPoolTaskScheduler` configured for your `@Scheduled` methods the re-deployment your app in context of Tomcat will give you the following warning message..."
permalink: '/2023/07/17/tomcat-thread-pool-executor-termination.html'
categories: [Spring Boot, Spring Framework, Java, Tomcat, JVM]
header:
  text: Terminating custom scheduled tasks in Spring Framework properly to prevent memory leaks
  image: post
---

You may or may not have seen this error before but when you have a custom `ThreadPoolTaskScheduler` configured for your `@Scheduled` methods the re-deployment of your app in context of Tomcat will give you the following warning message: 

```text
WARNING [Catalina-utility] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesThreads
The web application [x] appears to have started a thread named [y] but has failed to stop it. 
This is very likely to create a memory leak.
```

Before we start explaining why Tomcat displays this warning and how to get rid of it, let's first recreate the situation when it occurs.

First, let's configure a custom `ThreadPoolTaskScheduler` bean:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolTaskSchedulerConfiguration {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(2);
        threadPoolTaskScheduler.setThreadNamePrefix("houseKeepingScheduler - ");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
```

Then let's register it as a scheduler for `@Scheduled` tasks by implementing `SchedulingConfigurer` interface:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class HouseKeepingExecutorConfiguration implements SchedulingConfigurer {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public HouseKeepingExecutorConfiguration(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(threadPoolTaskScheduler);
    }
}
```

Now this is a state of the configuration that should trigger Tomcat to display the above-mentioned warning. Just for the sake of clarity when we say "re-deployment" we are talking about replacing a `*.war` file and letting Tomcat automatically trigger `org.apache.catalina.startup.HostConfig.undeploy` method to undeploy the application context and then run `org.apache.catalina.startup.HostConfig.deployWAR` to re-deploy the updated file.

Note that if we stop the Tomcat first and replace `*.war` file like that you will not see this error.

### The Problem
The problem arises due to the fact that Tomcat replaces the `ClassLoader` with a new one during redeploy and there's a problem
with the old class loader being garbage collected.

Here's a more detailed problem description from [this](https://stackoverflow.com/a/7791593){:target="_blank" rel="noopener"} answer:
> When you redeploy your application, Tomcat creates a new class loader. The old class loader must be garbage collected, otherwise you get a permgen memory leak.
>
> Tomcat cannot check if the garbage collection will work or not, but it knows about several common points of failures. If the webapp class loader sets a `ThreadLocal` with an instance whose class was loaded by the webapp class loader itself, the servlet thread holds a reference to that instance. This means that the class loader will not be garbage collected.

### The Solution

We clearly have to take care that our custom `ScheduledThreadPoolExecutor` is being correctly terminated when servlet context is being destroyed. 
In order to do so, we shall implement and configure `ServletContextListner` as follows:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class CustomServletContextListener implements ServletContextListener {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public CustomServletContextListener(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        this.scheduledThreadPoolExecutor = taskScheduler.getScheduledThreadPoolExecutor();
    }

    @Override
    public void contextDestroyed(ServletContextEvent context) {
        scheduledThreadPoolExecutor.shutdown();

        // see: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html
        try {
            if (!scheduledThreadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduledThreadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            scheduledThreadPoolExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
```

As a result, every time servlet context is about to shut down, we make sure to manually shut down spawned `ScheduledThreadPoolExecutor` too.


### References
- [https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html){:target="_blank" rel="noopener"}
- [https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ScheduledThreadPoolExecutor.html](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html){:target="_blank" rel="noopener"}
- [https://jakarta.ee/specifications/platform/9/apidocs/index.html?jakarta/servlet/ServletContextListener.html](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html){:target="_blank" rel="noopener"}
- [https://cwiki.apache.org/confluence/display/TOMCAT/MemoryLeakProtection](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html){:target="_blank" rel="noopener"}
- [https://stackoverflow.com/a/7791593](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html){:target="_blank" rel="noopener"}