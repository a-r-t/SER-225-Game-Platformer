package MapEditor;

import Level.Map;
import Maps.SlopeTestMap;
import Maps.TestMap;
import Maps.TitleScreenMap;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("SlopeTestMap");
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
            case "SlopeTestMap":
                return new SlopeTestMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
