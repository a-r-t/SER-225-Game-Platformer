---
layout: default
title: Game Loop
parent: Game Engine
nav_order: 1
permalink: /GameEngine/GameLoop
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Game Loop

## What is a game loop?

A game loop is the overall flow control for a game. To put it simply, it's an infinite loop that exists while the game application is running, 
and therefore will continually perform the same actions over and over again while the game is being played. 
Each game loop iteration is known as a frame. 
The number of game loop iterations that are completed per second is how frames per second (FPS) is calculated. 
For example, 60 FPS means that the game loop iterated 60 times in one second.

## How does this engine's game loop work?

The game loop is created using a thread, which is a process that runs in parallel to the rest of an application. 
The thread contains an infinite loop that continually updates game logic and then renders all graphics to the screen. 
The game currently targets 60 FPS, and will achieve it as long as the computer running it can handle that (which most can, this is a simple 2D Java game).
The target FPS can be changed by modifying the `TARGET_FPS` configuration properly in the `Config` class, however I've found that 60 FPS is the "sweet spot" on every computer I've tested this game out on. 

Note that the game loop ties FPS and UPS (updates per second) together, so there is always one update call per draw call.
This was done for simplicity, however it does mean the game will slow down if the FPS drops too much.
Considering this is a simple Java Swing game, I haven't found a computer in my testing that was unable to run it at 60 FPS, so this shouldn't be an issue...

For each "tick" of the game loop (each time the loop iterates, i.e. one frame), it does two things: updates game logic, 
and then updates the graphics that are rendered to the screen based on the updated game logic. 
That's it. 
Here is a very detailed diagram illustrating the game loop:

![game-loop-diagram.png](../../assets/images/game-loop-diagram.png)

All jokes aside, at the end of the day that is all the game loop is doing -- continually cycling between `update` and `draw` calls.
Each iteration of the game loop will wipe the screen and completely re-render all graphics to ensure what is being displayed is always consistent with the state of the game logic.

## What code belongs in the update cycle?

Game logic is any code that has to do with the game being carried out, which includes anything from detecting key presses, moving an entity to a new location,
collision detection, etc. 
All game logic belongs in the `update` section of the game loop. Each iteration of the game loop will force the game to re-run its game logic. 
An example of logic that belongs in the update cycle is moving a player character based off of a user's key inputs.
When a user holds the right key down, the `update` cycle will continually check for the
right arrow key being pressed and, if so, move the player to the right one pixel each time. 

Throughout this codebase, most classes have an `update` method, which is where the class's game logic is contained.

## What code belongs in the draw cycle?

Any code that is explicitly rendering ("painting") a graphic (usually an image) to the JPanel belongs in the `draw` section.
Each iteration of the game loop will force the game to "repaint" itself, meaning all graphics on the screen are updated (even if they haven't moved
since the previous cycle). As a result, any changes to the "state" of the graphics (such as if a new graphic needs to be rendered, or
an existing graphic has moved) are immediately updated on screen in response to game logic updates.

Throughout this codebase, most classes have an `draw` method, which is where the class's graphic rendering code is contained.
Graphics are all tied to game logic in some way, shape, or form.
Classes are often used to represent an entity's game logic AND associated graphics, which keeps the game organized and modular.

You can think of this step in the cycle as the "output" to the user playing the game. 
Technically a game can work without ever displaying any graphics, since all the game logic still plays out each cycle and the computer itself doesn't actually need
graphics on screen to do its job -- just like how a console app can technically run a program just fine without ever printing anything to the console. 
It is a game developer's job to determine what output to show to the user as the game progresses, however
the draw cycle should NOT be relied on to actually perform any critical game logic. 
Code in the draw cycle should NEVER be mutating (changing the value of) any class instance variable. Consider it in "read-only" mode.

### Lag

