package Builders;

import GameObject.Frame;
import Level.MapTile;
import Level.TileType;

import java.util.HashMap;

// Builder class to instantiate a MapTile class
public class MapTileBuilder {
    private HashMap<String, Frame[]> animations = new HashMap<>();
    private TileType tileType = TileType.PASSABLE;
    private int tileIndex = -1;

    public MapTileBuilder(Frame frame) {
        this.animations.put("DEFAULT", new Frame[] { frame });
    }

    public MapTileBuilder(Frame[] frames) {
        this.animations.put("DEFAULT", frames);
    }

    public MapTileBuilder withTileType(TileType tileType) {
        this.tileType = tileType;
        return this;
    }

    public MapTileBuilder withTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        return this;
    }

    public HashMap<String, Frame[]> cloneAnimations() {
        HashMap<String, Frame[]> animationsCopy = new HashMap<>();
        for (String key : animations.keySet()) {
            Frame[] frames = animations.get(key);
            Frame[] framesCopy = new Frame[frames.length];
            for (int i = 0; i < framesCopy.length; i++) {
                framesCopy[i] = frames[i].copy();
            }
            animationsCopy.put(key, framesCopy);
        }
        return animationsCopy;
    }

    public MapTile build(float x, float y) {
        return new MapTile(x, y, cloneAnimations(), tileType, tileIndex);
    }
}
