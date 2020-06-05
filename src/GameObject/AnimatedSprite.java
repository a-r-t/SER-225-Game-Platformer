package GameObject;

import Engine.GraphicsHandler;
import Utils.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class AnimatedSprite implements IntersectableRectangle {
	protected float x, y;
	protected HashMap<String, Frame[]> animations;
	protected String currentAnimationName = "";
	protected String previousAnimationName = "";
	protected int currentFrameIndex;
	protected boolean hasAnimationLooped;
	protected Frame currentFrame;
	private Timer timer = new Timer();

	public AnimatedSprite(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
		this.x = x;
		this.y = y;
		this.animations = getAnimations(spriteSheet);
		this.currentAnimationName = startingAnimationName;
		setCurrentSprite();
	}

    public AnimatedSprite(float x, float y, HashMap<String, Frame[]> animations, String startingAnimationName) {
        this.x = x;
        this.y = y;
        this.animations = animations;
        this.currentAnimationName = startingAnimationName;
        setCurrentSprite();
    }

	public AnimatedSprite(BufferedImage image, float x, float y, String startingAnimationName) {
		this.x = x;
		this.y = y;
		SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        this.animations = getAnimations(spriteSheet);
        this.currentAnimationName = startingAnimationName;
		setCurrentSprite();
	}

    public AnimatedSprite(float x, float y) {
        this.x = x;
        this.y = y;
        this.animations = new HashMap<>();
        this.currentAnimationName = "";
    }

	public void update() {
		if (!previousAnimationName.equals(currentAnimationName)) {
			currentFrameIndex = 0;
			setCurrentSprite();
			timer.setWaitTime(getCurrentFrame().getDelay());
			hasAnimationLooped = false;
		} else {
			if (getCurrentAnimation().length > 1) {
				if (timer.isTimeUp()) {
					currentFrameIndex++;
					if (currentFrameIndex >= animations.get(currentAnimationName).length) {
						currentFrameIndex = 0;
						hasAnimationLooped = true;
					}
					timer.setWaitTime(getCurrentFrame().getDelay());
					setCurrentSprite();
				}
			}
		}
		previousAnimationName = currentAnimationName;
	}

	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
	    return null;
    }

	protected void setCurrentSprite() {
		currentFrame = getCurrentFrame();
		currentFrame.setX(x);
		currentFrame.setY(y);
	}

	protected Frame getCurrentFrame() {
		return animations.get(currentAnimationName)[currentFrameIndex];
	}

	protected Frame[] getCurrentAnimation() { return animations.get(currentAnimationName); }

	public void draw(GraphicsHandler graphicsHandler) {
		currentFrame.draw(graphicsHandler);
	}

    public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
		currentFrame.drawBounds(graphicsHandler, color);
    }

    public float getXRaw() { return currentFrame.getXRaw(); }
    public float getYRaw() { return currentFrame.getYRaw(); }
	public float getX() { return currentFrame.getX(); }
	public float getY() { return currentFrame.getY(); }
	public float getX1() { return currentFrame.getX1(); }
	public float getY1() { return currentFrame.getY1(); }
	public float getX2() { return currentFrame.getX2(); }
	public float getScaledX2() { return currentFrame.getScaledX2(); }
	public float getY2() { return currentFrame.getY2(); }
	public float getScaledY2() { return currentFrame.getScaledY2(); }

	public void setX(float x) {
		this.x = x;
		currentFrame.setX(x);
	}
	public void setY(float y) {
		this.y = y;
		currentFrame.setY(y);
	}

	public void setLocation(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public void moveX(float dx) {
		this.x += dx;
		currentFrame.moveX(dx);
	}

	public void moveRight(float dx) {
		this.x += dx;
		currentFrame.moveRight(dx);
	}

	public void moveLeft(float dx) {
		this.x -= dx;
		currentFrame.moveLeft(dx);
	}

	public void moveY(float dy) {
		this.y += dy;
		currentFrame.moveY(dy);
	}

	public void moveDown(float dy) {
		this.y += dy;
		currentFrame.moveDown(dy);
	}

	public void moveUp(float dy) {
		this.y -= dy;
		currentFrame.moveUp(dy);
	}

	public float getScale() {
		return currentFrame.getScale();
	}

	public void setScale(float scale) {
		currentFrame.setScale(scale);
	}

	public int getWidth() {
		return currentFrame.getWidth();
	}
	public int getHeight() {
		return currentFrame.getHeight();
	}
	public void setWidth(int width) {
		currentFrame.setWidth(width);
	}
	public void setHeight(int height) {
		currentFrame.setHeight(height);
	}
	public int getScaledWidth() {
		return currentFrame.getScaledWidth();
	}
	public int getScaledHeight() {
		return currentFrame.getScaledHeight();
	}

	public Rectangle getBounds() {
		return currentFrame.getBounds();
	}

	public Rectangle getScaledBounds() {
		return currentFrame.getScaledBounds();
	}

    public float getBoundsX1() {
        return currentFrame.getBoundsX1();
    }

    public float getScaledBoundsX1() {
        return currentFrame.getScaledBoundsX1();
    }

    public float getBoundsX2() {
        return currentFrame.getBoundsX2();
    }

    public float getScaledBoundsX2() {
        return currentFrame.getScaledBoundsX2();
    }

    public float getBoundsY1() {
        return currentFrame.getBoundsY1();
    }

    public float getScaledBoundsY1() {
        return currentFrame.getScaledBoundsY1();
    }

    public float getBoundsY2() {
        return currentFrame.getBoundsY2();
    }

    public float getScaledBoundsY2() {
        return currentFrame.getScaledBoundsY2();
    }

	public void setBounds(Rectangle bounds) {
		currentFrame.setBounds(bounds);
	}

	@Override
    public Rectangle getIntersectRectangle() {
	    return currentFrame.getIntersectRectangle();
    }

    public boolean intersects(IntersectableRectangle other) {
        return currentFrame.intersects(other);
    }

	public boolean overlaps(IntersectableRectangle other) { return currentFrame.overlaps(other); }

	@Override
	public String toString() {
		return String.format("Current Sprite: x=%s y=%s width=%s height=%s bounds=(%s, %s, %s, %s)", x, y, getScaledWidth(), getScaledHeight(), getScaledBoundsX1(), getScaledBoundsY1(), getScaledBounds().getWidth(), getScaledBounds().getHeight());
	}
}
