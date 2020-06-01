package Scene;

import Engine.GraphicsHandler;

import GameObject.*;
import GameObject.Frame;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapTile extends MapEntity {
    private TileType tileType;
    private int tileIndex;

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int tileIndex, TileType tileType) {
        super(x, y, animations, startingAnimation);
        this.tileType = tileType;
        this.tileIndex = tileIndex;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void update() {
        super.update();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
