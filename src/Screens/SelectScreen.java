package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.*;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.awt.*;

// This is the class for the main menu screen
public class SelectScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont tutorial;
    protected SpriteFont mapOne;
    protected SpriteFont mapTwo;
    protected SpriteFont mapThree;
    protected Map background;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();

    public SelectScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;

    }

    @Override
    public void initialize() {
        tutorial = new SpriteFont("Tutorial", 200, 100, "Comic Sans", 30, new Color(49, 207, 240));
        tutorial.setOutlineColor(Color.black);
        tutorial.setOutlineThickness(3);
        mapOne = new SpriteFont("Level 1", 200, 200, "Comic Sans", 30, new Color(49, 207, 240));
        mapOne.setOutlineColor(Color.black);
        mapOne.setOutlineThickness(3);
        mapTwo = new SpriteFont("Level 2", 200, 300, "Comic Sans", 30, new Color(49, 207, 240));
        mapTwo.setOutlineColor(Color.black);
        mapTwo.setOutlineThickness(3);
        mapThree = new SpriteFont("Level 3", 200, 400, "Comic Sans", 30, new Color(49, 207, 240));
        mapThree.setOutlineColor(Color.black);
        mapThree.setOutlineThickness(3);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyTimer.setWaitTime(200);
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        // update background map (to play tile animations)
        background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered--;
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 3) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 3;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
            tutorial.setColor(new Color(255, 215, 0));
            mapOne.setColor(new Color(49, 207, 240));
            mapTwo.setColor(new Color(49, 207, 240));
            mapThree.setColor(new Color(49, 207, 240));

            pointerLocationX = 170;
            pointerLocationY = 80;
        } else if (currentMenuItemHovered == 1) {
            tutorial.setColor(new Color(49, 207, 240));
            mapOne.setColor(new Color(255, 215, 0));
            mapTwo.setColor(new Color(49, 207, 240));
            mapThree.setColor(new Color(49, 207, 240));

            pointerLocationX = 170;
            pointerLocationY = 180;
        } else if (currentMenuItemHovered == 2) {
            tutorial.setColor(new Color(49, 207, 240));
            mapOne.setColor(new Color(49, 207, 240));
            mapTwo.setColor(new Color(255, 215, 0));
            mapThree.setColor(new Color(49, 207, 240));

            pointerLocationX = 170;
            pointerLocationY = 280;
        } else if (currentMenuItemHovered == 3) {
            tutorial.setColor(new Color(49, 207, 240));
            mapOne.setColor(new Color(49, 207, 240));
            mapTwo.setColor(new Color(49, 207, 240));
            mapThree.setColor(new Color(255, 215, 0));

            pointerLocationX = 170;
            pointerLocationY = 380;
        }

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setMap(new TutorialMap());
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setMap(new OneMap());
                screenCoordinator.setGameState(GameState.LEVEL);
            }else if (menuItemSelected == 2){
                screenCoordinator.setMap(new TwoMap());
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 3) {
                screenCoordinator.setMap(new ThreeMap());
                screenCoordinator.setGameState(GameState.LEVEL);
            }

        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        tutorial.draw(graphicsHandler);
        mapOne.draw(graphicsHandler);
        mapTwo.draw(graphicsHandler);
        mapThree.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
    }

    public int getMenuItemSelected() {
        return menuItemSelected;
    }
}
