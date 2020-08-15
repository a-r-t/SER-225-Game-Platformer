package Maps;

import Scene.Enemy;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.NPC;
import Tilesets.CommonTileset;
import Utils.Point;
import java.awt.*;
import java.util.ArrayList;

public class TitleScreenMap extends Map {

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset(), new Point(1, 9));
    }

}
