package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.UFOEnemy;
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


    //ADDING ENEMIES
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(12, 13), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(28, 25), Direction.LEFT));
        enemies.add(new BugEnemy(getPositionByTileIndex(19, 25), Direction.LEFT));
        enemies.add(new UFOEnemy(getPositionByTileIndex(20, 3), Direction.RIGHT,200));
        return enemies;
    }

    //ADDING SPECIAL MAP TILES
    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(28, 5)
        ));
        return enhancedMapTiles;
    }




}// end class