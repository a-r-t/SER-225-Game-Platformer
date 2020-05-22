package GameObject;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;

public class GameObjectBuilder {
    protected SpriteSheet spriteSheet;
    protected HashMap<String, Frame[]> animations = new HashMap<>();
    protected String startingAnimation = "DEFAULT";

    public GameObjectBuilder(BufferedImage image) {
        this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
    }

    public GameObjectBuilder(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public GameObjectBuilder addAnimation(String animationName, Frame[] frames) {
        animations.put(animationName, frames);
        return this;
    }

    public GameObjectBuilder addDefaultAnimation(Frame[] frames) {
        animations.put("DEFAULT", frames);
        return this;
    }

    public GameObjectBuilder addAnimation(String animationName, Frame frame) {
        animations.put(animationName, new Frame[] { frame });
        return this;
    }

    public GameObjectBuilder addDefaultAnimation(Frame frame) {
        animations.put("DEFAULT", new Frame[] { frame });
        return this;
    }

    public GameObjectBuilder withStartingAnimation(String startingAnimation) {
        this.startingAnimation = startingAnimation;
        return this;
    }

    public HashMap<String, Frame[]> cloneAnimations() {
        HashMap<String, Frame[]> animationsCopy = new HashMap<>();
        for (String key : animations.keySet()) {
            Frame[] frames = animations.get(key);
            animationsCopy.put(key, Arrays.stream(frames).map(frame -> frame.copy()).toArray(size -> new Frame[size]));
        }
        return animationsCopy;
    }

    public GameObject build(float x, float y) {
        return new GameObject(spriteSheet, x, y, cloneAnimations(), startingAnimation);
    }
}