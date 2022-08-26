---
layout: default
title: Map Editor
nav_order: 6
permalink: /MapEditor
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Map Editor

## What is a map editor?

In this project, there is another "sub" project called the Map Editor (all located in the `MapEditor` package) that will load up
a GUI for designing game [tile maps](/GameDetails/Map/MapTilesAndTilesets) with a visual display instead of having to do so through
code and not being able to see the how things look as you go. Nearly every video game uses a map editor (level editor, level maker, etc.)
that game developers use to design the game's maps. Games like [Mario Maker](https://www.youtube.com/watch?v=tZ5g5n-6OFg) are literally
just one big map editor.

## This game's map editor

The inspiration behind the design of the map editor for this game engine came from the old CD-ROM game [Speedy Eggbert](https://en.wikipedia.org/wiki/Speedy_Eggbert),
which was a staple of my childhood. It was a standard platformer game (with some VERY odd design choices), but the level editor included with it
was the same one used by the game developers to actually make the game -- it was so fun as a child to be able to create my own levels
for a game I really liked. If you're curious, [here](https://www.youtube.com/watch?v=haRt2Z8-7-A) is a video of the Speedy Eggbert map editor being used.

While this game's map editor is limited compared to commercial map editors for other video games you may have seen/used, it is still very useful for designing
a map and being able to see what you are doing as you go. It is current only usable for placing map tiles -- other entities like enemies must be created and added through code.

To run this game's map editor, the `main` method in the `MapEditor` class must be run. Remember this is a separate program
from the actual game code, although both make use of the same classes in this project. You can have both the map editor and the game
running at the same time.

## Map Editor features

This Map Editor allows you to do the following:

- Add a new Map to the game
- Change the individual tiles of a map (which maintain their tile logic defined in code)
- Change the dimensions of a map (how many tiles the map contains width and height wise)

![map-editor.png](../assets/images/map-editor.PNG)

## How to use the map editor

It should be pretty self-explanatory how to use the map editor after playing around with it for a few minutes. 

The left-hand sidebar contain the tiles that can be used for the map. Each `Map` subclass (such as `TestMap`) defines
its own `Tileset` (such as `CommonTileset`) which is where these tile options come from. You can read more about the `Map` class [here](/GameDetails/Map) and about map tiles and tilesets [here](/GameDetails/Map/MapTilesAndTilesets). 

Any tile on the left-hand sidebar can be clicked to "select" it. The tile is highlighted with a yellow box. Afterwards, going to the map and clicking will
replace whichever tile was hovered by the mouse with the selected tile. Below is a gif of this process.

![map-editor-usage.gif](../assets/images/map-editor-usage.gif)

The map that is currently being edited can be changed using the dropdown on the top left. To save any changes to a map, hit the "Save Map"
button. Do not forget to do this -- switching maps or existing the program without saving the map changes will cause any changes to be discarded.

The "Set Map Dimensions" button will allow you to change the size of the map (number of tiles width and height). It'll also
allow you to choose which side of the map to apply changes to when growing/shrinking a map's dimensions -- for example, if increasing
the width of the map, more tile space can be added either to the right side or the left of the map.

Something to keep in mind is after a map has been created using that `Tileset`, you should not rearrange the order the tiles are defined and added to the list in a `Tileset's` `defineTiles` method. Doing so will break existing maps. You should ALWAYS just append tiles on to the end of the `defineTiles` method even though it may feel disorganized in order to preserve the tile map's integrity.

## Adding a new map to the map editor

Adding a new map to the map editor (and essentially creating a new map entirely) is a relatively easy process.

First start by creating a new class in the `Maps` package that extends the `Map` class. You can look at `TestMap` as a reference for how to do this.
Then for now, all that's needed is a constructor to define the map file name, tileset, and player starting position. An example is below:

```java
public class MyMap extends Map {
    public MyMap() {
        super("my_map.txt", new CommonTileset());
        this.playerStartPosition = new Point(1, 11);
    }
}
```

In the above example, the map's file will be named "my_map.txt", and the `CommonTileset` class will be used as the map's tileset.
The map file "my_map.txt" has not been created yet, but the Map Editor will do so for you momentarily. 
Make sure that two maps do not share the same map file name, or one will overwrite the other!

Now, in the `MapEditor` package, go to the `EditorMaps.java` file. In here, you will need to add your new map to some of the class's methods.

In the `getMapNames` method, add an entry to the list for the name you would want the map to be recognized by -- for this example, I will be using "MyMap".

{% raw %}
```java
public static ArrayList<String> getMapNames() {
    return new ArrayList<String>() {{
        add("TestMap");
        add("TitleScreen");
        add("MyMap");
    }};
}
```
{% endraw %}

Then, in the `getMapByName` method, add another switch case for your new map name and return an instance of your new Map class.
For this example, I am adding a switch case for "MyMap" (that is the name it was given in the `getMapNames` method) and it is returning `new MyMap()`:

```java
public static Map getMapByName(String mapName) {
    switch(mapName) {
        case "TestMap":
            return new TestMap();
        case "TitleScreen":
            return new TitleScreenMap();
        case "MyMap":
            return new MyMap();
        default:
            throw new RuntimeException("Unrecognized map name");
    }
}
```

The last thing you have to do is open the Map Editor and in the drop down select your new map. The Map Editor will create a new
blank map file for it upon saving the map. From there, you can change the map dimensions as desired -- it will start at a width and height of (0,0), so
you have to change the dimensions to actually add tiles. From here, you can start designing the map!

Map files can be found in the project's `MapFiles` folder. This folder location can be changed in the [game config](/GameEngine/Config) if desired.

## Options Menu

At the top left of the Map Editor window, there is a menu strip with an "Options" menu item. Clicking it will reveal three options - "Show NPCs", "Show Enhanced Map Tiles", and "Show Enemies". Toggling an option on will show the desired entities in the map editor. The entities cannot be moved or interacted with in the editor, but it does at least show you where these entities are placed on the map.

## How does the map editor work?

I used Java Swing components for the GUI and control functionality (such as buttons, drop down menus, etc.).
The problem with creating complex GUIs purely through code is that A LOT of code and logic is required and it is very easy for it
to get unruly, as many shortcuts/hacky solutions have to be used for all components take in the correct input and communicate with one-another.
As you can imagine, there is quite a bit of code for the map editor broken up between several different classes, and they aren't
the easiest code files to digest, and as a working unit it isn't immediately obvious which classes work with others -- that will happen
when using any GUI library, and especially Java Swing due to how old it is. 

I will say that this was NOT easy to code up and get working. It's also a bit fragile as all map editors are, 
since it has major dependencies on game logic classes that it has no control over such as the `Map`, `MapTile`, and `Tileset` classes,
and if any of those class change, it can easily break the map editor. Such is life.

While I do not plan on going over the entirety of the map editor code here because it would take me forever to type up and at the end of the day
I would just be re-describing the entire Java Swing Library, I will instead leave it be and give some generic guidance if you are looking
to develop it further.

The `EditorWindow` class is the JFrame. It sets the `EditorMainPanel` as its content pane.

The `EditorMainPanel` is a JFrame that holds two other JFrame components: the `EditorControlPanel` (map name selection drop down menu and
save map button are there, etc.) and the `MapBuilder`, which is the right side where the map is displayed
and tiles in it can be replaced.

The `EditorControlPanel` utilizes the `TilePicker`, which is ANOTHER JPanel that actually display the tile graphics that can be selected and is where
that selection logic takes place. The `SelectedTileIndexHolder` class is used to store the currently selected tile.

The `MapBuilder` JPanel defines the scroll pane and map info labels (like "width" and "height" at the bottom of the map display).
Then inside the scroll pane, it places the `TileBuilder` JPanel. The `TileBuilder` class is where tiles can be replaced
by the selected tile from the `TilePicker` based on the value in `SelectedTileIndexHolder`.

Both `TilePicker` and `TileBuilder` have listeners for mouse click and hover inputs.

The `ChangeMapSizeWindow` class is specifically for the window that pops up after clicking the "Change Map Size" button
in the `EditorControlPanel`. This class lets you change the size of the map.

The "Save Map" button will overwrite a map's assigned map file with what it looks like in the editor.

I apologize deeply that there are no comments for the map editor classes, I will go back sometime and fix that. Maybe. I'm not going to do it.

