package Scene;

import GameObject.*;
import GameObject.Frame;
import GameObject.Rectangle;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapEntity extends GameObject {
    protected MapEntityStatus mapEntityStatus = MapEntityStatus.ACTIVE;
    protected boolean isRespawnable = true;
    protected boolean isUpdateOffScreen = false;

    public MapEntity(float x, float y, SpriteSheet spriteSheet, String startingAnimation, Map map) {
        super(spriteSheet, x, y, startingAnimation, map);
    }

    public MapEntity(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, Map map) {
        super(x, y, animations, startingAnimation, map);
    }

    public MapEntity(BufferedImage image, float x, float y, String startingAnimation, Map map) {
        super(image, x, y, startingAnimation, map);
    }

    public MapEntity(BufferedImage image, float x, float y, Map map) {
        super(image, x, y, map);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, Map map) {
        super(image, x, y, scale, map);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Map map) {
        super(image, x, y, scale, imageEffect, map);
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, Map map) {
        super(image, x, y, scale, imageEffect, bounds, map);
    }

    public void initialize() {
        this.x = startPositionX;
        this.y = startPositionY;
        this.amountMovedX = 0;
        this.amountMovedY = 0;
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
