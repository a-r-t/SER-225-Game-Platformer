package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the credits screen
public class InstructionsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont goalLabel;
    protected SpriteFont goalExplanationLabel;
    protected SpriteFont enemiesLabel;
    protected SpriteFont enemiesExplanationLabel1;
    protected SpriteFont enemiesExplanationLabel2;
    protected SpriteFont hazardsLabel;
    protected SpriteFont hazardsExplanationLabel1;
    protected SpriteFont returnInstructionsLabel;

    public InstructionsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);

        //Goals text
        goalLabel = new SpriteFont("   GOAL", 220, 120, "Comic Sans", 23, new Color(49, 207, 240));
        	goalLabel.setOutlineColor(Color.black);
        	goalLabel.setOutlineThickness(3);
        
        goalExplanationLabel = new SpriteFont("Reach the end while avoiding enemies and hazards.", 60, 150, "Comic Sans", 18, new Color(255, 215, 0));
        	goalExplanationLabel.setOutlineColor(Color.black);
        	goalExplanationLabel.setOutlineThickness(3);
        
        //enemies text
        enemiesLabel = new SpriteFont("   ENEMIES", 200, 200, "Comic Sans", 23, new Color(49, 207, 240));
        	enemiesLabel.setOutlineColor(Color.black);
        	enemiesLabel.setOutlineThickness(3);

        enemiesExplanationLabel1 = new SpriteFont("Avoid touching hostile creatures", 140, 230, "Comic Sans", 18, new Color(255, 215, 0));
        	enemiesExplanationLabel1.setOutlineColor(Color.black);
        	enemiesExplanationLabel1.setOutlineThickness(3);

        enemiesExplanationLabel2 = new SpriteFont("Watch out for enemy projectiles", 140, 260, "Comic Sans", 18, new Color(255, 215, 0));
        	enemiesExplanationLabel2.setOutlineColor(Color.black);
        	enemiesExplanationLabel2.setOutlineThickness(3);

        //hazards text
        hazardsLabel = new SpriteFont("   HAZARDS", 200, 310, "Comic Sans", 23, new Color(49, 207, 240));
        	hazardsLabel.setOutlineColor(Color.black);
        	hazardsLabel.setOutlineThickness(3);

        hazardsExplanationLabel1 = new SpriteFont("Like most cats, you don't like water", 130, 340, "Comic Sans", 18, new Color(255, 215, 0));
        	hazardsExplanationLabel1.setOutlineColor(Color.black);
        	hazardsExplanationLabel1.setOutlineThickness(3);
        
        //Return to Main Menu Text
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 560, "Comic Sans", 30, Color.white);
        
        keyLocker.lockKey(Key.currentINTERACT);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.currentINTERACT)) {
            keyLocker.unlockKey(Key.currentINTERACT);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.currentINTERACT) && Keyboard.isKeyDown(Key.currentINTERACT)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        goalLabel.draw(graphicsHandler);
        goalExplanationLabel.draw(graphicsHandler);
        enemiesLabel.draw(graphicsHandler);
        enemiesExplanationLabel1.draw(graphicsHandler);
        enemiesExplanationLabel2.draw(graphicsHandler);
        hazardsLabel.draw(graphicsHandler);
        hazardsExplanationLabel1.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}