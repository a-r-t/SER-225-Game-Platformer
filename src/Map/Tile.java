package Map;

import GameObject.AnimatedSprite;
import GameObject.SpriteSheet;

import java.awt.*;

public class Tile extends AnimatedSprite {
    private TileType tileType;

    public Tile(float x, float y, int width, int height, SpriteSheet spriteSheet, TileType tileType) {
        super(x, y, width, height, spriteSheet);
        this.tileType = tileType;
    }

    public Tile(float x, float y, int width, int height, String imageFile, TileType tileType) {
        super(x, y, width, height, imageFile);
        this.tileType = tileType;
    }

    public Tile(float x, float y, int width, int height, String imageFile, Color transparentColor, TileType tileType) {
        super(x, y, width, height, imageFile, transparentColor);
        this.tileType = tileType;
    }

    public void update() {
        super.update(null);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
