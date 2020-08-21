package Scene;

import GameObject.*;
import GameObject.Frame;
import GameObject.Rectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class MapEntity extends GameObject {
    protected MapEntityStatus mapEntityStatus = MapEntityStatus.ACTIVE;
    protected boolean isRespawnable = true;
    protected boolean isUpdateOffScreen = false;

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

    public void initialize() {
        this.x = startPositionX;
        this.y = startPositionY;
        this.amountMovedX = 0;
        this.amountMovedY = 0;
        this.previousX = startPositionX;
        this.previousY = startPositionY;
        setCurrentSprite();
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

    public boolean isUpdateOffScreen() {
        return isUpdateOffScreen;
    }

    public void setIsUpdateOffScreen(boolean isUpdateOffScreen) {
        this.isUpdateOffScreen = isUpdateOffScreen;
    }
}
