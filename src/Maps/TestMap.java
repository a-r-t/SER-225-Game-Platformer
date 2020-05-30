package Maps;

import Engine.ImageLoader;
import GameObject.Rectangle;
import MapEntities.BugEnemy;
import MapEntities.Enemy;
import MapEntities.EnhancedMapTile;
import MapEntities.HorizontalMovingPlatform;
import Scene.Map;
import Scene.MapEntity;
import Tilesets.CommonTileset;

import java.awt.*;
import java.util.ArrayList;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(1, 9));
    }

    @Override
    public ArrayList<Enemy> getEnemies() {
        return new ArrayList<Enemy>() {{
           add(new BugEnemy(getPositionByTileIndex(2, 9)));
        }};
    }

    @Override
    public ArrayList<EnhancedMapTile> getEnhancedMapTiles() {
        return new ArrayList<EnhancedMapTile>() {{
            add(new HorizontalMovingPlatform(
                    ImageLoader.load("GreenPlatform.png"),
                    getPositionByTileIndex(2, 4),
                    getPositionByTileIndex(5, 4),
                    3,
                    new Rectangle(0, 6,16,4)));
        }};
    }
}
