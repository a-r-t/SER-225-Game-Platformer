package MapEditor;

import Level.Map;
import Maps.TestMap;
import Maps.TitleScreenMap;
import Maps.TutorialMap;
import Maps.OneMap;
import Maps.TwoMap;
import Maps.ThreeMap;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("TutorialMap");
            add("OneMap");
            add("TwoMap");
            add("ThreeMap");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "TutorialMap" :
                return new TutorialMap();
            case "OneMap" :
                return new OneMap();
            case "TwoMap" :
                return new TwoMap();
            case "ThreeMap" :
                return new ThreeMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
