package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap(String mapFileName) {
        super(mapFileName, new CommonTileset(), new Point(1, 11));
    }
    public TestMap() {
    	super("test_map.txt", new CommonTileset(), new Point(1, 11));
    }
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        if(mapFileName == "test_map.txt") {
        	enemies.add(new BugEnemy(getPositionByTileIndex(15, 9), Direction.LEFT));
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.RIGHT));
        	return enemies;
        } else if (mapFileName == "test_map_2.txt"){
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(10, 6).addY(2), getPositionByTileIndex(13, 6).addY(2), Direction.RIGHT));	
        	enemies.add(new BugEnemy(getPositionByTileIndex(13, 10), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(15, 10), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(17, 10), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(19, 10), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(21, 11), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(23, 11), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(25, 11), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(27, 11), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(29, 11), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(31, 11), Direction.LEFT));
        	return enemies;
        } else {
        	return enemies;
        }
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        if(mapFileName == "test_map.txt") {
        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(24, 6),
                getPositionByTileIndex(27, 6),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(32, 7)
        ));

        return enhancedMapTiles;
        } else {
        	enhancedMapTiles.add(new EndLevelBox(
                    getPositionByTileIndex(36, 5)
            ));
        	return enhancedMapTiles;
        }
        }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        if(mapFileName == "test_map.txt") {
        npcs.add(new Walrus(getPositionByTileIndex(30, 10).subtract(new Point(0, 13)), this));

        return npcs;
        } else {
        	return npcs;
        }
    }
}
