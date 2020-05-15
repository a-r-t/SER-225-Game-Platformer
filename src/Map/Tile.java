package Map;

import Engine.Painter;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Tile extends AnimatedSprite {

    public Tile(float x, float y, int width, int height, SpriteSheet spriteSheet) {
        super(x, y, width, height, spriteSheet);
    }

    public Tile(float x, float y, int width, int height, BufferedImage image) {
        super(x, y, width, height, image);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations() {
        return null;
    }

    public void update() {
        super.update(null);
    }

    public void draw(Painter painter) {
        super.draw(painter);
    }
}
