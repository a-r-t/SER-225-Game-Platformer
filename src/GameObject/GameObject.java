package GameObject;

import Builders.FrameBuilder;
import EnhancedMapTiles.HorizontalMovingPlatform;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.MapTile;
import Scene.Player;
import Utils.Direction;
import Utils.MathUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class GameObject extends AnimatedSprite {


	public GameObject(SpriteSheet spriteSheet, float x, float y, String startingAnimation) {
		super(spriteSheet, x, y, startingAnimation);
	}

	public GameObject(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
		super(x, y, animations, startingAnimation);
	}

	public GameObject(BufferedImage image, float x, float y, String startingAnimation) {
		super(image, x, y, startingAnimation);
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
	}


	public void update() {
		super.update();
	}

	public int moveXHandleCollision(Map map, float dx) {
		return handleCollisionX(map, dx);
	}

	public int moveYHandleCollision(Map map, float dy) {
		return handleCollisionY(map, dy);
	}

	public int handleCollisionX(Map map, float moveAmountX) {
		int amountToMove = (int)Math.abs(moveAmountX);
		float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);
		float currentXRemainder = MathUtils.getRemainder(getXRaw());

		if (moveAmountXRemainder + currentXRemainder >= 1) {
			amountToMove += 1;
			setX(getX());
			moveAmountXRemainder = 0;
		}
		int amountMoved = 0;
		Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;
		moveX(moveAmountXRemainder * direction.getVelocity());

		if (amountToMove > 0) {
			boolean hasCollided = false;
			for (int i = 0; i < amountToMove; i++) {
				moveX(direction.getVelocity());
				hasCollided = hasCollidedWithTilesX(map, direction);
				if (hasCollided) {
					moveX(-direction.getVelocity());
					break;
				}
				amountMoved = (i + 1) * direction.getVelocity();
			}
			onEndCollisionCheckX(hasCollided, direction);
		}

		return amountMoved;
	}

	public int handleCollisionY(Map map, float moveAmountY) {
		int amountToMove = (int)Math.abs(moveAmountY);
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);
		float currentYRemainder = MathUtils.getRemainder(getYRaw());

		if (moveAmountYRemainder + currentYRemainder >= 1) {
			amountToMove += 1;
			setY(getY());
		} else {
			moveY(moveAmountYRemainder);
		}

		int amountMoved = 0;
		Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;
		if (amountToMove > 0) {
			boolean hasCollided = false;
			for (int i = 0; i < amountToMove; i++) {
				moveY(direction.getVelocity());
				hasCollided = hasCollidedWithTilesY(map, direction);
				if (hasCollided) {
					moveY(-direction.getVelocity());
					break;
				}
				amountMoved = (i + 1) * direction.getVelocity();
			}
			onEndCollisionCheckY(hasCollided, direction);
		}
		return amountMoved;
	}

	protected boolean hasCollidedWithTilesX(Map map, Direction direction) {
		int numberOfTilesToCheck = Math.max(getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
		int edgeBoundX = direction == Direction.LEFT ? getScaledBounds().getX1() : getScaledBounds().getX2();
		Point tileIndex = map.getTileIndexByPosition(edgeBoundX, getScaledBounds().getY1());
		for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
			MapTile mapTile = map.getMapTile(tileIndex.x, tileIndex.y + j);
			if (mapTile != null && (hasCollidedWithMapTile(mapTile, direction) || hasCollidedWithEnhancedTile(map, direction))) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasCollidedWithTilesY(Map map, Direction direction) {
		int numberOfTilesToCheck = Math.max(getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
		int edgeBoundY = direction == Direction.UP ? getScaledBounds().getY() : getScaledBounds().getY2();
		Point tileIndex = map.getTileIndexByPosition(getScaledBounds().getX(), edgeBoundY);
		for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
			MapTile mapTile = map.getMapTile(tileIndex.x + j, tileIndex.y);
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
				return direction == Direction.DOWN && intersects(mapTile) && getScaledBoundsY2() - 1 == mapTile.getScaledBoundsY1();
			default:
				return false;
		}
	}

	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }
}
