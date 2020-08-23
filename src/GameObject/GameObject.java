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

/*
	The all important GameObject class is what every "entity" used in this game should be based off of
	It encapsulates all the other class logic in the GameObject package to be a "one stop shop" for all entity needs
	This includes:
	1. displaying an image (as a sprite) to represent the entity
	2. animation logic for the sprite
	3. collision detection with a map
	4. performing proper draw logic based on camera movement
 */
public class GameObject extends AnimatedSprite {

	// stores game object's start position
	// important to keep track of this as it's what allows the special draw logic to work
	protected float startPositionX, startPositionY;

	// how much game object's position has changed from start position over time
	// also important to keep track of for the special draw logic
	protected float amountMovedX, amountMovedY;

	// previous location the game object was in from the last frame
	protected float previousX, previousY;

	// the map instance this game object "belongs" to.
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
		// call to animation logic
		super.update();

		// update previous position to be the current position
		previousX = x;
		previousY = y;
	}

	// move game object along the x axis
	// will stop object from moving based on map collision logic (such as if it hits a solid tile)
	public void moveXHandleCollision(float dx) {
		if (map != null) {
			handleCollisionX(dx);
		} else {
			super.moveX(dx);
		}
	}

	// move game object along the y axis
	// will stop object from moving based on map collision logic (such as if it hits a solid tile)
	public void moveYHandleCollision(float dy) {
		if (map != null) {
			handleCollisionY(dy);
		} else {
			super.moveY(dy);
		}
	}

	// performs collision check logic for moving along the x axis against the map's tiles
	public float handleCollisionX(float moveAmountX) {
		// determines amount to move (whole number)
		int amountToMove = (int)Math.abs(moveAmountX);

		// gets decimal remainder from amount to move
		float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);

		// determines direction that will be moved in based on if moveAmountX is positive or negative
		Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;

		// moves game object one pixel at a time until total move amount is reached
		// if at any point a map tile collision is determined to have occurred from the move,
		// move player back to right in front of the "solid" map tile's position, and stop attempting to move further
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

		// if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountXRemainder)
		// it starts by moving the game object by that decimal amount
		// it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
		// if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
		if (!hasCollided) {
			moveX(moveAmountXRemainder * direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setX(newLocation);
			}
		}

		// call this method which a game object subclass can override to listen for collision events and react accordingly
		onEndCollisionCheckX(hasCollided, direction);

		// returns the amount actually moved -- this isn't really used by the game, but I have it here for debug purposes
		return amountMoved + (moveAmountXRemainder * direction.getVelocity());
	}

	// performs collision check logic for moving along the y axis against the map's tiles
	public float handleCollisionY(float moveAmountY) {
		// determines amount to move (whole number)
		int amountToMove = (int)Math.abs(moveAmountY);

		// gets decimal remainder from amount to move
		float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);

		// determines direction that will be moved in based on if moveAmountY is positive or negative
		Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;

		// moves game object one pixel at a time until total move amount is reached
		// if at any point a map tile collision is determined to have occurred from the move,
		// move player back to right in front of the "solid" map tile's position, and stop attempting to move further
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

		// if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountYRemainder)
		// it starts by moving the game object by that decimal amount
		// it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
		// if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
		if (!hasCollided) {
			moveY(moveAmountYRemainder * direction.getVelocity());
			float newLocation = MapTileCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
			if (newLocation != 0) {
				hasCollided = true;
				setY(newLocation);
			}
		}

		// call this method which a game object subclass can override to listen for collision events and react accordingly
		onEndCollisionCheckY(hasCollided, direction);

		// returns the amount actually moved -- this isn't really used by the game, but I have it here for debug purposes
		return amountMoved + (moveAmountYRemainder * direction.getVelocity());
	}

	// game object subclass can override this method to listen for x axis collision events and react accordingly after calling "moveXHandleCollision"
	public void onEndCollisionCheckX(boolean hasCollided, Direction direction) { }

	// game object subclass can override this method to listen for y axis collision events and react accordingly after calling "moveYHandleCollision"
	public void onEndCollisionCheckY(boolean hasCollided, Direction direction) { }

	// gets x location taking into account map camera position
	public float getCalibratedXLocation() {
		if (map != null) {
			return x - map.getCamera().getX();
		} else {
			return getX();
		}
	}

	// gets y location taking into account map camera position
	public float getCalibratedYLocation() {
		if (map != null) {
			return y - map.getCamera().getY();
		} else {
			return getY();
		}
	}

	// gets scaled bounds taking into account map camera position
	public Rectangle getCalibratedScaledBounds() {
		if (map != null) {
			Rectangle scaledBounds = getScaledBounds();
			return new Rectangle(
					scaledBounds.getX1() - map.getCamera().getX(),
					scaledBounds.getY1() - map.getCamera().getY(),
					scaledBounds.getScaledWidth(),
					scaledBounds.getScaledHeight()
			);
		} else {
			return getScaledBounds();
		}
	}

	// set this game object's map to make it a "part of" the map, allowing calibrated positions and collision handling logic to work
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
