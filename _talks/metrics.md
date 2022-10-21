---
layout: talk
title: 'Metrics 101'
speakers: ['markus', 'stephanc']
excerpt: 'Heute laufen Anwendungen nicht nur als Monolithen auf einem Server, sondern oft verteilt als (Micro-)Services in der Cloud.
Umso wichtiger ist hier eine kontinuierliche Überwachung.
Sobald man beispielsweise über knapp werdenden Speicherplatz, zu viele User-Sessions oder einen steigenden Memory-Verbrauch informiert werden will, reicht Logging alleine nicht mehr aus.
Für diese und viele weitere Szenarien sind Runtime Metriken heute das Mittel der Wahl.'
lectures: ['DiWoDo 2022','BaselOne 2022']
slides-link: /assets/talks/Slidedeck_RuntimeMetriken101_BaselOne.pdf
featuredImage: metrics2
header:
  image: talks
index: 45
---

Heute laufen Anwendungen nicht nur als Monolithen auf einem Server, sondern oft verteilt als (Micro-)Services in der Cloud.
Umso wichtiger ist hier eine kontinuierliche Überwachung.
Sobald man beispielsweise über knapp werdenden Speicherplatz, zu viele User-Sessions oder einen steigenden Memory-Verbrauch informiert werden will, reicht Logging alleine nicht mehr aus.
Für diese und viele weitere Szenarien sind Runtime Metriken heute das Mittel der Wahl.

In dieser Session wollen wir uns gemeinsam anschauen, wie man Metriken für Java-Anwendungen umsetzen, visualisieren und auswerten kann.
Hierbei wollen wir nicht nur Produkte und vorgefertigte Lösungen wie Grafana oder Prometheus betrachten.
Vielmehr wollen wir auch herausfinden, wie man Metriken in Java sinnvoll ohne ein Vendor-Lock-in umsetzen kann.
Mit Micrometer und Java Flight Recorder gibt es zum Beispiel verschiedene APIs, um eine Java-Anwendung einfach mit Metriken und der Möglichkeit eines kontinuierlichen Monitorings zu erweitern.
