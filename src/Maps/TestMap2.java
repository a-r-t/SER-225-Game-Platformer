package Maps;

import Scene.Enemy;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.NPC;
import Tilesets.CommonTileset;
import Utils.Point;
import java.awt.*;
import java.util.ArrayList;

public class TestMap2 extends Map {

    public TestMap2() {
        super("test_map2.txt", new CommonTileset(), new Point(1, 9));
    }

    @Override
    protected ArrayList<Enemy> loadEnemies() {
        return null;
    }

    @Override
    protected ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return null;
    }

    @Override
    protected ArrayList<NPC> loadNPCs() {
        return null;
    }
}
