package GameObject;

import Engine.Graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class AnimatedSprite implements GameObject {

	protected float x, y;
	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimation = "";
	protected String previousAnimation = "";
	protected int currentFrameIndex;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationFinished;
	protected Sprite currentSprite;

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y) {
		this.x = x;
		this.y = y;
		this.spriteSheet = spriteSheet;
		animations = loadAnimations();
		currentAnimation = getStartingAnimation();
		setCurrentSprite();
	}

	public AnimatedSprite(BufferedImage image, float x, float y) {
		this.x = x;
		this.y = y;
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
		currentSprite = getCurrentFrame();
		currentSprite.setX(x);
		currentSprite.setY(y);
	}

	protected Frame getCurrentFrame() {
		return animations.get(currentAnimation)[currentFrameIndex];
	}

	public Sprite getCurrentSprite() {
		return currentSprite;
	}

	@Override
	public void draw(Graphics graphics) {
		currentSprite.draw(graphics);
	}

	public int getX() { return currentSprite.getX(); }
	public int getY() { return currentSprite.getY(); }
	public int getX1() { return currentSprite.getX1(); }
	public int getY1() { return currentSprite.getY1(); }
	public int getX2() { return currentSprite.getX2(); }
	public int getY2() { return currentSprite.getY2(); }
	public int getWidth() {
		return currentSprite.getWidth();
	}
	public int getHeight() {
		return currentSprite.getHeight();
	}
	public int getScaledWidth() {
		return currentSprite.getScaledWidth();
	}
	public int getScaledHeight() {
		return currentSprite.getScaledHeight();
	}
	public void setX(float x) {
		this.x = x;
		currentSprite.setX(x);
	}
	public void setY(float y) {
		this.y = y;
		currentSprite.setY(y);
	}
	public void setWidth(int width) {
		currentSprite.setWidth(width);
	}
	public void setHeight(int height) {
		currentSprite.setHeight(height);
	}
	public Rectangle getBounds() {
		return currentSprite.getBounds();
	}
	public Rectangle getScaledBounds() {
		return currentSprite.getScaledBounds();
	}
	public void setBounds(Rectangle bounds) {
		currentSprite.setBounds(bounds);
	}
	public boolean intersects(AnimatedSprite other) {
		return currentSprite.intersects(other.getCurrentSprite());
	}
	public boolean intersects(Rectangle other) {
		return currentSprite.intersects(other);
	}

}
