---
layout: default
title: Creating a Simple Game Object
nav_order: 1
parent: Game Object
grand_parent: Game Code Details
permalink: /GameCodeDetails/CreatingSimpleGameObject
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Creating a Simple Game Object

The `GameObject` class can be used for just a single image (one graphic). 
Even though `GameObject` extends from `AnimatedSprite`, if no animation is desired for a specific game object, it provides certain constructors to essentially treat itself as a one frame animation that never changes.
This results in only one image being used.

For example, the following `GameObject` constructor can be used to define just a simple one image sprite:

```java
public GameObject(float x, float y, Frame frame) {
    // logic
}
```

And it can be instantiated like this:

```java
float x = 30;
float y = 100;
Frame frame = new Frame(ImageLoader.load("my_image.png"));
GameObject gameObject = new GameObject(x, y, frame);
```

More details on using the `ImageLoader` class to read in images can be found [here](../../../GameEngine/GameEngineSubSections/loading-images.md).
An image file can contain any graphic, and it will be loaded into the game and utilized by the `GameObject` in its `draw` cycle to "display" itself on screen.

Calling the game object's `draw` method will display it to the screen at its x and y location.

## Setting the Map instance in a Game Object

In the above example, the `GameObject` will always be drawn at its x and y position relative to the screen's coordinates.
Many classes however, such as the `Player` and `Enemy` classes, need to have their drawing logic changed based on where the map's camera has moved (this creates that "scrolling" level effect). 
To add the `Map` instance to the `GameObject` for it to automatically apply that draw logic, the `setMap` method can be used.

```java
gameObject.setMap(map);
```