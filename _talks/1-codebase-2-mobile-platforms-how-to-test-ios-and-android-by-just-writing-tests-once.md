---
layout: talk
title: >-
  1 Codebase, 2 Mobile Platforms: How to Test iOS and Android by Just Writing
  Tests Once
speakers: francois
excerpt: >
  Developing consistent mobile apps on iOS and Android is difficult enough, but
  writing automated end to end tests so they run on both platforms without
  writing them twice is even harder. The limited selectors on mobile make it
  very difficult to only use one testing code base for both platforms. By using
  accessibilityId’s with translation keys in the same way as in the app, we use
  the fastest and least brittle selector on mobile possible, being resistant to
  tests breaking in case of text changes, allowing to also check the app in all
  languages without any additional effort.

  Using a hybrid framework like React Native or NativeScript with WebdriverIO,
  François will show how to use utility methods to cleverly encapsulate all of
  the platform differences as much as possible, so writing tests is efficient,
  readable and maintainable.
lectures:
  - SauceCon Online 2020
slides-link: 'https://github.com/martinfrancois/saucecon-2020-1-codebase-2-mobile-platforms'
video-link: 'http://saucecon.com/agenda-2020?agendaPath=session/251027'
github-link: 'https://github.com/martinfrancois/wdio-mobile-utils'
featuredImage: mobile-e2e-talk
index: 27
header:
  image: talks

---

Developing consistent mobile apps on iOS and Android is difficult enough, but writing automated end to end tests so they run on both platforms without writing them twice is even harder. The limited selectors on mobile make it very difficult to only use one testing code base for both platforms. By using accessibilityId’s with translation keys in the same way as in the app, we use the fastest and least brittle selector on mobile possible, being resistant to tests breaking in case of text changes, allowing to also check the app in all languages without any additional effort.
Using a hybrid framework like React Native or NativeScript with WebdriverIO, François will show how to use utility methods to cleverly encapsulate all of the platform differences as much as possible, so writing tests is efficient, readable and maintainable.
