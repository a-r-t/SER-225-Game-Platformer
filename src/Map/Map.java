package Map;

import GameObject.Sprite;

import java.awt.*;

public abstract class Map {
    protected Tile[] tiles;
    protected int width;
    protected int height;
    protected Tileset tileset;

    public Map(int width, int height, Tileset tileset) {
        this.tileset = tileset;
        tiles = new Tile[height * width];
        this.width = width;
        this.height = height;
        int[] map = createMap();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = tileset.createTile(map[j + width * i], j, i);
                setTile(j, i, tile);
            }
        }
    }

    public abstract int[] createMap();

    public Sprite getTile(int x, int y) {
        return tiles[x + width * y];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x + width * y] = tile;
    }

    public void update() {
        for (Tile tile : tiles) {
            tile.update(null);
        }
    }

    public void draw(Graphics2D g) {
        for (Tile tile : tiles) {
            tile.draw(g);
        }
    }
}
