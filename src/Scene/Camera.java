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
    private final int UPDATE_OFF_SCREEN_RANGE = 4;

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
        int xIndex = Math.round(getX()) / tileWidth;
        int yIndex = Math.round(getY()) / tileHeight;
        return new Point(xIndex, yIndex);
    }

    public void update(Keyboard keyboard, Player player) {
        updateMapTiles();
        updateMapEntities(keyboard, player);
    }

    private void updateMapTiles() {
        Point tileIndex = getTileIndexByCameraPosition();
        for (int i = tileIndex.y - UPDATE_OFF_SCREEN_RANGE; i <= tileIndex.y + height + UPDATE_OFF_SCREEN_RANGE; i++) {
            for (int j = tileIndex.x - UPDATE_OFF_SCREEN_RANGE; j <= tileIndex.x + width + UPDATE_OFF_SCREEN_RANGE; j++) {
                MapTile tile = map.getMapTile(j, i);
                if (tile != null) {
                    tile.update();
                }
            }
        }
    }

    public void updateMapEntities(Keyboard keyboard, Player player) {
        activeEnemies = loadActiveObjects(Enemy.class);
        activeEnhancedMapTiles = loadActiveObjects(EnhancedMapTile.class);
        activeNPCs = loadActiveObjects(NPC.class);

        for (Enemy enemy : activeEnemies) {
            enemy.update(keyboard, player);
        }

        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            enhancedMapTile.update(keyboard, player);
        }

        for (NPC npc : activeNPCs) {
            npc.update(keyboard, player);
        }
    }

    private <T extends MapEntity> ArrayList<T> getValidEnts(Class<T> entClass) {
        if (entClass.equals(Enemy.class)) {
            return (ArrayList<T>)this.map.getEnemies();
        }
        else if (entClass.equals(EnhancedMapTile.class)) {
            return (ArrayList<T>)this.map.getEnhancedMapTiles();
        }
        else if (entClass.equals(NPC.class)) {
            return (ArrayList<T>)this.map.getNPCs();
        }
        else {
            throw new IllegalArgumentException("Entity must be an Enemy, EnhancedMapTile, or NPC!");
        }
    }

    private <T extends MapEntity> ArrayList<T> loadActiveObjects(Class<T> entClass) {
        ArrayList<T> activeObjs = new ArrayList<T>();

        for (T ent: this.getValidEnts(entClass)) {
            if (isMapEntityActive(ent)) {
                activeObjs.add(ent);
                if (ent.mapEntityStatus == MapEntityStatus.INACTIVE) {
                    ent.setMapEntityStatus(MapEntityStatus.ACTIVE);
                }
            } else if (ent.getMapEntityStatus() == MapEntityStatus.ACTIVE) {
                ent.setMapEntityStatus(MapEntityStatus.INACTIVE);
                if (ent.isRespawnable()) {
                    ent.initialize();
                }
            }
        }
        return activeObjs;
    }

    private boolean isMapEntityActive(MapEntity mapEntity) {
        return mapEntity.getMapEntityStatus() != MapEntityStatus.REMOVED && (mapEntity.isUpdateOffScreen() || containsUpdate(mapEntity));
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
            if (containsDraw(enemy)) {
                enemy.draw(graphicsHandler);
            }
        }
        for (EnhancedMapTile enhancedMapTile : activeEnhancedMapTiles) {
            if (containsDraw(enhancedMapTile)) {
                enhancedMapTile.draw(graphicsHandler);
            }
        }
        for (NPC npc : activeNPCs) {
            if (containsDraw(npc)) {
                npc.draw(graphicsHandler);
            }
        }
    }

    public boolean containsUpdate(MapEntity mapEntity) {
        return getX1() - (tileWidth * UPDATE_OFF_SCREEN_RANGE) < mapEntity.getX() + mapEntity.getScaledWidth() &&
                getEndBoundX() + (tileWidth * UPDATE_OFF_SCREEN_RANGE) > mapEntity.getX() &&
                getY1() - (tileHeight * UPDATE_OFF_SCREEN_RANGE) <  mapEntity.getY() + mapEntity.getScaledHeight()
                && getEndBoundY() + (tileHeight * UPDATE_OFF_SCREEN_RANGE) > mapEntity.getY();
    }

    public boolean containsDraw(MapEntity mapEntity) {
        return getX1() - tileWidth < mapEntity.getX() + mapEntity.getScaledWidth() && getEndBoundX() + tileWidth > mapEntity.getX() &&
                getY1() - tileHeight <  mapEntity.getY() + mapEntity.getScaledHeight() && getEndBoundY() + tileHeight >  mapEntity.getY();
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

    public float getEndBoundX() {
        return x + (width * tileWidth) + leftoverSpaceX;
    }

    public float getEndBoundY() {
        return y + (height * tileHeight) + leftoverSpaceY;
    }
}
