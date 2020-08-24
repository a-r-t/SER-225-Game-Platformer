---
layout: default
title: Map Entities
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

A map entity, represented by the `MapEntity` class in the `Level` package, is predictably any game object that is a part of a map.
This includes [map tiles](./map-tiles-and-tilesets.md) (`MapTile` class), [enemies](./enemies.md) (`Enemy` class), 
[enhanced map tiles](./enhanced-map-tiles.md) (`EnhancedMapTiles` class), and [NPCs](./npcs.md) (`NPC` class).

## What is the purpose of this class?

The main reason for the `MapEntity` class, which extends from `GameObject`, is that the `MapEntity` class adds a couple of instance variables
made for map entities other than the player -- this includes `isRespawnable` and `isUpdateOffScreen`, which mostly applies to enemies (and can be read about further
on the [enemies](./enemies.md) page), as well as an `initialize` method that will properly "reset" an entity on a map
back to its original position if necessary (such as for respawning an enemy).

## Initialize Method

When subclassing the `MapEntity` class, an `initialize` method will be available to be overridden. This method should "setup" the enemy,
as the `Camera` class will call this `initialize` method whenever an enemy becomes "active" on screen. When subclassing
`MapEntity`, it is important to remember to call the super class's `intitialize` method in order to execute its integral logic
for things like respawning.

## Map Entity Status

All map entities have an instance variable `mapEntityStatus` which the map's `Camera` uses to determine if the entity is
"active" or not. An active entity means it should be included in the level's `update`/`draw` cycle for a current frame. Entities
that are too far offscreen for example will often be removed from the `Camera's` `update`/`draw` cycle until they are back on screen,
as the game does not want to waste resources on entities that at that current frame have no affect on the level or the player.

The `MapEntityStatus` enum in the `Level` package defines three different possible statuses: `ACTIVE`, `INACTIVE`, and `REMOVED`.
An entity generally doesn't have to mess with this value as the `Camera` handles the logic for checking active vs inactive entities,
however an entity may set its own status to `REMOVED` to have it permanently removed from the level with no ability to respawn.
The `Fireball` class sets itself to `REMOVED` after a few seconds in order for it to fully disappear from the level.