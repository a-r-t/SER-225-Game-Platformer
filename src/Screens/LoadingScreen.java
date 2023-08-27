package Screens;

import Engine.*;
import SpriteFont.SpriteFont;

import java.awt.*;

// This is the class for the level loading screen
public class LoadingScreen extends Screen {
    protected SpriteFont loadingLabel;
    protected PlayLevelScreen playLevelScreen;

    public LoadingScreen(PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        loadingLabel = new SpriteFont("LOADING...", 335, 250, "Comic Sans", 24, Color.white);
        loadingLabel.setOutlineColor(Color.black);
        loadingLabel.setOutlineThickness(2.0f);
    }

    @Override
    public void update() { }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        loadingLabel.draw(graphicsHandler);
    }
}
