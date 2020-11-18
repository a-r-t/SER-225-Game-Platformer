package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the credits screen
public class CreditsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont createdByLabel;
    protected SpriteFont contributorsLabel;
    protected SpriteFont returnInstructionsLabel;
    protected SpriteFont musicContributorLabel, soundEffectLabel;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        creditsLabel = new SpriteFont("Credits", 15, 35, "Times New Roman", 30, Color.white);
        createdByLabel = new SpriteFont("Created by Alex Thimineur for Quinnipiac's SER225 Course.", 130, 140, "Times New Roman", 20, Color.white);
        contributorsLabel = new SpriteFont("Thank you to QU Alumni Brian Carducci, Joseph White,\nand Alex Hutman for their contributions.", 60, 240, "Times New Roman",20, Color.white);
        musicContributorLabel = new SpriteFont("Music: Guitalele's Happy Place by Stefan Kartenberg (c) copyright 2017 \nLicensed under a Creative Commons Attribution (3.0) license. \nhttp://dig.ccmixter.org/files/JeffSpeed68/56194 Ft: Kara Square (mindmapthat)", 130, 170, "Times New Roman", 15, Color.white);
        soundEffectLabel = new SpriteFont ("Sound Effects: Bubble Sound by Mike Koenig", 130, 190, "Times New Roman", 15, Color.white);
        returnInstructionsLabel = new SpriteFont("Press Enter to return to the menu", 20, 560, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.ENTER);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.ENTER)) {
            keyLocker.unlockKey(Key.ENTER);
        }

        // if ENTER is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        creditsLabel.draw(graphicsHandler);
        createdByLabel.draw(graphicsHandler);
        contributorsLabel.drawWithParsedNewLines(graphicsHandler);
        musicContributorLabel.drawWithParsedNewLines(graphicsHandler);
        soundEffectLabel.drawWithParsedNewLines(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}
