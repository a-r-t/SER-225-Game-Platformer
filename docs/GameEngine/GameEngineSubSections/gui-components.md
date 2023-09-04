---
layout: default
title: GUI Components
parent: Game Engine
nav_order: 2
permalink: /GameEngine/GUI Components
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# GUI Components

## Java Swing Library

This game uses components from Java's Swing library for its GUI (graphic user interface). All that's really used
is a JFrame for the application's UI window and a JPanel inside the JFrame which is where graphics are "painted" on to.

## Game Window

The `GameWindow` class in the `Engine` package extends the Java Swing `JFrame` class and just sets up the application window
as needed. Otherwise, it's pretty uneventful. 
This JFrame does hold the all-important `GamePanel` class, but otherwise it's only job is just to exist.

This is all the `GameWindow` brings up on its own:

![jframe.png](../../assets/images/jframe.png)

## Game Panel

The `GamePanel` class in the `Engine` package extends the Java Swing `JPanel` class and is responsible for rendering the game's graphics.
Additionally, the `GamePanel` class also sets up various other essential game resources like the `graphicsHandler` and the thread that runs the game loop.

The `GamePanel` class is also home to the universal pause function. 
Pressing the `P` key at any point while the game is running will immediately stop the game loop's `update` cycle but will continue the game's `draw` cycle,
which essentially "pauses" the game. 
It will also show the [sprite font](../../GameCodeDetails/GameCodeDetailsSubSections/sprite-font.md) text "PAUSE" in the middle of the screen while the game is paused. 
The `pauseLabel` variable is what defines that "PAUSE" text, and the `update` method and `draw` method contain the pause logic.

Additionally, the class has functionality for displaying the game's current FPS to the user. 
Pressing the `G` key at any point while the game is running will display an FPS label at the top right of the screen.

Every `draw` cycle, the `JPanel's` `repaint` method is called, which schedules a re-painting of all graphics to the screen.
