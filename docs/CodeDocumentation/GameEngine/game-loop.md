---
layout: default
title: Game Loop
parent: Game Engine
grand_parent: Code Documentation
nav_order: 1
permalink: /CodeDocumentation/GameEngine/GameLoop
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Game Loop

## What is a game loop?

A game loop is the overall flow control for a game. Generally, it's an infinite loop that exists while the game application is
running, and will continually do the same actions over and over again. Each game loop iteration is known as a frame. The number
of game loop iterations that are completed per second is how frames per second (FPS) is calculated.

## How does this engine's game loop work?

The game loop is created using the `Timer` class from Java Swing. The timer is set up to continually "tick" after a predefined number
of milliseconds have elapsed. While it's not perfect, it does the job well and for the most part maintains its tick cycle to match
the desired FPS. To adjust the FPS of the game, the `Config` class has a variable named `FPS` which can be changed as desired. It currently
is set to 100 as I found that the timer lags a bit when set to a standard 60 FPS, but YMMV based on the computer being used.

Each "tick" of the game loop (each time the loop iterates), it does two things: updates game logic and then updates the graphics
that are rendered to the screen based on the updated game logic. That's it. Here is a very detailed diagram illustrating the game loop:

![](../../assets/images/game-loop-diagram.png)

All jokes aside, at the end of the day that is all the game loop is doing -- continually cycling between `update` and `draw` calls.

## What code belongs in the update cycle?

Game logic is any code that has to do with the game being carried out, which includes anything from detecting key presses, moving something to a new location,
collision detection, etc. All game logic belongs in the `update` section of the game loop. Each iteration of the game loop will
force the game to re-run its game logic, which is essential to making the game run continually. If you write game logic that
moves a player one pixel to the right when the right arrow key is held down, the `update` cycle will continually check for the
right arrow key being pressed and if so move the player to the right one pixel each time. Without the game loop, there'd be no way
to apply this logic continually.

Anywhere in the codebase you see an `update` method, and you will see it in nearly every class, it is where game logic is being run
by the game loop while an instance of that class is active.

## What code belongs in the draw cycle?
Any code that is explicitly rendering ("painting") a graphic (usually an image) to the JPanel belongs in the `draw` section.
Each iteration of the game loop will force the game to "repaint" itself, meaning all graphics on the screen are updated (even if they haven't moved
since the previous cycle). This means that any changes to the "state" of the graphics (such as if a new image needs to be rendered or
an existing image has moved) are immediately updated on screen.

Anywhere in the codebase you see a `draw` method, and you will see it in nearly every class, it is where graphics are being
told where to be displayed on screen while an instance of that class is active.

You can think of this step in the cycle as the "output" to the player of the game. Technically a game can work without
ever displaying any graphics, since all the game logic still plays out each cycle and the computer itself doesn't actually need
graphics on screen to do its job -- just like how a console app can technically run a program just fine without ever printing
anything to the console. It is a game developer's job to determine what output to show to the user as the game progresses, however
the draw cycle should NOT be relied on to actually perform any critical game logic. Code in the draw cycle should NEVER
be mutating (changing the value of) any class instance variable. Consider it in "read-only" mode.

### Lag

The `draw` section of the game loop should only contain code to draw graphics to the screen and should NOT
include any kind of game logic, such as moving graphics to a new location. Not only is this best practice as mentioned above
(treat graphics as the "output" of the application), but it can actually break your game or cause unexplainable bugs otherwise. 
This is because of the way the Java JPanel's graphics painting is set up. While the game can call to the JPanel (which this game engine uses as a buffer to display graphics)
and tell it to update its graphics (also known as a "repaint"), this merely "schedules" the repaint instead of carrying it out right away. The JPanel
can then carry out the repainting whenever it damn well pleases outside of your program's control, and there's a possibility that it doesn't carry out the repaint at all.
When draw calls are skipped like this, it is known as lag, because the game is updating its game logic but not representing those logic changes
with updated graphics on the screen. Lag is unavoidable at the end of the day, but it's imperative to not place game logic updating in the `draw` section of the game loop
because having logic "skipped" or ran "out of order" can completely break a game, while the concept of "lag" will at least maintain game logic integrity
to keep the game working. This issue can entirely be avoided if instance variables (from any class) are NEVER mutated during
the `draw` cycle.

## Example of game loop running

Say I have the following class for an image:

```java
import javax.imageio.ImageIO;

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
    <img id="pusheen-image" src="/SER-225-Game/assets/images/pusheen.png" alt="Cat" style="padding-left:0px;visibility:hidden;">
</div>

<script>
let catMoving = false;

function triggerSimulation() {
    catMoving = true;
    const button = document.getElementById("run-simulation-button");
    button.disabled = true;
    const catImage = document.getElementById("pusheen-image");
    catImage.style.visibility = "visible";
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