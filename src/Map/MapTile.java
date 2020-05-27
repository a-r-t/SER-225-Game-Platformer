package Map;

import Engine.Graphics;
import Game.Kirby;

import GameObject.*;
import GameObject.Frame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapTile extends GameObject {
    private TileType tileType;
    private int tileIndex;

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int tileIndex) {
        super(x, y, animations, startingAnimation);
        tileType = TileType.PASSABLE;
        this.tileIndex = tileIndex;
    }

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int tileIndex, TileType tileType) {
        super(x, y, animations, startingAnimation);
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
    }

    public void update(Map map, Player player) {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
