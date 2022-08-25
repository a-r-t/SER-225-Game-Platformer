package GameObject;

import java.awt.image.BufferedImage;

// This class represents a Frame in an animation -- an array of Frames is an animation
// A frame is a sprite that has a delay value which specifies how many milliseconds to stay on this frame before transitioning to the next frame in an animation
public class Frame extends Sprite {
	private int delay;

	public Frame(BufferedImage image) {
		super(image, 0, 0);
	}

	public Frame(BufferedImage image, ImageEffect imageEffect, float scale, Rectangle bounds, int delay) {
		super(image, 0, 0, imageEffect);
		this.scale = scale;
		if (bounds != null) {
			this.bounds = bounds;
		}
		this.bounds.setScale(scale);
		this.delay = delay;
	}

	public Frame(BufferedImage image, ImageEffect imageEffect, float scale, Rectangle bounds) {
		super(image, 0, 0, imageEffect);
		this.scale = scale;
		if (bounds != null) {
			this.bounds = bounds;
		}
		this.bounds.setScale(scale);
	}

	public int getDelay() {
		return delay;
	}

	public Frame copy() {
		return new Frame(image, imageEffect, scale, bounds, delay);
	}
}
