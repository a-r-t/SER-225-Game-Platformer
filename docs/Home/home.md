---
layout: default
title: Home
nav_order: 1
permalink: /
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# SER-225-Game Documentation

<div id="cat-image-container">
    <img id="cat-gif" src="/SER-225-Game/assets/images/cat-walking-right.gif" alt="Cat" style="padding-left:0px;">
</div>

[Engine Package](../Engine/engine.md)

<script>
let walkDirection = 1;

function moveCat() {
    const container = document.getElementById("cat-image-container");
    const containerWidth = container.offsetWidth;
        
    const catImage = document.getElementById("cat-gif");
    const currentLeftPx = getComputedStyle(catImage).getPropertyValue("padding-left");
    const currentLeft = parseInt(currentLeftPx.replace("px", ""), 10);
    const catImageWidth = catImage.width;
    
    if (currentLeft + catImageWidth >= containerWidth) {
        walkDirection = -1;
        catImage.src = "/SER-225-Game/assets/images/cat-walking-left.gif"
    } else if (currentLeft <= 0) {
        walkDirection = 1;
        catImage.src = "/SER-225-Game/assets/images/cat-walking-right.gif"
    }

    catImage.style["padding-left"] = (currentLeft + walkDirection) + "px";
}

window.setInterval(moveCat, 10);
</script>