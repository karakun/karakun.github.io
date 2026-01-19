---
layout: post
title: 'Java with Sugar: Why Kotlin Is More than Cotton Candy on a Stick'
authors: [ 'iryna' ]
featuredImage: Kotlin_Java_with_Sugar
excerpt: "Kotlin 2.0 marks a turning point with the K2 compiler, multiplatform capabilities, and advanced features that go beyond “Java with sugar.” Discover how Kotlin bridges gaps in the Java ecosystem, boosts developer productivity, and sets the course for the future of JVM and cross-platform development."
permalink: '/2025/09/18/Kotlin-K2-Compiler.html'
categories: [ Java, Kotlin ]
header:
  text: 'Why Kotlin Is More than Cotton Candy on a Stick'
  image: post
---

---

Kotlin matters in 2025 because it has matured from a JVM companion to a multiplatform powerhouse. With Kotlin 2.0 and the K2 compiler, 
developers gain faster compilation, cross-platform business logic sharing, and modern language features that surpass Java’s evolution. 
Its combination of pragmatism, safety, and expressiveness makes Kotlin a cornerstone of enterprise and mobile development in the 
coming decade.

---

## Article Navigation
* [The History Behind Kotlin's Genesis](#history)
* [Multiplatform Wunderkind: Kotlin Beyond Android](#multiplatform)
* [The K2 Compiler Technology and FIR](#K2)
* [The Pragmatism at Its Heart: Key Kotlin Features](#features)
* [Kotlin vs Java: Interoperability and Syntax Differences](#kotlin-java)
* [What to Expect in the Near Future of Kotlin](#roadmap)
* [Conclusion](#conclusion)
* [Notes on JetBrains and the Kotlin Community](#jetbrains)
* [Let's connect](#cta)
* [References](#references)

---

[Kotlin](https://kotlinlang.org){:target="_blank"} recently celebrated its 14th birthday. It is a comparatively young programming 
language. I was fortunate enough to learn more about Kotlin when I hosted the online event with 
[Anton Arhipov](https://www.linkedin.com/in/antonarhipov/) “VirtualJUG: Kotlin 2.0 and beyond” in January. As is often the case in 
life, the most valuable lessons are learned through interaction with passionate people in the community. One learns much more through 
togetherness than through working alone — a quote that is so true in the technology era — *More Together Than Alone*. 

You often hear that Kotlin has advanced language features and is constantly being developed further — it is Java with Sugar! 
Anton Arhipov, the Kotlin Developer Advocate at JetBrains, once said in the foreword of [1](#references): *"Kotlin stands on the 
shoulders of giants: many features and best practices have been borrowed from other programming languages."* This process of 
reflection and continuous effort in further development and optimization are the keys to Kotlin's success and widespread use. 
That's why the [VirtualJUG](https://www.meetup.com/virtualjug/) session made me as a Java developer so curious! So let’s take a 
deep dive in this article at what Kotlin actually has to offer.

## <a name="history"></a> The History Behind Kotlin’s Genesis
The mood in the Java community in 2010 was mixed. When Oracle acquired Sun Microsystems in 2010, there were concerns about a loss 
in control, possible changes to the open source strategy, and the impact on OpenJDK and the freedom of the community. Some saw 
opportunities in more stable investments and resources, while others feared restrictions on patents, license models, and the 
openness of Java. Overall, there was skepticism about the long-term independence of Java, mixed with hopes for a stable future 
thanks to Oracle's support.

The Oracle-Sun deal in 2010 had no direct, immediate impact on the creation of Kotlin. But in the period following the 
Oracle-Sun acquisition, there was uncertainty coupled with a particular fear about the stagnation of the Java programming language. 
There was a need for stability and innovation within the Java ecosystem. Kotlin filled the gap, as it was developed by JetBrains 
in 2010, using Java 8 as a baseline, and officially released as a Kotlin Project in 2011, after Oracle had acquired Sun. However, 
the investment and open source culture in the Java economy indirectly influenced the direction of the Kotlin language development: 
a huge interest in modernized, robust JVM languages and in stable diverse platforms created a context in which Kotlin could become 
popular on top of Java. As such, Kotlin was born. A language named for the Kotlin island off the coast of the Russian city 
St. Petersburg — at that time mainly the JetBrains team in St. Petersburg was responsible for the language development.

![Kotlin island off the coast of the Russian city St. Petersburg](/assets/posts/2025-09-18-Kotlin/Kotlin.jpg "Kotlin island off the coast of the Russian city St. Petersburg")

Kotlin aims to address programming language issues and tooling gaps, while working seamlessly with the JVM, which indirectly 
benefited from the dynamics in the Java community at that time. As Java technology was already powerful, the language, compiler, 
and ecosystem had gaps compared to other modern languages, especially in areas such as productivity and language features, e.g., 
null safety, expressiveness, boilerplate code, and fast and effective development processes. Kotlin aims to provide developers 
with a leaner, more expressive, and safer syntax, reducing repetitive patterns. It enables reduced boilerplate with type 
inference, null safety through nullable and non-nullable types, extension functions, and data classes, just to name a few.

Kotlin compiles to Java bytecode and runs on the JVM, allowing existing Java libraries, frameworks, and ecosystems to be used 
out-of-the-box. This is a real example of interoperability, which means that Kotlin and Java code can be mixed, migrated 
gradually in the same project, without technical pressure to rebuild an entire system. Officially, Java and Kotlin are compatible 
up to 99%, making Kotlin vs Java interoperability one of its strongest advantages. This allowed companies to take advantage of 
modern language features without abandoning their existing Java infrastructures.

Interoperability was not merely a feature; it was a critical strategic decision. Kotlin benefited from the fact that the Java 
ecosystem, its build tools like Maven and Gradle, server frameworks like Spring, and tooling such as IDE support all remained 
robust. Thus, Kotlin could be integrated seamlessly into enterprise JVM development workflows. On the other side, Kotlin has 
filled the gaps and enriched the Java ecosystem, not least by driving tools development. The willingness to accept new languages 
grew because they could increase productivity without leaving the familiar Java ecosystem. Companies supported the vision and 
were open to languages that could be used efficiently, offered good type safety, and worked with existing Java libraries to 
optimize development cycles. Kotlin offered exactly that, which is why it was able to successfully gain a foothold in the JVM 
world so quickly.

Kotlin addressed specific developer needs and issues, and leveraged the existing JVM environment and Java ecosystem, 
positioning itself as an expressive, incremental improvement over pure Java—also benefiting from the trust that existed in the 
Java platform at the time. In February 2012, JetBrains made the project available as open-source software under the Apache 2 
license. On 15th February 2016, Kotlin reached its 1.0 release. The 1.2 release followed in November 2017. A team of nearly 
150 developers contributed to the development of Kotlin.

## <a name="multiplatform"></a> Multiplatform Wunderkind: Kotlin Beyond Android
Kotlin is mainly known as a JVM language for backend services. In the meantime, it has become the standard language for 
Android app development. Together with Jetpack Compose, for example, you can also design the frontend and UI. But that’s 
not all, because there is also Kotlin Multiplatform. With it, you can use the same code for Android, iOS, all JVM platforms 
(e.g., macOS, Linux, Windows), and the web—while still addressing platform-specific details. Kotlin Multiplatform (KMP) is 
a promising paradigm that enables developers to design and manage cross-platform applications for Android, iOS, web, and 
JVM environments efficiently. KMP is based on the Kotlin language, which is translated into specific code for the respective 
platform by specialized compilers and wrappers. This means that shared business logic can be used across platforms, while 
platform-specific code can still be added as needed. Especially if a company needs to develop applications for the web, Android, 
and iOS simultaneously, using a single programming language such as Kotlin can significantly reduce the required development and 
maintenance effort, as schemas only need to be implemented once and can then be shared across all platforms, thus making the 
Kotlin language and the Kotlin Multiplatform technology extremely attractive. JetBrains provides a robust ecosystem, including 
Ktor, Coroutines, and many other tools facilitating the development of scalable applications.

## <a name="K2"></a>The K2 Compiler Technology and FIR
Technically, the development of the new compiler frontend was one of the biggest challenges on the path to the development 
of a new compiler technology. Let’s take a look into the road to the K2 compiler and scratch on the surface of its magic.

The new frontend promised significant Kotlin 2.0 K2 compiler performance improvements, as it produces the Frontend 
Intermediate Representation (FIR) data structure. FIR is a syntax tree enriched with semantic information stored directly in 
it. This new data structure allowed for performance improvements, both for the compiler and IDE, as it was designed for these 
two different use cases.

In 2019, work started on the new compiler frontend, codenamed K2 compiler.

![Majestic view of the K2 Summit, the second highest mountain in the world](/assets/posts/2025-09-18-Kotlin/K2.jpg "Majestic view of the K2 Summit, the second highest mountain in the world")

The K2 is sometimes interchangeably called FIR. One of the main reasons was that a few language features had appeared unexpectedly 
in Kotlin, making it hard to maintain and evolve the existing compiler frontend implementations. Furthermore, the interaction 
between the compiler and IDEs needed to be improved. Only superficial solutions existed at that time, with no robust contracts 
and no stable APIs available. It required a complete reimplementation of the code base and infrastructure.

The concept of having both the frontend IR and the backend IR, or simply IR, is shown in the following figure.

![Kotlin compiler architecture](/assets/posts/2025-09-18-Kotlin/kotlin.svg "Kotlin compiler architecture")

While the frontend IR is designed and optimized for call resolution, the backend IR is designed for code generation. The Kotlin 
compiler translates Kotlin code into machine-understandable code and comprises a frontend compiler that parses, validates, and 
produces an intermediate representation (FIR), and a backend compiler that converts the IR into platform-specific machine code 
or bytecode (JVM, Native, or JavaScript). The frontend performs lexical analysis, parsing, semantic analysis, and type checking 
to build a syntax tree that becomes the IR, which the backend compilers use to generate executables. Furthermore, the FIR 
simplifies and optimizes different language constructs and features and transforms them into simpler ones. It also provides a 
public API for compiler plugins.

Kotlin offers backend compilers for Kotlin/JVM (Java bytecode on the JVM/Android), Kotlin/Native (LLVM-based native binaries 
for iOS, macOS, Linux), Kotlin/JS (JavaScript for browsers and Node.js), and Kotlin/Wasm (WebAssembly). All backends take the 
same FIR as input and share the same IR.

On 21st May 2024, Kotlin 2.0, with the K2 at its heart, was released. The release delivered better performance and stabilization 
of the language features across multiple compilation targets.

## <a name="features"></a> The Pragmatism at Its Heart: Key Kotlin Features
The list of distinguished and advanced features in Kotlin is long, e.g., lambda expressions, inline functions, null safety, 
string templates, operator overloading, to name just a few. Although string templates are also supported by Java 21, and Java, 
for its part, also has features that Kotlin does not—Kotlin still has a lot to offer. The following language features are 
definitely worth mentioning.

In Kotlin you can use string templates with expressions. In the following piece of code there are some interesting attributes 
and syntaxes. In Kotlin, the function arguments, as well as semicolons and the static modifier, are considered entirely 
optional. Type inference means that the compiler decides what type of variable should be used. However, you can define the 
type explicitly. Kotlin’s null safety feature eliminates probably the most notorious exception in Java, the NullPointerException, 
offering a clearer syntax advantage in Kotlin vs Java code. If your type should be able to receive null, you can mark it as 
nullable using the question mark syntax. You can use string templates in combination with expressions and operators for working 
with null values.

![Prints ‘Kotlin’ in uppercase.](/assets/posts/2025-09-18-Kotlin/stringTemplate.png "Kotlin code defining the string variable `language = "Kotlin"` and printing it in uppercase using string templates.")
![Prints ‘Kotlin’ in uppercase and tries to permute it.](/assets/posts/2025-09-18-Kotlin/st2.png "Kotlin code assigning the nullable string variable `language` the value ‘Kotlin’ and printing it twice – once in uppercase and once using a hypothetical `permutate(offset = 0.5)` function, which would require an implementation to compile.")

An extension function in Kotlin adds new functionality to an existing class without modifying its source code. It is defined 
outside the class and can be called as if it were a member function of that class. You can use it like “Kotlin“.randomString(). 
When you declare a function and you need to return a result, its type will come after the function declaration. Very nice are the 
default function argument values. You can also use named parameters during function calls.

![Prints a randomized version of the string ‘Kotlin’.](/assets/posts/2025-09-18-Kotlin/topLevelFunctions.png "Kotlin code defining a nullable string `language` and printing a randomized version using a custom extension function `randomString(offset = 1.5)` that generates a string of random characters based on the original length times the offset.")

Guard functions in Kotlin allow you to add conditions to pattern matching expressions like when without binding a value. Instead 
of matching and extracting a value, you use a boolean condition directly in a when branch. This is called a guard: the branch 
executes only if the condition is true. Consider the following function code. Here, each branch uses a guard condition. No value 
is bound; the branch is chosen based on the condition. This lets you combine pattern matching with extra logic.

![Classifies a number as positive, negative, or zero.](/assets/posts/2025-09-18-Kotlin/guards.png "Kotlin function `classifyNumber` returning ‘Positive’, ‘Negative’, or ‘Zero’ based on the input number using a `when` expression with a default value of 1.")

Destructuring in Kotlin lets you extract multiple values from an object at once and assign them to variables. It works with 
data classes, collections, and custom component functions. Here, name and age are taken directly from the user object. This 
makes assignments concise and readable.

![Prepares an order of cotton candy if conditions are met.](/assets/posts/2025-09-18-Kotlin/destructuring.png "Kotlin code defining a data class `CottonCandy` with properties sort, numberOfPieces, and amount. An order is created and processed with a `when` expression using destructuring; if the order has more than five in amount, preparation is started and printed with details.")

Interested readers are referred to the official overview page: [https://kotlinlang.org/docs/comparison-to-java.html#what-java-has-that-kotlin-does-not](https://kotlinlang.org/docs/comparison-to-java.html#what-java-has-that-kotlin-does-not){:target="_blank" rel="noopener"}. 
These sugared features combined together allow for more pragmatic and flexible work with your code base.

### <a name="kotlin-java"></a> Kotlin vs Java: Interoperability and Syntax Differences
Kotlin was designed as a pragmatic language that improves upon Java without replacing it. Thanks to 99% interoperability, 
developers can mix Kotlin and Java code within the same project, making adoption incremental rather than disruptive. The most 
visible Kotlin vs Java syntax differences are reduced boilerplate, null safety, extension functions, and more concise data 
structures. For example, Kotlin eliminates the need for verbose getters and setters with data classes and prevents common 
pitfalls such as NullPointerException through its strict null-safety system. These differences give Kotlin a modern and 
expressive style, while still leveraging the stability and ecosystem of the JVM.

## <a name="roadmap"></a> What to Expect in the Near Future of Kotlin
It is worth getting to know this exciting programming language and at least taking a look at it. Additionally, there are 
many ways and possibilities to get involved in the Kotlin Open Source Community. Visit [https://www.jetbrains.com/opensource/kotlin/](https://www.jetbrains.com/opensource/kotlin/){:target="_blank" rel="noopener"}
for more information. As the further development of Kotlin continues steadily, the next Kotlin features will be union types 
for errors, the introduction of context parameters, and effect system capabilities—the so-called contracts. Future versions 
of Kotlin, according to the roadmap 2025, will also include more features for working with data, as well as stronger 
abstractions and improvements in the type system. Let’s wait for it!

## <a name="conclusion"></a> Conclusion
Kotlin has grown from a pragmatic addition to the JVM into a modern, multiplatform language with advanced compiler technology 
and strong community support. With Kotlin 2.0 and the K2 compiler, it continues to improve performance and developer 
productivity while extending its reach across platforms. Far beyond being just “Java with sugar,” Kotlin has established 
itself as one of the most important programming languages for the coming decade.

## <a name="jetbrains"></a> Notes on JetBrains and the Kotlin Community
In response to Russia's invasion of Ukraine, JetBrains—now headquartered in Prague and central to the Kotlin community—has 
suspended sales and research & development activities in Russia, as well as sales in Belarus, for an indefinite period. The 
company condemns the aggression and supports the people of Ukraine, including its own colleagues and their families. 
JetBrains has liquidated its Russian subsidiary and relocated many of its employees from Russia to Europe to continue operations.

## <a name="cta"></a> Let's connect!
Do you have questions about Kotlin 2.0, Multiplatform, or the K2 compiler? Or would you like to share your own experiences 
with Kotlin vs Java? [Feel free to reach out](mailto:iryna.dohndorf@karakun.com?subject=[Karakun%20DevHub]%20Kotlin%20Article). 
I look forward to exchanging ideas with the community.

## <a name="references"></a> References

1. Alexey Soshin; Anton Arhipov, Kotlin Design Patterns and Best Practices: Build scalable applications using traditional, reactive, and concurrent design patterns in Kotlin, Packt Publishing, 2022.
2. [https://speakerdeck.com/antonarhipov/virtualjug-kotlin-2-dot-0-and-beyond](https://speakerdeck.com/antonarhipov/virtualjug-kotlin-2-dot-0-and-beyond){:target="_blank" rel="noopener"}
3. [https://speakerdeck.com/antonarhipov/kotlin-2-dot-1-language-updates](https://speakerdeck.com/antonarhipov/kotlin-2-dot-1-language-updates){:target="_blank" rel="noopener"}
4. [https://kotlinlang.org/docs/](https://kotlinlang.org/docs/){:target="_blank" rel="noopener"}
5. [https://blog.jetbrains.com/kotlin/2021/10/the-road-to-the-k2-compiler/](https://blog.jetbrains.com/kotlin/2021/10/the-road-to-the-k2-compiler/){:target="_blank" rel="noopener"}
6. [https://media.ccc.de/v/froscon2025-3289-kotlin_multiplatform#t=620](https://media.ccc.de/v/froscon2025-3289-kotlin_multiplatform#t=620){:target="_blank" rel="noopener"}
