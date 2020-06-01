package Scene;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.ScreenManager;
import GameObject.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Camera extends Rectangle {

    private float startPositionX, startPositionY;
    private Map map;
    private int tileWidth, tileHeight;
    private int leftoverSpaceX, leftoverSpaceY;
    private float amountMovedX, amountMovedY;
    private ArrayList<Enemy> activeEnemies = new ArrayList<>();
    private ArrayList<EnhancedMapTile> activeEnhancedMapTiles = new ArrayList<>();

    public Camera(int startX, int startY, int tileWidth, int tileHeight, Map map) {
        super(startX, startY, ScreenManager.getScreenWidth() / tileWidth, ScreenManager.getScreenHeight() / tileHeight);
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.leftoverSpaceX = ScreenManager.getScreenWidth() % tileWidth;
        this.leftoverSpaceY = ScreenManager.getScreenHeight() % tileHeight;
        this.startPositionX = startX;
        this.startPositionY = startY;
    }

    public Point getTileIndexByCameraPosition() {
        int xIndex = getX() / tileWidth;
        int yIndex = getY() / tileHeight;
        return new Point(xIndex, yIndex);
    }

    public void update(Keyboard keyboard, Player player) {
        updateMapTiles();
        updateMapEntities(keyboard, player);
    }

    private void updateMapTiles() {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + height + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + width + 1; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null) {
                    tile.calibrate(map);
                    tile.update();
                }
            }
        }
    }

    public void updateMapEntities(Keyboard keyboard, Player player) {
        activeEnemies = loadActiveEnemies();
        activeEnhancedMapTiles = loadActiveEnhancedMapTiles();

        for (Enemy enemy: map.getActiveEnemies()) {
            enemy.update(keyboard, map, player);
        }

        for (EnhancedMapTile enhancedMapTile: map.getActiveEnhancedMapTiles()) {
            enhancedMapTile.update(keyboard, map, player);
        }
    }

    private ArrayList<Enemy> loadActiveEnemies() {
        ArrayList<Enemy> activeEnemies = new ArrayList<>();
        for (Enemy enemy: map.getEnemies()) {
            enemy.calibrate(map);

            if (isMapEntityActive(enemy)) {
                activeEnemies.add(enemy);
                if (enemy.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    if (enemy.isRespawnable()) {
                        enemy.initialize();
                    }
                    enemy.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (enemy.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                enemy.setMapEntityStatus(MapEntityStatus.INACTIVE);
            }
        }
        return activeEnemies;
    }

    private ArrayList<EnhancedMapTile> loadActiveEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> activeEnhancedMapTiles = new ArrayList<>();
        for (EnhancedMapTile enhancedMapTile: map.getEnhancedMapTiles()) {
            enhancedMapTile.calibrate(map);

            if (isMapEntityActive(enhancedMapTile)) {
                activeEnhancedMapTiles.add(enhancedMapTile);
                if (enhancedMapTile.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    if (enhancedMapTile.isRespawnable()) {
                        enhancedMapTile.initialize();
                    }
                    enhancedMapTile.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (enhancedMapTile.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                enhancedMapTile.setMapEntityStatus(MapEntityStatus.INACTIVE);
            }
        }
        return activeEnhancedMapTiles;
    }

    private boolean isMapEntityActive(MapEntity mapEntity) {
        return mapEntity.getMapEntityStatus() != MapEntityStatus.REMOVED && (contains(mapEntity));
    }

    public void draw(GraphicsHandler graphicsHandler) {
        drawMapTiles(graphicsHandler);
        drawMapEntities(graphicsHandler);
    }

    public void drawMapTiles(GraphicsHandler graphicsHandler) {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - 1; i <= tileIndex.y + height + 1; i++) {
            for (int j = tileIndex.x - 1; j <= tileIndex.x + width + 1; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null) {
                    tile.draw(graphicsHandler);
                }
            }
        }
    }

    public void drawMapEntities(GraphicsHandler graphicsHandler) {
        for (Enemy enemy : map.getActiveEnemies()) {
            if (contains(enemy)) {
                enemy.draw(graphicsHandler);
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (contains(enhancedMapTile)) {
                enhancedMapTile.draw(graphicsHandler);
            }
        }
    }

    public boolean contains(MapEntity mapEntity) {
        return getX1() - tileWidth - amountMovedX < mapEntity.getX1() && getEndBoundX() + tileWidth - amountMovedX >  mapEntity.getScaledX2() &&
                getY1() - tileHeight - amountMovedY <  mapEntity.getY1() && getEndBoundY() + tileHeight - amountMovedY >  mapEntity.getScaledY2();
    }

    public ArrayList<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    public ArrayList<EnhancedMapTile> getActiveEnhancedMapTiles() {
        return activeEnhancedMapTiles;
    }

    public int getStartBoundX() {
        return getX();
    }

    public int getStartBoundY() {
        return getY();
    }

    public int getEndBoundX() {
        return getX1() + (width * tileWidth) + leftoverSpaceX;
    }

    public int getEndBoundY() {
        return getY1() + (height * tileHeight) + leftoverSpaceY;
    }

    public int getStartPositionX() {
        return (int)startPositionX;
    }

    public int getStartPositionY() {
        return (int)startPositionY;
    }

    public int getAmountMovedX() {
        return (int)amountMovedX;
    }

    public int getAmountMovedY() {
        return (int)amountMovedY;
    }

    @Override
    public void moveX(float dx) {
        this.amountMovedX += dx;
        super.moveX(dx);
    }

    @Override
    public void moveY(float dy) {
        this.amountMovedY += dy;
        super.moveY(dy);
    }
}
