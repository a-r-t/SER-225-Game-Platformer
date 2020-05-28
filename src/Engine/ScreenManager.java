package Engine;


import GameObject.Rectangle;

public class ScreenManager {
    private Screen currentScreen;
    private static Rectangle screenBounds = new Rectangle(0, 0, 0, 0);

    public void initialize(Rectangle screenBounds) {
        ScreenManager.screenBounds = screenBounds;
        setCurrentScreen(new DefaultScreen());
    }

    public void setCurrentScreen(Screen screen) {
        screen.initialize();
        this.currentScreen = screen;
    }

    public void update(Keyboard keyboard) {
        currentScreen.update(keyboard);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        currentScreen.draw(graphicsHandler);
    }

    public static int getScreenWidth() {
        return screenBounds.getWidth();
    }

    public static int getScreenHeight() {
        return screenBounds.getHeight();
    }

    public static Rectangle getScreenBounds() {
        return screenBounds;
    }
}
