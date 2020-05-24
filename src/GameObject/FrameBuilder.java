package GameObject;

import java.awt.image.BufferedImage;

public class FrameBuilder {
    private BufferedImage image;
    private int delay;
    private Rectangle bounds;
    private float scale;
    private ImageEffect imageEffect;

    public FrameBuilder(BufferedImage image, int delay) {
        this.image = image;
        this.delay = delay;
        if (this.delay < 0) {
            this.delay = 0;
        }
        this.scale = 1;
        this.imageEffect = ImageEffect.NONE;
    }

    public FrameBuilder withBounds(Rectangle bounds) {
        this.bounds = bounds;
        return this;
    }

    public FrameBuilder withBounds(float x, float y, int width, int height) {
        this.bounds = new Rectangle(Math.round(x), Math.round(y), width, height);
        return this;
    }

    public FrameBuilder withScale(float scale) {
        if (this.scale >= 0) {
            this.scale = scale;
        } else {
            this.scale = 0;
        }
        return this;
    }

    public FrameBuilder withImageEffect(ImageEffect imageEffect) {
        this.imageEffect = imageEffect;
        return this;
    }

    public Frame build() {
        return new Frame(image, scale, imageEffect, bounds, delay);
    }
}