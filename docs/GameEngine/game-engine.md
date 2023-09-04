---
layout: default
title: Game Engine
has_children: true
nav_order: 4
permalink: /GameEngine
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Game Engine

## What is the difference between a game and a game engine?

The difference between a particular game and a game engine (also called a game framework) is that "game logic" is specific to one game while a game engine is designed to create an environment from which a game can be made. 
Game engines commonly have built in functionality for loading resources (such as images), detecting user input (such as checking if a key on the keyboard is pressed),
and running the game through a cycled game loop that continually updates game logic and renders graphics to the screen.

Most game developers do not write their own game engine, and instead use an existing one that has the features they require to
create their desired game, such as [Unity](https://unity.com/) or [Unreal Engine](https://www.unrealengine.com/en-US/). Many larger game companies
have written their own game engines, which is then used by them to create their own games, such as the famous [Source](https://en.wikipedia.org/wiki/Source_(game_engine)) game engine created by [Valve](https://www.valvesoftware.com/en/).

For this game, the engine is created from scratch using components from the Java Swing library (mostly just for a UI window/graphic rendering), and everything else was
done using vanilla Java.

## Overview of this game engine

The code architecture and design for this engine was inspired by [XNA](https://en.wikipedia.org/wiki/Microsoft_XNA), Microsoft’s old game programming framework that they supported to allow developers to create games for the Xbox 360. 
While XNA is no longer supported by Microsoft and stopped receiving updates, the framework has been ported and released open source under the title [Monogame](https://www.monogame.net/), which sees active development/support.

Below are some features of the game engine, which are defined and created in the `Engine` package.

{% comment %} 
    The Jekyll theme used [just-the-docs](https://pmarsceill.github.io/just-the-docs/) will auto populate this file with a table of contents
{% endcomment %}
