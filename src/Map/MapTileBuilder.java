package Map;

import GameObject.Frame;
import GameObject.GameObjectBuilder;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;

public class MapTileBuilder extends GameObjectBuilder {
    public MapTileBuilder(BufferedImage image) {
        super(image);
    }

    public MapTileBuilder(Frame frame) {
        super(frame.getImage());
        addDefaultAnimation(frame);
    }

    public MapTileBuilder(SpriteSheet spriteSheet, Frame[] frames) {
        super(spriteSheet);
        addDefaultAnimation(frames);
    }

    @Override
    public MapTile build(float x, float y) {
        return new MapTile(spriteSheet, x, y, cloneAnimations(), startingAnimationName);
    }
}
