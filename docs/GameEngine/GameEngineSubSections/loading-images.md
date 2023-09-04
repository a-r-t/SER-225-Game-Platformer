---
layout: default
title: Loading Images
parent: Game Engine
nav_order: 4
permalink: /GameEngine/LoadingImages
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Loading Images

## Reading images into the game

The engine's `ImageLoader` class has methods that will read an image into the game as Java's `BufferedImage` data type.

The Image Loader provides two methods:
- **load** -- reads an image into the game
- **loadSubImage** -- reads a piece of an image into the game

Pass either method an image file and it will read it in.

## Resources Directory

The `ImageLoader` class will look for image files relative to the directory defined as the `RESOURCES_PATH`,
which is set in the `Config` class. By default, it is set to a `Resources/` directory at the root of the project.
If an image is placed in that `Resources/` directory named "cat.png", the file name can be supplied directly
to the `ImageLoader` methods.

```java
BufferedImage catImage = ImageLoader.load("cat.png");
```

It is fine to add subfolders to the `Resource` directory, just be sure to include that in the path to the image.
For example, if there was a folder named `CatPics` inside the `Resources` folder, the path would look like this:

```java
BufferedImage catImage = ImageLoader.load("CatPics/cat.png");
```

### Changing resources directory

You are free to change the value of the `RESOURCE_PATH` variable in the `Config` class to change the location
of the folder that the `ImageLoader` will look for images in. 
It is not recommended to remove this restriction, as a game needs to guarantee that all of its assets are where they should be in order to run properly, and keeping them in one base location limits room for error.

## Image Transparency

The "transparent" color of an image is a particular color in an image that is desired to be "invisible", meaning it will not be visible when the image is loaded into the game. 
Every sprite in the base game uses the color magenta (RGB 255 0 255) as its transparent color, as that color is ugly and very rarely, if ever, used by any image. 
This magenta color is set as the default transparent color by the `TRANSPARENT_COLOR` variable in the `Config` class.

For example, the following cat image used in game has its background set as magenta:

![cat-sprite.png](../../assets/images/cat-sprite.png)

When loading this image into the game, magenta is set as the transparent color, meaning in game, the magenta color will not be shown.
This allows for graphics to be drawn on top of each other without having to worry about unused background space in the image.

![game-screen-3.png](../../assets/images/game-screen-3.png)

There are pros and cons to this approach vs adding a proper alpha channel.
The main pro is it's a lot faster and easier to load up an image in a simple image editing tool and fill the background in with a transparent color vs using a more advanced image editing program to setup an alpha channel.
Additionally, the file size will be smaller doing it this way, which makes the images load into the game faster.

If you have an image file that requires a different transparent color other than magenta (RGB 255 0 255), the `ImageLoader` class has alternate methods
to allow for a transparent color to be specified instead of just using the default one.

```java
// sets transparent color to blue
BufferedImage catImage = ImageLoader.load("cat.png", Color.blue);
```

### Changing default transparent color

A new color can be set as the default transparent color by changing the `TRANSPARENT_COLOR` variable in the `Config` class.
I don't advise doing this as every image in the game currently is setup to use magenta as the transparent color, but the option to change it is there.