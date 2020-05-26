package Map;

import GameObject.Frame;
import GameObject.GameObjectBuilder;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;

public class MapTileBuilder extends GameObjectBuilder {

    private TileType tileType = TileType.PASSABLE;

    public MapTileBuilder(Frame frame) {
        super();
        addDefaultAnimation(frame);
    }

    public MapTileBuilder(Frame[] frames) {
        super();
        addDefaultAnimation(frames);
    }

    public MapTileBuilder withTileType(TileType tileType) {
        this.tileType = tileType;
        return this;
    }

    @Override
    public MapTile build(float x, float y) {
        return new MapTile(x, y, cloneAnimations(), startingAnimationName, tileType);
    }
}
