package Maps;

import GameObject.Rectangle;
import Scene.Map;
import Tilesets.CommonTileset;

import java.awt.*;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(1, 9));
    }
}
