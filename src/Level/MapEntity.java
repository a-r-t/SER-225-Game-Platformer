package Level;

import GameObject.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class represents a map entity, which is any "entity" on a map besides the player
// it is basically a game object with a few extra features for handling things like respawning
public class MapEntity extends GameObject {
    protected MapEntityStatus mapEntityStatus = MapEntityStatus.ACTIVE;
    protected TileType tileType;

    // if true, if entity goes out of the camera's update range, and then ends up back in range, the entity will "respawn" back to its starting parameters
    protected boolean isRespawnable = true;

    // if true, enemy cannot go out of camera's update range
    protected boolean isUpdateOffScreen = false;

    public MapEntity(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType type) {
        super(spriteSheet, x, y, startingAnimation);
        tileType = type;
    }

    public MapEntity(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType type) {
        super(x, y, animations, startingAnimation);
        tileType = type;
    }

    public MapEntity(BufferedImage image, float x, float y, String startingAnimation, TileType type) {
        super(image, x, y, startingAnimation);
        tileType = type;
    }

    public MapEntity(BufferedImage image, float x, float y, TileType type) {
        super(image, x, y);
        tileType = type;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, TileType type) {
        super(image, x, y, scale);
        tileType = type;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, TileType type) {
        super(image, x, y, scale, imageEffect);
        tileType = type;
    }

    public MapEntity(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, TileType type) {
        super(image, x, y, scale, imageEffect, bounds);
        tileType = type;
    }

    public void initialize() {
        this.x = startPositionX;
        this.y = startPositionY;
        this.amountMovedX = 0;
        this.amountMovedY = 0;
        this.previousX = startPositionX;
        this.previousY = startPositionY;
        updateCurrentFrame();
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
    
    public TileType getTileType() {
    	return tileType;
    }
    
    public void touchedPlayer(Player player) {
    	if(getTileType()==TileType.KILLER) {
    		player.hurtPlayer(this);
    	}
    }
    
    
}
