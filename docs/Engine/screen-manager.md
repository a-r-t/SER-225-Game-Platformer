---
layout: default
title: ScreenManager
parent: Engine
nav_order: 3
permalink: /Engine/ScreenManager
---

# ScreenManager

## What is this class for?

The `ScreenManager` class is what starts off the cascading `update` and `draw` calls for various other game screens created externally
from the engine. This class is how a Screen from outside the engine package can be "attached" to the engine, essentially allowing
a game to be made using the tools from this engine.

The class itself is pretty basic. It's main reason for existing is to hold an instance of a `Screen` (the `currentScreen`), which should
be passed into it using the `setCurrentScreen` method. The process for doing so is below:

`MyScreen` class, which extends off the `Screen` class:
```java
public class MyScreen extends Screen {
    	@Override
    	public void initialize() {

    	}

        @Override
        public void update(Keyboard keyboard) {
    	    
    	}

        @Override
        public void draw(GraphicsHandler graphicsHandler) {
    	    
        }
}
```

Some driver class:
```java
public static void main(String[] args) {
    GameWindow gameWindow = new GameWindow();
    gameWindow.startGame();
    ScreenManager screenManager = gameWindow.getScreenManager();
    screenManager.setCurrentScreen(new MyScreen());
}
```

From this point on, the `MyScreen` class would be called as a part of the game loop, meaning its `update` and `draw`
method will be continuously cycled through. Its `initialize` method is called once when the `ScreenManger` sets the screen
as its current screen.

## Access Screen Width and Height

The `ScreenManager` has three static methods: `getScreenWidth`, `getScreenHeight`, and `getScreenBounds`. This essentially allows
for any class in a game to access the width, height, and bounding rectangle (x, y, width height) of the current screen, which is really important
for doing things like rendering graphics in the correct place and detecting if something is off-screen. For this engine,
the current screen will take up the entirety of the `GamePanel`, meaning you're essentially getting the panel's width and height when using these.

