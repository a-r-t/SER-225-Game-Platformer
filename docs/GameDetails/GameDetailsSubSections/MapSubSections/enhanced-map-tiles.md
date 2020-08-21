---
layout: default
title: Enhanced Map Tiles
nav_order: 7
parent: Map
grand_parent: Game Details
permalink: /GameDetails/Map/EnhancedMapTiles
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Enhanced Map Tiles

## What is an enhanced map tile?

An enhanced map tile (represented by the `EnhancedMapTile` class in the `Scene` package) is a `MapEntity` subclass. The idea behind
this class is that it acts just like a `MapTile` does in every way, but with the ability to define its own `update` and `draw` logic
instead of just going with the `MapTile's` default logic. This allows a map tile to essentially do whatever it wants while still
being counted as a map tile, meaning the `Player` will still consider it during its collision checks based on the `EnhancedMapTile's` tile type. Like
every `MapEntity` subclass, an `EnhancedMapTile` during its `update` cycle will be given a reference to the `Player` instance,
so it is able to interact with the player directly.

And yes, I know the name "enhanced map tile" is dumb, I couldn't think of a better name to describe these and now I'm over it.

## Enhanced Map Tile Subclass

In the `EnhancedMapTiles` package, there are currently two subclasses of the `EnhancedMapTile` class -- `HorizontalMovingPlatform` and `EndLevelBox`.
Each one of these classes defines an enhanced map tile in the game, which can be seen in the game's one level.

## Adding a new enhanced map tile to the game

This is simple -- create a new class in the `EnhancedMapTiles` package, subclass the `EnhancedMapTile` class, and then just implement
desired logic from there. I recommend copying an existing enhanced map tile class as a "template" of sorts to help set up and design the enhanced map tile.

## Adding an enhanced map tile to a map

In a map subclass's `loadEnhancedMapTiles` method, enhanced map tiles can be defined and added to the map's enhanced map tile list. For example, in `TestMap`,
a `HorizontalMovingPlatform` and `EndLevelBox` are created and added to the enhanced map tile list:

```java
@Override
public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
    ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
    
    enhancedMapTiles.add(new HorizontalMovingPlatform(
            ImageLoader.load("GreenPlatform.png"), 
            getPositionByTileIndex(24, 6), 
            getPositionByTileIndex(27, 6), 
            TileType.JUMP_THROUGH_PLATFORM, 
            3, 
            new Rectangle(0, 6,16,4), 
            Direction.RIGHT
    ));
    
    enhancedMapTiles.add(new EndLevelBox(
            getPositionByTileIndex(32, 7),
    ));
    
    return enhancedMapTiles;
}
```

The horizontal moving platform has a pretty beefy constructor, but don't let that overwhelm you, it's all just information needed to set it up correctly. 

## Enhanced map tiles currently in game

### Horizontal Moving Platform

![horizontal-moving-platform.png](../../../assets/images/horizontal-moving-platform.png)

This enhanced map tile is defined by the `HorizontalMovingPlatform` class. It is what it sounds like -- a platform that moves
horizontally back and forth. The constructor for this class definitely has too much going on, but the goal was to create a platform
a more generic class where any image could be used as the platform. The level currently uses the above simple green platform image.

For this class, a start and stop position are passed in, and the platform will just continually go back and forth. The platform in game
is set to a tile type of `JUMP_THROUGH_PLATFORM` which allows the player to stand on it but also allows the player to jump through it from below.

While the player is standing on the platform, the `HorizontalMovingPlatform` class detects this in its `update` method and will adjust
the player's x position to move along with the platform.

![player-on-moving-platform.gif](../../../assets/images/player-on-moving-platform.gif)

Not going to lie, getting this to work was a lot tougher than I expected and required me to rewrite the entire collision handling system
at one point. I'm glad I decided to get this working, as it revealed a lot of flaws in the original collision handling code, due
to decimals screwing everything up (I'm telling you, it's a lot harder than it sounds!!!).

Also, if the platform's tile type is changed to `NOT_PASSABLE`, it will push the player if the player is in the way of it while it moves.

The image file for the green platform is `GreenPlatform.png`.

### End Level Box

![end-level-box.gif](../../../assets/images/end-level-box.gif)

This enhanced map tile is defined by the `EndLevelBox` class. Its job is simple upon being touched by the player, it will
set the player's "level state" to `LEVEL_COMPLETED`, which tells the player to do its win animation and whatever else may follow
the level being completed afterwards.

```java
if (intersects(player)) {
    player.setLevelState(LevelState.LEVEL_COMPLETED);
}
```

The image file for the end level box is `GoldBox.png`.
