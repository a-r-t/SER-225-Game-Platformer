package GameObject;

import java.awt.image.BufferedImage;

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
