---
layout: default
title: FAQs
nav_order: 9
permalink: /EnhancementIdeas
search_exclude: true
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Frequently Asked Questions

Below are some answers to some frequently asked questions.

## Why is the game slow/laggy/stuttering on my computer?

There are a number of reasons the game may not be running well on a specific computer.
Unfortunately, Java Swing is not an actual game engine and was not designed to make games.
It lacks performance capabilities that traditional game engines have.
As a result, a good CPU is required in order to run this game well, especially as more is added to the game.
There are other factors that can affect performance as well, such as RAM, as well as other background processes that are active while the game is running.

Something that often helps is changing the game loop process from power saver mode to max performance mode.
This can be set in the `Config` class.
Max performance mode is a CPU hog, but the game will run to its max potential, which is often enough even on low-end computers (from my testing).

## Why didn't you use an actual game engine to make this?

It keeps the game light and simple, and allows for students that naturally progressed through the previous Quinnipiac Computer Science courses before SER225 to have an easier time jumping right into the project.
It was designed to be a learning tool, NOT a commercial product.
Plus, it was fun to code!
It also gives insight into what other game engines are doing "behind the scenes" for you.

## Where can I see the projects from other students that took Quinnipiac's SER225 course?

From the Fall 2022 semester onward, all student SER225 projects are posted on the class project website [here](https://a-r-t.github.io/SER225-Project-Website/).
The website allows for you to browse by semester/team, read about and watch a video demo of a team's project, and even download the project for yourself!

## Am I allowed to fork this project and do my own thing with it, even if I am not in the SER225 course?

Sure! It's open-source, and I welcome anyone to use this project as a "base" for their own project.

## Can I contribute back to this base project?

This base project is something I keep very locked down, as it is purely designed to be a teaching tool.
I intentionally did things a certain way to suit the needs of my students. 

For example, in some areas of the game, I use coding techniques and patterns that may not be the most "optimal" or "stylish" in order to match the level that my students are at when they take SER225.
There are also some bugs I intentionally left in, or features I intentionally did not include, both of which are for challenging my students to take appropriate action when/if they come across them.

If there is something you'd like to add with all of this in mind that you feel would be purely beneficial for students taking on this project to have, please reach out to me to chat before working on it to get my thoughts/approval.
