---
layout: default
title: Creating a Simple Game Object
nav_order: 1
parent: Game Object
grand_parent: Game Details
permalink: /GameDetails/CreatingSimpleGameObject
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Creating a Simple Game Object

A `GameObject` class can be used for just a single image (one graphic). Even though `GameObject` extends from
`AnimatedSprite`, if no animation is desired for a specific game object, it provides certain constructors to essentially
treat itself as a one frame animation that never changes (which results in one image being used).

For example, the following `GameObject` constructor can be used to define just a simple one image sprite:

```java
public GameObject(BufferedImage image, float x, float y) {
    // logic
}
```

And can be instantiated like this:

```java
GameObject(ImageLoader.load("my_image.png"), 30, 100);
```

More details on using the `ImageLoader` class to read in images [here](/GameEngine/loading-images). An image file can contain any graphic,
and it will be loaded into the game and utilized by the `GameObject` in its `draw` cycle to "display" itself on screen.

From there, calling its `draw` method will display it to the screen at its x and y location.

## Setting the Map instance in a Game Object

In the above example, the `GameObject` will always be drawn at its x and y position relative to the screen's coordinates.
Many classes however, such as the `Player` and `Enemy` classes, need to have their drawing logic changed based on where the map's
camera has moved (this creates that "scrolling" level effect). To add the `Map` instance to the `GameObject` for it to automatically
apply that draw logic, the `setMap` method can be used.