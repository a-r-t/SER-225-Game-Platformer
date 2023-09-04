---
layout: default
title: Map Class Overview
nav_order: 1
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/MapClassOverview
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map Class Overview

## Map Resources Setup

The `Map` class does A LOT of setup upon instantiation for resources such as the camera, NPCs, map tile structure, etc.
Since the `Map` class handles and coordinates so many different areas of the game logic, getting the setup step perfect is key for a successful level. 
Most of the game logic in the `Map` class is for the initial setup.

The `Map` class is abstract, meaning it cannot be instantiated without a subclass. 
The constructor for the `Map` class has two parameters defined:
- **mapFileName** -- the map file name that defines the tile layout and map dimensions, which are text files located in the `MapFiles` folder of this project
- **tileset** -- which tiles this map uses -- this along with the map file will determine which map tile graphics are loaded in which locations

From there, the map does several setup steps to get all of its resources in order and ready to go for the game:
1. Read in map file (read more about the map file [here](./map-file.md)) 
1. Create map tiles and place them in the appropriate locations (process detailed [here](./map-tiles-and-tilesets.md))
1. Setup enemies (read more about enemies [here](./enemies.md))
1. Setup enhanced map tiles (read more about enhanced map tiles [here](./enhanced-map-tiles.md))
1. Setup NPCs (read more about NPCs [here](./npcs.md))
1. Setup camera which handles which area of the map are shown on the screen at any given time (read more about the camera [here](./map-camera.md))

## Map Class Methods

The `Map` class has a lot of methods, but most of them are very simple, and many of them are just getters.
Nearly all of these methods will be covered in the other setup pages linked above, as most are directly involved with the setup step. 
The methods `loadEnemies`, `loadNPCs`, and `loadEnhancedMapTiles` are all intended to be overridden by a subclass of the `Map` class.

The `update` method is very small, as the `Camera` class does most of the work updating the map,
however there are two very important methods it does call itself: `adjustMovementX` and `adjustMovementY`. 
These two methods are covered in the `Player` class documentation [here](../player.md), and are critical to allowing the map to properly "scroll" as the player moves.

The `draw` method tells the `Camera` class to `draw` what area of the map should be shown on the screen.

## Map Subclasses

Game maps are defined by subclassing the `Map` class. These are found in the `Maps` package.
Currently, `TestMap` is the only map in the game that plays out a level. 
There is also `TitleScreenMap` which just acts as the background to the title screen.

Each `Map` subclass must satisfy the super class and specify the map file name and tileset. 
It is also a good idea to set the `playerStartLocation` in the map subclass's constructor.

`TestMap` defines a map file of `test_map.txt`, and the `CommonTileset` class as its tileset choice (more on tilesets [here](./map-tiles-and-tilesets.md)),

```java
public TestMap() {
    super("test_map.txt", new CommonTileset());
    this.playerStartPosition = getMapTile(17, 20).getLocation();
}
```

It also sets the `playerStartLocation` variable to a specific location, which is what tells the game where to start the player at in the map when it's first loaded.

Each map subclass can also override the `loadEnemies`, `loadNPCs`, and `loadEnhancedMapTiles` methods in order to define
[enemies](./enemies.md), [NPCs](./npcs.md), and [enhanced map tiles](./enhanced-map-tiles.md) for a map.

For example, in `TestMap`, the `loadEnemies` override method looks like this:

```java
@Override
public ArrayList<Enemy> loadEnemies() {
    ArrayList<Enemy> enemies = new ArrayList<>();

    BugEnemy bugEnemy = new BugEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.LEFT);
    enemies.add(bugEnemy);

    DinosaurEnemy dinosaurEnemy = new DinosaurEnemy(getMapTile(19, 1).getLocation().addY(2), getMapTile(22, 1).getLocation().addY(2), Direction.RIGHT);
    enemies.add(dinosaurEnemy);

    return enemies;
}
```

This adds two enemies to the map -- the [bug](./enemies.md#Bug-Enemy) (`BugEnemy` class) and the [dinosaur](./enemies.md#Dinosaur-Enemy) (`DinosaurEnemy` class).
The `getMapTile` `Map` method is used to make it easier to place the NPC on a specific tile index in the map by getting the tile at that index's location. 