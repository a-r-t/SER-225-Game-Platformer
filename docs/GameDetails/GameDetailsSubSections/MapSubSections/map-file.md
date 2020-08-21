---
layout: default
title: Map File
nav_order: 3
parent: Map
grand_parent: Game Details
permalink: /GameDetails/Map/MapFile
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map File

## What is a map file?

A map file is a text file that contains information on the map dimensions as well as the map tile structure (which tiles go where).
Map files can be created with the Map Editor, which you can find information on [here](), and are located in the `MapFiles` folder
in this project.

## Map file structure
There are two parts to a map file. If you did the `Maze` project from CSC111, this file is very similar to the maze files.

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
1 1 10 1 1 0 0 0 0 1 10 1 1 7 1 9 1
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2
```

The first two numbers (in this case 17 and 12) are the width and height of the map.

Afterwards, each number in the "grid" corresponds to a specific map tile from a tileset. The Map Editor handles for you
which index maps to which map tile, so that is not something that has to be worried about. For example, the Map Editor may have mapped
the grass tile to the number 0, the sky tile to the number 1, etc.

## Reading the map file into the game

The `Map` class's `loadMapFile` method handles reading in a map file. It starts by opening up the map file and reading in the
width and height values. 

The `Map` class then defines an array of `MapTile` of length `width * height`. This is a regular array and NOT
a 2D array, however it is being used in a way where it still has the concept of "rows" and "columns". While a 2D array would be fine to use, they have slower accessing speeds (especially when taking into account caching) and use more memory,
both things that a game doesn't want in order to get the best possible FPS. For this reason, a regular array is used, and items
are retrieved from the array as if it were a 2D array using `x + width * y`.

Afterwards, each tile index of the map is read in one at a time and is given to the map's `Tileset` instance. The `Tileset`
then returns a `MapTileBuilder` that maps to that tile index (the `MapTilBuilder` is basically a pre-setup `MapTile` defined in the `Tileset`, however
it has not been instantiated yet). It is at this point that the `Map` class actually instantiates and initializes each `MapTile`
and sets its location to the correct position to construct the entire "map image".