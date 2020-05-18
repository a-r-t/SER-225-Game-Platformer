package Engine;


import GameObject.Rectangle;

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

    public void draw(Graphics graphics) {
        currentScreen.draw(graphics);
    }
}
