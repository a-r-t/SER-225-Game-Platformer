package GameObject;

import Engine.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class AnimatedSprite implements IntersectableRectangle {
	protected float x, y;
	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimationName = "";
	protected String previousAnimationName = "";
	protected int currentFrameIndex;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationLooped;
	protected Sprite currentSprite;

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
		this.x = x;
		this.y = y;
		this.spriteSheet = spriteSheet;
		this.animations = getAnimations();
		this.currentAnimationName = startingAnimationName;
		setCurrentSprite();
	}

    public AnimatedSprite(SpriteSheet spriteSheet, float x, float y, HashMap<String, Frame[]> animations, String startingAnimationName) {
        this.x = x;
        this.y = y;
        this.spriteSheet = spriteSheet;
        this.animations = animations;
        this.currentAnimationName = startingAnimationName;
        setCurrentSprite();
    }

	public AnimatedSprite(BufferedImage image, float x, float y, String startingAnimationName) {
		this.x = x;
		this.y = y;
		this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        this.animations = getAnimations();
        this.currentAnimationName = startingAnimationName;
		setCurrentSprite();
	}

    public AnimatedSprite(BufferedImage image, float x, float y, HashMap<String, Frame[]> animations, String startingAnimationName) {
        this.x = x;
        this.y = y;
        this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        this.animations = animations;
        this.currentAnimationName = startingAnimationName;
        setCurrentSprite();
    }

    public AnimatedSprite(BufferedImage image, float x, float y) {
        this.x = x;
        this.y = y;
        this.spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        this.animations = new HashMap<>();
        this.currentAnimationName = "";
    }

	public void update() {
		if (!previousAnimationName.equals(currentAnimationName)) {
			currentFrameIndex = 0;
			setCurrentSprite();
			beforeTime = System.currentTimeMillis();
			hasAnimationLooped = false;
		} else {
			if (getCurrentAnimation().length > 1) {
				long currentMillis = System.currentTimeMillis() - beforeTime;
				if (currentMillis > animations.get(currentAnimationName)[currentFrameIndex].getDelay()) {
					beforeTime = System.currentTimeMillis();
					currentFrameIndex++;
					if (currentFrameIndex >= animations.get(currentAnimationName).length) {
						currentFrameIndex = 0;
						hasAnimationLooped = true;
					}
					setCurrentSprite();
				}
			}
		}
		previousAnimationName = currentAnimationName;
	}

	public HashMap<String, Frame[]> getAnimations() {
	    return null;
    }

	protected void setCurrentSprite() {
		currentSprite = getCurrentFrame();
		currentSprite.setX(x);
		currentSprite.setY(y);
	}

	protected Frame getCurrentFrame() {
		return animations.get(currentAnimationName)[currentFrameIndex];
	}
	protected Frame[] getCurrentAnimation() { return animations.get(currentAnimationName); }

	public Sprite getCurrentSprite() {
		return currentSprite;
	}

	public void draw(Graphics graphics) {
		currentSprite.draw(graphics);
	}

    public void drawBounds(Graphics graphics, Color color) {
		currentSprite.drawBounds(graphics, color);
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

    public int getBoundsX1() {
        return currentSprite.getBoundsX1();
    }

    public int getScaledBoundsX1() {
        return currentSprite.getScaledBoundsX1();
    }

    public int getBoundsX2() {
        return currentSprite.getBoundsX2();
    }

    public int getScaledBoundsX2() {
        return currentSprite.getScaledBoundsX2();
    }

    public int getBoundsY1() {
        return currentSprite.getBoundsY1();
    }

    public int getScaledBoundsY1() {
        return currentSprite.getScaledBoundsY1();
    }

    public int getBoundsY2() {
        return currentSprite.getBoundsY2();
    }

    public int getScaledBoundsY2() {
        return currentSprite.getScaledBoundsY2();
    }

    public Rectangle getBounds() {
		return currentSprite.getBounds();
	}
	public Rectangle getScaledBounds() {
		return currentSprite.getScaledBounds();
	}

	@Override
    public Rectangle getIntersectRectangle() {
	    return currentSprite.getIntersectRectangle();
    }

    @Override
    public boolean intersects(IntersectableRectangle other) {
        return currentSprite.intersects(other);
    }

    public void setBounds(Rectangle bounds) {
		currentSprite.setBounds(bounds);
	}

    public float getScale() {
	    return currentSprite.getScale();
    }

    public void setScale(float scale) {
	    currentSprite.setScale(scale);
    }

    public String getCurrentAnimationName() {
	    return currentAnimationName;
    }

    public void setCurrentAnimationName() {
	    this.currentAnimationName = currentAnimationName;
    }

    public void setAnimations(HashMap<String, Frame[]> animations) {
	    this.animations = animations;
    }

    public void addAnimation(String animationName, Frame[] frame) {
	    animations.put(animationName, frame);
    }
}
