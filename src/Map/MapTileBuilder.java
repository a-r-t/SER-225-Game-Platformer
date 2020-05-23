package Map;

import GameObject.Frame;
import GameObject.GameObjectBuilder;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;

public class MapTileBuilder extends GameObjectBuilder {
    public MapTileBuilder(Frame frame) {
        super();
        addDefaultAnimation(frame);
    }

    public MapTileBuilder(Frame[] frames) {
        super();
        addDefaultAnimation(frames);
    }

    @Override
    public MapTile build(float x, float y) {
        return new MapTile(x, y, cloneAnimations(), startingAnimationName);
    }
}
