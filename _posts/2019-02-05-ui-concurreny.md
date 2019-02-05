---
layout: post
title:  'Concurrency in UI Toolkits'
author: hendrik
featuredImage: needle
excerpt: 'TODO'
categories: [Java, JavaFX, Swing]
header:
  text: Concurrency in UI Toolkits
  image: post
---

All ui toolkits today today have several things in common.
Once thing that each ui toolkits handles and provides is an thread that is used to repaint the gui, handles user interaction and can be used to mutate the views.
Not only desktop related tookits define such thread - mobile platforms like Android or iOS does all ui realted operations on a special thread, too.
Even all browsers internally manage such thread to handle the view of a website (or application).
Let's call such thread a "ui thread".

![ui thread]({{ "/assets/posts/2019-02-05-ui-concurreny/ui-thread.png" | absolute_url }})

In general a ui thread can be definied by this:

- The ui thread repaints your application (or parts of your application that have changed)
- The ui thread handles the window management
- The ui thread handles all user interaction (like calling input listeners)
- The ui thread must be used whenever parts of the gui will be manipulated

Whenever any of this operations is done on a different thread than the ui thread, horribly things can happen:
Maybe your view justs looks like totally wrong or your complete application crashes.
This problems can easily be described by the behavior of a textfield.
Let's assume we have an textfield in our gui that shows the text "Hello World".
The textfield will be rendered on screen by a ui toolkit specific repaint command that will autoamtically called by the toolkit on its ui thread.

![textfield]({{ "/assets/posts/2019-02-05-ui-concurreny/textfield-1.png" | absolute_url }})

Such textfield has a mutateable property "text" that can be editied to show a different text in the textfield. 
Based on this we can cheange the text simply by doing a call like this:

{% highlight java %}
textfield.setText("Shy boy");
{% endhighlight %}

If we would do such call on a different thread than the ui thread the runtime behavior of our application will look as shown in the following graph:

![bad background thread]({{ "/assets/posts/2019-02-05-ui-concurreny/bad-background-thread.png" | absolute_url }})

As you can see in the diagram the change of the text will happen in parallel to a repaint process of the ui toolkit.
While the imolementation of such repaint is toolkit specific we can not say for sure what impact such parallel call will have and since it's parallel we will nmore or less never get the same result.
One scenario that could happen is that the text is mutated by the custom call in the same moment as the rendering of the textfield is happening on the ui thread.
In such case we could end with a wrong ui:

![textfield]({{ "/assets/posts/2019-02-05-ui-concurreny/textfield-2.png" | absolute_url }})

Instead of displaying the correct information "Shy boy" in the textfield we now can see the text "Hellboy".
In this case the framework still used the old text content ("Hello World") when starting the rendering.
After the first 3 letters were rendered the text content has been changed by our custom thread.
Now the ui toolkit continue rendering based on the new content ("Shy boy") starting at character 4 since 3 characters were already rendered.

Wether you like the "Hellboy" comics or not this is defentely a big problem. 
When creating user interfaces one of the most important rules is that a user always sees the right data.
In this case the user can not rely on the information that he sees on the screen. 
Imagine what could happen if users in a credit institute just see wrong numbers on the screen ;)
Happily all ui toolkits provide utilities that you can use to handling such issues.

## Invocing code in the ui thread

TODO





----------------------


TODO


-> Kopie von http://www.guigarage.com/2015/01/concurrency-ui-toolkits-part-1/


Today every UI toolkit that is not running in a browser needs an UI Thread the handle the repainting and the event handling of the UI. Examples for this kinds of UI Toolkits are iOS, Android, SWT, JavaFX or Swing. Each of this toolkits defines a thread that will handle all the ui specific calls. Let’s call this thread “UI Thread”.


By definition all calls that will affect the UI or get data of the UI must be called on this thread. Accessing the UI from another thread than the UI Thread will result in a lot of different problems.

Normally all user events that are handled by the toolkit will be called on the UI thread. Therefore an event handler will be called on the thread, too. If a developer wants to interact with UI components as a result of an user event he can do this directly in the handler. The following code shows some pseudo code how this might look. Normally each UI Toolkits provide it’s own mechanism for event handling.

button.setOnAction(event -> button.setEnabled(false));

If you want to interact with the UI from outside of an event you need to invoke your code on the “UI Thread”.

PIC

Each UI Toolkit provides a helper method to handle this invocation. In a UI Toolkit independent and unified way this method might look like this:

