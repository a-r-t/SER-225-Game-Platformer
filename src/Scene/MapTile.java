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

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int tileIndex, TileType tileType) {
        super(x, y, animations, startingAnimation);
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType) {
        super(x, y, spriteSheet, startingAnimation);
        this.tileType = tileType;
    }

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType) {
        super(x, y, animations, startingAnimation);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType) {
        super(image, x, y, startingAnimation);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, TileType tileType) {
        super(image, x, y);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, TileType tileType) {
        super(image, x, y, scale);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, TileType tileType) {
        super(image, x, y, scale, imageEffect);
        this.tileType = tileType;
    }

    public MapTile(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, TileType tileType) {
        super(image, x, y, scale, imageEffect, bounds);
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
