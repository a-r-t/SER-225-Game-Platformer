---
layout: default
title: MapEntities
nav_order: 5
parent: Map
grand_parent: Game Details
permalink: /GameDetails/Map/MapEntities
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map Entities

## What is a map entity?

A map entity, represented by the `MapEntity` class in the `Scene` package, is predictably any game object that is a part of a map.
This includes [map tiles]() (`MapTile` class), [enemies]() (`Enemy` class), [enhanced map tiles]() (`EnhancedMapTiles` class), and [NPCs]() (`NPC` class).

## What is the purpose of this class?

The main reason for the `MapEntity` class, which extends from `GameObject`, is that the `MapEntity` class adds a reference to the `Map` class
as an instance variable and has several helper methods/processes to keep the game object's update logic and drawn location
stay relative to how much the camera has moved. This is a bit difficult to explain, but if you think about it, the screen pixel coordinates NEVER change,
pixel location (0, 0) is always at the top left of the screen no matter what. However, when the camera moves through the map,
technically the spot (0 ,0) represents a different coordinate location on the map image. In order to properly position entities on the map
and take this camera movement into account, the `MapEntity` class converts the game object's x and y position from map's coordinates into screen coordinates
which results in location integrity beings maintained in the game logic while the draw logic places graphics in the correct location on screen. This is all handled by the `Camera` class,
so there is no need to really worry too much about it.

Additionally, during a `MapEntity's` `update` cycle, the `Player` class instance is passed in, so map entities have the ability
to interact with the player directly. This is used in situations like an enemy detecting if it touched a player.

## Initialize Method

When subclassing the `MapEntity` class, an `initialize` method will be available to be overridden. This method should "setup" the enemy,
as the `Camera` class will call this `initialize` method whenever an enemy becomes "active" on screen.