---
layout: default
title: Game Code Details
nav_order: 5
has_children: true
permalink: /GameCodeDetails
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Game Code Details

## How is this game made and how does it work?

Utilizing the supplied game engine, the game code "hooks" on to the engine's game loop through the use of the `ScreenCoordinator` class,
and from there the game is directed by the various `Screen` classes (which exist in the `Screens` package). The subsections
of this document will go over how this game is made and how everything works.

Before reading on, it is important to understand how the game's update and draw cycle works at a surface level, which is described in the documentation [here](../GameEngine/GameEngineSubSections/game-loop.md).

Below are documents which go over difference pieces of the game code:

{% comment %} 
    The Jekyll theme used [just-the-docs](https://pmarsceill.github.io/just-the-docs/) will auto populate this file with a table of contents
{% endcomment %}