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

public class ThreeMap extends Map {
    public ThreeMap() {
        super("three_map.txt", new CommonTileset(), new Point(2,24));
    }//end constructor







}// end class