package Map;

import Engine.Painter;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

public class Tile extends AnimatedSprite {

    public Tile(SpriteSheet spriteSheet, float x, float y) {
        super(spriteSheet, x, y);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations() {
        return null;
    }

    @Override
    public String getStartingAnimation() {
        return "";
    }

    public void update() {
        super.update();
    }

    public void draw(Painter painter) {
        super.draw(painter);
    }
}
