package GameObject;

import Builders.FrameBuilder;
import Enemies.DinosaurEnemy;
import Engine.GraphicsHandler;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.MapTile;
import Scene.MapTileCollisionHandler;
import Utils.Direction;
import Utils.MathUtils;
import Utils.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class GameObject extends AnimatedSprite {

	protected float startPositionX, startPositionY;
	protected float amountMovedX, amountMovedY;
	protected Map map;

	public GameObject(SpriteSheet spriteSheet, float x, float y, String startingAnimation, Map map) {
		super(spriteSheet, x, y, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public GameObject(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, Map map) {
		super(x, y, animations, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public GameObject(BufferedImage image, float x, float y, String startingAnimation, Map map) {
		super(image, x, y, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public GameObject(BufferedImage image, float x, float y, Map map) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0).build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public GameObject(BufferedImage image, float x, float y, float scale, Map map) {
		super(x, y);
		this.animations = new HashMap<String, Frame[]>() {{
			put("DEFAULT", new Frame[] {
					new FrameBuilder(image, 0)
							.withScale(scale)
							.build()
			});
		}};
		this.currentAnimationName = "DEFAULT";
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Map map) {
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
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public GameObject(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, Map map) {
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
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
		this.map = map;
	}

	public float moveXHandleCollision(Map map, float dx) {
		return handleCollisionX(map, dx);
	}

	public float moveYHandleCollision(Map map, float dy) {
		return handleCollisionY(map, dy);
	}

	public float handleCollisionX(Map map, float moveAmountX) {
		int amountToMove = (int)Math.abs(moveAmountX);
		float absX = x;
		float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);
		float wholeX = Math.round(x);
		Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;
		if (absX + moveAmountXRemainder >= wholeX + .5f) {
			amountToMove += 1;
			moveAmountXRemainder = moveAmountXRemainder - 1f;
		}
		float amountMoved = 0;
		if (amountToMove >= 1) {
			boolean hasCollided = false;
			for (int i = 0; i < amountToMove; i++) {
				moveX(direction.getVelocity());
				hasCollided = MapTileCollisionHandler.hasCollidedWithTilesX(this, map, direction);
				if (hasCollided) {
					moveX(-direction.getVelocity());
					moveAmountXRemainder = 0;
					break;
				}
				amountMoved = (i + 1) * direction.getVelocity();
			}
			onEndCollisionCheckX(hasCollided, direction);
		}
		moveX(moveAmountXRemainder * direction.getVelocity());
		return amountMoved + (moveAmountXRemainder * direction.getVelocity());
	}

	public float handleCollisionY(Map map, float moveAmountY) {
		int amountToMove = (int)Math.abs(moveAmountY);
		float absY = y;
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);
		float wholeY = Math.round(y);
		Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;
		if (absY + moveAmountYRemainder >= wholeY + .5f) {
			amountToMove += 1;
			moveAmountYRemainder = moveAmountYRemainder - 1f;
		}
		float amountMoved = 0;
		if (amountToMove >= 1) {
			boolean hasCollided = false;
			for (int i = 0; i < amountToMove; i++) {
				moveY(direction.getVelocity());
				hasCollided = MapTileCollisionHandler.hasCollidedWithTilesY(this, map, direction);
				if (hasCollided) {
					moveY(-direction.getVelocity());
					moveAmountYRemainder = 0;
					break;
				}
				amountMoved = (i + 1) * direction.getVelocity();
			}
			onEndCollisionCheckY(hasCollided, direction);
		}
		moveY(moveAmountYRemainder * direction.getVelocity());
		return amountMoved + (moveAmountYRemainder * direction.getVelocity());
	}

	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }

	public float getCalibratedXLocation(Map map) {
		return x - map.getCamera().getX();
	}

	public float getCalibratedYLocation(Map map) {
		return y - map.getCamera().getY();
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawImage(
				currentFrame.getImage(),
				Math.round(getCalibratedXLocation(map)),
				Math.round(getCalibratedYLocation(map)),
				currentFrame.getScaledWidth(),
				currentFrame.getScaledHeight(),
				currentFrame.getImageEffect());
	}

	@Override
	public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
		Rectangle boundsTemp = currentFrame.getBoundsTemp();
		Rectangle scaledCalibratedBounds = new Rectangle(
				getCalibratedXLocation(map) + boundsTemp.getX() * boundsTemp.getScale(),
				getCalibratedYLocation(map) + boundsTemp.getY() * boundsTemp.getScale(),
				boundsTemp.getScaledWidth(),
				boundsTemp.getScaledHeight());
		scaledCalibratedBounds.setColor(color);
		scaledCalibratedBounds.draw(graphicsHandler);
	}
}
