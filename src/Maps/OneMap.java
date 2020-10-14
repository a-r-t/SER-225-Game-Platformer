package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.GameObject;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;
import SpriteFont.SpriteFont;
import NPCs.TutorialWalrus;


import java.awt.*;
import java.util.ArrayList;

public class OneMap extends Map {
    public OneMap() {
        super("one_map.txt", new CommonTileset(), new Point(1,9));
    }//end constructor







}// end class