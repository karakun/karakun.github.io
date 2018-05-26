---
layout: page
title: Talks
permalink: /talks/
---
Overview of talks that were done by members of Karakun:
{% for talk in site.data.talks %}
<p>
{% assign author = site.people | where:'name', talk.author | first %}
<h3>{{ talk.name }}</h3>
<p>{{ talk.description }}</p>
<p>Author: <a href="{{ author.url }}">{{ author.firstName }} {{ author.lastName }}</a></p>
</p>
{% endfor %}
