package Scene;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.ScreenManager;
import Utils.Point;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Map {
    protected MapTile[] mapTiles;
    protected int width;
    protected int height;
    protected Tileset tileset;
    protected Camera camera;
    protected Point playerStartTile;
    protected int xMidPoint, yMidPoint;
    protected int startBoundX, startBoundY, endBoundX, endBoundY;
    protected String mapFileName;

    protected ArrayList<Enemy> enemies;
    protected ArrayList<EnhancedMapTile> enhancedMapTiles;
    protected ArrayList<NPC> npcs;
    protected boolean adjustCamera = true;

    public Map(String mapFileName, Tileset tileset, Point playerStartTile) {
        this.mapFileName = mapFileName;
        this.tileset = tileset;
        setupMap();
        this.startBoundX = 0;
        this.startBoundY = 0;
        this.endBoundX = width * tileset.getScaledSpriteWidth();
        this.endBoundY = height * tileset.getScaledSpriteHeight();
        this.xMidPoint = ScreenManager.getScreenWidth() / 2;
        this.yMidPoint = (ScreenManager.getScreenHeight() / 2);
        this.playerStartTile = playerStartTile;
    }

    public void setupMap() {
        loadMapFile();

        this.enemies = loadEnemies();
        for (Enemy enemy: this.enemies) {
            enemy.setMap(this);
        }

        this.enhancedMapTiles = loadEnhancedMapTiles();
        for (EnhancedMapTile enhancedMapTile: this.enhancedMapTiles) {
            enhancedMapTile.setMap(this);
        }

        this.npcs = loadNPCs();
        for (NPC npc: this.npcs) {
            npc.setMap(this);
        }

        this.camera = new Camera(0, 0, tileset.getScaledSpriteWidth(), tileset.getScaledSpriteHeight(), this);
    }

    private void loadMapFile() {
        Scanner fileInput;
        try {
            fileInput = new Scanner(new File(Config.MAP_FILES_PATH + this.mapFileName));
        } catch(FileNotFoundException ex) {
            System.out.println("Map file " + Config.MAP_FILES_PATH + this.mapFileName + " not found! Creating empty map file...");

            try {
                createEmptyMapFile();
                fileInput = new Scanner(new File(Config.MAP_FILES_PATH + this.mapFileName));
            } catch(IOException ex2) {
                ex2.printStackTrace();
                System.out.println("Failed to create an empty map file!");
                throw new RuntimeException();
            }
        }

        this.width = fileInput.nextInt();
        this.height = fileInput.nextInt();
        this.mapTiles = new MapTile[this.height * this.width];
        fileInput.nextLine();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int tileIndex = fileInput.nextInt();
                MapTile tile = tileset.getTile(tileIndex)
                        .build(j * tileset.getScaledSpriteWidth(), i * tileset.getScaledSpriteHeight());
                tile.setMap(this);
                setMapTile(j, i, tile);
            }
        }

        fileInput.close();
    }

    private void createEmptyMapFile() throws IOException {
        FileWriter fileWriter = null;
        fileWriter = new FileWriter(Config.MAP_FILES_PATH + this.mapFileName);
        fileWriter.write("0 0\n");
        fileWriter.close();
    }

    public Point getPlayerStartPosition() {
        MapTile tile = getMapTile(Math.round(playerStartTile.x), Math.round(playerStartTile.y));
        return new Point(tile.getX(), tile.getY());
    }

    public Point getPositionByTileIndex(int xIndex, int yIndex) {
        MapTile tile = getMapTile(xIndex, yIndex);
        return new Point(tile.getX(), tile.getY());
    }

    public Tileset getTileset() {
        return tileset;
    }

    public String getMapFileName() {
        return mapFileName;
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

    public int getWidthPixels() {
        return width * tileset.getScaledSpriteWidth();
    }

    public int getHeightPixels() {
        return height * tileset.getScaledSpriteHeight();
    }

    public MapTile[] getMapTiles() {
        return mapTiles;
    }

    public void setMapTiles(MapTile[] mapTiles) {
        this.mapTiles = mapTiles;
    }

    public MapTile getMapTile(int x, int y) {
        if (isInBounds(x, y)) {
            return mapTiles[getConvertedIndex(x, y)];
        } else {
            return null;
        }
    }

    public void setMapTile(int x, int y, MapTile tile) {
        mapTiles[getConvertedIndex(x, y)] = tile;
    }

    public MapTile getTileByPosition(int xPosition, int yPosition) {
        Point tileIndex = getTileIndexByPosition(xPosition, yPosition);
        if (isInBounds(Math.round(tileIndex.x), Math.round(tileIndex.y))) {
            return getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y));
        } else {
            return null;
        }
    }

    public Point getTileIndexByPosition(float xPosition, float yPosition) {
        int xIndex = Math.round(xPosition) / tileset.getScaledSpriteWidth();
        int yIndex = Math.round(yPosition) / tileset.getScaledSpriteHeight();
        return new Point(xIndex, yIndex);
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private int getConvertedIndex(int x, int y) {
        return x + width * y;
    }

    protected ArrayList<Enemy> loadEnemies() {
        return new ArrayList<>();
    }

    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    protected ArrayList<NPC> loadNPCs() {
        return new ArrayList<>();
    }

    public Camera getCamera() {
        return camera;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<EnhancedMapTile> getEnhancedMapTiles() {
        return enhancedMapTiles;
    }
    public ArrayList<NPC> getNPCs() {
        return npcs;
    }
    public ArrayList<Enemy> getActiveEnemies() {
        return camera.getActiveEnemies();
    }
    public ArrayList<EnhancedMapTile> getActiveEnhancedMapTiles() {
        return camera.getActiveEnhancedMapTiles();
    }
    public ArrayList<NPC> getActiveNPCs() {
        return camera.getActiveNPCs();
    }

    public void addEnemy(Enemy enemy) {
        enemy.setMap(this);
        this.enemies.add(enemy);
    }

    public void addEnhancedMapTile(EnhancedMapTile enhancedMapTile) {
        enhancedMapTile.setMap(this);
        this.enhancedMapTiles.add(enhancedMapTile);
    }

    public void addNPC(NPC npc) {
        npc.setMap(this);
        this.npcs.add(npc);
    }

    public boolean adjustCamera() { return adjustCamera; }
    public void setAdjustCamera(boolean adjustCamera) {
        this.adjustCamera = adjustCamera;
    }

    public void update(Player player) {
        if (adjustCamera) {
            adjustMovementY(player);
            adjustMovementX(player);
        }
        camera.update(player);
    }

    private void adjustMovementX(Player player) {
        if (player.getCalibratedXLocation() > xMidPoint && camera.getEndBoundX() < endBoundX) {
            float xMidPointDifference = xMidPoint - player.getCalibratedXLocation();
            camera.moveX(-xMidPointDifference);
            if (camera.getEndBoundX() > endBoundX) {
                float cameraDifference = camera.getEndBoundX() - endBoundX;
                camera.moveX(-cameraDifference);
            }
        } else if (player.getCalibratedXLocation() < xMidPoint && camera.getX() > startBoundX) {
            float xMidPointDifference = xMidPoint - player.getCalibratedXLocation();
            camera.moveX(-xMidPointDifference);
            if (camera.getX() < startBoundX) {
                float cameraDifference = startBoundX - camera.getX();
                camera.moveX(cameraDifference);
            }
        }
    }

    private void adjustMovementY(Player player) {
        if (player.getCalibratedYLocation() > yMidPoint && camera.getEndBoundY() < endBoundY) {
            float yMidPointDifference = yMidPoint - player.getCalibratedYLocation();
            camera.moveY(-yMidPointDifference);
            if (camera.getEndBoundY() > endBoundY) {
                float cameraDifference = camera.getEndBoundY() - endBoundY;
                camera.moveY(-cameraDifference);
            }
        } else if (player.getCalibratedYLocation() < yMidPoint && camera.getY() > startBoundY) {
            float yMidPointDifference = yMidPoint - player.getCalibratedYLocation();
            camera.moveY(-yMidPointDifference);
            if (camera.getY() < startBoundY) {
                float cameraDifference = startBoundY - camera.getY();
                camera.moveY(cameraDifference);
            }
        }
    }

    public void reset() {
        setupMap();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        camera.draw(graphicsHandler);
    }
}
