package Map;

import Engine.Graphics;
import GameObject.Sprite;
import java.awt.image.BufferedImage;

public class Tile extends Sprite {
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

    public Tile(BufferedImage image, float x, float y, boolean hasCollision) {
        super(image, x, y);
        this.hasCollision = hasCollision;
    }

    public void update() {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
