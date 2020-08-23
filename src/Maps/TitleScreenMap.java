package Maps;

import Level.Map;
import Tilesets.CommonTileset;
import Utils.Point;

public class TitleScreenMap extends Map {

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset(), new Point(1, 9));
    }

}
