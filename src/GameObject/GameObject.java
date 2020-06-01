package GameObject;

import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.MapTile;
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
		}
		int amountMoved = 0;
		Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;
		if (amountToMove != 0) {
			boolean hasCollided = false;
			for (int i = 0; i < amountToMove; i++) {
				super.moveX(direction.getVelocity());
				hasCollided = hasCollidedWithTilesX(map, direction);
				if (hasCollided) {
					super.moveX(-direction.getVelocity());
					break;
				}
				amountMoved = (i + 1) * direction.getVelocity();
			}
			onEndCollisionCheckX(hasCollided, direction);
		}
		super.moveX(moveAmountXRemainder * direction.getVelocity());
		return amountMoved;
	}

	public int handleCollisionY(Map map, float moveAmountY) {
		int amountToMove = (int)Math.abs(moveAmountY);
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);
		float currentYRemainder = MathUtils.getRemainder(getYRaw());
		if (moveAmountYRemainder + currentYRemainder >= 1) {
			amountToMove += 1;
			setY(getY());
		}
		int amountMoved = 0;
		Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;
		if (amountToMove != 0) {
			boolean hasCollided = false;
			for (int i = 0; i < amountToMove; i++) {
				super.moveY(direction.getVelocity());
				hasCollided = hasCollidedWithTilesY(map, direction);
				if (hasCollided) {
					super.moveY(-direction.getVelocity());
					break;
				}
				amountMoved = (i + 1) * direction.getVelocity();
			}
			onEndCollisionCheckY(hasCollided, direction);
		}
		super.moveY(moveAmountYRemainder * direction.getVelocity());
		return amountMoved;
	}

	protected boolean hasCollidedWithTilesX(Map map, Direction direction) {
		int numberOfTilesToCheck = getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight();
		int edgeBoundX = direction == Direction.LEFT ? getScaledBounds().getX1() : getScaledBounds().getX2();
		Point tileIndex = map.getTileIndexByPosition(edgeBoundX, getScaledBounds().getY1());
		for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
			if (hasCollidedWithTile(map, tileIndex.x, tileIndex.y + j, direction) || hasCollidedWithEnhancedTile(map, direction)) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasCollidedWithTilesY(Map map, Direction direction) {
		int numberOfTilesToCheck = getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth();
		int edgeBoundY = direction == Direction.UP ? getScaledBounds().getY() : getScaledBounds().getY2();
		Point tileIndex = map.getTileIndexByPosition(getScaledBounds().getX(), edgeBoundY);
		for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
			if (hasCollidedWithTile(map,tileIndex.x + j, tileIndex.y, direction) || hasCollidedWithEnhancedTile(map, direction)) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasCollidedWithTile(Map map, int xTileIndex, int yTileIndex, Direction direction) {
		MapTile tile = map.getMapTile(xTileIndex, yTileIndex);

		if (tile == null) {
			return false;
		} else {
			switch (tile.getTileType()) {
				case PASSABLE:
					return false;
				case NOT_PASSABLE:
					return intersects(tile);
				case JUMP_THROUGH_PLATFORM:
					return direction == Direction.DOWN && intersects(tile) && getScaledBoundsY2() - 1 == tile.getScaledBoundsY1();
				default:
					return false;
			}
		}
	}

	protected boolean hasCollidedWithEnhancedTile(Map map, Direction direction) {
		for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
			switch (enhancedMapTile.getTileType()) {
				case PASSABLE:
					return false;
				case NOT_PASSABLE:
					return intersects(enhancedMapTile);
				case JUMP_THROUGH_PLATFORM:
					return direction == Direction.DOWN && intersects(enhancedMapTile) && getScaledBoundsY2() - 1 == enhancedMapTile.getScaledBoundsY1();
				default:
					return false;
			}
		}
		return false;
	}

	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }
}
