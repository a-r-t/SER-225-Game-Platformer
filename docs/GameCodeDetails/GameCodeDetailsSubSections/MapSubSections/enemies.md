---
layout: default
title: Enemies
nav_order: 6
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/Enemies
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Enemies

## What is an enemy?

An enemy (represented by the `Enemy` class in the `Level` package) is a `MapEntity` subclass. Enemies in a platformer game
serve to hinder the player from completing a level. [For example, in Mario games, enemies like goombas and koopas can hurt or kill Mario if touched](https://www.youtube.com/watch?v=iJyCk2zhMmE).
 
The `Enemy` class adds a `touchedPlayer` method that any `Enemy` subclass can override. 
An enemy can be given its own animation and graphics information,
as well as its own `update` cycle which defines its behavior. 
There really isn't any limit to what an enemy can be made to do, it's just up to the implementer's coding skills!

As of right now, the `Enemy` classes base `touchedPlayer` method will hurt the player (which in turn kills the player) if the player
touches the enemy. If this behavior is not desired, you can remove that line of code, or override the `touchedPlayer` method
in an enemy class and include logic to determine what should occur when the enemy touches the player.

Enemies adhere to similar collision detection as the player, and can choose to follow those collision rules using the `GameObject` class's
collision methods just like the player does. More details on collision detection and handling can be found [here](../PlayerSubSections/collision-detection.md).

## Enemy Subclass

In the `Enemies` package, there are currently three subclasses of the `Enemy` class -- `BugEnemy`, `DinosaurEnemy`, and `Fireball`.
Each one of these classes defines an enemy in the game, which can be seen in the game's `TestMap` map.

If an enemy's `isUpdateOffScreen` flag set to true, the enemy will always be considered "active" by the camera and will always have its update logic run.
This could be useful for something like a boss battle to prevent the player from being able to break the boss by moving the camera too far away.

## Adding a new enemy to the game

This is simple -- create a new class in the `Enemies` package, subclass the `Enemy` class, and then just implement desired logic from there. 
I recommend copying an existing enemy class as a "template" of sorts to help set up and design the enemy.

## Adding an enemy to a map

In a map subclass's `loadEnemies` method, enemies can be defined and added to the map's enemy list. 
For example, in `TestMap`, a `BugEnemy` and `DinosaurEnemy` are created and added to the enemy list:

```java
@Override
public ArrayList<Enemy> loadEnemies() {
    ArrayList<Enemy> enemies = new ArrayList<>();

    BugEnemy bugEnemy = new BugEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.LEFT);
    enemies.add(bugEnemy);

    DinosaurEnemy dinosaurEnemy = new DinosaurEnemy(getMapTile(19, 1).getLocation().addY(2), getMapTile(22, 1).getLocation().addY(2), Direction.RIGHT);
    enemies.add(dinosaurEnemy);

    return enemies;
}
```

## Enemies currently in game

Specific enemy classes can all be found in the `Enemies` package.

### Bug Enemy

![bug-enemy.gif](../../../assets/images/bug-enemy.gif)

This enemy is defined by the `BugEnemy` class. 
It is designed to replicate a typical goomba's movement patterns from the classic Mario games. 
Essentially, the bug enemy will continually walk forward. 
If it hits a wall, it will turn around. I
f it walks off the edge of a cliff, it will
fall down until it touches the ground again before it starts walking forward again.

The image file for the bug enemy is `BugEnemy.png`.

### Dinosaur Enemy

![dinosaur-enemy-walk.gif](../../../assets/images/dinosaur-enemy-walk.gif)

This enemy is defnied by the `DinosaurEnemy` class. 
It will walk back and forth between two specific points. 
Every few frames, it will stop, turn red, and shoot out a fireball.

![dino-enemy-shoot.png](../../../assets/images/dino-enemy-shoot.png)

![fireball.png](../../../assets/images/fireball.png)

This enemy is good to reference to see how to have an enemy create another entity on the map -- the dinosaur enemy in this case
creates a separate fireball entity:

```java
// define where fireball will spawn on map (x location) relative to dinosaur enemy's location
// and define its movement speed
int fireballX;
float movementSpeed;
if (facingDirection == Direction.RIGHT) {
    fireballX = Math.round(getX()) + getWidth();
    movementSpeed = 1.5f;
} else {
    fireballX = Math.round(getX() - 21);
    movementSpeed = -1.5f;
}

// define where fireball will spawn on the map (y location) relative to dinosaur enemy's location
int fireballY = Math.round(getY()) + 4;

// create Fireball enemy
Fireball fireball = new Fireball(new Point(fireballX, fireballY), movementSpeed, 60);

// add fireball enemy to the map for it to spawn in the level
map.addEnemy(fireball);
```

The image file for the dinosaur enemy is `DinosaurEnemy.png`.

### Fireball

![fireball.png](../../../assets/images/fireball.png)

This enemy is defnied by the `Fireball` class. 

As mentioned in the previous [dinosaur enemy](#dinosaur-enemy) section, the dinosaur enemy will shoot out a fireball every so often.
The fireball will travel straight for a few frames before disappearing. 
It will also disappear if it collides with a solid tile.
Notice that this enemy sets itself to `REMOVED` when it should disappear -- a map entity with a `REMOVED` status will be permanently removed from the map enemies list and will not ever respawn.

```java
// if timer is up, set map entity status to REMOVED
// the camera class will see this next frame and remove it permanently from the map
if (existenceFrames == 0) {
    this.mapEntityStatus = MapEntityStatus.REMOVED;
}
```

The image file for the fireball is `Fireball.png`.
