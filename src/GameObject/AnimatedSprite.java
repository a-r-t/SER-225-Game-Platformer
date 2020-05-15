package GameObject;

import Engine.Painter;

import java.util.HashMap;

public abstract class AnimatedSprite {

	protected float x, y;
	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimation = "";
	protected String previousAnimation = "";
	protected int currentFrame;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationFinished;

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y) {
		this.x = x;
		this.y = y;
		this.spriteSheet = spriteSheet;
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
		previousAnimation = currentAnimation;
	}
	
	public Frame getCurrentFrame() {
		return animations.get(currentAnimation)[currentFrame];
	}

	public void draw(Painter painter) {
		animations.get(currentAnimation)[currentFrame].draw(painter);
	}

}