void runOnUiToolkitThread(Runnable runnable);

By doing so any runnable can be called on the UI Thread. This is ok for some general use cases but it’s definitely not enough to create an big application. One of the big problems is that you don’t know when the code will be called and when the call is finished. The definition of this method only says that the code will be called in some future on the UI Thread. Therefore we need a second method that blocks until the call is finished.

PIC

In most cases this method will have the same signature as the previous one. Let’s define the method in a unified way:

void runOnUiToolkitThreadAndWait(Runnable runnable);

Thanks to Java 8 we can define this method as a default method based on the other one:

default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        FutureTask<Void> future = new FutureTask<>(runnable, null);
        runOnUiToolkitThread(future);
        future.get();
}

This looks good so far but there is still a problem. As said the UI must only be accessed by using the UI Thread. Let’s think about a background thread that want’s to call a web service based on some user input. To do so the thread needs to know the input of a textfield, for example. Because we can’t access the text from the background thread we need to invoke the call to the UI Thread:

PIC

The following code shows how such a call might look like:

public void runningOnBackgroundThread() {
  String userInput = runOnUiToolkitThreadAndWait(() -> textfield.getText());
  callWebservice(userInput);
}

To do so we need another helper method:

<T> T runOnUiToolkitThreadAndWait(Callable<T> callable);

In addition we can provide a method that won’t block until the call is finished:

<T> Provider<T> runOnUiToolkitThread(Callable<T> callable);

Now we have a set of methods that can be used to interact with the UI Thread:

public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);
  
  <T> Provider<T> runOnUiToolkitThread(Callable<T> callable);
  
  void runOnUiToolkitThreadAndWait(Runnable runnable);
  
  <T> T runOnUiToolkitThreadAndWait(Callable<T> callable);
  
}

Let’s have a deeper look how we can implement this methods by using default methods:

public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);

    default <T> Future<T> runOnUiToolkitThread(Callable<T> callable) {
        FutureTask<T> future = new FutureTask<>(callable);
        runOnUiToolkitThread(future);
        return future;
    }

    default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        runOnUiToolkitThread((Callable<Void>)() -> {
            runnable.run();
            return null;
        }).get();
    }

    default <T> T runOnUiToolkitThreadAndWait(Callable<T> callable) throws InterruptedException, ExecutionException {
        return runOnUiToolkitThread(callable).get();
    }
}

As you can see there are 2 differences to the basic interface definition. First we need to throw some exceptions because calling the get() method of a Future instance will throw exceptions. These exceptions are needed. Let’s think your runnable call that accesses the UI will contain an error and throws an exception. In this case you want to know about the error when checking the result of the call. As a next change the Provider result type of one method is changed to Future. Internally a Future is used that can’t be casted to the Provider interface. In addition a Provider won’t define the needed Exceptions as described earlier.

Conclusion
The defined interface contains only one method that needs to be implemented in a UI Toolkit specific way to create some helper methods. This is a good start but some of you might know, that there are still some problems in this methods. Maybe you call a *AndWait(..) method from the UI Thread. This will maybe end in a deadlock. In addition the Future interface defines the method “boolean cancel(boolean mayInterruptIfRunning)”. What happens if we call this on a task that is executed by the UI Thread? This issues will be discussed in the next post.






Kopie von http://www.guigarage.com/2015/02/concurrency-ui-toolkits-part-2/

In the first post of this series I showed how Concurrency is handled in UI Toolkits and how a generic approach to work with the toolkit specific thread may look like. This ends in the following interface:

public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);

    default <T> Future<T> runOnUiToolkitThread(Callable<T> callable) {
        FutureTask<T> future = new FutureTask<>(callable);
        runOnUiToolkitThread(future);
        return future;
    }

    default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        runOnUiToolkitThread((Callable<Void>)() -> {
            runnable.run();
            return null;
        }).get();
    }

    default <T> T runOnUiToolkitThreadAndWait(Callable<T> callable) throws InterruptedException, ExecutionException {
        return runOnUiToolkitThread(callable).get();
    }
}

But there are still some problems with this interface:

What happens if the runOnUiToolkitThreadAndWait(..) method is called on the UI-Thread?
One method returns a Future<> that defines the “boolean cancel(boolean mayInterruptIfRunning)” method. By definition thy method will interrupt the thread in that the task is running. But we never want to interrupt the UI Toolkit thread.
Let’s start with the first problem. Before we can solve this another question must be answered: I it’s a problem to call this methods on the toolkit thread we need a way to check if the current thread is the toolkit thread. To do so most toolkits provide a helper method that checks if the current thread is the toolkit Thread. Examples are shown in the following code snippet.

