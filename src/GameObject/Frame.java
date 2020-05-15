package GameObject;

import java.awt.image.BufferedImage;

public class Frame extends Sprite {
	private float delay;
	
	public Frame(BufferedImage image, float delay) {
		super(image);
		this.delay = delay;
	}

	public Frame(BufferedImage image, int scale, float delay) {
		super(image, scale);
		this.delay = delay;
	}

	public Frame(BufferedImage image, int scale, ImageEffect imageEffect, float delay) {
		super(image, scale, imageEffect);
		this.delay = delay;
	}

	public float getDelay() {
		return delay;
	}
}
