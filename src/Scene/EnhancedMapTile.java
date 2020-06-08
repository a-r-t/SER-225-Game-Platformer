package Scene;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class EnhancedMapTile extends MapTile {

    public EnhancedMapTile(float x, float y, SpriteSheet spriteSheet, String startingAnimation, TileType tileType, Map map) {
        super(x, y, spriteSheet, startingAnimation, tileType, map);
    }

    public EnhancedMapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, TileType tileType, Map map) {
        super(x, y, animations, startingAnimation, tileType, map);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, String startingAnimation, TileType tileType, Map map) {
        super(image, x, y, startingAnimation, tileType, map);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, Map map) {
        super(image, x, y, tileType, map);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, Map map) {
        super(image, x, y, scale, tileType, map);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect, Map map) {
        super(image, x, y, scale, imageEffect, tileType, map);
    }

    public EnhancedMapTile(BufferedImage image, float x, float y, TileType tileType, float scale, ImageEffect imageEffect, Rectangle bounds, Map map) {
        super(image, x, y, scale, imageEffect, bounds, tileType, map);
    }

    @Override
    public void initialize() {
        super.initialize();
    }


    public void update(Keyboard keyboard, Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
