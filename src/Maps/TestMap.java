package Maps;

import Engine.ImageLoader;
import GameObject.Rectangle;
import Enemies.BugEnemy;
import Scene.Enemy;
import Scene.EnhancedMapTile;
import EnhancedMapTiles.HorizontalMovingPlatform;
import Scene.Map;
import Scene.TileType;
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
}
