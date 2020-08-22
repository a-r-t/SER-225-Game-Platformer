---
layout: default
title: Map Class Overview
nav_order: 1
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/MapClassOverview
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map Class Overview

## Map Resources Setup

The `Map` class does A LOT of setup upon instantiation for resources such as the camera, enemies, map tile structure, etc.
Since the `Map` class handles so many different aspects of the platformer game logic, getting the setup step perfect is key
for a successful platformer level. Most of the game logic in the `Map` class goes towards this setup step.

The `Map` class is `abstract`, meaning it cannot be instantiated directly (but a subclass can). The constructor for the `Map` 
class has three key parameters defined:
- **mapFileName** -- the map file name that defines the tile layout and map dimensions, which are located in the `MapFiles` folder of this project
- **tileset** -- which tiles this map uses (along with the map file will determine which map graphics are loaded in which locations)
- **playerStartTile** -- the x and y index of the map tile the player should start on at the beginning of a level

From there, the map does several setup steps to get all of its resources in order and ready to go for the platformer game:
1. Read in map file correctly lay out the map tile graphics to the appropriate locations (process detailed [here](./map-tiles-and-tilesets.md))
1. Setup enemies (read more about enemies [here](./enemies.md))
1. Setup enhanced map tiles (read more about enhanced map tiles [here](./enhanced-map-tiles.md))
1. Setup NPCs (read more about NPCs [here](./npcs.md))
1. Setup camera which handles what parts of the map are shown on the screen at any given time (read more about the camera [here](./map-camera.md))

## Map Class Methods

The `Map` class has A LOT of methods, but most of them are very simple (also many of them are just getters).
Nearly all of these methods will be covered in the other setup pages linked above, as most are directly involved with
the setup step. Something to note is that the methods `loadEnemies`, `loadEnhancedMapTiles`, and `loadNPCs` are all intended
to be overridden by a subclass of the `Map` class (aka a class that extends from the `Map` class).

The `update` method is very simple, as the `Camera` class does most of the work updating the map, however
there are two very important methods it does call itself: `adjustMovementX` and `adjustMovementY`. These are covered
in the `Player` class documentation [here](../player.md), and are critical to allowing the map to properly "scroll" as the player moves.
These methods were honestly such a PITA to get working correctly in conjunction with the `Player` class's movement and the `Camera` class's
game logic so I highly recommend not trying to edit them because one tiny change will most likely cause the entire platformer game to break.

The `draw` method just tells the `Camera` class to `draw` what should be shown of the map to the screen.

## Map Subclasses

Game maps are defined by subclassing the `Map` class. These are found in the `Map` package.
The level's map class (the only level in the game) is `TestMap`. 

Each `Map` subclass must satisfy the super class and specify the map file name, tileset, and player start tile (as mentioned earlier in the
[map resources setup](#map-resources-setup)) section.

`TestMap` defines a map file of `test_map.txt`, the `CommonTileset` class as its tileset choice (more on tilesets [here](./map-tiles-and-tilesets.md)),
and a player start tile as tile index (1, 11).

```java
public TestMap() {
    super("test_map.txt", new CommonTileset(), new Point(1, 11));
}
```

Each map subclass can also override the `loadEnemies`, `loadEnhancedMapTiles`, and `loadNPCs` in order to define
[enemies](./enemies.md), [enhanced map tiles](./enhanced-map-tiles.md), and [npcs](./npcs.md) for a map.

For example, in `TestMap`, the `loadEnemies` override method looks like this:

```java
@Override
public ArrayList<Enemy> loadEnemies() {
    ArrayList<Enemy> enemies = new ArrayList<>();
    enemies.add(new BugEnemy(getPositionByTileIndex(15, 9), this, Direction.LEFT));
    enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), this, Direction.RIGHT));
    return enemies;
}
```

This adds two enemies to the map -- the [bug enemy](./enemies.md#bug-enemy) (`BugEnemy` class) and the [dinosaur enemy](./enemies.md#dinosaur-enemy) (`DinosaurEnemy` class).
The `getPositionByTileIndex` `Map` method is used to make it easy to place the enemy on a specific tile index in the map by
getting the tile at that index's absolute position in terms of pixels so it is placed in the right spot.