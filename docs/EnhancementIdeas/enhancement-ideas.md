---
layout: default
title: Enhancement Ideas
nav_order: 8
permalink: /EnhancementIdeas
search_exclude: true
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Enhancement Ideas

Below is a list of potential enhancement ideas that could be made to this game.

## Multiple levels

Right now this game only contains one short level. I think the game having multiple levels that the player
can progress through one after the other would make this game a lot more interesting and fun.

## Ways to kill enemies

Touching enemies and immediately dying is generally not a recipe for a fun game. There are many different ways platformers
go about allowing the player to kill enemies, but the most common are either jumping on enemies or being able to shoot enemies with
some sort of projectile.

## Lives/Health

Name a single platformer game where you get a game over after getting touched by an enemy one time...yeah none of them do this
because it's not very fun. Most games have a concept of "lives" where you can try a level again before a game over occurs, and they
are booted back to the main menu. Some games implement a concept of "health", where the player can be hurt by enemies multiple times
in a level before dying.

## More ways to die

Enemies work great to hinder the player's progression through a level, but most games have other means of doing so like falling
in a bottomless pit, landing on spikes, etc. Would definitely spice things up a bit.

## Game music/sounds

There is no music/sounds in this game! Music and sound support could be added to specific classes, or even
be added to the actual game engine itself so every class in the game can use them in a convenient manner.

## Slopes

Just putting this here to warn anyone that is interested in implementing slope map tile types into the game
that it is HARD and I don't recommend doing it without a solid understanding of how the game's map tile system works. 
There are so many ways to implement them with their own different pros and cons, and even games known for their
slopes like 2D Sonic the Hedgehog games have some bugs (you just don't notice them because in those games you move very fast).
[Here](http://higherorderfun.com/blog/2012/05/20/the-guide-to-implementing-2d-platformers/) is an interesting article about implementing slopes in 2D platformer games
that will likely steer you away from wanting to implement this feature...but it would be insanely cool.

## Improved jump mechanics

In nearly all platformer games, the player will jump at different heights based on how long the jump button is held.
I think this would make the controls feel a lot smoother, as the player jumping right now feels really high and it's impossible
to shorten the jump. Additionally, I think the jumping physics can definitely be updated/changed to make the player feel more in control and allow for more precise platforming. 
There are like 100 different ways to implement jump code all with different pros and cons, so it's definitely worth playing around with and seeing if you can make the jumping feel better.

# More Set Pieces

The game could use more variety, from map tile graphics to enemy types to level design.
It would make the game a lot more interesting to look at and traverse through.