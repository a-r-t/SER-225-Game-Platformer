package Maps;

import GameObject.Rectangle;
import Map.Map;
import Tilesets.CommonTileset;

import java.awt.*;

public class TestMap2 extends Map {

    public TestMap2() {
        super("test_map2.txt", new CommonTileset(), new Rectangle(0, 0, 0, 0), new Point(1, 9));
    }

    public TestMap2(Rectangle screenBounds) {
        super("test_map2.txt", new CommonTileset(), screenBounds, new Point(1, 9));
    }
}
