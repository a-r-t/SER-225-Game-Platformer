---
layout: default
title: Player Class Overview
nav_order: 1
parent: Player
grand_parent: Game Code Details
permalink: /GameCodeDetails/Player/PlayerClassOverview
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Player Class Overview

## Player Resources Setup

The `Player` class has a ton of instance variables that need to be setup upon creation, but they are all relatively straightforward.

First, let's start with the "movement" based variables -- the following define how the `Player` character is able to move throughout a level.
These are all float values, so decimal precision for movement is available.

- **walkSpeed** - how fast the player walks
- **gravity** - how fast the player falls when in air
- **jumpHeight** - how high the player jumps
- **jumpDegrade** - as the player is jumping upwards, less and less vertical velocity gets applied per cycle based on this value
- **momentumYIncrease** - how much extra the player falls in addition to gravity while in the air
- **terminalVelocityY** - the fastest speed the player can fall before the fall speed gets "capped" (stops being able to increase)

Generally, `Player` subclasses will set those values as desired.

The `Player` has several other important variables it uses to keep track of its current state:
- **playerState** - based on the `PlayerState` enum in the `Level` package, a `Player` can be in a certain state which affects its game logic; currently, the supported states are `STANDING`, `WALKING`, `JUMPING`, and `CROUCHING`
- **facingDirection** - which direction the player is facing; can be either `LEFT` or `RIGHT`
- **airGroundState** - if the player is currently on the ground `GROUND` or in the air `AIR` (yes I know this is a horrible variable name but I couldn't think of anything better)
- **levelState** - allows the player to keep track of the current level state so it can know if it has beaten a level or has died in a level; more details on `LevelState` can be found in the `PlayLevelScreen` documentation [here](../ScreensSubSections/play-level-screen.md)

## Player Class Methods

The player's `update` cycle handles a ton of different cases based on the current `PlayerState`, and the `Player` class has split up
most of that state logic into separate methods. For example, walking logic is in a separate method to jumping logic, etc. The
`handlePlayerState` method determines which game logic method to go to based on the `playerState` instance variable:

```java
protected void handlePlayerState() {
    switch (playerState) {
        case STANDING:
            playerStanding();
            break;
        case WALKING:
            playerWalking();
            break;
        case CROUCHING:
            playerCrouching();
            break;
        case JUMPING:
            playerJumping();
            break;
    }
}
```

## Player Subclasses

Players to be used in game are defined by subclassing the `Player` class. These are found in the `Player` package.
The cat player is defined by the `Cat` class. 

The subclass doesn't have to do much, as the `Player` class contains all of the standard player functionality. If a change is going to be
specific to one player, then it can be added to a subclass, but any changes that would affect all players should be done in the 
base `Player` class.

The `Cat` subclass sets various movement related attributes to desired values and also defines the player's animations.
The `Player` class expects certain animations be included in a player subclass by name, all of which are included in the `Cat` subclass,
although this can be easily changed and additional animations can be added freely. Currently, the `Player` class expects the following animations. Most
animations have a "left" and "right" counterpart, which are used based on the way the player is facing.


1. `STAND_RIGHT` and `STAND_LEFT` -- player standing still
1. `WALK_RIGHT` and `WALK_LEFT` -- player walking
1. `JUMP_RIGHT` and `JUMP_LEFT` -- player jumping (rising upwards)
1. `FALL_RIGHT` and `FALL_LEFT` -- player falling
1. `CROUCH_RIGHT` and `CROUCH_LEFT` -- player crouching
1. `DEATH_RIGHT` and `DEATH_LEFT` -- player dying
1. `SWIM_STAND_RIGHT` and `SWIM_STAND_LEFT` -- player is standing still in water

Looking at the `Cat` class's constructor is a good reference point for making an additional player.

```java
public Cat(float x, float y) {
    super(new SpriteSheet(ImageLoader.load("Cat.png"), 24, 24), x, y, "STAND_RIGHT");
    gravity = .5f;
    terminalVelocityY = 6f;
    jumpHeight = 14.5f;
    jumpDegrade = .5f;
    walkSpeed = 2.3f;
    momentumYIncrease = .5f;
}
```

The image file for the cat player is `Cat.png`.

## Player Moving

When the player moves through the level, you may notice that most of the time the player is not actually moving around screen, and instead
stays in the middle of the screen while the map's camera moves to show more of the map. The only time the player actually moves is when
the map's camera reaches the end of the map and has no more map to show. You can see this behavior in the below gif. Notice how the player is kept in the middle
of the screen a majority of the time while the camera continually shows different parts of the map.

![game-screen-1.gif](../../../assets/images/playing-level.gif)

While the player is in charge of the overall movement of the game, the map class's `adjustMovementX` and `adjustMovementY` methods are called
each frame which adjusts the player's position and the camera's position as needed. Usually, what happens is the player will move forward
while at the center of the screen, and then the adjust methods will move the player back to the center of the screen and move the camera instead to show more of the map. 
This gives the appearance that the map is "scrolling". 
And yes, nearly all games do this: 99% of the time your player character is not actually moving; instead the map's camera is.

## Player Update Cycle

Each `update` cycle, the `Player` class will do a few things:
1. Apply gravity (downward force)
1. Handle player state (more details on player state [here](./player-states.md))
1. Move player by the amount it should be moved by based on the results of the handle player state step
1. Handle player animation updates and see if a switch is needed
1. Call super class's update cycle in order to apply animation logic (`super.update();`)