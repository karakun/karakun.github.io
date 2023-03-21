---
layout: post
title: 'Writing Frontend in Rust using Yew and WebAssembly'
authors: ['giorgi', 'george']
featuredImage: adventure
excerpt: "In the previous article you have read about one of the projects implemented as part of Karakun Code-Camp 2022. This time we present to you another project with a bit of exploratory nature that has been worked on in parallel to our colleagues from the previous blog post."
permalink: '/2023/03/19/writing-frontend-in-rust-using-yew-and-webassembly.html'
categories: [Code Camp, Rust, Yew, WebAssembly]
header:
  text: Writing Frontend in Rust using Yew and WebAssembly
  image: post
---

[//]: # (TODO: Don't forget to change the date both in the name as well as in permalink)

In the [previous](https://dev.karakun.com/2023/01/31/x-buddies.html){:target="_blank" rel="noopener"} article you have read about one of the projects implemented as part of Karakun Code-Camp 2022. This time we present to you another project with a bit of exploratory nature that has been worked on in parallel to our colleagues from the previous blog post. 

The inspiration for our project was increased popularity of WebAssembly, Rust and a client framework of Rust called Yew. It is a well known fact that both Rust and WebAssembly advertises itself to be very fast in their domain of usage. Therefore, we were curious if we could write all-in-one web app using Rust, compile it to WASM, run in WebAssembly and see if we achieve any performance gains. An initial Google search for "Web Frameworks" landed us on the website of Yew - a very young client framework for Rust. We haven't waisted our precious time searching through alternatives and despite being very young decide to use the above-mentioned framework. 

Before we start diving into the details of the project it's worth mentioning that none of the team members had had any previous experience with any of the mentioned frameworks/technologies. This is very common experience on the codecamps, but usually it's just one technology that is completely new. This means that we have had to get used to everything starting from the syntax of the language ending with build tools. 

- Setup, tools, initial experiences
- Issues, problems, annoyances, 
- Some non-project related fun
- Learnings, Findings, surprises
- Conclusion