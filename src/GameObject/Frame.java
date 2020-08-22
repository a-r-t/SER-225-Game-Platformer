package GameObject;

import java.awt.image.BufferedImage;

// This class represents a Frame in an animation -- an array of Frames is an animation
// A frame is a sprite that has a delay value which specifies how many milliseconds to stay on this frame before transitioning to the next frame in an animation
public class Frame extends Sprite {
	private int delay;

	public Frame(BufferedImage image, float scale, ImageEffect imageEffect, Rectangle bounds, int delay) {
		super(image, scale, imageEffect);
		if (bounds != null) {
			this.bounds = bounds;
			this.bounds.setScale(scale);
		}
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public Frame copy() {
		return new Frame(image, scale, imageEffect, bounds, delay);
	}
}
