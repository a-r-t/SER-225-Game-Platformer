package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import NPCs.Walrus;
import Scene.*;
import Tilesets.CommonTileset;

import java.awt.*;
import java.util.ArrayList;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(20, 1));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        return new ArrayList<Enemy>() {{
           add(new BugEnemy(getPositionByTileIndex(2, 9)));
           add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2)));
        }};
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<EnhancedMapTile>() {{
            add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(4, 7),
                    getPositionByTileIndex(7, 7),
                    TileType.JUMP_THROUGH_PLATFORM,
                    3,
                    new Rectangle(0, 6,16,4)));
        }};
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        return new ArrayList<NPC>() {{
            add(new Walrus(
                    getPositionByTileIndex(30, 10).subtract(new Point(0, 13))
            ));
        }};
    }
}
