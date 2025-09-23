---
layout: default
title: Search
permalink: /search/
---

<link href="/pagefind/pagefind-ui.css" rel="stylesheet">
<script src="/pagefind/pagefind-ui.js"></script>

<h1 style="text-align: center">Site Search</h1>
<div id="search"></div>
<p style="text-align: center; font-size: 0.7em">Powered by <a target="_blank" href="https://pagefind.app/">PageFind</a></p>

<script>
    window.addEventListener('DOMContentLoaded', (event) => {
        new PagefindUI({ element: "#search", showSubResults: true });
    });
</script>