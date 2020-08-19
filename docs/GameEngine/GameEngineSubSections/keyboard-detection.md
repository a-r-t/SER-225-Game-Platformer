---
layout: default
title: Keyboard Detection
parent: Game Engine
nav_order: 3
permalink: /GameEngine/KeyboardDetection
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Keyboard Detection

## Keyboard Input

There is a `Keyboard` class in the engine that handles detecting if a key is currently pressed (or not pressed)
on the keyboard at any point while the game is running. The class is a "static" class (meaning it can't be instantiated) and exists
solely to poll the global keyboard state, which is generally used in `update` methods to be used to check if a certain key is currently being pressed or not. 
Every class has the ability to detect keyboard input and multiple classes can detect keyboard input at the same time and react to it as desired.

## Key Detection Methods
The `Keyboard` class supplies the following methods:
- **isKeyDown** -- check if a key is currently being pressed
- **isKeyUp** -- check if a key is not currently being pressed
- **areKeysDown** -- check if multiple keys are being pressed at the same time
- **areKeysUp** -- check if multiple keys are not being pressed at the same time

Since these methods are all static methods, they can be referenced directly from the `Keyboard` type (examples for this below).

Using these methods are really easy. There is a `Key` enum in the engine that has predefined keys set up to be checked for.
These can be used as arguments for the `Keyboard` class's methods.

```java
public void update() {
    // check if LEFT arrow key pressed
    if (Keyboard.isKeyDown(Key.LEFT)) {
        
    }

    // check if A key is pressed
    if (Keyboard.isKeyDown(Key.A)) {
        
    }
    
    // if 1 key is not pressed
    if (Keyboard.isKeyUp(Key.ONE)) {

    }

    // if both shift and space are pressed at the same time
    if (Keyboard.areKeysDown(new Key[] { Key.SHIFT, Key.SPACE })) {

    }      
}
```

## Supported Keys

<details>
  <summary>The following keys are supported:</summary>
  <ul>
    <li>UP</li>
    <li>DOWN</li>
    <li>RIGHT</li>
    <li>LEFT</li>
    <li>ENTER</li>
    <li>SHIFT</li>
    <li>A</li>
    <li>B</li>
    <li>C</li>
    <li>D</li>
    <li>E</li>
    <li>F</li>
    <li>G</li>
    <li>H</li>
    <li>I</li>
    <li>J</li>
    <li>K</li>
    <li>L</li>
    <li>M</li>
    <li>N</li>
    <li>O</li>
    <li>P</li>
    <li>Q</li>
    <li>R</li>
    <li>S</li>
    <li>T</li>
    <li>U</li>
    <li>V</li>
    <li>W</li>
    <li>X</li>
    <li>Y</li>
    <li>Z</li>
    <li>ONE</li>
    <li>TWO</li>
    <li>THREE</li>
    <li>FOUR</li>
    <li>FIVE</li>
    <li>SIX</li>
    <li>SEVEN</li>
    <li>EIGHT</li>
    <li>NINE</li>
    <li>ZERO</li>
    <li>SPACE</li>
    <li>ESC</li>
  </ul>
</details>

## Adding More Supported Keys
If additional keys are needed to be supported for key detection, an entry for it must be added to the `Key` enum (`Key.java`),
and then an entry also needs to be added to the `Keyboard` class's `buildKeyMap` `EnumMap` method mapping the enum entry with its
key code. You can look up each key's key code in the StackOverflow answer [here](https://stackoverflow.com/a/31637206). Note:
key codes for Java differ slightly from those in JavaScript, which most other websites will give you when googling for key codes -- please
use the provided link for correct key codes for Java.