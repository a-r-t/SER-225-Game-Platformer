package Engine;


import GameObject.Rectangle;

import java.awt.*;

public class ScreenManager {
    private Screen currentScreen;
    private Rectangle windowBounds;

    public void initialize(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
        setCurrentScreen(new DefaultScreen());
    }

    public void setCurrentScreen(Screen screen) {
        screen.initialize(windowBounds);
        this.currentScreen = screen;
    }

    public void update(Keyboard keyboard) {
        currentScreen.update(keyboard);
    }

    public void draw(Painter painter) {
        currentScreen.draw(painter);
    }
}
