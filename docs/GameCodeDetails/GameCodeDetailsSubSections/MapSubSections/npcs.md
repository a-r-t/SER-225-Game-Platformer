---
layout: default
title: NPCs
nav_order: 8
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/NPCs
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# NPCs

## What is an NPC?

An NPC (**n**on-**p**layer **c**haracter) (represented by the `NPC` class in the `Level` package) is a `MapEntity` subclass. This class adds
a `checkTalkedTo` method that any `NPC` subclass can override, which handles the code that is run if an NPC is talked to by the Player (the player
character has to be within the NPC's range and then press space to talk to it). An NPC can be given its own animation and graphics information,
as well as its own `update` cycle which defines its behavior.

NPCs in a game tend to act as a neutral character, meaning they usually do not harm the player and are meant to supplement the traversal through a level and also
improve overall game immersion.

As of now, once an NPC is talked to, they can display a message until their `talkedToTime`, runs out, at which point the message
disappears and they can be re-talked to again.

## NPC Subclass

In the `NPCs` package, there is currently only one subclass of the `NPC` class -- `Walrus`.
This `Walrus` NPC can be seen in the game's one level.

## Adding a new NPC to the game

This is simple -- create a new class in the `NPCs` package, subclass the `NPC` class, and then just implement
desired logic from there. I recommend copying an existing npc class as a "template" of sorts to help set up and design the npc.

## Adding an NPC to a map

In a map subclass's `loadNPCs` method, NPCs can be defined and added to the map's NPC list. For example, in `TestMap`,
a `Walrus` class instance is created added to the NPC list:

```java
@Override
public ArrayList<NPC> loadNPCs() {
    ArrayList<NPC> npcs = new ArrayList<>();

    npcs.add(new Walrus(getPositionByTileIndex(30, 10).subtract(new Point(0, 13))));

    return npcs;
}
```

## NPCs currently in game

### Walrus

![walrus.png](../../../assets/images/walrus.png)

This NPC is defined by the `Walrus` class. In addition being the best made piece of art you have ever laid your eyes on,
the walrus is able to be talked to when the player is in range of its "hurtbox" and presses space. Upon doing so, the `Walrus` class
will draw a box with text inside to the screen:

![walrus-talking.png](../../../assets/images/walrus-talking.png)

The code to bring up the "speech box" during the `draw` cycle just creates a rectangle, then a sprite font, and then places
them in the right location. An `NPCs` `drawMessage` method will automatically be called when the `NPC` is talked to.
This is what the overridden `drawMessage` method looks like in the `Walrus` class:

```java
@Override
public void drawMessage(GraphicsHandler graphicsHandler) {
    graphicsHandler.drawFilledRectangleWithBorder(Math.round(getCalibratedXLocation() - 2), Math.round(getCalibratedYLocation() - 24), 40, 25, Color.WHITE, Color.BLACK, 2);
    message.setLocation(getCalibratedXLocation() + 2, getCalibratedYLocation() - 8);
    message.draw(graphicsHandler);
}
``` 

Lining the rectangle up with the text and the NPC is a total PITA. Those random `+ 2s` and `- 8s` was just me moving the graphics
pixel by pixel until I was happy with the position.

The image file for the walrus is `Walrus.png`.