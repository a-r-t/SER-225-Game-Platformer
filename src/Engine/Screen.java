package Engine;

public abstract class Screen {
    public abstract void initialize();
    public abstract void update(Keyboard keyboard);
    public abstract void draw(GraphicsHandler graphicsHandler);
}
