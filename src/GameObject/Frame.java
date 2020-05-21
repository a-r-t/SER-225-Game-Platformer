package GameObject;

import java.awt.image.BufferedImage;

public class Frame extends Sprite {
	public static class FrameBuilder {
		private BufferedImage image;
		private float delay;
		private Rectangle bounds;
		private float scale;
		private ImageEffect imageEffect;

		public FrameBuilder(BufferedImage image, float delay) {
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
			this.scale = scale;
			if (this.scale < 0) {
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

	private float delay;

	private Frame(BufferedImage image, float scale, ImageEffect imageEffect, Rectangle bounds, float delay) {
		super(image, scale, imageEffect);
		if (bounds != null) {
			this.bounds = bounds;
			this.bounds.setScale(scale);
		}
		this.delay = delay;
	}

	public float getDelay() {
		return delay;
	}
}
