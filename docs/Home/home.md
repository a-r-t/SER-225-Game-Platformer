---
layout: default
title: Home
nav_order: 1
permalink: /
search_exclude: true
---

# SER-225-Game Documentation

<div id="cat-image-container">
    <img id="cat-gif" src="/SER-225-Game-Platformer/assets/images/cat-walking-right.gif" alt="cat-walking-right.gif" style="padding-left:0px;">
</div>

Welcome to the unnamed SER-225-Game's website! Here you can find information on the game, documentation on how the code works, 
and other miscellaneous notes.

## Table of contents

- [How to use this site](../HowToUseThisSite/how-to-use-this-site.md)
- [Game Overview](../GameOverview/game-overview.md)
- [Game Engine](../GameEngine/game-engine.md)
- [Game Code Details](../GameCodeDetails/game-code-details.md)
- [Map Editor](../MapEditor/map-editor.md)
- [Bug Report](../BugReport/bug-report.md)
- [Enhancement Ideas](../EnhancementIdeas/enhancement-ideas.md)
- [Advice](../Advice/advice.md)

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
        catImage.src = "/SER-225-Game-Platformer/assets/images/cat-walking-left.gif";
        catImage.alt = "cat-walking-left.gif";
    } else if (currentLeft <= 0) {
        walkDirection = 1;
        catImage.src = "/SER-225-Game-Platformer/assets/images/cat-walking-right.gif";
        catImage.alt = "cat-walking-right.gif";
    }

    catImage.style["padding-left"] = (currentLeft + walkDirection) + "px";
}

window.setInterval(moveCat, 10);
</script>