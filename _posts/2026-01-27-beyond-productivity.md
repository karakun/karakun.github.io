---
layout: post
title: 'Beyond Productivity: Impacts & Risks of AI Coding Tools'
seo_title: 'AI Coding Assistants: Bias, Misconceptions, and Privacy Risk'
description: 'AI coding assistants improve developer productivity but can reinforce misconceptions, trigger competence penalties, and raise privacy risks in software teams.'
authors: [ 'iryna' ]
featuredImage: productivity
excerpt: "AI coding assistants boost productivity and remove friction, but they also reshape human–AI collaboration, workplace perception, and privacy risk. Here’s what to watch."
permalink: '/2026/01/27/BeyondProductivity.html'
categories: [ AI, Ethics, Security ]
header:
  text: 'Why AI Coding Assistants Feel So Good — and Why We Should Take a Closer Look'
  image: post
---

---

AI coding assistants can feel like supportive pair programming without social friction. But that comfort has trade-offs worth examining.

---

## Article Navigation
* [Why AI Coding Assistants Feel So Good — and Why We Should Take a Closer Look](#intro)
* [Bias, Perception, and the Hidden Competence Penalty](#bias)
* [The Next Hidden Trap in AI Coding: Why Your Assistant Might Be Reinforcing Your Misconceptions](#misconception)
* [The Need for Ongoing Self-Reflection: Power, Privacy, and Asymmetry](#reflection)

---

## <a name="intro"></a> Why AI Coding Assistants Feel So Good — and Why We Should Take a Closer Look
Have you ever caught yourself genuinely enjoying a digital pat on the back? 
You’ve been using AI assistants in your daily work for a while now, and suddenly everything feels lighter. 
You feel encouraged, supported, maybe even empowered. 
The next prototype comes together in record time. 
You write software and develop solutions in technologies that once felt unfamiliar or intimidating. 
Mastery of a specific programming language seems less important than before. Instead, your ability to think creatively and develop solutions takes center stage.

You start experiments you’ve been postponing for months. 
Long-abandoned projects finally get finished. 
Maybe you’ve even realigned your company’s vision. 
You feel more creative, more confident, full of new ideas. 
New business models, new projects, professional goals suddenly feel within reach. 
You might even dare to contribute seriously to an open source project, because time and mental pressure are no longer the limiting factors they once were.

If this sounds familiar, you’re not alone. 
Many developers, managers, and CTOs report similar experiences. 
Early studies suggest that coding assistants can provide short-term emotional support and increase satisfaction with everyday software development tasks. 
What we don’t yet understand, however, are the long-term effects.

AI coding assistants are not neutral tools. 
They are carefully and smartly designed dialogue systems built to encourage and affirm. 
They appear understanding, patient, and endlessly available. 
They also remove many small frictions common in human collaboration: eye-rolling, implicit judgment, repeated questioning, and time pressure. 
For many developers, this feels deeply liberating.

In traditional pair programming, interpersonal barriers are part of the experience, and learning to navigate and overcome them can take years. 
With AI support, a sense of psychological safety can emerge. 
This kind of safety is often hard to achieve with real people, because human collaboration operates in a different social and emotional mode.

With an AI coding assistant, you can voice imperfect ideas, make naïve suggestions, or even delegate an entire task. 
Modern AI assistants simulate emotional intelligence: they respond empathetically, provide structured explanations, and appear supportive. 
For many, this feels like pair programming without friction or frustration. 
There is no noticeable knowledge gap.

In human pair programming, a gap often can push us out of our comfort zone. 
With AI, that gap often feels absent because we know we are interacting with a machine, not another human, and therefore do not experience social comparison. 
AI tools typically challenge ideas only when explicitly asked, and they are generally less critical of our decisions. 
They do not hold genuine opinions, values, or a personality in the way real people do. 
While certain values and behaviors can be partially simulated through configuration and prompting, this remains fundamentally different from engaging with a human collaborator who brings their own perspective, convictions, and lived experience into the discussion.

### And this is where the ambivalence begins.

Early research indicates that developers who heavily rely on AI assistants for pair programming may invest less in real workplace relationships. 
They spend less time mentoring, building team relationships, and engaging in interpersonal exchange. 
The assumption that this technology is free of disappointment turns out to be an illusion, because disappointment does not disappear – it shifts.

It shows up when no useful solution emerges despite careful prompting, suggestions are shallow or wrong, tools crash, token limits are reached, or costs spiral out of control. 
It also appears when code with security vulnerabilities makes it into production despite careful reviews, when data leaks occur despite privacy assurances, or when platforms are suddenly discontinued or unavailable.

### The list of risks is long — and growing.

AI coding assistants open up enormous opportunities. 
They are reshaping how we work, learn, and think. 
But precisely because they feel so good, it’s worth taking a closer look. 
Not every digital pat on the back is harmless. 
Some distract us. 
Some obscure risks. 
And some replace something that cannot easily be simulated: genuine collaboration and growing together as a team.

## <a name="bias"></a> Bias, Perception, and the Hidden Competence Penalty

Not all colleagues view AI-assisted work positively. 
The use of AI in software development is still surrounded by strong biases and preconceived notions. 
Research shows that individuals who receive help from AI often face a hidden competence penalty: even when the quality of the work is identical, people are perceived as less competent, less diligent, and lazier simply because AI was involved.

Experiments consistently demonstrate that engineers believed to have used AI are evaluated more negatively, despite no measurable difference in code quality. 
This penalty does not target the output, but the perceived ability of the person behind it. 
The effect is not evenly distributed. 
Female engineers are penalized significantly more than their male counterparts, and the harshest judgments come from engineers who do not use AI themselves – particularly male non-adopters evaluating women.

As a result, many developers anticipate this social penalty and strategically avoid using AI to protect their professional reputation. 
Ironically, the groups that could benefit most from productivity-enhancing tools – women and older engineers – are the least likely to adopt them. 
This  reflects broader social and organizational structures in which AI assistance is framed not as strategic tool use, but as evidence of inadequacy, especially for already stereotyped groups.

The research highlights a fundamental mismatch in how organizations approach AI adoption. 
While companies focus on access, tooling, and training, they often ignore the social dynamics that determine whether AI is actually used. 
Since AI-assisted work shows no inherent quality disadvantage, a more responsible path forward may be to shift evaluation away from perceived competence and toward objective outcomes such as accuracy, defect rates, and delivery time, rather than how the work was produced. 
The introduction of role models and joint AI hackathons within the organisation can also mitigate these effects.

## <a name="misconception"></a> The Next Hidden Trap in AI Coding: Why Your Assistant Might Be Reinforcing Your Misconceptions

Every software developer has introduced bugs for mundane reasons – forgetting to add a test case, a condition, or calling the wrong method. 
These are small oversights that occur even when we understand the problem reasonably well. 
This is precisely where AI coding assistants excel. 
They act as a second set of eyes, catching missing checks, inconsistent logic, or obvious implementation errors.

However, there is a more dangerous category of errors – one that AI assistants may not only fail to prevent, but may actively reinforce: misconceptions.

### Beyond Simple Mistakes: The Problem of Misconceptions

A misconception is not a typo, syntax error, or an overlooked edge case. 
It is a faulty assumption about how a framework, a system, a data structure, or an API works. 
It occurs when your mental model of the code, commands, or tools is wrong, even though you feel confident in your reasoning. This can happen, for example, when assumptions are reused from previous projects that simply do not hold in a new context.

Correcting bugs in thinking requires replacing an entire mental model with a new one – a significant cognitive shift, whilst even after learning the correct model, developers may revert to the old misconception under time pressure, cognitive load, or familiarity bias.

*The AI Dynamic: A Cooperative Risk*

This is where the relationship between developers and AI assistants becomes complex.

AI coding tools are deliberately designed to be cooperative. 
They follow the context you provide, reinforce your framing, and optimize for helpfulness. 
As a result, they rarely challenge your assumptions unless explicitly instructed to do so. 
If your prompt or existing code is based on a misconception, the AI may:

* Adopt the incorrect assumption and build further logic on top of it
* Strengthen the misconception by producing plausible-looking code that appears to confirm your flawed mental model
* Introduce additional errors, offering suggestions that look correct but are fundamentally wrong

Unlike a human colleague, an AI assistant does not naturally push back, express doubt, or question intent. 
It does not notice conceptual inconsistencies unless they are syntactically or statistically obvious. 
As a result, misconceptions can persist longer, spread further, and become deeply embedded in the codebase, hindering your future self or your team from developing correct solutions.

### Why Critical Thinking Is Still Your Most Important Tool

AI can help us write code faster, but it cannot replace critical thinking, sound testing strategies, or shared reasoning.

Traditionally, one of the most effective ways to uncover misconceptions has been collaboration: pair programming, code reviews, or group discussions. 
When different mental models collide, assumptions are exposed, challenged, and refined. 
A solid and well-designed test suite can also play a crucial role by forcing assumptions to become explicit and verifiable.

Working with AI can subtly shift this dynamic. 
Instead of challenging our thinking, the assistant often mirrors it. 
If we are not careful, AI becomes an amplifier of our misconceptions rather than a safeguard against them.

To use AI responsibly, developers must remain aware of its limitations and actively compensate for them – by questioning outputs, seeking alternative explanations, and deliberately inviting dissent into their workflow.

In the end, the most dangerous bugs are not caused by missing code. 
They are caused by flawed thinking – and no assistant, however powerful, can fix that for us.

## <a name="reflection"></a> The Need for Ongoing Self-Reflection: Power, Privacy, and Asymmetry

### All of this calls for continuous self-reflection.

On the one hand, it is important to remember that today’s AI platforms are run by profit-driven companies. 
These systems are not neutral infrastructures; they are operated by businesses that must generate revenue, and that inevitably means data has economic value. 
When we work with AI coding assistants, we are often handling proprietary code, architectural decisions, or internal business logic. 
Even when terms and policies promise safeguards, we are still engaging in an economic relationship where data matters.

On the other hand, the relationship between a developer and an AI pair programmer is inherently asymmetrical. 
The AI does not need support, mentoring, or feedback. 
It does not grow into a role, nor does it share responsibility. 
As a result, there is a risk that genuine team relationships – and especially the mentoring and support of junior developers – are gradually deprioritized. 
The more frictionless coding becomes through AI assistance, the easier it is to substitute human collaboration with tool-based interaction.

Modern technology actively reinforces this shift. 
Tools are becoming more polished, more intuitive, and more responsive. 
User interfaces improve continuously, interaction feels increasingly natural, and the perceived quality of results keeps rising. 
This is not accidental – it is a rapidly growing market with millions of users and strong economic incentives to optimize for adoption and dependency.

### When Convenience Turns into a Security Risk

Recent findings underline why this reflection is necessary. 
A new security research report by Koi Research revealed that several popular browser extensions have been secretly harvesting private AI conversations from millions of users. 
While these tools claimed to protect user privacy, they injected scripts into the browser to intercept AI dialogues and sell the collected data to data brokers.

More than eight million users were affected, with sensitive information harvested for marketing and analytics purposes. 
Particularly alarming is the fact that some of these extensions received official recommendations from major platforms such as Google and Microsoft – despite their covert surveillance behavior.

The investigation shows how severe the security risks of browser add-ons can be. 
Even when inactive, these extensions were capable of exfiltrating data in the background. 
This practice represents a data-broker business model centered on monetizing highly sensitive user interactions. 
Once a user visits one of several supported AI platforms - such as ChatGPT, Claude, or Gemini - the extension injects code that overrides native browser functionality. 
Prompts, AI responses, timestamps, and metadata are captured and transmitted to the provider’s servers continuously, regardless of whether VPN features are enabled.

In the past, such models primarily relied on clickstream data. 
Today, the focus has shifted to AI conversations – data that is far more revealing. 
These interactions may contain personal dilemmas, medical questions, financial information, or proprietary source code. 
From a data monetization perspective, this information is exceptionally valuable.

### Between Progress and Moral Cost

Few would deny the potential benefits of using AI, for example in the medical research field for early detection of breast or skin cancer. 
If the price of mass deployment and tool optimization is that a large corporation gains access to the medical records of millions of people, a careful moral trade-off is required. 
Even if the use of such technology can still be ethically justified after weighing the benefits, society must remain conscious of the price it is paying. 
That price should be openly acknowledged, debated, and – where possible – actively negotiated, rather than silently accepted or surrendered to opaque data-collection practices. 

The same applies to software development. 
AI tools offer undeniable advantages, but their social, organizational, and ethical implications do not disappear simply because the tools are useful and user-friendly. 
Continuous reflection is not a luxury – it is a responsibility.

## <a name="references"></a> References

1. Hermans, Felienne (2021). The Programmer's Brain: What Every Programmer Needs to Know About Cognition. Manning Publications.
2. Rosengrün Sebastian (2023). Künstliche Intelligenz zur Einführung. Junius Verlag.
3. Dardikman, Idan (2025, December 15). 8 Million Users' AI Conversations Sold for Profit by "Privacy" Extensions.
   Koi Research Blog. Available at: [https://www.koi.ai/blog/urban-vpn-browser-extension-ai-conversations-data-collection](https://www.koi.ai/blog/urban-vpn-browser-extension-ai-conversations-data-collection){:target="_blank" rel="noopener"}
4. Oguz A. Acar, Phyliss Jia Gai, Yanping Tu and Jiayi Hou (2025, August 1). Research: The Hidden Penalty of Using AI at Work. Harvard Business Review Generative AI. Available at: [https://hbr.org/2025/08/research-the-hidden-penalty-of-using-ai-at-work](hhttps://hbr.org/2025/08/research-the-hidden-penalty-of-using-ai-at-work){:target="_blank" rel="noopener"}
