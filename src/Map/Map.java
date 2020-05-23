package Map;

import Engine.Graphics;
import GameObject.Rectangle;

import java.awt.*;

public abstract class Map {
    protected MapTile[] tiles;
    protected int[] movementPermissions;
    protected int width;
    protected int height;
    protected Tileset tileset;
    protected Rectangle camera;
    protected Point playerStart;

    public Map(int width, int height, Tileset tileset, Rectangle screenBounds, Point playerStart) {
        this.tileset = tileset;
        tiles = new MapTile[height * width];
        camera = new Rectangle(0, 0, screenBounds.getWidth() / tileset.getSpriteWidth(), screenBounds.getHeight() / tileset.getSpriteHeight());
        this.width = width;
        this.height = height;
        this.playerStart = playerStart;
        int[] map = createMap();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                MapTile tile = tileset.getTile(map[j + width * i])
                        .build(j * tileset.getScaledSpriteWidth(), i * tileset.getScaledSpriteHeight());
                setTile(j, i, tile);
            }
        }
        movementPermissions = createMovementPermissions();
    }

    public abstract int[] createMap();
    public abstract int[] createMovementPermissions();

    public MapTile getTile(int x, int y) {
        if (isInBounds(x, y)) {
            return tiles[x + width * y];
        } else {
            return null;
        }
    }

    public MapTile getTile(Point index) {
        if (isInBounds(index.x, index.y)) {
            return tiles[index.x + width * index.y];
        } else {
            return null;
        }
    }

    public void setTile(int x, int y, MapTile tile) {
        tiles[x + width * y] = tile;
    }

    public void setTile(Point index, MapTile tile) {
        tiles[index.x + width * index.y] = tile;
    }

    public int getTileWidth() {
        return tileset.getScaledSpriteWidth();
    }
    public int getTileHeight() {
        return tileset.getScaledSpriteHeight();
    }

    public int getMovementPermission(int x, int y) {
        if (isInBounds(x, y)) {
            return movementPermissions[x + width * y];
        } else {
            return -1;
        }
    }
    public void setMovementPermission(int x, int y, int movementPermission) { movementPermissions[x + width * y] = movementPermission; }

    public MapTile getTileByPosition(int xPosition, int yPosition) {
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

    public Point getTileIndexByPosition(int xPosition, int yPosition) {
        int xIndex = xPosition / Math.round(tileset.getScaledSpriteWidth());
        int yIndex = yPosition / Math.round(tileset.getScaledSpriteHeight());
        return new Point(xIndex, yIndex);
    }

    private boolean isInBounds(int x, int y) {
        int index = x + width * y;
        return index >= 0 && index < tiles.length;
    }

    public void update() {
        for (MapTile tile : tiles) {
            tile.update(this, null);
        }
    }

    public void draw(Graphics graphics) {
        for (int i = camera.getY1() - 1; i < camera.getY2() + 1; i++) {
            for (int j = camera.getX1() - 1; j < camera.getX2() + 1; j++) {
                if (isInBounds(j, i) && tiles[j + width * i] != null) {
                    getTile(j, i).draw(graphics);
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
