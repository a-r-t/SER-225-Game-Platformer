package Engine;


import GameObject.Rectangle;

import java.awt.*;

public class ScreenManager {
    private GameScreen currentGameScreen;
    private Rectangle windowBounds;

    public void initialize(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
        setCurrentGameScreen(new DefaultGameScreen());
    }

    public void setCurrentGameScreen(GameScreen gameScreen) {
        gameScreen.initialize(windowBounds);
        this.currentGameScreen = gameScreen;
    }

    public void update(Keyboard keyboard) {
        currentGameScreen.update(keyboard);
    }

    public void draw(Graphics2D g) {
        currentGameScreen.draw(g);
    }
}
