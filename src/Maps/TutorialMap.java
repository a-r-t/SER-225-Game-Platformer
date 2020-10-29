package Maps;

import Enemies.TestEnemy;
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

public class TutorialMap extends Map {
    public TutorialMap() {
        super("tutorial_map.txt", new CommonTileset(), new Point(1,9));
    }//end constructor


    //ADDING ENEMIES
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(35, 9), Direction.LEFT));
        enemies.add(new TestEnemy(getPositionByTileIndex(9, 2), Direction.RIGHT));
        return enemies;
    }

    //ADDING SPECIAL MAP TILES
    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(26, 6),
                getPositionByTileIndex(31, 6),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT


        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(45, 7)
        ));


        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        TutorialWalrus wal1 = new TutorialWalrus(getPositionByTileIndex(4, 9).subtract(new Point(0, 13)), this);
        wal1.setMessage(new SpriteFont("Hello! Use WASD or the arrow keys to move!", wal1.getX(), wal1.getY() - 10, "Arial", 12, Color.BLACK));
        npcs.add(wal1);

        TutorialWalrus wal2 = new TutorialWalrus(getPositionByTileIndex(24, 8).subtract(new Point(0, 13)), this);
        wal2.setMessage(new SpriteFont("Jump on the platform to avoid the water!", wal1.getX(), wal1.getY() - 10, "Arial", 12, Color.BLACK));
        npcs.add(wal2);

        TutorialWalrus wal3 = new TutorialWalrus(getPositionByTileIndex(32, 8).subtract(new Point(0, 13)), this);
        wal3.setMessage(new SpriteFont("Avoid the big scary bug! It will hurt you!", wal1.getX(), wal1.getY() - 10, "Arial", 12, Color.BLACK));
        npcs.add(wal3);

        TutorialWalrus wal4 = new TutorialWalrus(getPositionByTileIndex(42, 9).subtract(new Point(0, 13)), this);
        wal4.setMessage(new SpriteFont("Yay you made it! Touch the victory box!", wal1.getX(), wal1.getY() - 10, "Arial", 12, Color.BLACK));
        npcs.add(wal4);



        return npcs;
    }



}// end class