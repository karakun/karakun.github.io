---
layout: post
title:  "JavaFX and CSS: Styling Controls"
author: hendrik
featuredImage: "css"
excerpt: "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
tags: [JavaFX, CSS]
---
In this post I want to show a control in JavaFX can be styled. To do so I will use the JavaFX Button as an example. When having a look at the regular JavaFX button it looks like this:

![Basic button]({{ "/assets/posts/javafx-css/button.png" | absolute_url }})

## A first approach
Since the Button class already defines the `.button` style class we can simply use this to define a new style for all buttons in an application. So as a first step we will define a simple CSS rule for the buttons:

{% highlight css %}
.button {
  -fx-background-color: orange;
}
{% endhighlight %}

As a first step you might think that this will end in a perfect orange button like shown in this picture:

![Expected button]({{ "/assets/posts/javafx-css/buttonA.png" | absolute_url }})

But when starting the application you will see that the border of the button is gone:

![Styled button]({{ "/assets/posts/javafx-css/buttonB.png" | absolute_url }})

And it’s getting worse when you try to interact with the styled button. The nice hover effect is gone and even when you click the button you don’t see any visual feedback. All states of the button (armed, focused, disabled, …) now looks absolutely the same. Since this is definitely not the behavior that we wanted we might need to have a deeper look at the CCS.

## Style different states
If you have read [my previews post about pseudo classes](http://www.guigarage.com/2016/02/javafx-and-css-pseudo-classes/) you might have an idea why all visual states of the button are gone now: Our CSS rule overrides the styling of the button in all states. If we want to get visual feedback again we need to define some additional rules for specific pseudo classes. Let’s start with a rule that will affect a button in the hover state:

{% highlight css %}
.button:hover {
  -fx-background-color: darkorange;
}
{% endhighlight %}

When we start our application and place the mouse cursor over a button we will see that the color of the button changes as we have defined it in the new CSS rule:

![Hover effect]({{ "/assets/posts/javafx-css/hover.png" | absolute_url }})

Based on this we can now define specific styles for all the different states of a button but there are still some points that aren’t clear. Even if we know how to create a style for the different states it’s not really clear why we need to do this and why the simple first rule to style the button overrides all other stylings that are defined for the different states of the button by JavaFX. In addition the border of the button still don’t look right. Let’s have a look at this maybe unexpected effect.

## Todo
Todo