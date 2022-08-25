---
layout: default
title: Player
nav_order: 6
parent: Game Code Details
has_children: true
permalink: /GameCodeDetails/Player
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Player

## What does the Player class do?

The `Player` class (found in the `Level` package) is responsible for everything to do with the player character, which includes the player's graphics, movement, and interactions with the map (such as collision detection).

In the gif below, everything the cat is doing and how it is interacting with the map is handled by the `Player` class.

![game-screen-1.gif](../../assets/images/playing-level.gif)

The `Player` class extends off the `GameObject` class, so everything in the [game object](./game-object.md) documentation applies to the `Player` class (any instance variables, animation setup, etc.)