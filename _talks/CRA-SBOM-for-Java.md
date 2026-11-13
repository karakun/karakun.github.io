---
layout: talk
title: "CRA & SBOMs for Java: What Software Teams Need to Know"
speakers: ['ixchel', 'markus']
excerpt: "By December 2027, the EU Cyber Resilience Act (CRA) will require Software Bills of Materials (SBOMs) for almost all software products placed on the European market. For teams working with Java, this is a significant compliance task, given their work with deep dependency trees, complex build systems and layered deployment models."
lectures: ['BaselOne 2026, Diwodo 2026, Java Forum Nord 2026, Java Forum Stuttgart 2026']
slides-link: 'https://baselone.org/wp-content/uploads/2026/10/BaselOne-SecurityCRA.pdf'
video-link: 'https://youtu.be/A5KQS5YzM-8'
featuredImage: cra
header:
  text: Cyber Resilience Act & SBOM
  image: talks
index: 54
---

How do you make a complex Java application SBOM-ready for the EU Cyber Resilience Act?

In this, [Ixchel Ruiz](/people/ixchel) and [Markus Schlichting](/people/markus) provide a clear and technical overview of what Java engineers, architects and DevOps teams must understand in order to meet CRA expectations and to avoid risk under NIS2 and DORA. 
These two initiatives increasingly treat SBOMs as evidence of supply-chain control. 

The session covers practical challenges in Java software supply chains, including:

* transitive dependencies
* shaded JAR contents
* BOM-managed versions
* container layers
* embedded services and runtime components
* SBOM generation with Maven, Gradle and CycloneDX
* container-image inventories
* Dependency-Track for vulnerability and license visibility
* workflows and governance for keeping SBOMs accurate across releases

The goal: make software systems SBOM-ready while improving software quality and supply-chain resilience.