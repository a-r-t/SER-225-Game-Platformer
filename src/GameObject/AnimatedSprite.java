package GameObject;

import Engine.Painter;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class AnimatedSprite extends Sprite {

	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimation = "";
	protected String previousAnimation = "";
	protected int currentFrameIndex;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationFinished;
	protected Frame currentFrame;

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y) {
		super(x, y);
		this.spriteSheet = spriteSheet;
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
		setCurrentSprite();
	}

	public AnimatedSprite(BufferedImage image, float x, float y) {
		super(image, x, y);
		this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
		setCurrentSprite();
	}

    public abstract HashMap<String, Frame[]> loadAnimations();

	public abstract String getStartingAnimation();

	public void update() {
		if (!previousAnimation.equals(currentAnimation)) {
			currentFrameIndex = 0;
			setCurrentSprite();
			beforeTime = System.currentTimeMillis();
			hasAnimationFinished = false;
		} else {
			long currentMillis = System.currentTimeMillis() - beforeTime;
			if (currentMillis > animations.get(currentAnimation)[currentFrameIndex].getDelay()) {
				beforeTime = System.currentTimeMillis();
				currentFrameIndex++;
				if (currentFrameIndex >= animations.get(currentAnimation).length) {
					currentFrameIndex = 0;
					hasAnimationFinished = true;
				}
				setCurrentSprite();
			}
		}
		previousAnimation = currentAnimation;
	}

	private void setCurrentSprite() {
		currentFrame = getCurrentFrame();
		this.image = currentFrame.getImage();
		this.width = currentFrame.getWidth();
		this.height = currentFrame.getHeight();
		this.bounds = new Rectangle(getX() + currentFrame.getBounds().getX(), getY() + currentFrame.getBounds().getY(), currentFrame.getBounds().getWidth(), currentFrame.getBounds().getHeight());
		this.scale = currentFrame.getScale();
		this.imageEffect = currentFrame.getImageEffect();
	}

	public Frame getCurrentFrame() {
		return animations.get(currentAnimation)[currentFrameIndex];
	}

	@Override
	public void draw(Painter painter) {
		super.draw(painter);
	}

}
