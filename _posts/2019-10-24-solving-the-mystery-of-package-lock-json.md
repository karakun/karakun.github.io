---
layout: post
title:  'Solving the mystery about package-lock.json'
author: markus
featuredImage: mystery
excerpt: 'A lot of people wonder, why package-lock.json is always updated but never used as-is. Here is the explanation.'
permalink: '/rico/2019/10/24/package-lock-mystery.html'
categories: [JavaScript, TypeScript, Angular, WebComponents, React, VueJS]
header:
  text: Solving the <span class="my-karakun">package-lock.json</span> mystery
  image: post
---

Often times, I've been asked the following question so I decided to write it down here for future reference.

## The question 

The question I have been asked very often is

 "why is package-lock.json always updated but never used as reference to stick the the packages it defines?"

## the quick answer

`package-lock.json` is updated with every normal `npm install` to constantly reflect the packages that were used on the last build. 

To use exectly the versions pinned in `package-lock.json`, one needs to use the `npm ci` command ([npm docs](https://docs.npmjs.com/cli/ci)). It's called `ci` as this command is expected to be used primarily within CI systems / for builds executed in CI systems.

Thus, *you should usually use `npm ci`* instead of `npm install` in your build executed in CI jobs / pipelines.

## Some background

`npm-ci` was introduced in [March 2018](https://blog.npmjs.org/post/171556855892/introducing-npm-ci-for-faster-more-reliable) to provide faster, more reliable builds and a way for clean installs of your project based on a exact definition - the `package-lock.json` that is created and maintained on every regular `npm install`. Thus, the `package-lock.json` is intended to be pushed in your code repository and maintained along with your source code.

## Conclusion

I hope this article clarifies the meaning of `package-lock.json` and how you can profit from it, especially if you're not working exclusively in the context of NPM based projects. 


