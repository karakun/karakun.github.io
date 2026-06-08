---
layout: post
title: "Open Source Doesn't Need Another Pull Request. It Needs Triage."
seo_title: "Open Source Triage: The Contribution Maintainers Need Most"
description: "On busy open source projects, triage can be more valuable than another pull request. Learn how to clarify issues, connect related work, and help maintainers make better decisions."
authors: [ 'francois' ]
featuredImage: 'open-source-triage'
excerpt: "On busy open source projects, the highest-leverage contribution is often not another pull request but triage. Clear issue tracker context prevents duplicate work, exposes partial fixes, and helps maintainers make the next decision faster."
permalink: '/2026/06/09/open-source-doesnt-need-another-pull-request-it-needs-triage.html'
categories: [ Development, Open source, OpenClaw, Community ]
header:
  text: "Open Source Doesn't Need Another Pull Request. It Needs Triage."
  image: 'post'
---

Most engineers think contributing to open source starts when you write code.

But on busy open source projects, the most valuable contribution is often not another pull request. 
It is triage: clarifying issues, connecting related work, identifying incomplete fixes, and helping maintainers decide what should happen next.

Large issue trackers are not just backlogs. 
They are the project’s shared memory. 
When that memory is vague, outdated, or misleading, contributors duplicate work, maintainers merge partial fixes, and users keep running into problems the project may already have half-solved elsewhere.

This article explains why open source triage is engineering work, how it helps maintainers, how to distinguish related issues from true duplicates, and how AI coding agents can support triage without replacing human judgment.

---

## Table of Contents

