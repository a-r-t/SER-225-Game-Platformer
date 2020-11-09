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

public class TwoMap extends Map {
    public TwoMap() {
        super("two_map.txt", new CommonTileset(), new Point(1,7));
    }//end constructor

    //ADDING ENEMIES
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new UFOEnemy(getPositionByTileIndex(12, 2), Direction.RIGHT,200));
        //enemies.add(new DinosaurEnemy(getPositionByTileIndex(10, 7).addY(2), getPositionByTileIndex(15, 7).addY(2), Direction.RIGHT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(25, 8).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.LEFT));

        return enemies;
    }

    //ADDING SPECIAL MAP TILES
    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(4, 5),
                getPositionByTileIndex(9, 5),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(15, 5),
                getPositionByTileIndex(19, 5),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(28, 5)
        ));
        return enhancedMapTiles;
    }






}// end class