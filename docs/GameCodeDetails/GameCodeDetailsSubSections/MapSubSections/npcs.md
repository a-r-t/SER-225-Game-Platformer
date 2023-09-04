---
layout: default
title: NPCs
nav_order: 8
parent: Map
grand_parent: Game Code Details
permalink: /GameCodeDetails/Map/NPCs
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# NPCs

## What is an NPC?

An NPC (**n**on-**p**layer **c**haracter) (represented by the `NPC` class in the `Level` package) is a `MapEntity` subclass. 
An NPC can be given its own graphics/animations, as well as its own `update` cycle which defines its behavior.

NPCs in a game tend to act as a neutral character, meaning they usually do not harm the player.
They are typically designed to supplement the traversal through a level, as well as improve overall game immersion.

The `NPC` class adds a `checkTalkedTo` method that any `NPC` subclass can override, which handles the code that is run if an NPC is talked to by the player. 
The player character has to be within the NPC's range and then press space to talk to the NPC, and the NPC's `isInteractable` attribute must be set to `true`. 
When an NPC is talked to, they can display a message until their `talkedToTime`, runs out, at which point the message disappears and they can be re-talked to again. 
An NPC can be made to do any desired behavior, and is not limited to only this.

## NPC Subclass

In the `NPCs` package, there is currently only one subclass of the `NPC` class -- `Walrus`.
This `Walrus` NPC can be seen in the `TestMap` map.

## Adding a new NPC to the game

This is simple -- create a new class in the `NPCs` package, subclass the `NPC` class, and then just implement
desired logic from there. 
I recommend copying an existing npc class as a "template" of sorts to help set up and design the npc.

## Adding an NPC to a map

In a map subclass's `loadNPCs` method, NPCs can be defined and added to the map's NPC list. For example, in `TestMap`,
a `Walrus` class instance is created added to the NPC list:

```java
@Override
public ArrayList<NPC> loadNPCs() {
    ArrayList<NPC> npcs = new ArrayList<>();

    Walrus walrus = new Walrus(getMapTile(30, 10).getLocation().subtractY(13));
    npcs.add(walrus);

    return npcs;
}
```

## NPCs currently in game

### Walrus

![walrus.png](../../../assets/images/walrus.png)

This NPC is defined by the `Walrus` class. 
In addition being the best made piece of art you have ever laid your eyes on, the walrus is able to be talked to when the player is in range of its "bounds" and presses space. 
Upon doing so, the `Walrus` class will draw a box with text inside to the screen:

![walrus-talking.png](../../../assets/images/walrus-talking.png)

The `Walrus` class puts the message "Hello!" into the textbox and positions it appropriately in order to display the textbox with the message inside of it when talked to.

The image file for the walrus is `Walrus.png`.

## Textbox

All NPCs come with a `Textbox` attribute, which can be optionally used to display a textbox when the NPC is talked to.

The `Walrus` NPC sets up its textbox in its constructor like so:

```java
talkedToTime = 200;
textbox.setText("Hello!");
textboxOffsetX = -4;
textboxOffsetY = -34;
```

The `talkedToTime` is how many frames the textbox will stay up for.
Setting `talkedToTime` to a negative number will cause it to last indefinitely.

The `setText` method adds text to be displayed in the textbox.
Text can be displayed on separate lines by using new line characters `\n`.

The `textboxOffsetX` and `textboxOffsetY` textbox properties tell the textbox where to position itself relative to the NPC's location.
The above code is telling the textbox to position itself 4 pixels to the left and 34 pixels upwards from the Walrus's location.
This positioning often takes some testing and tweaking to get it to be in the right spot.

The textbox has a ton of additional customization options:

`setTextColor` -- sets color of text
`setFontSize` -- sets font size for text, such as "size 12"
`setFontStyle` -- sets font style for text, such as `Font.PLAIN` for no style, `Font.BOLD` for bolded font, etc.,
`setFontName` -- sets font family for text, such as `"Times New Roman`, `Arial`, etc.
`setFillColor` -- sets color of textbox
`setBorderColor` -- sets color of textbox border
`setBorderThickness` -- sets how thick textbox border is
`setVPadding` -- sets how much whitespace there is between the box and the text in the box vertically
`setHPadding` -- sets how much whitespace there is between the box and the text in the box horizontally
`gap` -- if text has new line characters in it (`\n`), this sets how much space should be between each line; has no effect if text has no new line characters in it.
