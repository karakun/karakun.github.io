---
layout: post
title:  'Gradle: flavoured artefacts from a multi-module build'
author: markus
featuredImage: flavours-tom-hermans-642316-unsplash
excerpt: 'Customized artefacts can be a headache to build and distribute. To the rescue, Gradle provides a powerful DSL that can solve that task easily - here is an example how.'
tags: [Java, Gradle]
header:
  image: post
---

While providing a solution customized for many customers, you might want to package an application artefact in multiple flavours. 
Each with a set of dependencies to provide just the customer specific implementations.

With gradle this can be accomplished fairly easy. 
To demonstrate it, I've created a [example project available on github](https://github.com/madmas/gradle-flavoured-artefacts).

It consists of five modules:
* an application module
* an API interface module
* three modules as API implementations 

The API interface module is not needed during runtime but helpful during development time.
It helps to enforce the same expected interface in the implementation classes.

The `lib-impl*` modules contain each a custom implementation for the `CustomDataProvider` and demonstrate the different flavours of the artefacts.

Now, the question is how to provide several artefacts of the application, 
each bundled with one of the library implementations.

One solution (as always, there a probably many possible out there) is to use Gradle _configurations_ with the [Shadow Plugin]( http://imperceptiblethoughts.com/shadow/#introduction ).
Using the shadow plugin is the exchangeable part here. 
As we basically do rely on the grade configurations, 
the way the application is package is completely open and this solution can probably also be applied to other packaging plugins.

Having a look at the [`build.gradle` of the application module]( https://github.com/madmas/gradle-flavoured-artefacts/blob/master/application/build.gradle ) reveals that we create a configuration for each flavour we want to have a seperate artefact for.

{% highlight groovy %}
configurations {
    customerAcompile
    customerBcompile
    customerCcompile
}
{% endhighlight %}

Now, we add the dependencies...

{% highlight groovy %}
dependencies {
    customerAcompile project(':lib-implA')
    customerBcompile project(':lib-implB')
    customerCcompile project(':lib-implC')
}
{% endhighlight %}

And finally we want to have a task for each artefact to be generated. 
In our case such is a ShadowJar task. 
To make this concise and clean and easy to extend, we can use the Gradle DSL based on Groovy.

First, we define a array of the variants we want:

{% highlight groovy %}

ext {
    variants = [
            [name: "customerA"],
            [name: "customerB"],
            [name: "customerC"]
    ]
}
{% endhighlight %}

And then we use that array to loop over and create the build tasks:

{% highlight groovy %}
variants.each { variant ->
    task "${variant.name}ShadowJar"(type: ShadowJar) {
        classifier = "${variant.name}"
        from sourceSets.main.output
        configurations = [project.configurations."${variant.name}compile"]
        manifest {
            attributes 'Main-Class': mainClassName
        }
    }
    shadowJar.dependsOn("${variant.name}ShadowJar")
}
{% endhighlight %}

The `classifier`property is used to get an distingushable artefact name. 
`from` and `configurations` make up the thing comined together into the shadowJar artefact.

>If you need to apply the base idea of this article to another packaging context, 
those two properties are probably those you want to take and apply accordingly.
{:.admonition-note}


And finally, we define the `Main-Class` name as an sttribute for the manifest. 
ShadowJar usually adopts it automatically when the application plugin is used, 
but as we are using a custom shadowJar task here, we also need to apply it with those three lines.

Finally, by defining the dependency with `dependsOn`, we can afterwards just type
`gradle shadowJar` to have all variants build and waiting for us in `build/libs/`.
Execution shows the expected behaviour for each of them:

{% highlight bash %}
➜ java -jar build/libs/application-customerC.jar                               
s = Implementation or customer C

➜ java -jar build/libs/application-customerB.jar
s = Implementation or customer B

➜ java -jar build/libs/application-customerA.jar
s = Implementation or customer A

{% endhighlight %}

Have fun building the solutions that make your customers happy! :-)

(Photo by [Tom Hermans on Unsplash](https://unsplash.com/@tomhermans))
