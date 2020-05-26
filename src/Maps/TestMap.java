package Maps;

import GameObject.Rectangle;
import Map.Map;
import Tilesets.CommonTileset;

import java.awt.*;

public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Rectangle(0, 0, 0, 0), new Point(1, 9));
    }

    public TestMap(Rectangle screenBounds) {
        super("test_map.txt", new CommonTileset(), screenBounds, new Point(1, 9));
    }
}
