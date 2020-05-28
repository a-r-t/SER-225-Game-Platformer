package MapEditor;

import Scene.Map;
import Maps.TestMap;
import Maps.TestMap2;

import java.util.HashMap;

public class EditorMaps {
    public static HashMap<String, Map> getMaps() {
        return new HashMap<String, Map>() {{
            put("TestMap", new TestMap());
            put("TestMap2", new TestMap2());
        }};
    }
}
