package Map;

import Engine.Graphics;
import Game.Kirby;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.Frame.FrameBuilder;
import GameObject.Sprite;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Tile extends AnimatedSprite {
    private boolean hasCollision = false;

    public static class TileBuilder {
        private BufferedImage image;
        private boolean hasCollision;

        public TileBuilder(BufferedImage image) {
            this.image = image;
            hasCollision = false;
        }

        public TileBuilder hasCollision(boolean hasCollision) {
            this.hasCollision = hasCollision;
            return this;
        }

        public Tile build(float x, float y) {
            return new Tile(image, x, y, hasCollision);
        }
    }

    public Tile(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations() {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0,0), 0).withScale(3).build()
            });
        }};
    }

    @Override
    public String getStartingAnimation() {
        return "DEFAULT";
    }

    public Tile(BufferedImage image, float x, float y, boolean hasCollision) {
        super(image, x, y);
        this.hasCollision = hasCollision;
    }

    public void update(Map map, Kirby kirby) {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