The `draw` section of the game loop should only contain code to draw graphics to the screen and should NOT
include any kind of game logic, such as moving graphics to a new location. 
Not only is this best practice as mentioned above (treat graphics as the "output" of the application), but it can actually break your game or cause unexplainable bugs. 
This is because of the way the Java JPanel's graphics painting is set up. While the game can call to the JPanel (which this game engine uses as a buffer to display graphics)
and tell it to update its graphics (also known as a "repaint"), this merely "schedules" the repaint instead of carrying it out right away. 
The JPanel can then carry out the repainting whenever it has the computing power to do so, which is outside of your program's control...and there's a possibility that it doesn't carry out the repaint at all.
When draw calls are skipped like this, it is known as lag, because the game is updating its game logic but not representing those logic changes
with updated graphics on the screen. 

It's imperative to not place game logic updating in the `draw` section of the game loop because having logic "skipped" or ran "out of order" can completely break a game, while the concept of "lag" will at least maintain game logic integrity to keep the game working.
To completely avoid this potential complication of game logic potentially getting "skipped" or ran "out of order", do NOT mutate class instance variables during the `draw` cycle.

## Example of game loop running

Say I have the following class for an image:

```java
import javax.imageio.ImageIO;
import java.io.File;

public class CatImage {
    private BufferedImage image = ImageIO.read(new File("pusheen.png"));
    private int xLocation = 0;
    private int yLocation = 0;
    
    public int getXLocation() {
        return xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }
    
    // this would be called during the update cycle
    public void update() {
        xLocation += 1;
    }
    
    // this would be called during the draw cycle
    public void draw(Graphics2D g) {
        g.drawImage(image, xLocation, yLocation, null);    
    }
}
```

During the game loop's `update` cycle, this class's `update` method will move its position 1 pixel to the right (adding 1 to `xLocation`).
During the game loop's `draw` cycle, the image will be drawn to the screen at the position defined by its `xLocation` and `yLocation` variables.
As the game loop iterates and continuously calls the `update` and `draw` methods over and over again, the `update` cycle will continually move the image's position to the right,
and the `draw` will continually "repaint" the image to the screen at its updated location.

Click the button below for a live simulation of how this example `CatImage` class would behave during the game loop cycle:

<button id="run-simulation-button" onclick="triggerSimulation()">Run Simulation</button>

<div id="cat-image-container" style="border:1px solid black;">
    <img id="pusheen-image" src="/SER-225-Game-RPG/assets/images/pusheen.png" alt="pusheen.png" style="padding-left:0px;">
</div>

<script>
let catMoving = false;

function triggerSimulation() {
    catMoving = true;
    const button = document.getElementById("run-simulation-button");
    button.disabled = true;
    const catImage = document.getElementById("pusheen-image");
    catImage.style["padding-left"] = "0px";
}

function moveCat() {
    if (catMoving) {
        const container = document.getElementById("cat-image-container");
        const containerWidth = container.offsetWidth;
            
        const catImage = document.getElementById("pusheen-image");
        const currentLeftPx = getComputedStyle(catImage).getPropertyValue("padding-left");
        const currentLeft = parseInt(currentLeftPx.replace("px", ""), 10);
        const catImageWidth = catImage.width;

        if (currentLeft + catImageWidth >= containerWidth - 2) {
            catMoving = false;
            const button = document.getElementById("run-simulation-button");
            button.disabled = false;
        } else {
            catImage.style["padding-left"] = (currentLeft + 1) + "px";
        }
    }

}

window.setInterval(moveCat, 10);
</script>

## Game Loop Type

In the `Config` class, there is a flag `GAME_LOOP_TYPE` that can be set between two values: `GameLoopType.POWER_SAVER` and `GameLoopType.MAX_PERFORMANCE`.
By default, it is set to `GameLoopType.POWER_SAVER`.
Due to the nature of this game being created with Java Swing, which is NOT a true game framework, it has to "fight" to run at a reasonable performance.
Power saver mode will make the game loop process run well enough, but will prioritize not hogging CPU cyles too much to ensure other apps that need it will have access to it,
which in turn lowers its power consumption.
Max performance mode will cause the game to do whatever it takes to run at the target FPS -- it will take all the CPU cycles it needs.
I recommend leaving it in power saver mode if your computer's CPU is on the lower-end spec-wise.
If you are experiencing any stuttering/lag, switching to max performance mode will likely eliminate it.