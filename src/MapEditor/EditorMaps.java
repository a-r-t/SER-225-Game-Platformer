package MapEditor;

import Maps.TestMap;
import Maps.TitleScreenMap;
import Scene.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
