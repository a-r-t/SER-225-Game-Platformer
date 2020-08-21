---
layout: default
title: Game Patterns
nav_order: 7
parent: Game Details
permalink: /GameDetails/GamePatterns
---

# Navigation Structure
{: .no_toc }

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

The menu screens use `KeyLocker` a lot to prevent spamming certain keys, as it can make the menu really hard to navigate if a button can be held down.
This is especially true due to how quickly (in real time) each game loop iteration happens -- without the `KeyLocker` pattern shown above, one key press
can easily count as multiple key presses due to the game loop iterating quicker than a human can lift their finger off the key. Sometimes this is fine,
like when moving a player throughout the level, but sometimes this is not desired which is what this pattern resolves.

## Stopwatch

The `Stopwatch` class, located in the `Utils` folder, will act as a stopwatch in game to allow for timed events. This is used
in several different classes, and is also used for the game's overall animation process to move from frame to frame based on a set delay time.

Like the `KeyLocker`, `Stopwatch` classes tend to be instantiated as an instance variable, and many of them can be used in the same class
if there are multiple processes that need to be timed.

```java
Stopwatch timer = new Stopwatch();
```

Upon initializing a `Stopwatch` class, the method `setWaitTime` is where the number of milliseconds to time is specified. 1000 milliseconds in a second
in case you forgot.

```java
timer.setWaitTime(10000); // wait for 10 seconds
```

The `isTimeUp` method will return true or false based on if the amount of seconds since calling `setWaitTime` have passed by.
```java
if (timer.isTimeUp()) {
    
}
```

The `reset` method will reset the timer:

```java
timer.reset();
```

Sometimes, the `Stopwatch` class will be used in a way similar to `KeyLocker`, but instead of forcing a key to be pressed and released each time, it will
"delay" how often key detection will take place while a key is held down. Below is an example of that:

```java

// some constructor somewhere
timer.setWaitTime(1000);

public void update() {
    if (Keyboard.isKeyDown(Key.SPACE) && timer.isTimeUp()) {
        timer.reset();
        // other action logic...
    }
}
```

The above forces 1 second to pass by each time for the space key detection to cause an action to occur even if it is held down.

## Builder Pattern

I use the Builder Pattern around the code base, and all builder classes are defined in the `Builders` package. Below goes
into detail on what the Builder Pattern is and how it makes Java programming life easier.

The Builder Pattern is a code pattern that is used in the Java programming language a lot out of necessity because there are no
default parameters in the language. Java is one of the only modern day programming languages to not have this feature. Default parameters (also often called
named parameters or optional parameters) are the ability to specify a method parameter and give it a default value, 
and allow any class to optionally pass in a value for that value -- if no value is passed in, that parameter will just use its default value. 
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

Such a nice powerful tool but Java does not have it. This causes a lot of problems, mostly because there are A LOT of situations where
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
if these were all more complicated data types as well...it really is a huge issue with this programming language.

The builder pattern attempts to alleviate this issue by creating a class called a "builder" class which will "build" and then
instantiate/initialize a class for you. For my `Cat` class example, I would create a `CatBuilder` class SEPARATE from the `Cat` class.
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

Whew, what a weird looking class. What is going on here? Well, the result is that the actual call to the `Cat` class's
constructor is now abstracted away behind this `build` method. This class as a result of its structure allows us to create a new
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
away behind the `build` method, so the messy call is hidden. Finally, default parameters now exist -- if I left off `hasRabiesShot(true)`,
the `CatBuilder` class will just default it to false and construct the `Cat` using that. Even just immediately doing a `build` without
any of the other optional parameters works, as all those values have default values (e.g. `nickname` would be "N/A", `weight` would be -1, etc.).

```java
Cat cat = new CatBuilder("Callie", 2).build();
```

One more thing this pattern does is removes the need for multiple constructors, which as you may have seen from the `GameObject` class
and its subclasses that they have A LOT of constructors due to Java having no default parameters. Unfortunately it wasn't feasible to make
a builder class for every single subclass that extends from `GameObject` so constructor hell exists over there.

The builder pattern is used in the code base where the constructor for a class was getting too crazy, which commonly happens
with game code due to how much setup is required for resources like graphics. It really isn't scary once you know what it's doing.

