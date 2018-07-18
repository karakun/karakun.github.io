---
layout: post
title:  'Integration tests for open source software'
author: hendrik
featuredImage: whale
excerpt: 'This post gives an overview how we at Karakun use Docker to create integration tests for JavaEE / JakartaEE based 
libraries and frameworks. Since Travis supports Docker since some time the post will show how you can use that approach even 
for open source software.'
categories: [Java, Docker, Open Source, Travis, Integration Tests, Tests, JavaEE, JakartaEE]
header:
  image: post
---

When writing software that is based on JavaEE / JakartaEE you have one big benefit: All APIs are specified and therefore 
your software will run on any application server :)

Sadly the reallity is different. While the introduction is true for mostly 99% of all use cases there are still some pitfalls.
Since all application servers like TomEE, Wildfly or Payara are developed by human they all have bugs. I do not want to say
that they are not useable. Too be true the big players are really stable and flexible but sometimes you will find some
behavior that is different in one application sever against all the other ones. If you write an application this is not that
important since you normally will run your app only with one application server type. It won't make sense to test your
application on Wildfly and use TomEE in production. But if ypou want to develop a library or a framework that depends on the
JavaEE specification and should be useable in any application it really makes sense to test your code on all application
servers or at least at as many as possible. This post will give you an overview how you can achive this goal by using docker.

## How to write tests for an enterprise library

Let's assume you develop a library that adds [Server Timing](https://www.w3.org/TR/server-timing/) information to HTTP 
responses. Server Timing is a new W3C features that allows you to add some metrics about the request handling to the response.
The following images shows how such information would be rendered in the developer console of chrome:

![Duke]({{ "/assets/posts/2018-07-18-integration-docker/server-timing.png" | absolute_url }})

When developing such a feature for JavaEE an implementation of the `javax.servlet.Filter` interface will be a good choose. In 
our library we could provide the following class:

{% highlight java %}
package com.karakun.enterprise;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServerTimingFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {}

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        chain.doFilter(request, response);
        ServerTiming.writeTiming(response);
    }

    @Override
    public void destroy() {}
}
{% endhighlight %}

As you can see we call the static `ServerTiming.writeTiming()` method in our filter. This methods adds some headers to the
HTTP response that is represented by the `ServletResponse` instance that is passed to the method. Even if this method is using
APIs from the JavaEE specifications (the `ServletResponse` interface that is part of the servlet spec) we can easily provide 
some unit tests to check the functionallity of the method. A test method could look this:

{% highlight java %}
@Test
public void testServerTiming() {
    final ServletResponse response = new ResponseMock();
    ServerTiming.addTiming("Call DB", 3450);
    ServerTiming.writeTiming(response);
    assertTrue(response.containsTiming("Call DB", 3450));
}
{% endhighlight %}

By doing so we can easily test that all information is added to the response and our Server Timing implementation is working.

One thing that we can not test is the usage of the `javax.servlet.Filter`. Such a filter can be added to a servlet context and
than mutate each response for a defined endpoint. If you want to add the filtering to all requests that a server application 
receives you can add the `javax.servlet.annotation.WebFilter` annotation to your class or do the registration in code like it
is shown in the following snippet:

{% highlight java %}
final Filter filter = new ServerTimingFilter(true);
final FilterRegistration.Dynamic createdFilter = servletContext.addFilter("ServerTiming", filter);
createdFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
{% endhighlight %}

To check that the code snippet is working and your custom filter will really be called for each request you need to test your
library with an application server. Since your library will be used in several applications in near future it is important to
test it with all application server types (and releases) that are used by your customers.

>The given example will easily work on every application server since it only use some common and well tested features of the servlet API. Since the main focus of this article should be the workflow to provide integration tests I do not wanted to make
the sample complicate.

Let's assume you need to check that your library is running fine on TomEE and Payara. To do so you can easily install local
instances of the applicstion serversn on your machine, deploy a test application that internally uses your library and open
some endpoints in your browser. Here you can check if you really see the Server Timing information in the developer view of your browser.

While the described workflow is fine for a first test you do not want to do this after every change in the sourcecode of the
library. Once you did it 3 times you now that you need to automate the workflow in any way. Next to this your team members
maybe do not know how you do the tests and push code changes without checking the functionallity on TomEE.

## Integration tests with Docker

Since we want to test the integration of our library in specific application servers so called integration tests are the 
solution to our problem. To test this automatically after every code change or with every build we need to automate the
following steps:

* Build the library
* Build a sample application that uses the library
* Install a Payara
* Install a TomEE
* Configuare Payara and TomEE to use different ports on the local machine
* Deploy the sample app in Payara and TomEE
* Trigger some Endpoints
* Check the response

Let's start with the most easy part that can be done 100% in Java: Writing a tests that triggers endpoints and checks the
response. One sultion to create such a test that can dynamically called for several server instances is the data provider
functionallity of TestNG. Here we can provide a list of configurations for our tests. The following snippet cotains a method 
that is annotated with the `org.testng.annotations.DataProvider` annotation and provides some configurations for our tests:

{% highlight java %}
@DataProvider(name = "endpoints")
public Object[][] getEndpoints() {
    return new Object[][]{
            new Object[]{"TomEE", "8080"},
            new Object[]{"Payara", "8081"}
    };
}
{% endhighlight %}

The method provides the configuration for 2 endpoints (TomEE on port 8080 and Payara on port 8081). This configurations
can now used in a test method:

{% highlight java %}
@Test(dataProvider =  "endpoints")
public void testEndpoints(String containerType, String port) {
    print("Testing " + containerType);
    final String url = "http://localhost:" + port + "/test";
    final Map<String, Long> timings = callEndpoint(url);
    assertContains(timings, "Call DB", 3450);
}
{% endhighlight %}

By executing the test TestNG will automatically call it 2 times based on the given configuration. At the moment the tests will
fail since we do not have any applications that are deployed or maybe not even an application running.

To automatically bootstrap an application server with our test application we will use Docker. I will not describe the
functionallity of Docker since this will XXXXX the scope of this article.

Adam Bean provides some good Docker containers for JavaEE application servers that can be used as a base for our containers.
You can find all needed Docker files [at Github](https://github.com/AdamBien/docklands). For our sample we will use this 
container descriptions as a base and extend them with the needed functionallity. For TomEE our Docker file will look like this:

{% highlight java %}
FROM airhacks/tomee:7.0.4-plus
MAINTAINER Hendrik Ebbers, karakun.com
COPY sample.war ${DEPLOYMENT_DIR}
{% endhighlight %}

The `DEPLOYMENT_DIR` variable is already defined in the docker file from Adam and we can easily use it to add our application 
(the `sample.war`) to the TomEE instance that is running in the Docker container. The only importantb point is that the war is
in the same folder as the docker file when you build and start the container. When starting the container by hand you can 
easily map the internal port of the application server (8080) to any free port of your local system by adding a port mapping:

{% highlight shell %}
docker run -p 8080:8080
{% endhighlight %}