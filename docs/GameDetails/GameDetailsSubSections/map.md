---
layout: default
title: Map
nav_order: 4
parent: Game Details
has_children: true
permalink: /GameDetails/Map
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map

## What does the Map class do?

The `Map` class (found in the `Scene` package) is responsible for everything on the screen during the platformer game except for the player character, which includes the map tile graphics,
the camera (current view -- which part of the map is being shown on the screen), the enemies, and the npcs. It has a lot of duties to carry out,
but a vast majority of the `Map` class is just "setup" -- once everything is ready to go, the actual game logic is pretty simple (it pretty much just calls `update` on every class and
lets them decide what they want to do).

Everything you see in the below gif except for the player character (the cat) is handled/coordinated by the `Map` class.

![game-screen-1.gif](../../assets/images/playing-level.gif)
