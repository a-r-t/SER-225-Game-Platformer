package Map;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import GameObject.GameObject;

import GameObject.SpriteSheet;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapEntity extends GameObject {
    private boolean exists = true;
    private boolean updateWhileOffScreen = false;

    public MapEntity(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(spriteSheet, x, y, startingAnimation);
    }

    public MapEntity(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public MapEntity(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
    }

    public MapEntity(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
    }

    public boolean exists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isUpdateWhileOffScreen() {
        return updateWhileOffScreen;
    }

    public void setUpdateWhileOffScreen(boolean updateWhileOffScreen) {
        this.updateWhileOffScreen = updateWhileOffScreen;
    }

    public void update(Keyboard keyboard, Map map, Player player) {
        super.update();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
