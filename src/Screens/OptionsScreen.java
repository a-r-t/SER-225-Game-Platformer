package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.*;

// This class is for the credits screen
public class OptionsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected int currentMenuItemHovered = 0;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY, activePresetX, activePresetY;
    protected SpriteFont controlsLabel;
    protected SpriteFont controlsDescLabel;
    protected SpriteFont wasd1Label;
    	protected SpriteFont wasd1DescLabel1; 
    	protected SpriteFont wasd1DescLabel2;
    	protected SpriteFont wasd1DescLabel3;
    protected SpriteFont wasd2Label;
    	protected SpriteFont wasd2DescLabel1;
    	protected SpriteFont wasd2DescLabel2;
    	protected SpriteFont wasd2DescLabel3;
    protected SpriteFont arrows1Label;
    	protected SpriteFont arrows1DescLabel1;
    	protected SpriteFont arrows1DescLabel2;
    	protected SpriteFont arrows1DescLabel3;
    protected SpriteFont arrows2Label;
    	protected SpriteFont arrows2DescLabel1;
    	protected SpriteFont arrows2DescLabel2;
    	protected SpriteFont arrows2DescLabel3;
    protected SpriteFont numpadLabel;
    	protected SpriteFont numpadDescLabel1;
    	protected SpriteFont numpadDescLabel2;
    	protected SpriteFont numpadDescLabel3;
    protected SpriteFont returnOptionsLabel;
    protected SpriteFont returnOptionsLabel2;

    public OptionsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
    	background = new TitleScreenMap();
        background.setAdjustCamera(false);

       	// setup graphics on screen
        	//Option Menu text
        controlsLabel = new SpriteFont("   CONTROLS", 180, 60, "Comic Sans", 24, new Color(49, 207, 240));
        controlsLabel.setOutlineColor(Color.black);
        controlsLabel.setOutlineThickness(3);

        controlsDescLabel = new SpriteFont("Movement Presets", 180, 100, "Comic Sans", 22, new Color(49, 207, 240));
        controlsDescLabel.setOutlineColor(Color.black);
        controlsDescLabel.setOutlineThickness(3);

        ///////////////////////////////////////////////////////////////
        	//WASD w/ F text
        wasd1Label = new SpriteFont("WASD Variant 1", 50, 150, "Comic Sans", 20, new Color(49, 207, 240));
        wasd1Label.setOutlineColor(Color.black);
        wasd1Label.setOutlineThickness(3);

        		//Preset Description
        wasd1DescLabel1 = new SpriteFont("Movement: WASD", 270, 150, "Comic Sans", 20, new Color( 255, 215, 0));
        wasd1DescLabel1.setOutlineColor(Color.black);
        wasd1DescLabel1.setOutlineThickness(3);

        wasd1DescLabel2 = new SpriteFont("Interact:     'F'", 270, 180, "Comic Sans", 20, new Color( 255, 215, 0));
        wasd1DescLabel2.setOutlineColor(Color.black);
        wasd1DescLabel2.setOutlineThickness(3);

        wasd1DescLabel3 = new SpriteFont("Pause:       'P'", 270, 210, "Comic Sans", 20, new Color( 255, 215, 0));
        wasd1DescLabel3.setOutlineColor(Color.black);
        wasd1DescLabel3.setOutlineThickness(3);

        //////////////////////////////////////////////////////////////
        	//WASD w/ SPACE text
        wasd2Label = new SpriteFont("WASD Variant 2", 50, 180, "Comic Sans", 20, new Color(49, 207, 240));
        wasd2Label.setOutlineColor(Color.black);
        wasd2Label.setOutlineThickness(3);

        		//Preset Description
        wasd2DescLabel1 = new SpriteFont("Movement: WASD", 270, 150, "Comic Sans", 20, new Color( 255, 215, 0));
        wasd2DescLabel1.setOutlineColor(Color.black);
        wasd2DescLabel1.setOutlineThickness(3);

        wasd2DescLabel2 = new SpriteFont("Interact:     SPACE", 270, 180, "Comic Sans", 20, new Color( 255, 215, 0));
        wasd2DescLabel2.setOutlineColor(Color.black);
        wasd2DescLabel2.setOutlineThickness(3);

        wasd2DescLabel3 = new SpriteFont("Pause:       'P'", 270, 210, "Comic Sans", 20, new Color( 255, 215, 0));
        wasd2DescLabel3.setOutlineColor(Color.black);
        wasd2DescLabel3.setOutlineThickness(3);

        ///////////////////////////////////////////////////////////////
        	//ARROWS w/ SPACE text
        arrows1Label = new SpriteFont("Arrow Keys Variant 1", 50, 210, "Comic Sans", 20, new Color(49, 207, 240));
        arrows1Label.setOutlineColor(Color.black);
        arrows1Label.setOutlineThickness(3);

        		//Preset Description
        arrows1DescLabel1 = new SpriteFont("Movement: Arrow Keys", 270, 150, "Comic Sans", 20, new Color( 255, 215, 0));
        arrows1DescLabel1.setOutlineColor(Color.black);
        arrows1DescLabel1.setOutlineThickness(3);

        arrows1DescLabel2 = new SpriteFont("Interact:     SPACE", 270, 180, "Comic Sans", 20, new Color( 255, 215, 0));
        arrows1DescLabel2.setOutlineColor(Color.black);
        arrows1DescLabel2.setOutlineThickness(3);

        arrows1DescLabel3 = new SpriteFont("Pause:       'P'", 270, 210, "Comic Sans", 20, new Color( 255, 215, 0));
        arrows1DescLabel3.setOutlineColor(Color.black);
        arrows1DescLabel3.setOutlineThickness(3);

        ///////////////////////////////////////////////////////////////
        	//ARROWS w/ NUMPAD "." text
        arrows2Label = new SpriteFont("Arrow Keys Variant 2", 50, 240, "Comic Sans", 20, new Color(49, 207, 240));
        arrows2Label.setOutlineColor(Color.black);
        arrows2Label.setOutlineThickness(3);

        		//Preset Description
        arrows2DescLabel1 = new SpriteFont("Movement: Arrow Keys", 270, 150, "Comic Sans", 20, new Color( 255, 215, 0));
        arrows2DescLabel1.setOutlineColor(Color.black);
        arrows2DescLabel1.setOutlineThickness(3);

        arrows2DescLabel2 = new SpriteFont("Interact:     NUMPAD '.'", 270, 180, "Comic Sans", 20, new Color( 255, 215, 0));
        arrows2DescLabel2.setOutlineColor(Color.black);
        arrows2DescLabel2.setOutlineThickness(3);

        arrows2DescLabel3 = new SpriteFont("Pause:       'P'", 270, 210, "Comic Sans", 20, new Color( 255, 215, 0));
        arrows2DescLabel3.setOutlineColor(Color.black);
        arrows2DescLabel3.setOutlineThickness(3);

        /////////////////////////////////////////////////////////////////
        	//NUMPAD Arrows text
        numpadLabel = new SpriteFont("Numberpad Arrows", 50, 270, "Comic Sans", 20, new Color(49, 207, 240));
        numpadLabel.setOutlineColor(Color.black);
        numpadLabel.setOutlineThickness(3);

        		//Preset Description
        numpadDescLabel1 = new SpriteFont("Movement: Numberpad 8, 4, 5, 6", 270, 150, "Comic Sans", 20, new Color( 255, 215, 0));
        numpadDescLabel1.setOutlineColor(Color.black);
        numpadDescLabel1.setOutlineThickness(3);

        numpadDescLabel2 = new SpriteFont("Interact:     LEFT Arrow", 270, 180, "Comic Sans", 20, new Color( 255, 215, 0));
        numpadDescLabel2.setOutlineColor(Color.black);
        numpadDescLabel2.setOutlineThickness(3);

        numpadDescLabel3 = new SpriteFont("Pause:       'P'", 270, 210, "Comic Sans", 20, new Color( 255, 215, 0));
        numpadDescLabel3.setOutlineColor(Color.black);
        numpadDescLabel3.setOutlineThickness(3);

        ///////////////////////////////////////////////////////////////////
        	//Return to Main Menu Text
        returnOptionsLabel = new SpriteFont("Press the INTERACT key to select a preset", 40, 320, "Comic Sans", 22, new Color(49, 207, 240));
        returnOptionsLabel.setOutlineColor(Color.black);
        returnOptionsLabel.setOutlineThickness(3);
        returnOptionsLabel2 = new SpriteFont("A game restart is required when selecting presets", 40, 342, "Comic Sans", 20, new Color(255, 215, 0));
        returnOptionsLabel2.setOutlineColor(Color.black);
        returnOptionsLabel2.setOutlineThickness(3);

        keyLocker.lockKey(Key.currentINTERACT);
        keyTimer.setWaitTime(200);
    }

    public void update() {
    	background.update(null);

    		//sets currentMenuItemHovered
        if (Keyboard.isKeyDown(Key.currentDOWN) && keyTimer.isTimeUp()) {
    		keyTimer.reset();
    		currentMenuItemHovered++;
    	} else if (Keyboard.isKeyDown(Key.currentUP) && keyTimer.isTimeUp()) {
    		keyTimer.reset();
    		currentMenuItemHovered--;
    	}

    		// if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
    	if (currentMenuItemHovered > 4) {
    		currentMenuItemHovered = 0;
    	} else if (currentMenuItemHovered < 0) {
    		currentMenuItemHovered = 4;
    	}

        if (currentMenuItemHovered == 0) {
        		//Highlights WASD w/ F controls
        	wasd1Label.setColor(new Color( 255, 215, 0));
            wasd2Label.setColor(new Color( 49, 207, 240));
            arrows1Label.setColor(new Color( 49, 207, 240));
            arrows2Label.setColor(new Color( 49, 207, 240));
            numpadLabel.setColor(new Color( 49, 207, 240));
            pointerLocationX = 10;
            pointerLocationY = 133;
	
            	//displays associated preset
            wasd1DescLabel1.setFontSize(20);
            wasd1DescLabel2.setFontSize(20);
            wasd1DescLabel3.setFontSize(20);

            wasd2DescLabel1.setFontSize(0);
            wasd2DescLabel2.setFontSize(0);
            wasd2DescLabel3.setFontSize(0);

            arrows1DescLabel1.setFontSize(0);
            arrows1DescLabel2.setFontSize(0);
            arrows1DescLabel3.setFontSize(0);

            arrows2DescLabel1.setFontSize(0);
            arrows2DescLabel2.setFontSize(0);
            arrows2DescLabel3.setFontSize(0);

            numpadDescLabel1.setFontSize(0);
            numpadDescLabel2.setFontSize(0);
            numpadDescLabel3.setFontSize(0);

        } else if (currentMenuItemHovered == 1) {
        		//displays WASD w/ SPACE controls
        	wasd1Label.setColor(new Color( 49, 207, 240));
            wasd2Label.setColor(new Color( 255, 215, 0));
            arrows1Label.setColor(new Color( 49, 207, 240));
            arrows2Label.setColor(new Color( 49, 207, 240));
            numpadLabel.setColor(new Color( 49, 207, 240));
            pointerLocationX = 10;
            pointerLocationY = 163;

            	//displays associated preset
            wasd1DescLabel1.setFontSize(0);
            wasd1DescLabel2.setFontSize(0);
            wasd1DescLabel3.setFontSize(0);

            wasd2DescLabel1.setFontSize(20);
            wasd2DescLabel2.setFontSize(20);
            wasd2DescLabel3.setFontSize(20);

            arrows1DescLabel1.setFontSize(0);
            arrows1DescLabel2.setFontSize(0);
            arrows1DescLabel3.setFontSize(0);

            arrows2DescLabel1.setFontSize(0);
            arrows2DescLabel2.setFontSize(0);
            arrows2DescLabel3.setFontSize(0);

            numpadDescLabel1.setFontSize(0);
            numpadDescLabel2.setFontSize(0);
            numpadDescLabel3.setFontSize(0);

        } else if (currentMenuItemHovered == 2) {
        		//displays ARROWs w/ SPACE controls
        	wasd1Label.setColor(new Color( 49, 207, 240));
            wasd2Label.setColor(new Color( 49, 207, 240));
            arrows1Label.setColor(new Color( 255, 215, 0));
            arrows2Label.setColor(new Color( 49, 207, 240));
            numpadLabel.setColor(new Color( 49, 207, 240));
            pointerLocationX = 10;
            pointerLocationY = 193;

            	//displays associated preset
            wasd1DescLabel1.setFontSize(0);
            wasd1DescLabel2.setFontSize(0);
            wasd1DescLabel3.setFontSize(0);

            wasd2DescLabel1.setFontSize(0);
            wasd2DescLabel2.setFontSize(0);
            wasd2DescLabel3.setFontSize(0);

            arrows1DescLabel1.setFontSize(20);
            arrows1DescLabel2.setFontSize(20);
            arrows1DescLabel3.setFontSize(20);

            arrows2DescLabel1.setFontSize(0);
            arrows2DescLabel2.setFontSize(0);
            arrows2DescLabel3.setFontSize(0);

            numpadDescLabel1.setFontSize(0);
            numpadDescLabel2.setFontSize(0);
            numpadDescLabel3.setFontSize(0);

        } else if (currentMenuItemHovered == 3) {
        		//displays ARROWs w/ NUMPAD "." controls
        	wasd1Label.setColor(new Color( 49, 207, 240));
            wasd2Label.setColor(new Color( 49, 207, 240));
            arrows1Label.setColor(new Color( 49, 207, 240));
            arrows2Label.setColor(new Color( 255, 215, 0));
            numpadLabel.setColor(new Color( 49, 207, 240));
            pointerLocationX = 10;
            pointerLocationY = 223;

            	//displays associated preset
            wasd1DescLabel1.setFontSize(0);
            wasd1DescLabel2.setFontSize(0);
            wasd1DescLabel3.setFontSize(0);

            wasd2DescLabel1.setFontSize(0);
            wasd2DescLabel2.setFontSize(0);
            wasd2DescLabel3.setFontSize(0);

            arrows1DescLabel1.setFontSize(0);
            arrows1DescLabel2.setFontSize(0);
            arrows1DescLabel3.setFontSize(0);

            arrows2DescLabel1.setFontSize(20);
            arrows2DescLabel2.setFontSize(20);
            arrows2DescLabel3.setFontSize(20);

            numpadDescLabel1.setFontSize(0);
            numpadDescLabel2.setFontSize(0);
            numpadDescLabel3.setFontSize(0);

        } else if (currentMenuItemHovered == 4) {
        		//displays NUMPAD w/ NUMPAD ENTER controls
        	wasd1Label.setColor(new Color( 49, 207, 240));
            wasd2Label.setColor(new Color( 49, 207, 240));
            arrows1Label.setColor(new Color( 49, 207, 240));
            arrows2Label.setColor(new Color( 49, 207, 240));
            numpadLabel.setColor(new Color( 255, 215, 0));
            pointerLocationX = 10;
            pointerLocationY = 253;

            	//displays associated preset
            wasd1DescLabel1.setFontSize(0);
            wasd1DescLabel2.setFontSize(0);
            wasd1DescLabel3.setFontSize(0);

            wasd2DescLabel1.setFontSize(0);
            wasd2DescLabel2.setFontSize(0);
            wasd2DescLabel3.setFontSize(0);

            arrows1DescLabel1.setFontSize(0);
            arrows1DescLabel2.setFontSize(0);
            arrows1DescLabel3.setFontSize(0);

            arrows2DescLabel1.setFontSize(0);
            arrows2DescLabel2.setFontSize(0);
            arrows2DescLabel3.setFontSize(0);

            numpadDescLabel1.setFontSize(20);
            numpadDescLabel2.setFontSize(20);
            numpadDescLabel3.setFontSize(20);
        }

        if (Keyboard.isKeyUp(Key.currentINTERACT)) {
            keyLocker.unlockKey(Key.currentINTERACT);
        }

        	// applied selected preset and returns to main menu
        if (!keyLocker.isKeyLocked(Key.currentINTERACT) && Keyboard.isKeyDown(Key.currentINTERACT)) {

        	try {
        		FileWriter fileWriter = new FileWriter("SavedData/ControlPreferences.txt");
        		if(currentMenuItemHovered == 0) {
        			//set control preset to WASD preset 1
        			fileWriter.write("Active Preset: 1");
        			fileWriter.close();
        			System.exit(1);
        			screenCoordinator.setGameState(GameState.MENU);
        		} else if (currentMenuItemHovered == 1) {
        			//set control preset to WASD preset 2
        			fileWriter.write("Active Preset: 2");
        			fileWriter.close();
        			System.exit(1);
        			screenCoordinator.setGameState(GameState.MENU);
        		} else if (currentMenuItemHovered == 2) {
        			//set control preset to Arrows preset 1
        			fileWriter.write("Active Preset: 3");
        			fileWriter.close();
        			System.exit(1);
        			screenCoordinator.setGameState(GameState.MENU);
        		} else if (currentMenuItemHovered == 3) {
        			//set control preset to Arrows preset 2
        			fileWriter.write("Active Preset: 4");
        			fileWriter.close();
        			System.exit(1);
        			screenCoordinator.setGameState(GameState.MENU);
        		} else if (currentMenuItemHovered == 4) {
        			//set control preset to NUMPAD preset
        			fileWriter.write("Active Preset: 5");
        			fileWriter.close();
        			System.exit(1);
        			screenCoordinator.setGameState(GameState.MENU);
        		}
        	} catch (IOException e) {
        		System.out.println("Error");
        	}
        }
    }

    	//displays screen elements
    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        controlsLabel.draw(graphicsHandler);
        controlsDescLabel.draw(graphicsHandler);
        wasd1Label.draw(graphicsHandler);
        	wasd1DescLabel1.draw(graphicsHandler);
        	wasd1DescLabel2.draw(graphicsHandler);
        	wasd1DescLabel3.draw(graphicsHandler);
        wasd2Label.draw(graphicsHandler);
        	wasd2DescLabel1.draw(graphicsHandler);
        	wasd2DescLabel2.draw(graphicsHandler);
        	wasd2DescLabel3.draw(graphicsHandler);
        arrows1Label.draw(graphicsHandler);
        	arrows1DescLabel1.draw(graphicsHandler);
        	arrows1DescLabel2.draw(graphicsHandler);
        	arrows1DescLabel3.draw(graphicsHandler);
        arrows2Label.draw(graphicsHandler);
        	arrows2DescLabel1.draw(graphicsHandler);
        	arrows2DescLabel2.draw(graphicsHandler);
        	arrows2DescLabel3.draw(graphicsHandler);
        numpadLabel.draw(graphicsHandler);
        	numpadDescLabel1.draw(graphicsHandler);
        	numpadDescLabel2.draw(graphicsHandler);
        	numpadDescLabel3.draw(graphicsHandler);
        returnOptionsLabel.draw(graphicsHandler);
        returnOptionsLabel2.draw(graphicsHandler);

        	//yellow box creation and location set based on active control preset
        File controlsFile = new File("SavedData/ControlPreferences.txt");
		 Scanner fileInput = null;
		 try {
			 fileInput = new Scanner(controlsFile);
			 String activePreset = fileInput.nextLine();
			 if (activePreset.equals("Active Preset: 1")) {
				 graphicsHandler.drawFilledRectangleWithBorder(10, 133, 32, 20, new Color(255, 215, 0), Color.black, 2);
			 }
			 if (activePreset.equals("Active Preset: 2")) {
				 graphicsHandler.drawFilledRectangleWithBorder(10, 163, 32, 20, new Color(255, 215, 0), Color.black, 2);
			 }
			 if (activePreset.equals("Active Preset: 3")) {
				 graphicsHandler.drawFilledRectangleWithBorder(10, 193, 32, 20, new Color(255, 215, 0), Color.black, 2);
			 }
			 if (activePreset.equals("Active Preset: 4")) {
				 graphicsHandler.drawFilledRectangleWithBorder(10, 223, 32, 20, new Color(255, 215, 0), Color.black, 2);
			 }
			 if (activePreset.equals("Active Preset: 5")) {
				 graphicsHandler.drawFilledRectangleWithBorder(10, 253, 32, 20, new Color(255, 215, 0), Color.black, 2);
			 }
		 } catch (FileNotFoundException e) {
			 System.out.println("Error");
			 System.exit(1);
		 }

		 	//blue cursor for scrolling through options
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
        
    }

}