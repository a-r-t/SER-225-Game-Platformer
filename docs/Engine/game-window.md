---
layout: default
title: GameWindow
parent: Engine
nav_order: 1
permalink: /Engine/GameWindow
---

# GameWindow

## What is this class for?

The `GameWindow` class defines a JFrame window to be used for a game.
While the `GameWindow` is key due to the fact that without a GUI of some kind there would be no game at all, it also defines and
contains and sets up the all important `GamePanel` class instance, which is a JPanel that contains the game loop logic and is where
graphics are rendered ("painted" as Java calls it).

Besides defining a JFrame window and holding on to the `GamePanel` class, the `GameWindow` class doesn't do much else.
The width and height for the window can be changed in the `Config` file. The window is set to not be resizable,
but that can be easily changed using its `setResizable` method.

