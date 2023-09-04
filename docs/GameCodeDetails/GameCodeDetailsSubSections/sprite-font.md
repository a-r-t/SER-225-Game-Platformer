---
layout: default
title: Sprite Font
nav_order: 3
parent: Game Code Details
permalink: /GameCodeDetails/SpriteFont
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Sprite Font

## What is a sprite font?

A sprite font is what it sounds like -- a "sprite" (graphic) of a font (like Times New Roman, Arial, etc). 
The `SpriteFont` class in the `SpriteFont` package allows for easily creating and customizing a sprite font that can be displayed on screen. 
An example of sprite fonts in action are on the menu screen -- both the "PLAY GAME" and "CREDITS" menu options are separate `SpriteFont` class instances defined in the `MenuScreen` class:

![menu-screen.png](../../assets/images/menu-screen.png)

## SpriteFont class

The `SpriteFont` class provides `draw` logic that will properly draw any sprite font to the screen.
The following values must be provided to initialize a `SpriteFont` instance:

- **text** -- the text of the sprite font
- **x** -- the x location on screen of the sprite font
- **y** -- the y location on screen of the sprite font
- **fontName** -- the name of the font to use, such as "Times New Roman", "Arial", etc. Any font on the computer running this game will work here.
- **fontSize** -- size of font (the larger the number, the larger the sprite font text will be)
- **color** -- color of the text

Additionally, there are a couple other values that can be set after initialization with the appropriate setter methods:

- **setFontStyle** -- using Java's built in `Font` enum, you can set the sprite font to have a style such as bold or italic. For example, the font can be set to bold like this: `setFontStyle(Font.BOLD);` 
- **setOutlineColor** -- when this is set, the sprite font will be drawn with an outline around it with the chosen color
- **setOutlineThickness** -- the thickness of the outline drawn around the sprite font (will only take effect if outline color has also been set)

The `SpriteFont` class is used all across the application from the menus to various game objects.

Below is the code for creating the "PLAY GAME" sprite font for the `MenuScreen`:

```java
SpriteFont playGame = new SpriteFont("PLAY GAME", 200, 150, "Comic Sans", 30, new Color(49, 207, 240));
playGame.setOutlineColor(Color.black);
playGame.setOutlineThickness(3);
```

Pretty easy to use! Don't forget to add it to the `draw` cycle:

```java
playGame.draw();
```

## Using sprite fonts in game objects

Using a `SpriteFont` inside a `GameObject` to have the sprite font text show on the map works just like normal. 
To do this, simply set up the sprite font and add it to the game object's `draw` cycle. 
One thing to look out for is that a `SpriteFont` will not automatically calibrate its draw location based on the map's camera unlike a `GameObject` does, since `GameObjects` have extra logic added for that. 
To resolve this issue, you can update the `SpriteFont's` location relative to the `GameObject's` calibrated location. 
