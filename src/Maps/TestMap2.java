package Maps;

import GameObject.Rectangle;
import Map.Map;
import Tilesets.CommonTileset;

import java.awt.*;

public class TestMap2 extends Map {

    public TestMap2() {
        super("test_map2.txt", new CommonTileset(), new Point(1, 9));
    }
}
