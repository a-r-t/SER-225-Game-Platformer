package Map;

import Engine.Graphics;
import Game.Kirby;

import GameObject.Sprite;

import java.awt.image.BufferedImage;

public class MapTile extends Sprite implements Tile {
    private boolean hasCollision = false;

    public static class MapTileBuilder {
        private BufferedImage image;
        private boolean hasCollision;

        public MapTileBuilder(BufferedImage image) {
            this.image = image;
            hasCollision = false;
        }

        public MapTileBuilder hasCollision(boolean hasCollision) {
            this.hasCollision = hasCollision;
            return this;
        }

        public MapTile build(float x, float y, float scale) {
            return new MapTile(image, x, y, scale, hasCollision);
        }
    }

    private MapTile(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    private MapTile(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    private MapTile(BufferedImage image, float x, float y, boolean hasCollision) {
        super(image, x, y);
        this.hasCollision = hasCollision;
    }

    private MapTile(BufferedImage image, float x, float y, float scale, boolean hasCollision) {
        super(image, x, y, scale);
        this.hasCollision = hasCollision;
    }

    public void update(Map map, Kirby kirby) {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
