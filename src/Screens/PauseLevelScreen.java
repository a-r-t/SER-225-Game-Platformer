package Screens;

import java.awt.Color;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

public class PauseLevelScreen extends Screen{
	
	protected SpriteFont resetLevel;  
	protected SpriteFont exitGame;
	protected SpriteFont pauseLevelTitle;
	protected ScreenCoordinator screenCoordinator;
	protected KeyLocker keyLocker = new KeyLocker();
	protected Stopwatch keyTimer = new Stopwatch();
	protected int currentMenuItemHovered = 0;
	protected int menuItemSelected = -1;
	protected int pointerLocationX, pointerLocationY;
	protected PlayLevelScreen playLevelScreen;
	
	 public PauseLevelScreen(ScreenCoordinator screenCoordinator, PlayLevelScreen playLevelScreen) {
	        this.screenCoordinator = screenCoordinator;
	        this.playLevelScreen = playLevelScreen;
	    }
	    
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
			pauseLevelTitle = new SpriteFont("PAUSE MENU", 200, 75, "Comic Sans", 30, Color.BLACK);
			pauseLevelTitle.setOutlineColor(Color.black);
			pauseLevelTitle.setOutlineThickness(3);
        
			resetLevel = new SpriteFont("RESET LEVEL", 200, 150, "Comic Sans", 30, new Color(49, 207, 240));
	        resetLevel.setOutlineColor(Color.black);
	        resetLevel.setOutlineThickness(3);
	        
	        //"Exit the Level" main menu text
	        exitGame = new SpriteFont("EXIT THE LEVEL", 200, 250, "Comic Sans", 30, new Color(49, 207, 240));
	        exitGame.setOutlineColor(Color.black);
	        exitGame.setOutlineThickness(3);
	        
	        keyTimer.setWaitTime(200);
	        menuItemSelected = -1;
	        keyLocker.lockKey(Key.SPACE);
	}
	
	@Override
	public void update() {
		// Pause menu logic
		  // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.DOWN) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyTimer.isTimeUp()) {
            keyTimer.reset();
            currentMenuItemHovered--;
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
        	//Highlists "Reset Level"
            resetLevel.setColor(new Color(255, 215, 0));
            exitGame.setColor(new Color( 49, 207, 240));
            pointerLocationX = 170;
            pointerLocationY = 130;
        } else if (currentMenuItemHovered == 1) {
        	//Highlights "Exit the Level"
            resetLevel.setColor(new Color(49, 207, 240));
            exitGame.setColor(new Color(255, 215, 0));
            pointerLocationX = 170;
            pointerLocationY = 230;
        } 
        
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                playLevelScreen.initialize();
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.MENU);
            } 
        }
	}
	
	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawFilledRectangle(ScreenManager.getScreenWidth()/2 - 250, ScreenManager.getScreenHeight()/2 - 250, 500, 500, new Color(0, 255, 255, 100));
		graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
		pauseLevelTitle.draw(graphicsHandler);
		resetLevel.draw(graphicsHandler);
		exitGame.draw(graphicsHandler);
	}

}
