package Map;

import GameObject.AnimatedSprite;
import GameObject.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends AnimatedSprite {
    private TileType tileType;

    public Tile(float x, float y, int width, int height, SpriteSheet spriteSheet, TileType tileType) {
        super(x, y, width, height, spriteSheet);
        this.tileType = tileType;
    }

    public Tile(float x, float y, int width, int height, BufferedImage image, TileType tileType) {
        super(x, y, width, height, image);
        this.tileType = tileType;
    }

    public Tile(float x, float y, int width, int height, BufferedImage image, Color transparentColor, TileType tileType) {
        super(x, y, width, height, image, transparentColor);
        this.tileType = tileType;
    }

    public void update() {
        super.update(null);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
