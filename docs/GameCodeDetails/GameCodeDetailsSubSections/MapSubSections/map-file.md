---
layout: default
title: Map File
nav_order: 3
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/MapFile
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map File

## What is a map file?

A map file is a text file that contains information on the map dimensions as well as the map tile structure (which tiles go where).
Map files can be created with the Map Editor, which you can find information on [here](../../../MapEditor/map-editor.md), and are located in the `MapFiles` folder.

## Map file structure
There are two parts to a map file. If you did the infamous `Maze` project in Quinnipiac's CSC111 course, this file will look very familiar.

Below is an example map file:

```
17 12
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 3 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 8 8 8 1 1
1 1 1 1 1 1 1 1 1 1 1 8 8 8 8 8 1
1 1 1 1 1 1 1 1 1 1 1 8 8 8 8 8 1
1 1 1 1 1 1 1 1 1 1 1 1 1 7 1 1 1
1 1 1 1 1 1 1 9 1 1 1 1 1 7 1 1 1
1 1 1 1 1 0 0 0 0 1 1 1 1 7 1 9 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
```

The first two numbers (in this case 17 and 12) are the width and height of the map.

Afterwards, each number in the "grid" corresponds to a specific map tile from a tileset.
The Map Editor handles for you which index maps to which map tile, so that is not something that has to be worried about.
For example, the Map Editor may have mapped the grass tile to the number 0, the water tile to the number 1, etc.

## Reading the map file into the game

The `Map` class's `loadMapFile` method handles reading in a map file. 
It starts by opening up the map file and reading in the width and height values. 

The `Map` class then defines an array of `MapTile` of length `width * height`. 
This is a regular one-dimensional array and NOT a two-dimensional array, however it is being used in a way where it still has the concept of "rows" and "columns". 
While a 2D array would be fine to use, they have slower accessing speeds (especially when taking into account caching) and use more memory,
which are both things that are bad for a game that is trying to achieve the best possible FPS. 
For this reason, a one-dimensional array is used, and items are retrieved from the array as if it were a two-dimensional array by using `x + width * y`, where `x` is the row and `y` is the column.

Afterwards, each tile index of the map is read in one at a time and is given to the map's `Tileset` instance. 
The `Tileset` then returns a `MapTileBuilder` that maps to that tile index (the `MapTilBuilder` is basically a pre-setup `MapTile` defined in the `Tileset`, however it has not been instantiated yet). 
It is at this point that the `Map` class actually instantiates and initializes each `MapTile` and sets its location to the correct position to construct the entire "map image".