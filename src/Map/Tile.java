package Map;

import GameObject.AnimatedSprite;
import GameObject.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends AnimatedSprite {

    public Tile(float x, float y, int width, int height, SpriteSheet spriteSheet) {
        super(x, y, width, height, spriteSheet);
    }

    public Tile(float x, float y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    public Tile(float x, float y, int width, int height, BufferedImage image, Color transparentColor) {
        super(x, y, width, height, image, transparentColor);
    }

    public void update() {
        super.update(null);
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
