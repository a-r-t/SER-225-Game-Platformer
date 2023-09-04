---
layout: default
title: Level
nav_order: 4
parent: Game Code Details
permalink: /GameCodeDetails/Level
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Level

## How does the game's level work?

The `PlayLevelScreen` class (documentation for that class found [here](./ScreensSubSections/play-level-screen.md)) continually runs `update` and `draw` calls on its instantiated `Map` and `Player` classes, which carries out the level and gameplay. 
The `Map` and `Player` classes hold all the game code that brings the level together -- this is the most complicated aspect of this application (as expected),
but it isn't so bad once you get the hang of how the `Map` and `Player` classes work together and how the classes are structured.

![game-screen-1.gif](../../assets/images/playing-level.gif)

Documentation for the `Map` class can be found [here](./map.md).

Documentation for the `Player` class can be found [here](./player.md).

Documentation for usage of the Map Editor can be found [here](../../MapEditor/map-editor.md).

## The Level package

The `Level` package in this project contains all the "core" classes and game logic necessary for the main game to play out. 
Many of the classes found in the `Level` package exist solely to be extended from. 
You can think of such classes as templates -- examples of this include the `Player`, `NPC`, `EnhancedMapTile`, and `Tileset` classes.