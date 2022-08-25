---
layout: default
title: Config
parent: Game Engine
nav_order: 6
permalink: /GameEngine/Config
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Config

## What is a config?

Most applications have a concept of a "config file", which is just a configuration file that is separated out from program logic.
Configuration files generally contain global constant variable values that an application references as needed, and treats them as "read only"
(they should not be changed while the application is running). The values chosen to be included in a configuration file differ depending on the application,
but by having one it makes it possible to tweak/change values without having to go digging through class files, and also makes it very easy
to test out how value changes affect an application. Often times applications are used by multiple people on various platforms -- having
certain values strategically separated out like this can make a program a lot more flexible to work for other people's use cases. You can
literally think of these variable values as "configuration options", just like hitting the "settings" button on any other application and personalizing
your experience while using it.

## Where is this game's config file located?

The `Config.java` file in the `Engine` package contains several configuration variables that can be easily changed while
the application is not running in order to modify a "global" aspect of it. The following variable values are defined in the `Config` class:

- **FPS** -- how many game loop cycles per second are run (FPS = **F**rames **P**er **S**econd)
- **RESOURCES_PATH** -- root folder path to where all game assets will be stored (image files, etc)
- **MAP_FILES_PATH** -- root folder path to where all map files will be stored
- **GAME_WINDOW_WIDTH** -- width of the game's JFrame window
- **GAME_WINDOW_HEIGHT** -- height of the game's JFrame window
- **TRANSPARENT_COLOR** -- default transparent color the `ImageLoader` class will use when loading an image into the game

## How do you access a `Config` class variable from other classes?

Any class can access a `Config` class variable since all variables are defined as `public` `static`.

```java
public void update() {
    System.out.println("The game window width is: " + Config.GAME_WINDOW_WIDTH);
    System.out.println("The game window height is: " + Config.GAME_WINDOW_HEIGHT);
}
```

## How do you decide if a variable should be added to the Config class?

It's honestly up to the programmer which values they want to have exposed separately as a "configuration" option. The only
real "rule" is that it must be a global read-only variable, meaning the value of the variable CANNOT be modified while the game is running,
and every class must be able to access it. I pretty much only use it for "game engine setup" values.