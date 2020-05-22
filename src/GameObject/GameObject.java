package GameObject;

import GameObject.FrameBuilder;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class GameObject extends AnimatedSprite {

	public GameObject(SpriteSheet spriteSheet, float x, float y, String startingAnimation) {
		super(spriteSheet, x, y, startingAnimation);
	}

	public GameObject(SpriteSheet spriteSheet, float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(spriteSheet, x, y, animations, startingAnimation);
	}

	public GameObject(BufferedImage image, float x, float y, String startingAnimation) {
		super(image, x, y, startingAnimation);
	}

	public GameObject(BufferedImage image, float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(image, x, y, animations, startingAnimation);
	}

	public GameObject(BufferedImage image, float x, float y) {
		super(image, x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0).build()
			});
		}};
		this.currentAnimation = "DEFAULT";
		setCurrentSprite();
	}

	public GameObject(BufferedImage image, float x, float y, float scale) {
		super(image, x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.build()
			});
		}};
		this.currentAnimation = "DEFAULT";
		setCurrentSprite();
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
		super(image, x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.withImageEffect(imageEffect)
							.build()
			});
		}};
		this.currentAnimation = "DEFAULT";
		setCurrentSprite();
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
		super(image, x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[]{
					new FrameBuilder(image, 0)
							.withScale(scale)
							.withImageEffect(imageEffect)
							.withBounds(bounds)
							.build()
			});
		}};
		this.currentAnimation = "DEFAULT";
		setCurrentSprite();
	}
}
