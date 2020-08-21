---
layout: default
title: Level
nav_order: 3
parent: Game Details
permalink: /GameDetails/Level
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Level

## How does the platformer level work?

The `PlayLevelScreen` class (documentation for that class found [here](./ScreensSubSections/play-level-screen.md)) continually runs `update` and `draw` calls
on its instantiated `Map` and `Player` classes, which carries out the platformer level. The `Map` and `Player` classes
hold all the game code that brings the level together -- this is the most complicated aspect of this application (as expected)
but it isn't so bad once you get the hang of how the `Map` and `Player` classes work together and how the classes are structured.

![game-screen-1.gif](../../assets/images/playing-level.gif)

Documentation for the `Map` class can be found [here](./map.md).

Documentation for the `Player` class can be found [here](/GameDetails/Player).

Documentation for usage of the Map Editor can be found [here]().

## The Scene package

The `Scene` package in this project contains all the "core" classes and game logic necessary for the platformer game to play out. Many of the classes
found in here exist solely to be extended from by a different class to be built upon, so you can think of them as a template -- examples of this include the
`Player`, `Enemy`, `NPC`, `EnhancedMapTile`, and `Tileset` classes.