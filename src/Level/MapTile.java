package Level;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// Represents a map tile in a Map's tile map
public class MapTile extends MapEntity {
    // this determines a tile's properties, like if it's passable or not
    protected TileType tileType;

    private int tileIndex;

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int tileIndex, TileType tileType) {
        super(x, y, animations, startingAnimation, tileType);
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType) {
        super(x, y, spriteSheet, startingAnimation, tileType);
        this.tileType = tileType;
    }

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType) {
        super(x, y, animations, startingAnimation, tileType);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType) {
        super(image, x, y, startingAnimation, tileType);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, TileType tileType) {
        super(image, x, y, tileType);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, TileType tileType) {
        super(image, x, y, scale, tileType);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, TileType tileType) {
        super(image, x, y, scale, imageEffect, tileType);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, TileType tileType) {
        super(image, x, y, scale, imageEffect, bounds, tileType);
        this.tileType = tileType;
    }
    
    //water constructor
    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, TileType tileType, Player player) {
        super(image, x, y, scale, imageEffect, bounds, tileType);
        this.tileType = tileType;
    }
    public TileType getTileType() {
        return tileType;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void update() {
        super.update();
    }
    
    public void touchedPlayer(Player player) {
        player.hurtPlayer(this);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        //drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
    }
    
   
}
