package Map;

import GameObject.Rectangle;
import GameObject.Sprite;

import java.awt.*;

public abstract class Map {
    protected Tile[] tiles;
    protected int width;
    protected int height;
    protected Tileset tileset;
    protected Rectangle camera;
    protected Point playerStart;

    public Map(int width, int height, Tileset tileset, Rectangle screenBounds, Point playerStart) {
        this.tileset = tileset;
        tiles = new Tile[height * width];
        camera = new Rectangle(0, 0, screenBounds.getWidth() / tileset.getSpriteWidth(), screenBounds.getHeight() / tileset.getSpriteHeight());
        this.width = width;
        this.height = height;
        this.playerStart = playerStart;
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
        for (int i = camera.getY1() - 1; i < camera.getY2() + 1; i++) {
            for (int j = camera.getX1() - 1; j < camera.getX2() + 1; j++) {
                if (j + width * i >= 0 && j + width * i < tiles.length && tiles[j + width * i] != null) {
                    tiles[j + width * i].draw(g);
                }
            }
        }
    }

    public Point getPlayerStart() {
        return playerStart;
    }
}
