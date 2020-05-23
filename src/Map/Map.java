package Map;

import Engine.Graphics;
import Game.Kirby;
import GameObject.Rectangle;

import java.awt.*;

public abstract class Map {
    protected MapTile[] tiles;
    protected int[] movementPermissions;
    protected int width;
    protected int height;
    protected Tileset tileset;
    protected Camera camera;
    protected Point playerStart;
    protected int xMidPoint, yMidPoint;
    protected int startBoundX, startBoundY, endBoundX, endBoundY;

    public Map(int width, int height, Tileset tileset, Rectangle screenBounds, Point playerStart) {
        this.tileset = tileset;
        tiles = new MapTile[height * width];
        camera = new Camera(0, 0, screenBounds, tileset.getScaledSpriteWidth(), tileset.getScaledSpriteHeight());
        this.startBoundX = 0;
        this.startBoundY = 0;
        this.endBoundX = width * tileset.getScaledSpriteWidth();
        this.endBoundY = height * tileset.getScaledSpriteHeight();
        this.xMidPoint = screenBounds.getWidth() / 2;
        this.yMidPoint = screenBounds.getHeight() / 2;
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
    public Rectangle getCamera() {
        return camera;
    }

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
        int xIndex = (xPosition + camera.getX()) / Math.round(tileset.getScaledSpriteWidth());
        int yIndex = (yPosition + camera.getY()) / Math.round(tileset.getScaledSpriteHeight());
        return new Point(xIndex, yIndex);
    }

    private boolean isInBounds(int x, int y) {
        int index = x + width * y;
        return x >= 0 && y >= 0 && x < width && y < height && index >= 0 && index < tiles.length;
    }

    public void update(Kirby player) {
        int xMidPointDifference = 0;
        if (player.getX() > xMidPoint && camera.getEndBoundX() < endBoundX) {
            xMidPointDifference = xMidPoint - player.getX();
            player.moveX(xMidPointDifference);
            camera.moveX(-xMidPointDifference);
            if (camera.getEndBoundX() > endBoundX) {
                int cameraDifference = camera.getEndBoundX() - endBoundX;
                player.moveX(cameraDifference);
                camera.moveX(-cameraDifference);
            }
        } else if (player.getX() < xMidPoint && camera.getX() > startBoundX) {
            xMidPointDifference = xMidPoint - player.getX();
            player.moveX(xMidPointDifference);
            camera.moveX(-xMidPointDifference);
            if (camera.getX() < startBoundX) {
                int cameraDifference = startBoundX - camera.getX();
                player.moveX(-cameraDifference);
                camera.moveX(cameraDifference);
            }
        }

        Point tileIndex = getTileIndexByPosition(0, 0);
        for (int i = tileIndex.y - 1; i <= tileIndex.y + camera.getHeight() + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + camera.getWidth() + 1; j++) {
                MapTile tile = getTile(j, i);
                if (tile != null) {
                    int tileStartX = j * tile.getScaledWidth();
                    int tileStartY = i * tile.getScaledHeight();
                    tile.setX(tileStartX - camera.getX());
                    tile.setY(tileStartY - camera.getY());
                    tile.update(this, player);
                }
            }
        }
    }

    public void draw(Graphics graphics) {
        Point tileIndex = getTileIndexByPosition(0, 0);
        for (int i = tileIndex.y - 1; i <= tileIndex.y + camera.getHeight() + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + camera.getWidth() + 1; j++) {
                MapTile tile = getTile(j, i);
                if (tile != null) {
                    tile.draw(graphics);
                    if (getMovementPermission(j, i) == 1) {
                        tile.drawBounds(graphics, new Color(0, 0, 255, 170));
                    }
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
