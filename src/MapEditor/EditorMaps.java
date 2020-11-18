package MapEditor;

import Level.Map;
import Maps.TestMap;
import Maps.TestMap2;
import Maps.TitleScreenMap;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("TestMap2");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "TestMap2":
            	return new TestMap2();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
