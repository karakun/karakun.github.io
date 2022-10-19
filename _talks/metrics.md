---
layout: talk
title: 'Metrics 101'
speakers: ['markus', 'stephanc']
excerpt: 'Eine Einleitung in Metriken'
lectures: ['BaselOne 2022']
slides-link: https://speakerdeck.com/madmas/fresh-the-next-level-in-web-applications
featuredImage: lucas-santos-citrus-fresh-unsplash
header:
  text: Do I need to pay for <span class="my-karakun">Java<span> now?
  image: post
---

Heute laufen Anwendungen nicht nur als Monolithen auf einem Server, sondern oft verteilt als (Micro-)Services in der Cloud.
Umso wichtiger ist hier eine kontinuierliche Überwachung.
Sobald man beispielsweise über knapp werdenden Speicherplatz, zu viele User-Sessions oder einen steigenden Memory-Verbrauch informiert werden will, reicht Logging alleine nicht mehr aus.
Für diese und viele weitere Szenarien sind Runtime Metriken heute das Mittel der Wahl.

In dieser Session wollen wir uns gemeinsam anschauen, wie man Metriken für Java-Anwendungen umsetzen, visualisieren und auswerten kann.
Hierbei wollen wir nicht nur Produkte und vorgefertigte Lösungen wie Grafana oder Prometheus betrachten.
Vielmehr wollen wir auch herausfinden, wie man Metriken in Java sinnvoll ohne ein Vendor-Lock-in umsetzen kann.
Mit Micrometer und Java Flight Recorder gibt es zum Beispiel verschiedene APIs, um eine Java-Anwendung einfach mit Metriken und der Möglichkeit eines kontinuierlichen Monitorings zu erweitern.
