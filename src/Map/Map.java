package Map;

import Engine.Painter;
import GameObject.Rectangle;
import GameObject.Sprite;

import java.awt.*;

public abstract class Map {
    protected Tile[] tiles;
    protected int[] movementPermissions;
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
        movementPermissions = createMovementPermissions();
    }

    public abstract int[] createMap();
    public abstract int[] createMovementPermissions();

    public Tile getTile(int x, int y) {
        return tiles[x + width * y];
    }
    public void setTile(int x, int y, Tile tile) {
        tiles[x + width * y] = tile;
    }

    public int getMovementPermission(int x, int y) { return movementPermissions[x + width * y]; }
    public void setMovementPermission(int x, int y, int movementPermission) { movementPermissions[x + width * y] = movementPermission; }

    public Tile getTileByPosition(int xPosition, int yPosition) {
        int xIndex = xPosition / Math.round(tileset.getSpriteWidth() * tileset.getScale());
        int yIndex = yPosition / Math.round(tileset.getSpriteHeight() * tileset.getScale());
        if (isInBounds(xIndex, yIndex)) {
            return getTile(xIndex, yIndex);
        } else {
            return null;
        }
    }

    public int getMovementPermissionByPosition(int xPosition, int yPosition) {
        int xIndex = xPosition / Math.round(tileset.getSpriteWidth() * tileset.getScale());
        int yIndex = yPosition / Math.round(tileset.getSpriteHeight() * tileset.getScale());
        if (isInBounds(xIndex, yIndex)) {
            return getMovementPermission(xIndex, yIndex);
        } else {
            return -1;
        }
    }
    private boolean isInBounds(int x, int y) {
        int index = x + width * y;
        return index >= 0 && index < tiles.length;
    }

    public void update() {
        for (Tile tile : tiles) {
            tile.update();
        }
    }

    public void draw(Painter painter) {
        for (int i = camera.getY1() - 1; i < camera.getY2() + 1; i++) {
            for (int j = camera.getX1() - 1; j < camera.getX2() + 1; j++) {
                if (isInBounds(j, i) && tiles[j + width * i] != null) {
                    tiles[j + width * i].draw(painter);
                }
            }
        }
    }

    public Point getPlayerStart() {
        return playerStart;
    }
    public Tileset getTileset() {
        return tileset;
    }
}
