package Map;

import GameObject.Sprite;

import java.awt.*;

public abstract class Map {
    protected Sprite[] tiles;
    protected int width;
    protected int height;
    protected Tileset tileset;

    public Map(int width, int height) {
        int[] map = createMap();
        tiles = new Sprite[height * width];
        this.width = width;
        this.height = height;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Sprite tile = tileset.getTiles().get(map[width * j + i]);
                tile.setX(j * tileset.getSpriteWidth());
                tile.setY(i * tileset.getSpriteHeight());
            }
        }
    }

    public abstract int[] createMap();

    public Sprite getTile(int x, int y) {
        return tiles[width * x + y];
    }

    public void setTile(int x, int y, Sprite tile) {
        tiles[width * x + y] = tile;
    }

    public void update() {
        for (Sprite tile : tiles) {
            tile.update(null);
        }
    }

    public void draw(Graphics2D g) {
        for (Sprite tile : tiles) {
            tile.draw(g);
        }
    }
}
