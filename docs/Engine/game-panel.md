---
layout: default
title: GamePanel
parent: Engine
nav_order: 2
permalink: /Engine/GamePanel
---

# GamePanel

## What is this class for?
The `GamePanel` class defines a JPanel component which is placed inside the `GameWindow`.
This class is what ties the entire engine together. It executes the game loop, listens for keyboard events, paints itself 
with game graphics, and cycles through game logic.

## Game Loop
A game loop is exactly what it sounds like -- an infinite loop that continually updates game logic and renders (draws) game graphics
as the game is running.

Using a `Timer` class, the `GamePanel` sets up the main game loop. The game loop cycles between `update` and `draw` calls continuously
through the lifetime of the game application. The number of times the game goes through an `update` and `draw` call per second is how
a game's "frames per second" is determined.

In the `GamePanel` class's constructor, the game loop `Timer` is defined (but not started):
```java
timer = new Timer(1000 / Config.FPS, new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        update(keyboard);
        repaint();
    }
});
```
The `1000 / Config.FPS` tells the timer how often it should "tick" (run its `actionPerformed` method). This is known as the `delay`.
The `Timer` class asks for the `delay` to be in milliseconds.
There are 1000 milliseconds in a second, so `1000 / desired FPS` will land you around the ball park of the desired FPS value
(although it won't be perfect due to the way Java's `Timer` works and a bunch of other factors...but it's close enough).
Out of the box, the `FPS` variable in the `Config` file is set to 100, so the game loop is told to cycle every ten milliseconds.
While 60 FPS is more standard (cycles about every 16 milliseconds), after much testing I found that the engine runs smoother at a higher FPS.
The FPS value in the `Config` class can be changed as desired to any value and the game loop will adjust to it.

Each cycle, the game loop calls the `GamePanel's` `update(keyboard)` and `repaint()` methods.

The `startGame` method is what actually starts the `Timer` and therefore beings the execution of the game loop. 

### Update

The `Timer's` `update(keyboard)` method call goes to the `GamePanel's` `update` method and starts the game logic chain.
From here, the `GamePanel` calls the `update` method on the `ScreenManager` class which starts off the game logic each cycle (more on this later). 
This forces the game logic to continually "update" as things in a game happen, 
for example moving a image's location forward by 1 pixel while the right arrow key is pressed.

### Draw

The `Timer's` `repaint()` method call goes to the overridden `paintComponent` method...when it's ready. `repaint` does NOT
automatically "paint" the JPanel with graphics, it actually "schedules" a `repaint` but that can happen whenever the JPanel
is ready to execute it.

Once the `paintComponent` method is actually called, the `graphicsHandler` class is given the JPanel's graphics object instance
(more on this class later) and then finally the `GamePanel's` `draw` method is called. Like the above `update` method,
the `draw` method will then call the `draw` method on the `ScreenManager` class, which starts off the game's graphics being painted each cycle.

## Pause Game Functionality

The `GamePanel` class's `update` method does hold functionality to pause all game logic updating with the press of the `P` key.
Doing so will stop `update` calls to the `ScreenManager's` `update` method, which effectively stops the game from running its logic cycles,
but it will not stop the `draw` cycles so the game itself will still be shown on screen (like any traditional pause menu).

The pause functionality can easily be removed if it is not desired without affecting the game loop.

The pause key can be changed by changing the `pauseKey` instance variable to a new keyboard key.
Additionally, other pause related things, like what happens when the game is paused, can be easily changed with a quick look through of the class.
Some obvious ones are the `pauseLabel` SpriteFont (declared in the constructor) which currently displays a "PAUSE" message in the middle of the screen,
and in the `draw` method there is code that tints the entire screen when the game is paused:
```java
graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), new Color(0, 0, 0, 100));
```
Note that the `new Color(0, 0, 0, 100)` defines the Color that the background is overlaid with. RGB 0, 0, 0 is black, and the 100 is the alpha value (transparency -- 0 is fully transparent, 255 completely opaque).

## Configuration Options

I don't really recommend touching this class unless you know what you are doing, as an entire game relies on the game loop
working properly and the engine is setup in a way where things have to be carried out in a particular order (mostly because Java's Swing components are REALLY annoying to work with).
The pause game functionality though can be easily edited in this class as described in the above section.



