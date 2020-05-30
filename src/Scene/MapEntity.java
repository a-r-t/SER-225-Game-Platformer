package Scene;

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
    protected boolean exists = true;
    protected boolean updateWhileOffScreen = false;
    protected float startPositionX, startPositionY;
    protected float moveAmountX, moveAmountY;
    private float amountMovedX, amountMovedY;

    public MapEntity(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(spriteSheet, x, y, startingAnimation);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public MapEntity(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public MapEntity(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public MapEntity(BufferedImage image, float x, float y) {
        super(image, x, y);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
        this.startPositionX = x;
        this.startPositionY = y;
    }

    public int getStartPositionX() {
        return Math.round(startPositionX);
    }

    public int getStartPositionY() {
        return Math.round(startPositionY);
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

    public int getAmountMovedX() {
        return Math.round(amountMovedX);
    }

    public int getAmountMovedY() {
        return Math.round(amountMovedY);
    }

    public void update(Keyboard keyboard, Map map, Player player) {
        super.update();
        amountMovedX += moveAmountX;
        amountMovedY += moveAmountY;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
