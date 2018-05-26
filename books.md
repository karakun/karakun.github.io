---
layout: page
title: Books
permalink: /books/
---
Overview of books that were written by members of Karakun:
{% for book in site.data.books %}
<p>
{% assign author = site.people | where:'name', book.author | first %}
<h3>{{ book.name }}</h3>
<p>{{ book.description }}</p>
<p>Author: <a href="{{ author.url }}">{{ author.firstName }} {{ author.lastName }}</a></p>
<p><a href="{{ book.link }}" target="_blank">more infos</a></p>
</p>
{% endfor %}
