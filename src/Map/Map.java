package Map;

import Engine.Config;
import Engine.Graphics;
import Game.Kirby;
import GameObject.Rectangle;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Map {
    protected MapTile[] tiles;
    protected int[] tileIndexes;
    protected int[] movementPermissions;
    protected int width;
    protected int height;
    protected Tileset tileset;
    protected Camera camera;
    protected Point playerStartTile;
    protected int xMidPoint, yMidPoint;
    protected int startBoundX, startBoundY, endBoundX, endBoundY;

    public Map(String mapFileName, Tileset tileset, Rectangle screenBounds, Point playerStartTile) {
        this.tileset = tileset;
        loadMapFile(mapFileName);
        camera = new Camera(0, 0, screenBounds, tileset.getScaledSpriteWidth(), tileset.getScaledSpriteHeight());
        this.startBoundX = 0;
        this.startBoundY = 0;
        this.endBoundX = width * tileset.getScaledSpriteWidth();
        this.endBoundY = height * tileset.getScaledSpriteHeight();
        this.xMidPoint = screenBounds.getWidth() / 2;
        this.yMidPoint = (screenBounds.getHeight() / 2);
        this.playerStartTile = playerStartTile;
    }

    private void loadMapFile(String mapFileName) {
        Scanner fileInput;
        try {
            fileInput = new Scanner(new File(Config.MAP_FILES_PATH + mapFileName));
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Map file not found!");
            throw new RuntimeException();
        }
        this.width = fileInput.nextInt();
        this.height = fileInput.nextInt();
        this.tiles = new MapTile[this.height * this.width];
        this.tileIndexes = new int[this.height * this.width];
        fileInput.nextLine();
        fileInput.nextLine();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tileIndex = fileInput.nextInt();
                MapTile tile = tileset.getTile(tileIndex)
                        .build(j * tileset.getScaledSpriteWidth(), i * tileset.getScaledSpriteHeight());
                setTile(j, i, tile);
                this.tileIndexes[j + this.width * i] = tileIndex;
            }
        }
        fileInput.nextLine();
        fileInput.nextLine();

        this.movementPermissions = new int[this.height * this.width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int movementPermissionIndex = fileInput.nextInt();
                setMovementPermission(j, i, movementPermissionIndex);
            }
        }

        fileInput.close();
    }

    public MapTile[] getMapTiles() {
        return tiles;
    }

    public int[] getMapTileIndexes() {
        return tileIndexes;
    }

    public int[] getMovementPermissions() {
        return movementPermissions;
    }

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

    public Point getTileIndexByCameraPosition() {
        int xIndex = camera.getX() / Math.round(tileset.getScaledSpriteWidth());
        int yIndex = camera.getY() / Math.round(tileset.getScaledSpriteHeight());
        return new Point(xIndex, yIndex);
    }

    private boolean isInBounds(int x, int y) {
        int index = x + width * y;
        return x >= 0 && y >= 0 && x < width && y < height && index >= 0 && index < tiles.length;
    }

    public void update(Kirby player) {
        adjustCameraY(player);
        adjustCameraX(player);

        Point tileIndex = getTileIndexByCameraPosition();
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

    private void adjustCameraX(Kirby player) {
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
    }

    private void adjustCameraY(Player player) {
        int yMidPointDifference = 0;
        if (player.getY() > yMidPoint && camera.getEndBoundY() < endBoundY) {
            yMidPointDifference = yMidPoint - player.getY();
            player.moveY(yMidPointDifference);
            camera.moveY(-yMidPointDifference);
            if (camera.getEndBoundY() > endBoundY) {
                int cameraDifference = camera.getEndBoundY() - endBoundY;
                player.moveY(cameraDifference);
                camera.moveY(-cameraDifference);
            }
        } else if (player.getY() < yMidPoint && camera.getY() > startBoundY) {
            yMidPointDifference = yMidPoint - player.getY();
            player.moveY(yMidPointDifference);
            camera.moveY(-yMidPointDifference);
            if (camera.getY() < startBoundY) {
                int cameraDifference = startBoundY - camera.getY();
                player.moveY(-cameraDifference);
                camera.moveY(cameraDifference);
            }
        }
    }

    public void draw(Graphics graphics) {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + camera.getHeight() + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + camera.getWidth() + 1; j++) {
                MapTile tile = getTile(j, i);
                if (tile != null) {
                    tile.draw(graphics);
//                    if (getMovementPermission(j, i) == 1) {
//                        tile.drawBounds(graphics, new Color(0, 0, 255, 170));
//                    }
                }
            }
        }
    }

    public Point getPlayerStartPosition() {
        MapTile tile = getTile(playerStartTile.x, playerStartTile.y);
        return new Point(tile.getX(), tile.getY());
    }
    public Tileset getTileset() {
        return tileset;
    }

    public int getWidthPixels() {
        return width * tileset.getScaledSpriteWidth();
    }

    public int getHeightPixels() {
        return height * tileset.getScaledSpriteHeight();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