* [Triage is debugging the issue tracker](#Triage-is-debugging-the-issue-tracker)
* [Why this matters more than most people think](#Why-this-matters-more-than-most-people-think)
* [Related isn't the same as duplicate](#Related-isn't-the-same-as-duplicate)
* [The 5-minute workflow I wish more people used](#The-5-minute-workflow-I-wish-more-people-used)
* [What good triage comments sound like](#What-good-triage-comments-sound-like)
* [The fastest ways to make triage worse](#The-fastest-ways-to-make-triage-worse)
* [AI makes human triage more important, not less](#AI-makes-human-triage-more-important)
* [Final thoughts](#Final-Thoughts)

---

Before getting into the workflow, here is the kind of situation where triage matters.

Imagine a successful open source project with thousands of open issues. 
Somewhere in that backlog are three related reports:

- one says the Web UI only accepts images
- another asks for document uploads, such as PDF and Word files
- a third asks for support for uploading any file type

In the pull request queue, two related fixes already exist:

- one changes only the file picker in the frontend
- another changes both the file picker and the backend

Now one engineer finds the first issue and starts writing a third pull request because the bug looks easy to fix. 
At the same time, a maintainer sees the frontend-only PR, assumes it solves the whole problem, and merges it.
The UI now looks fixed, but the backend still drops the files. 
Multiple people have spent their evening on the same problem, and the issue tracker is now misleading everyone.

The most expensive open source bug is often not the hardest one. 
It is the one that gets fixed three times.

You might think this is just a communication problem. 
In a company, people might notice each other's work in a stand-up or Slack channel. 
Open source does not work like that.

On large open source projects, contributors work across time zones and personal schedules.
Maintainers cannot manually connect every duplicate issue, related pull request, partial fix, and stale report. 
If they did, they would have no time left to review the work that actually needs to be merged.

That is why missing links between issues and pull requests are not a minor inconvenience. 
If one engineer claims an issue but another finds a duplicate elsewhere, they may never realize that someone is already halfway through the fix—or that two pull requests already address the same problem.

I almost did exactly that.
While using [OpenClaw](https://openclaw.ai){:target="_blank"} on my Android phone, I noticed that tapping the paperclip in the Web UI only let me choose images, while Telegram let me upload any file.
Since OpenClaw is an AI coding assistant, I asked it to investigate whether this was a bug. 
It found the technical cause quickly and immediately asked whether it should prepare a pull request.

Instead, I asked it to check the issue tracker and pull requests first.
That changed everything.

Several related issues and two pull requests already existed. 
One PR changed only the frontend. 
The other changed both frontend and backend.
The key detail was that we already knew the backend dropped these files, so a UI-only fix would create a feature that looked complete but still failed.

At that point, writing another fix was the least useful thing I could do.
The useful contribution was mapping the existing work so maintainers could see the overlap, close duplicates, and focus on the pull request that actually solved the whole problem.
That is triage.

## <a name="Triage-is-debugging-the-issue-tracker"></a>Triage is debugging the issue tracker

At first glance, triage sounds boring.

It sounds like paperwork.
It sounds like process.
It sounds like the thing you do when you can't contribute code.
I think that's backwards.

On a busy open source project, triage is one of the most important contributions you can make, because it changes what everyone else does next.

The best short definition I've come up with is this:

**Triage is debugging the issue tracker until the next action becomes obvious.**

That next action might be:

1. Is this reproducible?
2. Is there already a better issue for it?
3. Is there already a PR for it?
4. Does that PR solve the whole problem, or only part of it?
5. Is this still relevant, or has later work already changed the behavior?

A good triage comment usually doesn't try to do everything.

It does one job: it reduces uncertainty.

If you only remember one thing from this article, remember this:

**A short, accurate comment is better than a long, uncertain one.**

## <a name="Why-this-matters-more-than-most-people-think"></a>Why this matters more than most people think

### Duplicate work is easier than people realize

As we saw with the OpenClaw example, the asynchronous nature of open source makes it incredibly easy for two people to spend their evening on the exact same problem without realizing it.

That sounds small until it happens again and again.

Then it becomes a tax on everyone involved.

### A merged PR isn't the same as a solved issue

A PR title can sound complete. 
The green "Merged" badge feels like a finish line. 
But a merged PR doesn't automatically mean the whole problem is gone.

Recently, a severe issue was reported in OpenClaw: sending a binary file via Telegram caused the bot to dump raw, unsanitized bytes into the context. 
A single file could blow up the prompt to [around 460,000 tokens](https://github.com/openclaw/openclaw/pull/66663){:target="_blank"}. 
This wasn't just a bug; it posed a massive risk of resource exhaustion and cost amplification.

Shortly after the issue was reported, an OpenClaw contributor opened a PR to fix it.
Because the issue affected prompt handling and could dramatically increase token usage, a maintainer merged the change quickly.
When I looked at the diff, the fix seemed surprisingly small for the scope of the problem. 
I deployed the updated OpenClaw branch locally and tried to reproduce the issue myself.
Given the severity of the problem and the volume of incoming work, I completely understood why the maintainer had merged it so quickly.

Normally, your time is better spent triaging open issues than rechecking merged PRs.
But in this case, the diff left me unsure whether the entire problem had actually been solved.

Uploading a 100 KB EPUB file immediately blew up my local prompt to [231,000 tokens](https://github.com/openclaw/openclaw/pull/66877){:target="_blank"}.

The PR author had fixed part of the issue, but not all of it.
The PR also skipped the repository's verification checklist, so nobody had explicitly confirmed the fix worked outside the code review itself.

If the missing verification had been obvious earlier, someone else could have tested the change before it was merged. 
Whether a human ignores a template or an AI omits it entirely, maintainers lose important context for judging how much trust to place in a fix.

After I patched the remaining upload leak, I kept digging. 
Experience tells me that where there is one bug, there are usually neighbors. 
Instead of stopping at the narrowest interpretation of the bug, I tried *replying* to a message of a previously sent binary file on Telegram. 
Sure enough, it pulled the raw bytes into the context again. 
It was a different path, but the same broader problem.

I packaged both fixes into a new [PR (#66877)](https://github.com/openclaw/openclaw/pull/66877){:target="_blank"}, which the maintainers merged an hour later.

The real lesson here is about what code review looks like under pressure. 
In a perfect world, every PR would be tested locally before it is merged. 
In reality, maintainers often have to rely on diffs, contributor claims, and community feedback to decide whether a fix is ready.

This is exactly where triage steps in. 
You don't have to write the code to save the day. 
If you take an open PR, test it locally, and leave a comment saying, *"I deployed this branch and followed the reproduction steps, but the issue is still present,"* you just saved the project from shipping a broken feature. 
Catching an incomplete fix before it gets merged makes you a hero to the maintainers.

In my case, the broken PR was merged within minutes because it was an urgent security fix, leaving no window for the community to verify the code before it landed. 
But normally, PRs sit in the review queue for days or weeks. 
That gives you plenty of time to pull the branch, test the fix yourself, and raise that exact flag.

However, if the code actually works, commenting, *"I deployed this locally and confirmed: on main the issue happens, but on this branch it is completely resolved,"* is extremely valuable for a maintainer. 
Doing the manual verification that maintainers don't have time for is one of the most valuable triage contributions you can make.

### 3. A messy issue tracker lies to people

An unclear issue tracker doesn't just look untidy.
It actively changes what people decide to do.

Someone spends an evening reproducing a bug that already has a PR.
A maintainer assumes the problem is solved because the title sounds right.
A contributor opens a duplicate issue for a "PDF upload error" because the original report was vaguely titled "mobile attachment bug" and nobody ever added the specific keywords or error codes that would have made it show up in a search.

Bridging these gaps doesn't require you to be the lead architect. 
It just means applying a **technical perspective** to look past the surface-level description. 
In my case, it was easy to assume that because Telegram already allowed all file types, the backend was fine - making a frontend-only fix look like the complete answer. 
Triage is that "Wait a minute" moment where you pause to verify if that assumption is actually true.

Whether you're identifying a shared root cause between two different-looking bugs, or noticing that a PR only masks a symptom instead of fixing the logic, you're using engineering judgment. That's why keeping the issue tracker trustworthy isn't admin work; it's **engineering work**.

### 4. It's one of the best ways to start contributing

Many engineers assume they need deep knowledge of the codebase before they can contribute to open source.

That's understandable, but it's often wrong.

You don't need to know every service, build step, and deployment detail to notice that:

- the reproduction steps are missing
- the version is missing
- the PR description doesn't match the changed files
- two PRs address the same issue but aren't linked to the issue yet
- a PR fixes an issue that hasn't been linked
- two issues describe the exact same problem from slightly different angles
- a PR only touches the frontend because it **looks** as though the backend is already "done" (as I initially assumed). You don't need to know the code to ask: 
"Since Telegram **already allows all file types**, are we sure the Web UI uses the exact same API path, or are we just **hoping** it does?"

That level of careful reading is an immediate, high-value contribution. 
On a project with a large review queue, the last thing maintainers need is another item added to it. 
Even a one-line "quick fix" adds to the noise. 
Connecting the dots is more valuable because it helps clear the backlog instead of adding to it.

## <a name="Related-isn't-the-same-as-duplicate"></a> Related isn't the same as duplicate

This is one of the easiest mistakes to make if you're new to open source. 
Several things can exist in the same part or functionality of the software without being the same issue.

In my OpenClaw example, all of these were about file uploads:

- the web UI only accepts images
- one issue asks for support for document files
- a broader issue wants support for any kind of file
- one PR changes only the frontend
- another changes frontend and backend
- there was also a question about whether uploading files actually worked at all

Those items are clearly connected, but they aren't identical. 
If you treat them as a single "bucket" and start closing them simply based on which one arrived first, you create a chain reaction of waste:

**1. The "Incomplete Fix" Trap**

It's easy to think, *"Obviously, I would keep the PR that fixes both the frontend and the backend."* 
But in reality, triagers and maintainers rarely have the time to deeply compare the code of every duplicate. 
Usually, they just see two PRs with similar titles that claim to fix the same problem.

Ideally, the PR author would leave a note saying, *"I'm opening this because PR #123 is an incomplete fix."* 
But in practice, most contributors don't realize they should search for existing, unlinked PRs before writing code. 
They usually have no idea the older PR even exists.

If you just assume the two PRs are identical and blindly close the newer one as a duplicate, you might accidentally bury the actual solution. 
If the older, partial fix gets merged, the feature will still be broken. 
But since the "right" PR was already closed, nobody will think to look for it. 
Weeks from now, a third contributor will end up spending hours just to rewrite the exact same backend code that was already sitting in the PR you closed.

**2. The Scope Trap**

If you close the request for "any file type" in favor of the narrower "documents support," you have unintentionally limited the project's potential. 
It creates a confusing experience where users can send images through the Telegram bot, but not through the Web UI. 
This mismatch happens when a triager takes an issue author's request too literally. 
Issue authors often think about their immediate needs, like uploading a PDF, but a good triager has to translate that narrow complaint into a broader system requirement. 
If you just assume the issue author's specific example is the whole story, you guarantee that developers will have to rewrite the exact same code the moment someone else tries to upload a code file.

**3. The "Cannot Reproduce" Trap**

The most dangerous mistake in triage is assuming a bug doesn't exist just because you can't reproduce it yourself. 
It's incredibly common to read an issue report, try it out, see it working perfectly, and immediately close the issue as non-reproducible. 
But unless the issue author is an LLM, they probably aren't hallucinating. 
If you can't trigger the bug, it's almost always because you don't fully understand the issue author's environment, or because they left out a specific detail that felt too "obvious" to mention. 
When you experience this, the best thing you can ask yourself is: *"What am I missing?"*

You wouldn't believe how many issues get closed this way, leaving real, systemic bugs hiding in the codebase to frustrate users for years. 
Maintainers don't do this because they don't care. 
With thousands of issues to fix, they just don't have the time to chase down missing details for every vague report.

This is exactly where you can step in.
By asking clarifying questions, recreating the issue author's environment as closely as possible, or testing different configurations, you provide the exact help maintainers often don't have time to provide themselves.
When you can't reproduce a bug, don't ask, "Should I close this?" Ask, "What am I missing?"

Good triage means looking for the missing variable instead of closing the issue at the first obstacle.

There's one more trap that causes the same kind of waste, even when no duplicate is involved.

**4. The "Confident Diagnosis" Trap**

Some issue reports do more than describe a problem. 
They also explain what the issue author believes caused it.
That's helpful but it can also hide the next thing you should verify.

Imagine an issue that says:

> The database doesn't save profile changes.
>
> I changed my display name, clicked Save, saw a success message, refreshed the page, and the old name was back.
>
> I checked the user record in the database and found that it still contains the old display name, so I suspect there's an issue with writing the change to the database.

The issue author may be right. 
Maybe the backend really doesn't persist the change to the database.

But the problem could also be somewhere else: the frontend never sent the changed field, the backend rejected the change but the frontend still showed a success message, the backend wrote to a different record, or the write was attempted but rolled back.

Good faith means assuming the issue author is trying to help. It doesn't mean assuming their diagnosis is automatically correct.

A useful triage comment could say:

> Thanks for the clear steps and for checking the database.
>
> You mentioned that after clicking Save, you see a success message, but the user record in the database still contains the old display name.
>
> I'd like to confirm where in the flow the change gets lost. Could you check the browser network tab when clicking Save and see whether the request contains the changed display name and whether the response is successful?
>
> That would help narrow this down: either the frontend doesn't send the change, the backend rejects it but the frontend still shows success, the backend writes to a different record, or the write is attempted but rolled back.

This kind of comment takes the issue seriously without accepting the first diagnosis too quickly. 
It tests the simple explanations first, but still leaves room for the issue author to have information you don't have yet.

That balance matters. 
If you assume the issue author is wrong, your comment can sound dismissive and make them defensive. 
If you assume the issue author's diagnosis is right, you may skip the simplest explanation and turn a misunderstanding into a misclassified bug, a misplaced feature request, or a much larger investigation than necessary.

**Don't waste a misunderstanding**

And if the issue author comes back and says, "You were right, I misunderstood how this works," don't treat that as the end of the story.

That misunderstanding may still be useful.

This isn't limited to configuration misunderstandings. 
Whenever an issue ends without any change to code, docs, examples, error messages, or repository guidance, pause for a moment: is there a small change that would have helped the issue author find the answer before needing to open an issue?

You can ask:

> Thanks for confirming, glad it works now.
>
> One more thought: this sounds like something the docs could make clearer. 
What should the docs say so the next person can find the answer before needing to open an issue?

If the issue author suggests a clearer wording, don't let that disappear in the thread. 
If you have time, turn it into a small docs PR and link the PR back to the original issue. 
You don't need to be a maintainer to do that.

If you don't have time to make the docs change yourself, create a follow-up issue instead. 
Link the original discussion and write down what was confusing, so someone else can pick it up later.

Even if the original issue has already been closed, this can still be valuable. 
If comments are still open, you can ask the question there. 
If not, you can still open a docs issue that points back to the original discussion.

A misunderstanding isn't always just user error. 
Sometimes it's evidence that the project is teaching the right thing in the wrong way.

That's still triage. 
You took one confusing issue and turned it into something the project can learn from.

In the end, good triage sits in the middle. 
It keeps the differences that matter and removes the duplication that doesn't. 
Sometimes the real question isn't just "what links to what?" It's "what kind of problem is this, actually?" 
In my case, the Web UI behavior looked like a bug because Telegram allowed arbitrary file types. 
But after reading more closely, it also looked plausible that the Web UI had simply been implemented as an image-only flow on purpose. 
Good triage makes that kind of distinction visible instead of pretending it's obvious.

## <a name="The-5-minute-workflow-I-wish-more-people-used"></a> The 5-minute workflow I wish more people used

You don't need a large, complicated process.
You need one simple enough that you'll actually use it. 
If a workflow feels like a chore, you'll skip it the second you get busy. 
If it's natural and intuitive, it actually gets followed.

### 1. Read the whole thing

Don't triage from the title.
Read the body, screenshots, reproduction steps, version information, linked issues, recent comments, and, for PRs, the changed files.

A surprising amount of poor triage comes from people reacting to names instead of content.
As you read, slow down whenever the issue author moves from "this happened" to "therefore this is the cause."

This describes what happened:

> I changed my display name, clicked Save, saw a success message, refreshed the page, and the old name was back.

This is a possible cause:

> There must be an issue with writing to the database.

The issue author may be right. 
But the cause could also be the frontend request, backend validation, a different database record, a rollback, stale cached data, or a draft state.

That doesn't mean the issue author is wrong. 
It just tells you what to check next.

### 2. Ask whether there is enough information to act

Before doing detective work, ask a simpler question:
Is there even enough detail here to classify the problem?

For an issue, that usually means:

- exact version
- reproduction steps
- expected behavior
- actual behavior
- environment
- logs or screenshots, when relevant

For a PR, it can also mean:

- scope
- linked issues
- tests
- migration notes
- whether the changed files actually match the claim

If the basics are missing, asking for them may already be the most useful thing you can do.

### 3. Search for existing context

Before you comment, build a tiny map in your head by searching for:

- the same symptom
- a broader issue in the same area
- a deeper issue behind the symptom
- an existing PR that may already cover it
- a PR that may only cover part of it
- newer releases or merged PRs that may already have changed the behavior

That small map is often enough to stop you from commenting too early or opening something that never needed to exist.

### 4. Decide the one job of your comment

Before writing anything, finish this sentence:

**The job of this comment is to...**

For example:

- ask for missing details
- link this issue to a broader one
- point out that the PR is partial
- tell readers where the real implementation work is happening
- explain that two related issues aren't duplicates

If your comment tries to do five jobs, it will usually do none of them well.

### 5. Only state what you have verified

This is the rule I trust most: **Don't guess. Only state exactly what you've personally checked.**

Not the longest explanation.
Not the most confident-sounding assumption.
Just the verified facts.

Comment on the **issue** when the main point is that the problem needs clarification, is narrower or broader than another issue, or already has a relevant PR.

Comment on the **PR** when the main point is that the proposed fix is partial, broader than the linked issue, or overlapping with other work.

Only comment on both the issue and the PR if the two audiences (the issue authors reporting the bug and the developers reviewing the code) genuinely need different information.

And whatever you do, match your certainty to what you actually verified.

Don't write:

```md
Fixed by #123.
```

because the title sounds right.
Write it only if you checked the diff and are confident it really solves the issue.

If you're not there yet, softer wording is better:

```md
This may be addressed by #123.
```

That sounds like a small difference.
In triage, it isn't.

On GitHub, if you write [`Fixes #123`](https://docs.github.com/en/issues/tracking-your-work-with-issues/using-issues/linking-a-pull-request-to-an-issue){:target="_blank"} in a PR description, the linked issue will usually get closed automatically once the PR is merged. 
If you're wrong, the bug stays in production, users get frustrated, and someone has to open a new issue weeks later. 
That false confidence is expensive.

## <a name="What-good-triage-comments-sound-like"></a> What good triage comments sound like

The best triage comments are usually short, concrete, and slightly boring in the best possible way.

They don't try to sound clever.
They don't try to sound authoritative.
They remove confusion.

A useful triage comment usually does three things:

1. Lead with the conclusion.
2. Explain why.
3. Then stop.

That doesn't mean the comment has to be tiny. 
It means the comment should contain the information needed to make the next decision without requiring everyone else to reconstruct your reasoning.

Here are four real patterns from the OpenClaw issues and PRs that inspired this post.

### Linking a narrower issue to a broader one

From [Issue #50337](https://github.com/openclaw/openclaw/issues/50337#issuecomment-4184430216){:target="_blank"}:

> This issue is similar to [#56344](https://github.com/openclaw/openclaw/issues/56344){:target="_blank"}.
>
> This issue is about allowing documents to be uploaded in addition to images through the Web UI, while [#56344](https://github.com/openclaw/openclaw/issues/56344){:target="_blank"} is about allowing all file types. 
I prefer the approach of [#56344](https://github.com/openclaw/openclaw/issues/56344){:target="_blank"}, because it's consistent with what channels like Telegram allow and would also cover other useful file types like `.patch`, `.md`, `.adoc`, etc.
>
> Because of that, I think this issue could be closed in favor of [#56344](https://github.com/openclaw/openclaw/issues/56344){:target="_blank"}. 
The PR for that broader change is [#57707](https://github.com/openclaw/openclaw/pull/57707){:target="_blank"}.

This works because it doesn't just say "duplicate" or "related". 
It explains the relationship between the issues: one is narrower, the other is broader, and the broader one already has an implementation path.

The useful pattern is:

1. Name the related issue.
2. Explain how it's related.
3. Explain which one should stay open and why.
4. Point to the PR, if one exists.

### Explaining that a PR is only partial

From [PR #54248](https://github.com/openclaw/openclaw/pull/54248#issuecomment-4184430558){:target="_blank"}:

> This PR is incomplete, because it only covers the UI side of the upload flow.
>
> Files other than images would still not be handled properly by the backend, so this would not fully solve the problem. 
I think this PR could be closed in favor of [#57707](https://github.com/openclaw/openclaw/pull/57707){:target="_blank"}, because that one covers both the frontend and backend parts of the same issue.

This works because it leads with the conclusion but still includes the reason that matters. 
The problem isn't that the PR is bad. 
The problem is that it only fixes one side of the flow.

The useful pattern is:

1. State that the PR is incomplete.
2. Say exactly which part it covers.
3. Say exactly which part is still missing.
4. Link to the more complete PR.

### Pointing issue readers to the implementation

From [PR #57707](https://github.com/openclaw/openclaw/pull/57707#issuecomment-4184430466){:target="_blank"}:

> Implements [#56344](https://github.com/openclaw/openclaw/issues/56344) and [#58423](https://github.com/openclaw/openclaw/issues/58423){:target="_blank"}.
>
> It also includes the smaller change requested in [#50337](https://github.com/openclaw/openclaw/issues/50337){:target="_blank"}, since allowing all file types also covers allowing documents in addition to images through the Web UI.

This works because it makes the scope of the PR explicit. 
Someone reading one of the issues can understand that this implementation covers more than one request, and why the smaller request is included in the broader one.

The useful pattern is:

1. List the issues the PR implements.
2. Mention smaller related requests that are also covered.
3. Explain why they are covered, instead of assuming that the link is obvious.

### Asking for the one detail that matters next

Adapted from [Issue #56375](https://github.com/openclaw/openclaw/issues/56375#issuecomment-4184430620){:target="_blank"}:

> The upload button isn't just decorative. Uploading image files through it works for me.
>
> You're on `2026.3.24`. Can you check whether this still happens on `2026.4.2`?
>
> It would help if you could share the file type you are trying to upload, the actual file if you can attach it, whether this only affects one file or different file types, whether it also happens in another browser, what OS/environment OpenClaw runs on, and whether you use an ad blocker, VPN, router-level blocking, or something similar.
>
> Since the screenshot shows a custom API setup, it would also help to know whether this happens with other providers/models and to see the relevant part of your `openclaw.json`, with secrets redacted.
>
> One important detail: the Web UI currently only supports image uploads. So if your file picker lets you choose a non-image file, that could explain what you are seeing. This may also change with [#57707](https://github.com/openclaw/openclaw/pull/57707){:target="_blank"}, which adds support for all file types in the Web UI.

This works because it doesn't just ask for "more information". 
It asks for the specific information that would help separate several possible causes: an old version, an unsupported file type, a browser issue, an environment issue, a blocking extension, or a provider/model configuration problem.

The useful pattern is:

1. State what you could verify yourself.
2. Ask the issue author to test the newest relevant version, if they aren't already using it.
3. Ask for the smallest useful set of missing details.
4. Explain any limitations that might already explain the issue report.
5. Link to the PR that may change that behavior.

The pattern is the same in all four cases: say what you think should happen, give enough context to make it actionable, and avoid turning the comment into another discussion thread.

Good triage comments aren't short because information is missing. 
They're short because everything unrelated to the next decision has been removed.

## <a name="The-fastest-ways-to-make-triage-worse"></a> The fastest ways to make triage worse

Bad triage is worse than no triage because it adds noise and false confidence.

The fastest ways to make a busy issue tracker worse are usually these:

- opening a new issue or PR before checking what already exists
- posting bare links like `Related: #123` or no links at all without saying why the link matters
- posting comments like "I have this issue too" without providing any relevant context or reproduction steps
- guessing from titles instead of reading the diff
- assuming that just because two issues touch the same part of the UI, they must be the exact same bug
- asking for information that's already in the issue body or screenshot
- sounding more certain than you really are
- treating "it was my mistake" as the end of the story, instead of turning the misunderstanding into a docs improvement or follow-up issue
- pasting AI-generated comments without reviewing them first

Remember: triage is supposed to reduce work, not increase it!

## <a name="AI-makes-human-triage-more-important"></a> AI makes human triage more important, not less

AI is genuinely useful for triage.
It can help with things like:

- finding related issues and PRs
- checking whether an issue or PR follows the repository template
- suggesting better search terms
- summarizing the overlap between two issues
- mapping the surrounding repository context

But AI is also very good at sounding certain when it shouldn't be.
That makes it useful as an assistant and dangerous as a substitute for judgment.
A simple way I think about it is this:

**AI is a speed multiplier. It multiplies good process and bad process.**

In my OpenClaw case, the assistant quickly understood the code and was ready to fix it. 
What it didn't naturally do was the human part: slow down, inspect the issue tracker carefully, and figure out whether a new PR would actually help.

Instead of letting AI blindly post comments for you, the best way to use it for triage is to map the territory first.

You can use AI to scan the repository, identify similar issues, check recent PRs, read contribution guidelines, and inspect issue or PR templates before you ever write a line of code.

One tedious part of that work is checking whether an existing issue or PR actually follows the repository's own template. 
To make that easier, I wrote a [reusable prompt](https://gist.github.com/martinfrancois/b38b3d14098ec585f431299a61c3f7c9){:target="_blank"} for checking whether an issue or PR follows the repository's own template. 
You paste in the issue or PR URL, and it asks the agent to find the relevant template, compare the body against it, classify the result, flag possible inconsistencies, and draft a concise comment for you to review, if one is needed.

I contributed that prompt to the [Good OSS Citizen](https://github.com/tesslio/good-oss-citizen){:target="_blank"} skills, so if you use an AI coding agent, Good OSS Citizen is the more convenient version: same idea, less copy-pasting, and more structure.

From the cloned fork of the open source project you plan to work from, install it with one of these commands (requires [Node.js](https://nodejs.org/en/download){:target="_blank"} or [Bun](https://bun.com/docs/installation){:target="_blank"}):

```bash
# npm
npx tessl i tessl-labs/good-oss-citizen

# Yarn
yarn dlx tessl i tessl-labs/good-oss-citizen

# pnpm
pnpm dlx tessl i tessl-labs/good-oss-citizen

# Bun
bunx tessl i tessl-labs/good-oss-citizen
```

If your coding agent has internet access and can run shell commands, you can also point it to the Good OSS Citizen repository and ask it to install the tool in your fork. 
Review the command before running it.

Then ask your agent:

```md
Triage this issue:
https://github.com/example/project/issues/123
```

That's it.

The triage skill in Good OSS Citizen does a bit more than the raw prompt. 
It can fetch the already-open issue or PR body, fetch the matching templates, apply a reusable rubric, write a `triage_comment.md` handoff, and explicitly tell the agent not to post to GitHub. 
It drafts; you decide whether to post.

Good OSS Citizen also includes broader open source contribution checks through its rules, skills, and scripts: contribution guidelines, AI policies, prior rejected PRs, claimed issues, DCO requirements, and changelog expectations. 
For triage, the important part is that the agent does the boring checks first and leaves the judgment to you.

Use AI for the heavy lifting.
Let it search.
Let it summarize.
Let it prepare a draft.

But don't outsource the judgment.

## <a name="Final-Thoughts"></a> Final thoughts

Triage isn't glamorous.
It doesn't give you the same dopamine hit as opening a PR, seeing green CI checks, and getting something merged.
But on busy open source projects, it's often the most impactful contribution you can make.

Issue trackers don't usually get messy because of a single big mistake. 
They become messy the same way a kitchen junk drawer does. 
One day, you toss a vague bug report in. 
The next day, an unlinked PR. 
Then an overconfident comment. 
Nobody cleans it out, and six months later, no one can find the batteries.

Good triage works in the opposite direction.
It makes the issue tracker easier to trust.
It makes the next decision easier.
It helps maintainers spend more time reviewing the right work and less time reconstructing context that should already be there.

And if you're not sure what kind of help a project needs, ask.

Most projects link to their community from the `README.md`, `CONTRIBUTING.md`, or documentation. 
Look for words like "Community", "Contributing", "Support", "Chat", or "Getting help". 
That might lead you to Discord, Slack, Matrix, Zulip, a forum, a mailing list, or GitHub Discussions.

Once you find the most relevant place, ask a simple question:

> I like this project and would like to contribute in a way that actually helps. Is this the right place to ask what would be most useful right now?

Even if it isn't the perfect place, this makes it easy for someone to point you in the right direction.

So the next time you want to contribute to open source, don't start by asking:

**"What can I code?"**

Also ask:

**"What can I clarify?"**

On busy projects, that's triage.
And very often, that's exactly the contribution maintainers need most.

If you'd like to share your own experiences with triage, want a second opinion on a messy issue tracker, or need specific advice, feel free to reach out via [email](mailto:francois.martin@karakun.com) or connect with me on [LinkedIn](https://linkedin.com/in/françoismartin){:target="_blank"}.
