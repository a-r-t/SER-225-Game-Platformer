---
layout: default
title: Game Patterns
nav_order: 7
parent: Game Code Details
permalink: /GameCodeDetails/GamePatterns
---

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

# Game Patterns

Here are details on some common design patterns that can be seen around the codebase.

## Key Locker

The game engine provides a `KeyLocker` class to help keep track of locked keys. This doesn't actually lock a key from being pressed
on the keyboard entirely throughout the entire game, it is instead just a "management" tool for a class to determine if it should listen for a key
press or not. The concept of "locking" a key means it should no longer count as a key press until it is "unlocked". It's a lot simpler than it sounds
I promise, it's hard to word this correctly.

Generally, the `KeyLocker` class is useful for forcing the player to press and release a key each time to perform a specific action
rather than being able to hold the key down. For example, the player jumping in game requires that the up key be pressed each time,
and does not allow you to hold it down for continuous jumping.

The pattern for setting this up is really simple. First, a `KeyLocker` class instance is declared as an instance variable so it 
can be used anywhere in the class during an `update` cycle:

```java
protected KeyLocker keyLocker = new KeyLocker();
```

This can be seen in the `Player` class, `MenuScreen` class, and the `CreditsScreen` class.

Then, to "lock" a key, you can use `KeyLocker's` `lockKey` method to add the key to the locker. The below example will "lock" the space key. Remember
that this does NOT "lock" it throughout the entire engine or stop keyboard detection, it is just a management tool for the class
to keep track of which keys it has personally locked.

```java
keyLocker.lockKey(Key.SPACE);
```

Later to unlock a key, the `unlockKey` method can be used:

```java
keyLocker.unlockKey(Key.SPACE);
```

To check if a key is locked, the `isKeyLocked` method can be used:

```java
keyLocker.lockKey(Key.SPACE);

if (keyLocker.isKeyLocked(Key.SPACE)) {

}
```

Put all together, and this is how the `KeyLocker` can be used to force a key be pressed and released rather than detecting while
the key is held down:

```java
public void update() {
    // if space key is released, unlock space key
    if (Keyboard.isKeyUp(Key.SPACE)) {
        keyLocker.unlockKey(Key.SPACE);
    }
    
    // if space is pressed and space is not locked
    if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE) {
        player.jump();
        
        // lock space key
        keyLocker.locKey(Key.SPACE);
    }
}
```

The menu screens uses `KeyLocker` a lot to prevent spamming certain keys, as it can make the menu really hard to navigate if a button can be held down.
This is especially true due to how quickly (in real time) each game loop iteration happens -- without the `KeyLocker` pattern shown above, one key press
can easily count as multiple key presses due to the game loop iterating quicker than a human can lift their finger off the key. Sometimes this is fine,
like when moving a player throughout the level, but sometimes this is not desired which is what this pattern resolves.

## Frame Timers

Across the game, there are many situations where something is being "waited for".
For example, the game may want to wait x number of frames after a certain event has taken place before another event can take place.
To do this, the game often uses a simple int variable to keep track of how many frames have passed.
It usually looks like this:

```java
int frameTimer = 0;

public void update() { 
    if (...something happened) {
        frameTimer = 20;
    }

    if (frameTimer > 0) {
        // tick timer down
        frameTimer--;
    }       
    else {
        // time is up
        ... do something
    }
}
```

An example of this can be see in the `MenuScreen` class.
The code that allows for the menu selection to change when up or down is pressed looks like this:

```java
if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
    keyPressTimer = 14;
    currentMenuItemHovered++;
} else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
    keyPressTimer = 14;
    currentMenuItemHovered--;
} else {
    if (keyPressTimer > 0) {
        keyPressTimer--;
    }
}
```

Due to how fast the game loop runs, one key press can often register as many key presses,
as the game may register that a press is still happening before the user had a chance to take their finger off of a key.
This would be an issue in the menu screen, as pressing an arrow key up or down should only move one selection for a good user experience.
This code solves the issue by implementing a simple "keyPressTimer" that forces 14 frames to pass after each time either the up or down key is pressed before it can check again for another key press.

## Builder Pattern

I use the Builder Pattern around the code base, and all builder classes are defined in the `Builders` package. Below goes
into detail on what the Builder Pattern is and how it makes Java programming life easier.

The Builder Pattern is a code pattern that is used in the Java programming language a lot out of necessity because there are no
named parameters in the language. Java is one of the only modern day programming languages to not have named parameters. Named parameters (also often called
default parameters or optional parameters) are the ability to specify an optional method parameter and give it a default value, 
and allow any class to pass in an argument for that value if needed -- if no value is passed in, that parameter will just use its default value. 
An example of how this looks in the C# programming language is below:

```cs
public void printMessage(String message = "Hello") {
    System.out.println(message);
}
```

It can then be called with an argument for message:

```cs
printMessage(message: "HELLO THERE!");
```

It can also be called with no arguments. When doing this, the `printMessage` method will use the default value for `message`,
which is "Hello".

```cs
printMessage();
```

Named parameters are such a nice language feature...but Java does not have it. This causes a lot of problems, mostly because there are A LOT of situations where
default parameters really help make code more clear, and also prevent having to make 1000 of the same named methods with one parameter difference
each. Lastly, when constructors in Java get really long, the lack of being able to specify the parameter name to the value when calling it
makes it REALLY hard to know which value goes well. For example:

