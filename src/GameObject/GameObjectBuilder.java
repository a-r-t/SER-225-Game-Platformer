package GameObject;

import java.util.Arrays;
import java.util.HashMap;

public class GameObjectBuilder {
    protected HashMap<String, Frame[]> animations = new HashMap<>();
    protected String startingAnimationName = "DEFAULT";

    public GameObjectBuilder() { }

    public GameObjectBuilder(Frame frame) {
        addDefaultAnimation(frame);
    }

    public GameObjectBuilder(Frame[] frames) {
        addDefaultAnimation(frames);
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

    public GameObjectBuilder withStartingAnimation(String startingAnimationName) {
        this.startingAnimationName = startingAnimationName;
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
        return new GameObject(x, y, cloneAnimations(), startingAnimationName);
    }
}