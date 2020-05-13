package GameObject;

import Engine.Keyboard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class AnimatedSprite extends Sprite {

	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimation = "";
	protected String previousAnimation = "";
	protected int currentFrame;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationFinished;
	private boolean isAnimated = false;

	public AnimatedSprite(float x, float y, int width, int height, SpriteSheet spriteSheet) {
		super(x, y, width, height);
		this.spriteSheet = spriteSheet;
		isAnimated = true;
		animations = loadAnimations();
	}

	public AnimatedSprite(float x, float y, int width, int height, String imageFile) {
		super(x, y, width, height, imageFile);
	}

    public AnimatedSprite(float x, float y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.setImage(image);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public HashMap<String, Frame[]> loadAnimations() {
        return null;
    }

	@Override
	public void update(Keyboard keyboard) {
	    if (isAnimated) {
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
            image = getCurrentFrame().getFrameImage();
        }
	}
	
	public Frame getCurrentFrame() {
		return animations.get(currentAnimation)[currentFrame];
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