```java
// constructor
public Cat(String name, String nickname, int age, String color, int weight, boolean hasRabiesShot, boolean hasDistemperShot) {
    
}
```

Look what happens when I call this constructor -- it's impossible just by looking at the line to determine which value is what,
especially if you haven't looked at the code in a hot minute:

```java
Cat cat = new Cat("Callie", "Beeboo", 2, "tortoiseshell", 7, true, true);
```

Which argument is age and which one is weight? Name vs nickname? Rabies shot vs distemper shot?
It's hard to tell without looking back at the `Cat` class, and even then lining each argument up is a pain. Imagine
if these were all more complicated data types as well...it really is a huge issue with the Java programming language in my opinion. Add on the fact that there is no ability to create objects or dictionaries "on the fly" to make creating a param grouping easier (which languages like JavaScript and Python have), class constrcutors can quickly become bloated and difficult to read/manage.

The builder pattern attempts to alleviate this issue by creating a class called a "builder" class which will "build" and then
instantiate/initialize a class for you. For my `Cat` class example above, I would create a `CatBuilder` class SEPARATE from the `Cat` class.
The `CatBuilder` class's job is to help setup and instantiate/intialize a `Cat` object, and additionally this class can define its own
default values so not every single parameter needs to be passed in. Lastly, each parameter is set using a specific method call,
meaning it's super obvious to determine which argument goes where.

Below is an example of what a `CatBuilder` class can look like. It is okay if you don't understand this class in its entirety, the code
is a bit strange:

```java
public class CatBuilder {
    private String name;
    private int age;

    // defaulting to "N/A" for these
    private String nickname = "N/A";
    private String color = "N/A";

    // defaulting to -1 for "no weight information known"
    private int weight = -1;

    // defaulting to false if no shot info
    private boolean hasRabiesShot = false;
    private boolean hasDistemperShot = false;

    // required, non optional parameters go here
    public CatBuilder(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // setter for nickname
    public CatBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    // setter for color
    public CatBuilder withColor(String color) {
        this.color = color;
        return this;
    }

    // setter for weight
    public CatBuilder withWeight(int weight) {
        this.weight = weight;
        return this;
    }
    
    // setter for has rabies shot
    public CatBuilder hasRabiesShot(boolean hasShot) {
        this.hasRabiesShot = hasShot;
        return this;
    }

    // setter for has distemper shot
    public CatBuilder hasDistemperShot(boolean hasShot) {
        this.hasDistemperShot = hasShot;
        return this;
    }

    public Frame build() {
        return new Cat(name, nickname, age, color, weight, hasRabiesShot, hasDistemperShot);
    }
}
```

What is going on here? Well, the result is that the actual call to the `Cat` class's
constructor is now abstracted away behind the builder's `build` method. This class as a result of its structure allows us to create a new
`Cat` in a much cleaner way:

```java
Cat cat = new CatBuilder("Callie", 2)
    .withNickname("Beeboo")
    .withColor("tortoiseshell")
    .withWeight(7)
    .hasRabiesShot(true)
    .hasDistemperShot(true)
    .build();
```

With this pattern, it's very obvious which argument goes to which parameter. Additionally, the actual call to the `Cat` constructor is abstracted
away behind the `build` method, so the messy object instantiation is hidden. Finally, default parameters now exist -- if I left off `hasRabiesShot(true)`,
the `CatBuilder` class will just default it to false and construct the `Cat` using that. Even just immediately doing a `build` without
any of the other optional parameters works, as all of them have default values (e.g. `nickname` would be "N/A", `weight` would be -1, etc.).

```java
Cat cat = new CatBuilder("Callie", 2).build();
```

One more thing this pattern does is removes the need for multiple constructors. The `GameObject` class and its subclasses that they have A LOT of constructors due to Java's limitations. Unfortunately it wasn't feasible to make a builder class for every single subclass that extends from `GameObject`, so as a result, constructor hell exists over there.

The builder pattern is used in the code base where the constructor for a class was getting too crazy, which commonly happens
with game code due to how much setup is required for resources like graphics.

A nice side effect of the bulider pattern is it allows an object's instantiation to be delayed, as the `build` method
can be called at any point in time to actually instantiate the object. This technique is used in the `Map` class when loading a map file -- the `Tileset` defines
all tiles using the `MapTileBuilder`, but it doesn't `build` them -- it instead lets the `Map` class build them since they require additional information from the `Map` class to finish their creation.

In the `Builders` package, there are two builders defined: `FrameBuilder` (for creating a `Frame` object instance) and `MapTileBuilder` (for creating a `MapTile` object instance). Both are used heavily in the `CommonTileset` class to construct `MapTile` class instances, and the `FrameBuilder` is used in nearly every game object's `loadAnimations` method (such as the player, NPCs, etc.).

## Utility Enums

The `Direction` enum defined in the `Utils` package creates a data type that has four possible values: `LEFT`, `RIGHT`, `UP`, and `DOWN`. It is used in various places around the program. Since
a 2D space only has those four directions, it's a very nice data type to have available.

## Utility Point

There is a `Point` class in the `Utils` package that will store "point" data (x, y) as floats and also contains several methods for performing "point math" such as adding
and subtracting points. Do not confuse this with Java's built in `Point` class -- this is a custom `Point` class! It's used in several places over Java's standard `Point` class so these additional methods are always available whenever needed.