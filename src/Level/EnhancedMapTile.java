package Level;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

// This class is a base class for all enhanced map tiles in the game -- all enhanced map tiles should extend from it
public class EnhancedMapTile extends MapTile {

    public EnhancedMapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType) {
        super(x, y, spriteSheet, startingAnimation, tileType);
    }

    public EnhancedMapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType) {
        super(x, y, animations, startingAnimation, tileType);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType) {
        super(image, x, y, startingAnimation, tileType);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType) {
        super(image, x, y, tileType);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale) {
        super(image, x, y, scale, tileType);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect, tileType);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds, tileType);
    }

    @Override
    public void initialize() {
        super.initialize();
    }


    public void update(Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
