package MapEntities;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Scene.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class EnhancedMapTile extends MapEntity {

    protected TileType tileType;

    public EnhancedMapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType) {
        super(x, y, spriteSheet, startingAnimation);
        this.tileType = tileType;
    }

    public EnhancedMapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType) {
        super(x, y, animations, startingAnimation);
        this.tileType = tileType;
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType) {
        super(image, x, y, startingAnimation);
        this.tileType = tileType;
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType) {
        super(image, x, y);
        this.tileType = tileType;
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale) {
        super(image, x, y, scale);
        this.tileType = tileType;
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
        this.tileType = tileType;
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void update(Keyboard keyboard, Map map, Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
