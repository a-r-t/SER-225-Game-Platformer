package GameObject;

import Engine.Painter;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class AnimatedSprite extends Sprite {

	protected float scale;
	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimation = "";
	protected String previousAnimation = "";
	protected int currentFrame;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationFinished;

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y) {
		super(x, y);
		this.spriteSheet = spriteSheet;
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
	}

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y, float scale) {
		super(x, y, scale);
		this.spriteSheet = spriteSheet;
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
	}

	public AnimatedSprite(BufferedImage image, float x, float y) {
		super(image, x, y);
		this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
	}

	public AnimatedSprite(BufferedImage image, float x, float y, float scale) {
		super(image, x, y, scale);
		this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
	}

    public abstract HashMap<String, Frame[]> loadAnimations();

	public abstract String getStartingAnimation();

	public void update() {
		if (!previousAnimation.equals(currentAnimation)) {
			currentFrame = 0;
			beforeTime = System.currentTimeMillis();
			hasAnimationFinished = false;
		} else {
			long currentMillis = System.currentTimeMillis() - beforeTime;
			if (currentMillis > animations.get(currentAnimation)[currentFrame].getDelay()) {
				beforeTime = System.currentTimeMillis();
				currentFrame++;
				if (currentFrame >= animations.get(currentAnimation).length) {
					currentFrame = 0;
					hasAnimationFinished = true;
				}

			}
		}
		setSprite();
		previousAnimation = currentAnimation;
	}

	private void setSprite() {
		Frame currentFrame = getCurrentFrame();
		currentFrame.setX(x);
		currentFrame.setY(y);
		this.image = currentFrame.getImage();
		this.scale = currentFrame.getScale();
		this.imageEffect = currentFrame.getImageEffect();
		this.bounds = currentFrame.getBounds();
		this.width = currentFrame.getWidth();
		this.height = currentFrame.getHeight();
	}

	public Frame getCurrentFrame() {
		return animations.get(currentAnimation)[currentFrame];
	}

//	@Override
//	public boolean intersects(Rectangle other) {
//		if (other instanceof AnimatedSprite) {
//			AnimatedSprite otherSprite = (AnimatedSprite) other;
//			return getBoundsX1() < otherSprite.getBoundsX2() && getBoundsX2() > otherSprite.getBoundsX1() &&
//					getBoundsY1() < otherSprite.getBoundsY2() && getBoundsY2() > otherSprite.getBoundsY1();
//		} else {
//			return super.intersects(other);
//		}
//	}

	@Override
	public void draw(Painter painter) {
		super.draw(painter);
	}

}
