---
layout: default
title: Map
nav_order: 5
parent: Game Code Details
has_children: true
permalink: /GameCodeDetails/Map
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map

## What does the Map class do?

The `Map` class (found in the `Level` package) is responsible for updating and drawing nearly everything during the game, which includes the map tiles, the camera (current view -- which part of the map is being shown on the screen), NPCs, etc. 
It has a lot of duties to carry out, but a vast majority of the `Map` class is just "setup" -- once everything is ready to go, it just continuously calls `update` on every class instance it holds and lets them decide what they want to do.

Everything you see in the below gif is handled/coordinated by the `Map` class or its `Camera` class instance.
The only exception is the player, which has its own class `Player`.

![game-screen-1.gif](../../assets/images/playing-level.gif)
