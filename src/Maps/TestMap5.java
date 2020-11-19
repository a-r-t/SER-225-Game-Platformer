package Maps;

import java.util.ArrayList;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.TileType;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

public class TestMap5 extends Map{

	public TestMap5(String mapFileName) {
        super(mapFileName, new CommonTileset(), new Point(1, 11));
    }
    public TestMap5() {
    	super("test_map_5.txt", new CommonTileset(), new Point(1, 11));
    }
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        if(mapFileName == "test_map.txt") {
        	enemies.add(new BugEnemy(getPositionByTileIndex(15, 9), Direction.LEFT));
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.RIGHT));
        	return enemies;
        } else {
        	return enemies;
        }
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();
        if (mapFileName == "test_map.txt") {
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