//JavaFX Helper Method
Platform.isFxApplicationThread();

//Swing Helper Method
SwingUtilities.isEventDispatchThread()
Because most Toolkits support this method we can simply add it to our interface:

public interface UIThread {
  
  void runOnUiToolkitThread(Runnable runnable);
 
 boolean isUIToolkitThread();
 
    default <T> Future<T> runOnUiToolkitThread(Callable<T> callable) {
        FutureTask<T> future = new FutureTask<>(callable);
        runOnUiToolkitThread(future);
        return future;
    }
 
    default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
        runOnUiToolkitThread((Callable<Void>)() -> {
            runnable.run();
            return null;
        }).get();
    }
 
    default <T> T runOnUiToolkitThreadAndWait(Callable<T> callable) throws InterruptedException, ExecutionException {
        return runOnUiToolkitThread(callable).get();
    }
}

Once this is done we can have a deeper look at the methods that will block until a task was executed on the ui toolkit. In the defined interface the two methods that are named “runOnUiToolkitThreadAndWait” defines this behavior. Once the method is called a new task is created and added to the ui thread. Because the thread has a lot of work to do normally a queue will handle this tasks and execute them by using a first in first out approach. The following image shows an example.

PIC

By doing so our task will be added to the queue and executed once all task that has been added earlier to the queue were executed. If we call this method from the ui thread the created task can’t be executed before the task that is currently running is finished. But because the “runOnUiToolkitThreadAndWait” methods will wait for the execution of the new task we will end in an deadlock that is definitely the worst think that can happen. By doing so nothing can be handled on the UI thread: No user interaction or rendering can be done and the application is frozen. Because no Exception will be thrown the application just hangs we will receive no information what has triggered the error.
With the help of the new “isUIToolkitThread()” method we can avoid this behavior and refactor the methods to an more fail-safe version. With a simple if-statement we can add a special behavior if the “runOnUiToolkitThreadAndWait” method is called from the ui thread:

default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
  if(isUIToolkitThread()) {
    //what should we do now?? 😰
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}

Once this is done we need to decide what we want to do if the method was called join the ui thread. In general there are two different ways how this is handled by ui toolkits:
throw an Exception (checked or unchecked)
directly execute the runnable by calling the run() method
Here are the implementations for this approaches:

//unchecked exception
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
  if(isUIToolkitThread()) {
    throw new RuntimeException("This method should not be called on the UI Thread")
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}

//unchecked exception
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException, ToolkitException {
  if(isUIToolkitThread()) {
    throw new ToolkitException("This method should not be called on the UI Thread")
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}

//Call the runnable directly
default void runOnUiToolkitThreadAndWait(Runnable runnable) throws InterruptedException, ExecutionException {
  if(isUIToolkitThread()) {
    try {
      runnable.run();
    } catch(Exception e) {
      throw new ExecutionException(e);
    }
  } else {
    runOnUiToolkitThread((Callable<Void>)() -> {
      runnable.run();
      return null;
    }).get();
  }
}

The first 2 methods looks mostly the same. Only the exception type is different. The first method uses an unchecked exception that will end in a more readable code when using the method because you don’t need to catch the new exception type all the time. But developers need to know that an unchecked exception will be thrown whenever the method is called on the ui thread to avoid errors at runtime.
The third method can be called on any thread. A developer doesn’t need to think about it. If you know what you do, this is the most flexibel way how such a method can be defined. But on the other hand this can cause problems. I have seen a lot of projects where developers used this type of method way to often. Because they ad no idea how to handle the ui thread invokeAndWait(..) methods were called all over the code. By doing so your stack trace ends in something like this:

PIC

This will end in code that is unperformant, unstable and can’t be maintained anymore. Therefore I would choose one of the first 2 implementations. But that’s only how I see this things and maybe you have a complete different opinion. Therefore it would be great if you can leave a comment here about your favorite way how to handle this problems. JSR-377 will contain such a interface and we want to resolve all the shown problems in an ui toolkit independent way. If you are interested in the JSR or want to share your opinion about this topic you should have a look at the JSR Mailing List. In the next post I will have a deeper look at the Future<> interface in combination with ui threads.

