package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Engine.ScreenManager;
import GameObject.SpriteFont;
import Utils.Colors;
import Utils.Timer;

import java.awt.*;

public class LevelClearedScreen {
    protected SpriteFont winMessage;

    public LevelClearedScreen() {
        winMessage = new SpriteFont("Level Cleared", 320, 270, "Comic Sans", 30, Color.white);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.draw(graphicsHandler);
    }
}
