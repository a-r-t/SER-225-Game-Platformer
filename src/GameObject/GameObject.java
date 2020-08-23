package GameObject;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Level.Map;
import Level.MapTileCollisionHandler;
import Utils.Direction;
import Utils.MathUtils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class GameObject extends AnimatedSprite {

	protected float startPositionX, startPositionY;
	protected float amountMovedX, amountMovedY;
	protected float previousX, previousY;
	protected Map map;

	public GameObject(SpriteSheet spriteSheet, float x, float y, String startingAnimation) {
		super(spriteSheet, x, y, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	public GameObject(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(x, y, animations, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	public GameObject(BufferedImage image, float x, float y, String startingAnimation) {
		super(image, x, y, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	public GameObject(BufferedImage image, float x, float y) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0).build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	public GameObject(BufferedImage image, float x, float y, float scale) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.withImageEffect(imageEffect)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[]{
					new FrameBuilder(image, 0)
							.withScale(scale)
							.withImageEffect(imageEffect)
							.withBounds(bounds)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		updateCurrentFrame();
		this.startPositionX = x;
		this.startPositionY = y;
		this.previousX = x;
		this.previousY = y;
	}

	@Override
	public void update() {
		super.update();
		previousX = x;
		previousY = y;
	}

	public float moveXHandleCollision(float dx) {
		return handleCollisionX(dx);
	}

	public float moveYHandleCollision(float dy) {
		return handleCollisionY(dy);
	}

	public float handleCollisionX(float moveAmountX) {
		int amountToMove = (int)Math.abs(moveAmountX);
		float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);
		Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;
		float amountMoved = 0;
		boolean hasCollided = false;
		for (int i = 0; i < amountToMove; i++) {
			moveX(direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setX(newLocation);
				break;
			}
			amountMoved = (i + 1) * direction.getVelocity();
		}
		if (!hasCollided) {
			moveX(moveAmountXRemainder * direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setX(newLocation);
			}
		}
		onEndCollisionCheckX(hasCollided, direction);
		return amountMoved + (moveAmountXRemainder * direction.getVelocity());
	}

	public float handleCollisionY(float moveAmountY) {
		int amountToMove = (int)Math.abs(moveAmountY);
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);
		Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;
		float amountMoved = 0;
		boolean hasCollided = false;
		for (int i = 0; i < amountToMove; i++) {
			moveY(direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setY(newLocation);
				break;
			}
			amountMoved = (i + 1) * direction.getVelocity();
		}
		if (!hasCollided) {
			moveY(moveAmountYRemainder * direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setY(newLocation);
			}
		}
		onEndCollisionCheckY(hasCollided, direction);
		return amountMoved + (moveAmountYRemainder * direction.getVelocity());
	}

	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }

	public float getCalibratedXLocation() {
		return x - map.getCamera().getX();
	}

	public float getCalibratedYLocation() {
		return y - map.getCamera().getY();
	}

	public Rectangle getCalibratedScaledBounds() {
		Rectangle scaledBounds = getScaledBounds();
		return new Rectangle(
			scaledBounds.getX1() - map.getCamera().getX(),
			scaledBounds.getY1() - map.getCamera().getY(),
			scaledBounds.getScaledWidth(),
			scaledBounds.getScaledHeight()
		);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		if (map != null) {
			graphicsHandler.drawImage(
					currentFrame.getImage(),
					Math.round(getCalibratedXLocation()),
					Math.round(getCalibratedYLocation()),
					currentFrame.getScaledWidth(),
					currentFrame.getScaledHeight(),
					currentFrame.getImageEffect());
		} else {
			super.draw(graphicsHandler);
		}
	}

	@Override
	public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
		if (map != null) {
			Rectangle scaledCalibratedBounds = getCalibratedScaledBounds();
			scaledCalibratedBounds.setColor(color);
			scaledCalibratedBounds.draw(graphicsHandler);
		} else {
			super.drawBounds(graphicsHandler, color);
		}
	}
}