A nice side effect of the bulider pattern also is it allows an object's instantiation to be delayed, as the `build` method
can be called whenever to actually instantiate the object. This technique is used in the `Map` class when loading a map file -- the `Tileset` defines
all tiles using the `MapTileBuilder`, but it doesn't `build` them and instead lets the `Map` class bulid them since they require information from the `Map` class
to finish their creation.

In hte `Builders` package, there are four builders defined, but the only two that you will really see come up are
`FrameBuilder` (for creating a `Frame` object instance) and `MapTileBuilder` (for creating a `MapTile` object instance).

## Observer Pattern

I use the Observer Pattern one time to enable the `Player` class to trigger events in the `PlayLevelClass` when the `Player` has
either completed a level or died, which then lets the `PlayerLevelClass` react to those events.

I'm not going to go into much detail on this pattern since it's only used one time and it's not all that complex. Basically,
an interface named `PlayerListener` is defined in the `Scene` class, and any class that implements this interface must implement the methods
`onLevelCompleted` and `onDeath`. A class would implement this interface to "listen" to events from the `Player` class.

Then in the `Player` class, an `ArrayList` instance variable of type `PlayerListener` is defined (the variable's name is `listeners`). There is also an
`addListener` method which will add a `PlayerListener` to the player's list of listeners. The `PlayLevelScreen` implements `PlayerListener` and
then passes itself in to the `Player` using the `addListener` method:

```java
// create new player
this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
this.player.setMap(map);

// PlayLevelScreen adds itself to the Player class as a "listener"
this.player.addListener(this);
```

That's all the setup that's needed. Now whenever the player wants, it can "trigger" events for its listeners by calling their
`onLevelCompleted` or `onDeath` method where appropriate. For example, the `Player` does this in its `updateLevelCompleted` method once it
has finished playing out the "win" animation to let the `PlayLevelScreen` know that a level has been completed:

```java
for (PlayerListener listener : listeners) {
    listener.onLevelCompleted();
}
```

And then in the `PlayLevelScreen`, which HAS to have an `onLevelCompleted` method because it implements `PlayerListner`, the method
looks like this:

```java
@Override
public void onLevelCompleted() {
    playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
}
```

Essentially it just updates its own level state here and then its `update` cycle logic will see that change and perform the
desired actions (in this case, will load the level win message screen).

Now, while I could have just passed in the `PlayLevelScreen` instance to the `Player` class, I didn't want to do that because it really
didn't belong in the `Player` class. The `PlayLevelScreen` has nothing to do with the `Player` class, and if I ever wanted to use the `Player` class
somewhere else/in another program, that dependency on the `PlayLevelScreen` would not be welcomed. Additionally, if the `Player` were
to be used on a different screen (like maybe the menu screen, like how some games show the character walking around), the `PlayLevelScreen`
would be unavailable to be passed in. Instead, with this pattern, ANY class can be a `PlayerListener` and have events for `onLevelCompleted`
and `onPlayerDeath`, and the `Player` is free to trigger them by just calling those methods on its `listeners` when its ready to.

Like I said, this pattern is only used in that one place, so you won't see it or have to interact with it much. The Observer Pattern
is actually really easy once you take a look at it (assuming you understand how interfaces work) and is very commonly used elsewhere in programming,
notably Android development relies heavily on it.

[This video](https://www.youtube.com/watch?v=WRkw0l72BL4) provides a good overview of the Observer Pattern and why it's important
using real-world examples.

## Utility Enums

There are a two enums defined in the `Utils` package that several classes use: `Direction` and `AirGroundState`.

`Direction` has four possible values: `LEFT`, `RIGHT`, `UP`, and `DOWN`. It is used in various places around the program. Since
a 2D space only has those four directions, it's a very nice data type to have available.

The `AirGroundState` enum is used mainly for map entities that can have a concept of being on ground vs in the air (such as the `Player` when
jumping/falling or various enemies). This enum only has two possible values: `GROUND` and `AIR`. Nice and simple but very handy!

## Utility Point

There is a `Point` class in the `Utils` package (do not confuse this with Java's built in `Point` class -- this is a custom `Point` class!)
that will store "point" data (x, y) as floats and also contains several methods for performing "point math" such as adding
and subtracting points. It's used in several places over Java's standard `Point` class so these additional methods are always available
whenever needed.