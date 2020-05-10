package Maps;

import Map.Map;
import Tilesets.CommonTileset;

public class TestMap extends Map {

    public TestMap() {
        super(4, 3, new CommonTileset());
    }

    @Override
    public int[] createMap() {
        return new int[] {
            0, 0, 0, 0,
            1, 1, 1, 1,
            2, 2, 2, 2
        };
    }
}
