package GameObject;

import Builders.FrameBuilder;
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
	protected float moveAmountX, moveAmountY;
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

	public float getCalibratedXLocation(Map map) {
		return startPositionX + amountMovedX - map.getCamera().getAmountMovedX();
	}

	public float getCalibratedYLocation(Map map) {
		return startPositionY + amountMovedY - map.getCamera().getAmountMovedY();
	}

	public float getPureXLocation() {
		return startPositionX + amountMovedX;
	}

	public float getPureYLocation() {
		return startPositionY + amountMovedY;
	}

	public float moveXHandleCollision(Map map, float dx) {
		return handleCollisionX(map, dx);
	}

	public float moveYHandleCollision(Map map, float dy) {
		return handleCollisionY(map, dy);
	}

	public float handleCollisionX(Map map, float moveAmountX) {
		int amountToMove = (int)Math.abs(moveAmountX);
		float absX = startPositionX + amountMovedX;
		float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);
		float wholeX = Math.round(startPositionX + amountMovedX);
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
		float absY = startPositionY + amountMovedY;
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);
		float wholeY = Math.round(startPositionY + amountMovedY);
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

	public float getStartPositionX() {
		return startPositionX;
	}

	public float getStartPositionY() {
		return startPositionY;
	}

	public void update() {
		super.update();
		amountMovedX += moveAmountX;
		amountMovedY += moveAmountY;
		moveAmountX = 0;
		moveAmountY = 0;
	}

	@Override
	public void moveX(float dx) {
		moveAmountX += dx;
		super.moveX(dx);
	}

	@Override
	public void moveY(float dy) {
		moveAmountY += dy;
		super.moveY(dy);
	}

	@Override
	public void setX(float x) {
		float difference = x - this.x;
		moveAmountX += difference;
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		float difference = y - this.y;
		moveAmountY += difference;
		super.setY(y);
	}

	@Override
	public void setLocation(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	@Override
	public void moveRight(float dx) {
		moveAmountX += dx;
		super.moveRight(dx);
	}

	@Override
	public void moveLeft(float dx) {
		moveAmountX -= dx;
		super.moveLeft(dx);
	}

	@Override
	public void moveDown(float dy) {
		moveAmountY += dy;
		super.moveDown(dy);
	}

	@Override
	public void moveUp(float dy) {
		moveAmountY -= dy;
		super.moveUp(dy);
	}

	@Override
	public Rectangle getIntersectRectangle() {
		return getScaledBounds();
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
	public Rectangle getScaledBounds() {
		Rectangle boundsTemp = currentFrame.getBoundsTemp();
		return new Rectangle(
				getX() + boundsTemp.getX() * boundsTemp.getScale(),
				getY() + boundsTemp.getY() * boundsTemp.getScale(),
				boundsTemp.getScaledWidth(),
				boundsTemp.getScaledHeight());
	}


	@Override
	public float getScaledBoundsX1() {
		return getScaledBounds().getX1();
	}


	@Override
	public float getScaledBoundsX2() {
		return getScaledBounds().getX2();
	}


	@Override
	public float getScaledBoundsY1() {
		return getScaledBounds().getY1();
	}


	@Override
	public float getScaledBoundsY2() {
		return getScaledBounds().getY2();
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
