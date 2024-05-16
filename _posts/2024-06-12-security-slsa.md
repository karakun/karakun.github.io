---
layout: post
title: 'Building blocks'
authors: [ 'ixchel' ]
featuredImage: security 
excerpt: "Security requires continuous effort, as there is no singular, straightforward solution that can be universally applied. The ever-changing nature of software means that what may be considered secure today could become vulnerable tomorrow"
permalink: '/2024/04/30/security-slsa.html'
categories: [Security, SLSA, Provenance, Software Supply Chain]
header:
  text: 'Building blocks of a hardened software supply chain: SLSA'
  image: post
---
Security requires continuous effort, as there is no singular, straightforward solution that can be universally applied. The ever-changing nature of software means that what may be considered secure today could become vulnerable tomorrow. Software is often a composition of several components, making the security challenge recursive. Each piece of software we include in our projects carries a level of trust we must have in its functionality, security, performance, and overall quality. Developers and organizations often integrate numerous components from different sources to achieve their project goals. While this approach can be beneficial, it also introduces new risks. The fate of a software product is intertwined with the security and integrity of its dependencies. If a dependency contains a vulnerability, the entire project could be at risk, potentially leading to severe consequences.

Establishing trust is a conscious effort that requires intentional building and nurturing. The cornerstone of this trust should rest on transparency and comprehension. Transparency plays a crucial role in reducing risks and uncertainties, enabling a higher level of control and visibility. Conversely, trust entails embracing a certain degree of vulnerability while placing reliance on the information and assurances offered by the software and its elements.

Improving the state of our software supply chain requires a multi-faceted, holistic approach. On the positive side, there are several tools and organisations working hard to develop frameworks, tools, best practices and guidelines.

In this series of articles we will explore different ways in which we can use frameworks, tools, best practices and guidelines to improve the overall security of our software supply chains.

## [SLSA](https://slsa.dev)

The SLSA framework provides confidence with guidelines and tamper-resistant evidence to secure each step of the software production process. _SLSA focuses on improving software integrity and securing artefacts and infrastructure throughout the supply chain._

The new version ([SLSA version 1.0](https://slsa.dev/spec/v1.0/)) was released in April 2023, and that's the version we'll be discussing here.

The SLSA provides guidelines that outline the necessary security measures from development to deployment. _It aims to mitigate the risk of software supply chain attacks by establishing **best practices** and **standards**_ for software developers and DevOps teams to follow.

The framework defines the requirements and objectives for each control and establishes three trust boundaries around standards, attestation and technical controls, as well as four certification levels.

What sets SLSA apart from other security guidelines is its focus on automatically creating verifiable metadata, rather than simply providing a list of best practices.

This metadata helps inform policy decisions and goes beyond describing the necessary security measures for the software supply chain - it helps put security into practice and provide the data to prove it.

The framework is structured into levels that describe increasing levels of security to provide confidence that software has not been tampered with and can be securely traced back to its source.

SLSA serves as a common language for supply chain security, enabling the identification of a software's security status and providing guidance on how to improve its security level.

The SLSA requirements are categorised into four levels to improve supply chain security.

### Security Risks on the supply chain

In the figure below, you can get a taste of _a few_ of the inflection points in the security of the entire software supply chain. SLSA focuses primarily on supply chain integrity and secondarily on availability. Integrity means protecting against tampering or unauthorised modification at any stage of the software lifecycle. SLSA distinguishes between source health and build health.

**Source integrity:** Ensuring that the software producer's intent is reflected in all changes to the source code. An organisation's intent is difficult to define. Therefore, SLSA is expected to approximate this as approval by two authorised representatives.

**Build integrity:** Ensuring that the package is built from the correct, unmodified sources and dependencies according to the build recipe defined by the software publisher. Ensuring that artefacts are not modified as they move between development stages.

**Availability:** Ensure that all code and change history is available for investigation and incident response, and that the package can be built and maintained in the future. 

![SLSA Supply chain threats](/assets/posts/2024-04-30-security-slsa/2024-04-30-supply-chain-threats.svg)


### Levels and tracks

_“SLSA levels are split into tracks. Each track has its own set of levels that measure a particular aspect of supply chain security. The purpose of tracks is to recognize progress made in one aspect of security without blocking on an unrelated aspect. Tracks also allow the SLSA spec to evolve: we can add more tracks without invalidating previous levels.”_ -- [SLSA Security levels ](https://slsa.dev/spec/v1.0/levels)

 A system with no assurance is classified as **Build Level 0 (No guarantees)**.  This level doesn’t have any requirements nor provides any guarantees. This level  indicates a lack of SLSA. It is intended for software development or test builds, such as unit tests, that are built and run on the same machine. 

**Build Level 1 (Provenance exists)** Organizations  are required to maintain a reproducible and consistent build environment at Build L1. This implies that tracking environment modifications and documenting the build environment are necessary. The build environment and inputs must also be identical for the build process to be replicated.

**Build Level 2 (Hosted build platform)** requires tamper protection, which includes the use of version control and a hosted build service to generate provenance, thereby increasing consumer confidence in the software source. The level of trust in the build service determines the effectiveness of the tamper protection. The build platform runs on a dedicated infrastructure, not an individual's workstation, and the provenance is tied to that infrastructure through a digital signature.

**Build Level 3 (Hardened builds)** provides additional protection against specific threats by ensuring that both the build and source platforms meet auditing standards and maintain the integrity of the provenance. The build platform implements strong controls to prevent runs from interfering with each other, even within the same project, and to prevent secret material used to sign the provenance from being accessible to custom build steps.

Artifacts Integrity is about the ability to trust the authenticity of artifacts, meaning verifying that the artifact you get is really the original artifact uploaded by its author. Establishing the provenance of the artifacts produce is extremely important. So what is _"Provenance"_ all about?

### Provenance 

_Provenance is verifiable information, or metadata, about how a software artefact was created._ This can include information about what source code, build system and build steps were used, as well as who initiated the build and why. Provenance can be used to determine the authenticity and trustworthiness of software artefacts.
In order to trace software back to its source and define the moving parts in a complex supply chain, provenance needs to be there from the get-go

Specifically, provenance ties source code and build steps to the resulting artifacts. By documenting how the resulting artefacts were created in relation to the build, it essentially acts as a receipt generated for the build process.
