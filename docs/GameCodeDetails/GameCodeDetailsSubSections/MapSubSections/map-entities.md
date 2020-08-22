---
layout: default
title: MapEntities
nav_order: 5
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/MapEntities
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
This includes [map tiles](./map-tiles-and-tilesets.md) (`MapTile` class), [enemies](./enemies.md) (`Enemy` class), 
[enhanced map tiles](./enhanced-map-tiles.md) (`EnhancedMapTiles` class), and [NPCs](./npcs.md) (`NPC` class).

## What is the purpose of this class?

The main reason for the `MapEntity` class, which extends from `GameObject`, is that the `MapEntity` class adds a couple of instance variables
made for map entities other than the player -- this includes `isRespawnable` and `isUpdateOffScreen`, which mostly applies to enemies (and can be read about further
on the [enemies](./enemies.md) page), as well as an `initialize` method that will properly "reset" an entity on a map
back to its original position if necessary (such as for respawning an enemy).

## Initialize Method

When subclassing the `MapEntity` class, an `initialize` method will be available to be overridden. This method should "setup" the enemy,
as the `Camera` class will call this `initialize` method whenever an enemy becomes "active" on screen.