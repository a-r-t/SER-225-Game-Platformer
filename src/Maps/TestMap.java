package Maps;

import GameObject.Rectangle;
import MapEntities.BugEnemy;
import MapEntities.Enemy;
import Scene.Map;
import Scene.MapEntity;
import Tilesets.CommonTileset;

import java.awt.*;
import java.util.ArrayList;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(1, 9));
    }

    public ArrayList<MapEntity> getMapEntities() {
        return new ArrayList<MapEntity>() {{
            add(new BugEnemy(getPositionByTileIndex(2, 9)));
        }};
    }
}
