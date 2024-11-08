---
layout: talk
title: "My code is faster than yours... let me prove it to you!"
speakers: ['francois']
excerpt: "You have never done any performance comparisons before. You desperately search through your bookmarks, hoping to find something that could help you in this predicament. Then it strikes you — you remember François Martin will give a conference talk showing how to do microbenchmarking in Java using JMH, scheduled to take place in just a few days. It was like a lifeline was thrown to you. With renewed hope, you quickly register for the conference, eager to gain the knowledge you need to save this code review. You sigh in relief, marking the talk's date in your calendar with a red pen, counting down the days until the conference..."
lectures: ['BaselOne 2024']
slides-link: https://baselone.org/wp-content/uploads/2024/10/BaselOne-2024-My_code_is_faster_than_yours_let_me_prove_it_to_you.pdf
# video-link: ''
featuredImage: B124-Logo
header:
  text: Documentation
  image: talks
index: 49
---

You stare at the notification on your screen, your heart sinking with each passing second. It is a pull request from your colleague, Lisa, asking you to review her code. You take a deep breath and click on the link, hoping for a smooth review process. Little did you know, this simple task would soon turn into a daunting challenge.

As you scan through the code, one particular line catches your attention — a regular expression. Immediately, you knew this was a problem waiting to happen. You leave a comment on the pull request, expressing your concerns about the regex. You point out that it is not only slow but also difficult to read and maintain. You suggest Lisa replace it with a more explicit block of code that achieves the same result, albeit at the cost of increased length.

Within minutes, Lisa responded to your comment, refusing to make any changes. She argues her regex is not only shorter but also just as easy to understand. She challenges you, stating she wouldn't change it unless you could prove your proposed solution was faster.

Your panic levels begin to rise. You have never done any performance comparisons before. You desperately search through your bookmarks, hoping to find something that could help you in this predicament. Then it strikes you — you remember François Martin will give a conference talk showing how to do microbenchmarking in Java using JMH, scheduled to take place in just a few days. It was like a lifeline was thrown to you. With renewed hope, you quickly register for the conference, eager to gain the knowledge you need to save this code review. You sigh in relief, marking the talk's date in your calendar with a red pen, counting down the days until the conference...