package Scene;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapTile extends MapEntity {
    protected TileType tileType;
    private int tileIndex;

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int tileIndex, TileType tileType, Map map) {
        super(x, y, animations, startingAnimation, map);
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType, Map map) {
        super(x, y, spriteSheet, startingAnimation, map);
        this.tileType = tileType;
    }

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType, Map map) {
        super(x, y, animations, startingAnimation, map);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType, Map map) {
        super(image, x, y, startingAnimation, map);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, TileType tileType, Map map) {
        super(image, x, y, map);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, TileType tileType, Map map) {
        super(image, x, y, scale, map);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, TileType tileType, Map map) {
        super(image, x, y, scale, imageEffect, map);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, TileType tileType, Map map) {
        super(image, x, y, scale, imageEffect, bounds, map);
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

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
