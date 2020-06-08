package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import NPCs.Walrus;
import Scene.*;
import Tilesets.CommonTileset;
import Utils.Point;
import java.awt.*;
import java.util.ArrayList;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(20, 1));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(2, 9), this));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), this));
        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        enhancedMapTiles.add(new HorizontalMovingPlatform(
            ImageLoader.load("GreenPlatform.png"),
            getPositionByTileIndex(20, 0),
            getPositionByTileIndex(24, 0),
            TileType.JUMP_THROUGH_PLATFORM,
            3,
            new Rectangle(0, 6,16,4),
            this));
        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        //npcs.add(new Walrus(getPositionByTileIndex(30, 10).subtract(new Point(0, 13)), this));
        return npcs;
    }
}
