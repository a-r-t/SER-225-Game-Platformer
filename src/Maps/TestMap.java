package Maps;

import Enemies.BugEnemy;
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
        super("test_map.txt", new CommonTileset(), new Point(1, 9));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        return new ArrayList<Enemy>() {{
           add(new BugEnemy(getPositionByTileIndex(2, 9)));
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
