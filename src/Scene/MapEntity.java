package Scene;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import GameObject.*;
import GameObject.Frame;
import GameObject.Rectangle;
import Utils.MathUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapEntity extends GameObject {
    protected MapEntityStatus mapEntityStatus = MapEntityStatus.ACTIVE;
    protected boolean isRespawnable = true;
    //protected float startPositionX, startPositionY;
    private float moveAmountX, moveAmountY;
    protected Map map;

    public MapEntity(float x, float y, SpriteSheet spriteSheet, String startingAnimation, Map map) {
        super(spriteSheet, x, y, startingAnimation);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public MapEntity(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, Map map) {
        super(x, y, animations, startingAnimation);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public MapEntity(BufferedImage image, float x, float y, String startingAnimation, Map map) {
        super(image, x, y, startingAnimation);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public MapEntity(BufferedImage image, float x, float y, Map map) {
        super(image, x, y);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, Map map) {
        super(image, x, y, scale);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Map map) {
        super(image, x, y, scale, imageEffect);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, Map map) {
        super(image, x, y, scale, imageEffect, bounds);
        this.startPositionX = x;
        this.startPositionY = y;
        this.map = map;
    }

    public void initialize() {
        this.amountMovedX = 0;
        this.amountMovedY = 0;
    }

    public int getStartPositionX() {
        return Math.round(startPositionX);
    }

    public int getStartPositionY() {
        return Math.round(startPositionY);
    }

    public MapEntityStatus getMapEntityStatus() {
        return mapEntityStatus;
    }

    public void setMapEntityStatus(MapEntityStatus mapEntityStatus) {
        this.mapEntityStatus = mapEntityStatus;
    }

    public boolean isRespawnable() {
        return isRespawnable;
    }

    public void setIsRespawnable(boolean isRespawnable) {
        this.isRespawnable = isRespawnable;
    }

    public void calibrate(Map map) {
        super.setX(getCalibratedXLocation(map));
        super.setY(getCalibratedYLocation(map));
    }


    public void update() {
        super.update();
        amountMovedX += moveAmountX;
        amountMovedY += moveAmountY;
        moveAmountX = 0;
        moveAmountY = 0;
    }

    @Override
    public void moveX(float dx) {
        moveAmountX += dx;
        super.moveX(dx);
    }

    @Override
    public void moveY(float dy) {
        moveAmountY += dy;
        super.moveY(dy);
    }

    @Override
    public void setX(float x) {
        float difference = x - this.x;
        moveAmountX += difference;
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        float difference = y - this.y;
        moveAmountY += difference;
        super.setY(y);
    }

    @Override
    public void setLocation(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void moveRight(float dx) {
        moveAmountX += dx;
        super.moveRight(dx);
    }

    @Override
    public void moveLeft(float dx) {
        moveAmountX -= dx;
        super.moveLeft(dx);
    }

    @Override
    public void moveDown(float dy) {
        moveAmountY += dy;
        super.moveDown(dy);
    }

    @Override
    public void moveUp(float dy) {
        moveAmountY -= dy;
        super.moveUp(dy);
    }

    @Override
    public Rectangle getIntersectRectangle() {
        return getScaledBounds();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawImage(
                currentFrame.getImage(),
                Math.round(getCalibratedXLocation(map)),
                Math.round(getCalibratedYLocation(map)),
                currentFrame.getScaledWidth(),
                currentFrame.getScaledHeight(),
                currentFrame.getImageEffect());
    }

    @Override
    public Rectangle getScaledBounds() {
        Rectangle scaledBounds = currentFrame.getScaledBounds();
        return new Rectangle(
                scaledBounds.getX() - map.getCamera().getAmountMovedX(),
                scaledBounds.getY() - map.getCamera().getAmountMovedY(),
                scaledBounds.getWidth(),
                scaledBounds.getHeight());
    }


    @Override
    public float getScaledBoundsX1() {
        return getScaledBounds().getX1();
    }


    @Override
    public float getScaledBoundsX2() {
        return getScaledBounds().getX2();
    }


    @Override
    public float getScaledBoundsY1() {
        return getScaledBounds().getY1();
    }


    @Override
    public float getScaledBoundsY2() {
        return getScaledBounds().getY2();
    }

    @Override
    public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        Rectangle scaledBounds = getScaledBounds();
        scaledBounds.setColor(color);
        scaledBounds.draw(graphicsHandler);
    }
}
