---
layout: default
title: Menu Screen
nav_order: 2
parent: Screens
grand_parent: Game Details
permalink: /GameDetails/Screens/MenuScreen
---

# Navigation Structure
{: .no_toc }

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Menu Screen

The screen handles the logic and graphics related to the menu that is loaded upon the game starting up.

![menu-screen.png](../../../assets/images/menu-screen.png)

The class file for it is `MenuScreen.java` which can be found in the `Screens` package.

## Functionality

The menu screen's only real job is to allow the player to select between its two options "Play Game" and "Credits".
Upon selecting an option, `MenuScreen` will change `ScreenCoordinator's` game state which will force it to load the appropriate screen based
on the option selected.

```java
// if down key is pressed, add one to current menu item hovered
// "Play Game" option is menu item 0, pressing down will add 1 to the current menu item hovered, 
// which changes it to the "Credits" option
if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
    keyTimer.reset();
    currentMenuItemHovered++;
}
// if up key is pressed, subtract one to current menu item hovered
// "Credits" option is menu item 1, pressing up will sbutract 1 to the current menu item hovered, 
// which changes it to the "Play Game" option
} else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp()) {
    keyTimer.reset();
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

    // if secpmd menu item is selected "CREDITS, set ScreenCoordinator game state to CREDITS
    } else if (menuItemSelected == 1) {
        screenCoordinator.setGameState(GameState.CREDITS);
    }
}
```

Additionally, a `Timer` is used (from the `Utils` package) to specify how long in between key presses of the up and down key are allowed. Since key presses
are so sensitive (every 10ms the `update` cycle is run), pressing the down key or up key just one time would move the selection MANY different times, since
the next `update` cycle would come so fast it would still think the key was held down as the player did not have any time to release the key yet. To prevent this,
the `keyTimer` variable is used to force 200ms to go by before either the up or down key can be detected again after it has already been pressed, which feels a lot more
natural for the common key press. The milliseconds for the `keyTimer` to wait is set in the screen's `initialize` method. The above code already showed how the `keyTimer` integrates
with the checks for the up or down keys being pressed, but here is a snippet of it again just to illustrate its functionality:

```java
// if down key is pressed 
// and 200 milliseconds have passed by since the last down key press
if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
    // reset keyTimer to wait out its set wait time,
    // which in this class is set to 200ms
    keyTimer.reset(); 

    // move currentMenuItemHovered to the next option
    currentMenuItemHovered++;
}
```

## Graphics

The background of the menu screen uses a `Map` specifically made for it (`TitleScreenMap.java` in the `Maps` package), which is the same type of `Map` class which
is used when actually playing the platformer game. While any image could have been used, I thought it'd be more fun to use a map as the background.

The little blue square moves based on the value of `currentMenuItemHovered` (which changes value when up or down is pressed) to be in front
of the currently hovered item's text. The text that is hovered over will also change color to gold while the one not hovered will change color to black.

The menu item text ("Play Game" and "Credits") use the `SpriteFont` class and are defined in the `initialize` method (variables `playGame` and `credits`.

## How to add a new menu item

Honestly, this class was not made the best; it was neglected because the vast majority of my development time went into the actual platformer game. The menu works fine,
but it is not as modular as it could be. Regardless, adding a new menu item is easy, but it will be a bit tedious to space everything out correctly, add
the necessary logic checks for when that menu item is selected, and getting the little blue square graphic to move to the correct spot on hover.