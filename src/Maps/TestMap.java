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
        	enemies.add(new BugEnemy(getPositionByTileIndex(33, 11), Direction.LEFT));
        	return enemies;
        } else if (mapFileName == "test_map_3.txt"){
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(13, 4).addY(2), getPositionByTileIndex(16, 4).addY(2), Direction.RIGHT));
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(25, 4).addY(2), getPositionByTileIndex(28, 4).addY(2), Direction.RIGHT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(8, 11), Direction.RIGHT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(15, 11), Direction.RIGHT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(27, 11), Direction.RIGHT));
        	return enemies;
        } else if (mapFileName == "test_map_4.txt"){
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(21, 9).addY(2), getPositionByTileIndex(23, 9).addY(2), Direction.RIGHT));
        	enemies.add(new DinosaurEnemy(getPositionByTileIndex(21, 3).addY(2), getPositionByTileIndex(24, 3).addY(2), Direction.RIGHT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(5, 10), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(7, 10), Direction.LEFT));
        	return enemies;
        } else if (mapFileName == "test_map_5.txt"){
        	enemies.add(new BugEnemy(getPositionByTileIndex(14, 11), Direction.LEFT));
        	enemies.add(new BugEnemy(getPositionByTileIndex(20, 11), Direction.LEFT));
        	return enemies;
        }  else {
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
        } else if (mapFileName == "test_map_2.txt") {
        	enhancedMapTiles.add(new EndLevelBox(getPositionByTileIndex(36, 3)));
        	return enhancedMapTiles;
        } else if (mapFileName == "test_map_3.txt") {
        	enhancedMapTiles.add(new EndLevelBox(getPositionByTileIndex(36, 3)));
        	enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(7, 7),
                    getPositionByTileIndex(10, 7),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
        	enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(19, 6),
                    getPositionByTileIndex(22, 6),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
        	enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(30, 6),
                    getPositionByTileIndex(33, 6),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
        	return enhancedMapTiles;
        } else if (mapFileName == "test_map_4.txt") {
        	enhancedMapTiles.add(new EndLevelBox(getPositionByTileIndex(28, 0)));
        	enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(9, 8),
                    getPositionByTileIndex(11, 8),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
        	enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(15, 7),
                    getPositionByTileIndex(20, 7),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
        	enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(15, 13),
                    getPositionByTileIndex(20, 13),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
        	return enhancedMapTiles;
        } else if (mapFileName == "test_map_5.txt") {
        	enhancedMapTiles.add(new EndLevelBox(
                    getPositionByTileIndex(48, 9)
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(2, 10),
                    getPositionByTileIndex(6, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(8, 10),
                    getPositionByTileIndex(13, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.RIGHT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(15, 10),
                    getPositionByTileIndex(19, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(21, 10),
                    getPositionByTileIndex(23, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(25, 10),
                    getPositionByTileIndex(29, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(30, 10),
                    getPositionByTileIndex(34, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.RIGHT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(35, 10),
                    getPositionByTileIndex(40, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(42, 10),
                    getPositionByTileIndex(48, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            return enhancedMapTiles;
        } else if (mapFileName == "test_map_4.txt") {
        	enhancedMapTiles.add(new EndLevelBox(
                    getPositionByTileIndex(48, 8)
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(2, 10),
                    getPositionByTileIndex(6, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(8, 10),
                    getPositionByTileIndex(13, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.RIGHT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(15, 10),
                    getPositionByTileIndex(19, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(21, 10),
                    getPositionByTileIndex(23, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(25, 10),
                    getPositionByTileIndex(29, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(30, 10),
                    getPositionByTileIndex(34, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.RIGHT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(35, 10),
                    getPositionByTileIndex(40, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
            ));
            enhancedMapTiles.add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(42, 10),
                    getPositionByTileIndex(48, 10),
                    TileType.NOT_PASSABLE,
                    3,
                    new Rectangle(0, 6,16,4),
                    Direction.LEFT
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
