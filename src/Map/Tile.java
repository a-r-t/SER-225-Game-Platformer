package Map;

import Engine.Graphics;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Tile extends AnimatedSprite {

    public Tile(SpriteSheet spriteSheet, float x, float y) {
        super(spriteSheet, x, y);
    }

    public Tile(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations() {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                    new Frame(spriteSheet.getSprite(0, 0), 3,0)
            });
        }};
    }

    @Override
    public String getStartingAnimation() {
        return "DEFAULT";
    }

    public void update() {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
