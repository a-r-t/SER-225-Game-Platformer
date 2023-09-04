---
layout: default
title: Menu Screen
nav_order: 2
parent: Screens
grand_parent: Game Code Details
permalink: /GameCodeDetails/Screens/MenuScreen
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Menu Screen

The menu screen handles the logic and graphics related to the main menu that is loaded upon the game starting up.

![menu-screen.png](../../../assets/images/menu-screen.png)

The class file for it is `MenuScreen.java`, which can be found in the `Screens` package.

## Functionality

The menu screen's only real job is to allow the player to select between its two options: "Play Game" and "Credits".
Upon selecting an option, `MenuScreen` will change `ScreenCoordinator's` game state which will force it to load the appropriate screen based on the option selected.

```java
// if down key is pressed, add one to current menu item hovered
// "Play Game" option is menu item 0, pressing down will add 1 to the current menu item hovered, 
// which changes it to the "Credits" option
if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
    keyPressTimer = 14;
    currentMenuItemHovered++;
} 
// if up key is pressed, subtract one to current menu item hovered
// "Credits" option is menu item 1, pressing up will sbutract 1 to the current menu item hovered, 
// which changes it to the "Play Game" option
else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
    keyPressTimer = 14;
    currentMenuItemHovered--;
}
```

The `MenuScreen` class's update cycle mainly checks if the user has pressed the down or up keys and if so will move the little blue square from one
option to the other and make it clear which option is currently being "hovered" over. Pressing the space bar will select the option and is the trigger
that leads to `ScreenCoordinator's` game state being changed.

```java
// if space is pressed, item is selected
if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
    menuItemSelected = currentMenuItemHovered;
    
    // if first menu item is selected "PLAY GAME", set ScreenCoordinator game state to LEVEL
    if (menuItemSelected == 0) {
        screenCoordinator.setGameState(GameState.LEVEL);
    }
    // if second menu item is selected "CREDITS, set ScreenCoordinator game state to CREDITS
    else if (menuItemSelected == 1) {
        screenCoordinator.setGameState(GameState.CREDITS);
    }
}
```

Additionally, there is code in place force a certain amount of time to pass between pressing up or down to move the menu selection..
Key presses are very sensitive -- if the game is running at 60 FPS, it is running the key press detection 60 times per second.
As a result, pressing the down key or up key just one time would move the selection MANY times, since the next `update` cycle would come so fast it would still think the key was held down as the user did not have any time to release the key yet. 
To prevent this, the `keyPressTimer` variable is used to force 14 frames to go by before either the up or down key can be detected again after it has already been pressed, which makes the menu navigation a lot more user friendly.

```java
// if down or up is pressed, and key press timer is up
// change menu item "hovered" over
if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
    // change menu selection, reset key press timer to wait 14 frames
    keyPressTimer = 14;
    currentMenuItemHovered++;
} else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
    // change menu selection, reset key press timer to wait 14 frames
    keyPressTimer = 14;
    currentMenuItemHovered--;
} else {
    // if key press timer is not up yet, decrement it
    if (keyPressTimer > 0) {
        keyPressTimer--;
    }
}
```

## Graphics

The background of the menu screen uses a `Map` specifically made for it (`TitleScreenMap.java` in the `Maps` package), which is the same type of `Map` class which is used when actually playing the main game. 
While any image could have been used, I thought it'd be more fun to use a map as the background.

The little blue square moves based on the value of `currentMenuItemHovered` (which changes value when up or down is pressed) to be in front of the currently hovered item's text. 
The text that is hovered over will also change color to gold while the one not hovered will change color to blue.

The menu item labels ("Play Game" and "Credits") use the `SpriteFont` class and are defined in the `initialize` method (variables `playGame` and `credits`).

## How to add a new menu item

Adding a new menu item is easy, but it's a bit tedious to space everything out correctly, add the necessary logic checks for when that menu item is selected, and getting the little blue square graphic to move to the correct spot on hover.