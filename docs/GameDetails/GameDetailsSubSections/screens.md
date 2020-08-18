---
layout: default
title: Screens
nav_order: 1
parent: Game Details
has_children: true
permalink: /GameDetails/Screens
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Screens

## What is a Screen?

This engine uses a concept of "screens" to split up sections of a game into logical separate classes. Games tend to be heavy on code,
so it is important to use screens as a means to organize different pieces of the game to keep the codebase maintainable and navigable. 
Screens have their own instance variables and `update`/`draw` methods that are crafted specifically for its owned part of the game code.

The `ScreenCoordinator` class, which details on can be found [here](#/ScreenCoordinator), is the most important `Screen` in the codebase,
as it's job is to "coordinate" which other `Screens` are loaded at which times based on what needs to happen in the game.

## What is the Screen class?

The `Screen` class is an abstract class provided by the engine, and all screens defined in the game are to extend it and implement its required methods
to ensure they are set up correctly.

## Screen class structure

All screens in this game extend the engine's `Screen` class, which is an abstract class requiring the methods `initialize`, `update`, and `draw`
to be implemented:

```java
public abstract class Screen {
    public abstract void initialize();
    public abstract void update(Keyboard keyboard);
    public abstract void draw(GraphicsHandler graphicsHandler);
}
```

The `ScreenCoordinator` class handles calling the `initialize`, `update`, and `draw` methods for you at the appropriate times in the game loop
for screens that it handles. `Screen` classes can load other screen themselves, in which case it is up to that `Screen` class to add it to
the game loop cycle properly (usually just by calling its `update` and `draw` calls as a part of its own cycle). The `initialize` method
"sets up" a screen and is usually called immediately after the screen is instantiated -- it is very similar to a constructor in this regard, however constructors
cannot be called again while it is often useful to call the `initialize` method again to "reset" a screen. An example of this is resetting a level from the beginning
by calling its `initialize` method and letting it handle re-setting everything up.

The `update` method is passed the `Keyboard` class so it can always be used to detect keyboard input, and the
`draw` method is passed the `GraphicsHandler` class in order to paint to the engine's backing JPanel.]

## How to add a new Screen class?

Start by making a new class in the `Screens` package, where all game screens are defined. From there, extend the engine's base `Screen` class and
implement its methods. Below is an example if an `InstructionsScreen` were to be created:

```java
public class InstructionsScreen extends Screen {

    public void initialize() {
        
    }   

    public void update(Keyboard keyboard) {

    }

    public void draw(GraphicsHandler graphicsHandler) {

    }      
}
```

From there, the code added to those methods should handle setting up the instructions screen resources,
the game logic for the instructions screen, and finally what graphics should be shown on the JPanel while the instruction screen
is loaded.

A way for this screen to be loaded will also need to be added to the game logic, either adding it to the `ScreenCoordniator` or setting up an existing screen to handle it.
The `PlayLevelScreen` is the most bulky screen in this game, but it's a good example of how a screen can be loaded from another screen
(upon beating a level or dying in a level, the `LevelClearedScreen` or `LevelLoseScreen` is loaded up while the `PlayLevelScreen` is still active). 


