---
layout: default
title: Game Object
nav_order: 2
parent: Game Details
has_children: true
permalink: /GameDetails/GameObject
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Game Object

## What is a Game Object?

Inspired by the [Unity](https://unity.com/) game engine, the class `GameObject` (along with the entire package `GameObject`) was
created as a sort of "one stop shop" for creating game entities. The `GameObject` package contains a series of classes that all work
together which ultimately build up to the `GameObject` class containing all necessary information and functionality required for a map entity,
which makes it quick and easy to use without requiring the code logic used to be rewritten again and again.

In this game, the `GameObject` class is subclassed by every map entity, which includes the [player](/GameDetails/Player), 
and any subclasses of `MapEntity` which include [enemies](/GameDetails/Map/Enemies), [npcs](/GameDetails/Map/NPCs), and [map tiles](/GameDetails/Map/MapTilesAndTilesets) (as well as
[enhanced map tiles](/GameDetails/Map/EnhancedMapTiles)). That means that all of these subclasses (and their subclasses) include
all functionality of the `GameObject` class under the hood.

## Features of the GameObject class

The `GameObject` class provides the following features for every subclass:

1. Sprite logic (loading in images, scaling images, flipping images, defining a bounding box which can be thought of as a "hurtbox")
2. Animation support and logic (and the ability to switch between different animations)
3. Graphical support -- will automatically handle drawing graphics in correct location based on map/camera position
4. Collision detection with other GameObjects, including collision detection with map set pieces (like map tiles)

## Subclassing GameObject

The `GameObject` class can be subclassed by any entity regardless of what it is or what it will be doing in game, 
and it contains many constructors to support a variety of needs. Generally, each `GameObject` at the very least contains
an image (sprite) that is to be attached to a rectangle (x, y, width, height) to be displayed and moved around in game.

## GameObject Package Overview

As mentioned earlier, the classes in the `GameObject` package work together and build up to the `GameObject` class.

### Rectangle

The `Rectangle` class is the "base", which is a means to implement the (x, y, width, height) instance variables which every
`GameObject` needs (it also contains some simple "rectangle" math such as detecting collision and moving a rectangle in different directions).

### Sprite

Then, the `Sprite` class extends the `Rectangle` class. The `Sprite` class adds functionality on for storing an image (one image).
it also optionally allows defining a bounding box `bounds` which is a `Rectangle` used in collision detection -- often times when working with collision detection,
a sprite doesn't want its entire image to be able to be "touched". An example of this can be seen in the following Megaman sprites,
where the bounding box where collisions can be detected is much smaller than Megaman itself:

![megaman-bounds.png](../../assets/images/megaman-bounds.png)

When working with 2D games, it's common to leave off limbs like in the above picture and have the core body be able to be
detected for collision.

### AnimatedSprite

Then, the most complex class of the group pops up -- `AnimatedSprite`. This does NOT extend `Sprite`. Instead, it contains a `HashMap`
which maps a string value (animation name) to an array of `Frame` type (animation data and graphics). 

An array of `Frames` is what defines an animation. Each `Frame` in the array is one frame of an animation,
and the game will cycle through that animation continuously (and upon reaching the last frame will go back to the first).
The `Frame` class extends `Sprite` with an added `delay` instance variable which specifies how long an animation frame is to
last before switching over to the next frame. `Frames` in the same animation can have different `delay` values.

This class provides functionality for any `GameObject` to define their own animations as desired, give the animations names,
play out the `GameObject's` current animation during the game, and allow for the current animation to be changed at any time. Any subclass
of `GameObject` that overrides `getAnimations` (which is where animations are defined) utilize the `AnimatedSprite` class's functionality.

Something important to note about this class is that even though it doesn't directly extend `Sprite`, it is structured to be treated
in game exactly as a `Sprite` would. At any given time, the `AnimatedSprite` class will have a current animation with a current frame,
and this current frame is a `Sprite` with a location, collision potential, etc. While an `AnimatedSprite` is a "collection" of various
`Sprites` at the end of the day, it does only have one "active" `Sprite` out at a time. As a result, the `AnimatedSprite` class
redefines nearly every method from the `Sprite` and `Rectangle` classes to have them utilize the `currentFrame` variable's information.
As a result, an `AnimatedSprite` has all the behavior of the `Sprite` class without needing to extend it. I know this sounds a bit silly,
but I originally did have it extend from the `Sprite` class and after some time I found that the drawbacks for doing that greatly
outweigh the minor inconvenience of having to redefine some methods.

### GameObject

And finally, the `GameObject` class extends from the `AnimatedSprite` class. The `GameObject` is to be given an instance
of the current `Map` being used through its `setMap` method after creation in order for it to provide functionality based on the `Map`,
including move methods that handle map tile collisions (`moveXHandleCollision` and `moveYHandleCollision`) and special `draw` logic to convert "map coordinates" to "screen coordinates".
The `GameObject` class also does some sneaky constructor crafting in order to allow it to be instantiated both as an animated sprite (nearly every entity uses this such as the `Player` and enemies like `BugEnemy`)
or just as one sole sprite (which is done in the `HorizontalMovingPlatform` class).

More details on `GameObject's` collision detection and handling can be found [here](/GameDetails/Player/CollisionDetection).

Note that if no `Map` class instance is passed in a `GameObject` class using the `setMap` method, the `GameObject` will draw itself
at its exact location on the screen rather than accounting for the map camera moving. This can be desired behavior for certain graphics,
while not for others.