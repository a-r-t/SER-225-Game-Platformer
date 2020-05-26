package Map;

import GameObject.Frame;
import GameObject.GameObjectBuilder;

public class MapTileBuilder extends GameObjectBuilder {

    private TileType tileType = TileType.PASSABLE;

    public MapTileBuilder(Frame frame) {
        super(frame);
    }

    public MapTileBuilder(Frame[] frames) {
        super(frames);
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
