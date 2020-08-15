package MapEditor;

import Maps.TestMap;
import Maps.TitleScreenMap;
import Scene.Map;

import java.util.HashMap;

public class EditorMaps {
    public static HashMap<String, Map> getMaps() {
        return new HashMap<String, Map>() {{
            put("TestMap", new TestMap());
            put("TitleScreen", new TitleScreenMap());
        }};
    }
}
