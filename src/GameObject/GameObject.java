package GameObject;

import Builders.FrameBuilder;
import EnhancedMapTiles.HorizontalMovingPlatform;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.MapTile;
import Scene.Player;
import Utils.Direction;
import Utils.MathUtils;
import Utils.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class GameObject extends AnimatedSprite {

	protected float startPositionX, startPositionY;
	protected float amountMovedX, amountMovedY;

	public GameObject(SpriteSheet spriteSheet, float x, float y, String startingAnimation) {
		super(spriteSheet, x, y, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
	}

	public GameObject(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(x, y, animations, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
	}

	public GameObject(BufferedImage image, float x, float y, String startingAnimation) {
		super(image, x, y, startingAnimation);
		this.startPositionX = x;
		this.startPositionY = y;
	}

	public GameObject(BufferedImage image, float x, float y) {
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
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
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
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
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
		setCurrentSprite();
		this.startPositionX = x;
		this.startPositionY = y;
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

	public void update() {
		super.update();
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
				hasCollided = hasCollidedWithTilesX(map, direction);
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
				hasCollided = hasCollidedWithTilesY(map, direction);
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

	protected boolean hasCollidedWithTilesX(Map map, Direction direction) {
		int numberOfTilesToCheck = Math.max(getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
		float edgeBoundX = direction == Direction.LEFT ? getScaledBounds().getX1() : getScaledBounds().getX2();
		Point tileIndex = map.getTileIndexByPosition(Math.round(edgeBoundX), Math.round(getScaledBounds().getY1()));
		for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
			MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y + j));
			if (mapTile != null && (hasCollidedWithMapTile(mapTile, direction) || hasCollidedWithEnhancedTile(map, direction))) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasCollidedWithTilesY(Map map, Direction direction) {
		int numberOfTilesToCheck = Math.max(getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
		float edgeBoundY = direction == Direction.UP ? getScaledBounds().getY() : getScaledBounds().getY2();
		Point tileIndex = map.getTileIndexByPosition(Math.round(getScaledBounds().getX1()), Math.round(edgeBoundY));
		for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
			MapTile mapTile = map.getMapTile(Math.round(tileIndex.x) + j, Math.round(tileIndex.y));
			if (mapTile != null && (hasCollidedWithMapTile(mapTile, direction) || hasCollidedWithEnhancedTile(map, direction))) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasCollidedWithEnhancedTile(Map map, Direction direction) {
		for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
			if (hasCollidedWithMapTile(enhancedMapTile, direction)) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasCollidedWithMapTile(MapTile mapTile, Direction direction) {
		switch (mapTile.getTileType()) {
			case PASSABLE:
				return false;
			case NOT_PASSABLE:
				return intersects(mapTile);
			case JUMP_THROUGH_PLATFORM:
				//int previousPlayerY2 = Math.round(previousLocationY + (getBoundsTemp().getY2() * getScale()));
				//int previousTileY1 = Math.round(mapTile.getPreviousLocationY() + (mapTile.getBoundsTemp().getY1() * mapTile.getScale()));
				if (direction == Direction.DOWN) {
					System.out.println("PLAYER  Y2: " + (getScaledBoundsY2()));
					System.out.println("TILE  Y1: " + (mapTile.getScaledBoundsY1()));
					System.out.println("PLAYER Y2 ROUNDED: " + Math.round(getScaledBoundsY2()));
					System.out.println("TILE Y1 ROUNDED: " + Math.round(mapTile.getScaledBoundsY1()));
				}
				//System.out.println(mapTile.getPreviousLocationY());
				if (direction == Direction.DOWN && bottomIntersectsTop(mapTile, true)) {
					System.out.println("IT HAPPENED");
					//System.exit(1);
				}
				return direction == Direction.DOWN && bottomIntersectsTop(mapTile, false);
			default:
				return false;
		}
	}

	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }
}
