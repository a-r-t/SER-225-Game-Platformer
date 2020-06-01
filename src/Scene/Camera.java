package Scene;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.ScreenManager;
import GameObject.Rectangle;
import NPCs.Walrus;

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
    private ArrayList<NPC> activeNPCs = new ArrayList<>();

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
        activeNPCs = loadActiveNPCs();

        for (Enemy enemy : activeEnemies) {
            enemy.update(keyboard, map, player);
        }

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            enhancedMapTile.update(keyboard, map, player);
        }

        for (NPC npc : activeNPCs) {
            npc.update(keyboard, map, player);
        }
    }

    private ArrayList<Enemy> loadActiveEnemies() {
        ArrayList<Enemy> activeEnemies = new ArrayList<>();
        for (Enemy enemy: map.getEnemies()) {
            enemy.calibrate(map);

            if (isMapEntityActive(enemy)) {
                activeEnemies.add(enemy);
                if (enemy.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    enemy.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (enemy.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                enemy.setMapEntityStatus(MapEntityStatus.INACTIVE);
                if (enemy.isRespawnable()) {
                    enemy.initialize();
                }
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

    private ArrayList<NPC> loadActiveNPCs() {
        ArrayList<NPC> activeNPCs = new ArrayList<>();
        for (NPC npc: map.getNPCs()) {
            npc.calibrate(map);

            if (isMapEntityActive(npc)) {
                activeNPCs.add(npc);
                if (npc.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    if (npc.isRespawnable()) {
                        npc.initialize();
                    }
                    npc.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (npc.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                npc.setMapEntityStatus(MapEntityStatus.INACTIVE);
            }
        }
        return activeNPCs;
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
        for (Enemy enemy : activeEnemies) {
            if (contains(enemy)) {
                enemy.draw(graphicsHandler);
            }
        }
        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            if (contains(enhancedMapTile)) {
                enhancedMapTile.draw(graphicsHandler);
            }
        }
        for (NPC npc : activeNPCs) {
            if (contains(npc)) {
                npc.draw(graphicsHandler);
            }
        }
    }

    public boolean contains(MapEntity mapEntity) {
        return getX1() - tileWidth < mapEntity.getScaledX2() + amountMovedX && getEndBoundX() + tileWidth > mapEntity.getX1() + amountMovedX &&
                getY1() - tileHeight <  mapEntity.getScaledY2() + amountMovedY && getEndBoundY() + tileHeight >  mapEntity.getY1() + amountMovedY;
    }

    public ArrayList<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    public ArrayList<EnhancedMapTile> getActiveEnhancedMapTiles() {
        return activeEnhancedMapTiles;
    }

    public ArrayList<NPC> getActiveNPCs() {
        return activeNPCs;
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
