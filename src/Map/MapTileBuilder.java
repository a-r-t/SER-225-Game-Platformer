package Map;

import GameObject.Frame;
import GameObject.GameObjectBuilder;

import java.awt.image.BufferedImage;

public class MapTileBuilder extends GameObjectBuilder {
    public MapTileBuilder(BufferedImage image) {
        super(image);
    }

    public MapTileBuilder(Frame frame) {
        super(frame.getImage());
        addDefaultAnimation(frame);
    }

    @Override
    public MapTile build(float x, float y) {
        return new MapTile(spriteSheet, x, y, cloneAnimations(), startingAnimation);
    }
}
