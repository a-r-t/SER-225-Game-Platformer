package Maps;

import Engine.GraphicsHandler;
import Level.Map;
import Level.NPC;
import NPCs.Walrus;
import Players.Cat;
import Tilesets.CommonTileset;
import Utils.Point;

import java.util.ArrayList;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Cat cat;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        Point tileLocation = getMapTile(6, 8).getLocation().subtractX(6).subtractY(7);
        cat = new Cat(tileLocation.x, tileLocation.y);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        cat.draw(graphicsHandler);
    }

}
