---
layout: default
title: Keyboard Detection
parent: Game Engine
grand_parent: Code Documentation
nav_order: 2
permalink: /CodeDocumentation/GameEngine/KeyboardDetection
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

There is a `Keyboard` class constructed in the engine that handles detecting if a key is currently pressed (or not pressed)
on the keyboard at any point while the game is running. An instance of the class is created on game start up and it is passed around
to nearly every class's `update` methods to be used to check if a certain key is currently being pressed or not. Every class has the ability
to detect keyboard input and multiple classes can detect keyboard input at the same time and react to it as desired.

## Key Detection Methods
The `Keyboard` class supplies the following methods:
- **isKeyDown** -- check if a key is currently being pressed
- **isKeyUp** -- check if a key is not currently being pressed
- **areKeysDown** -- check if multiple keys are being pressed at the same time
- **areKeysUp** -- check if multiple keys are not being pressed at the same time

Using these methods are really easy. There is a `Key` enum in the engine that has predefined keys set up to be checked for.
These can be used as arguments for the `Keyboard` class's methods.

```java
public void update(Keyboard keyboard) {}
    if (keyboard.isKeyDown(Key.))
}
```

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