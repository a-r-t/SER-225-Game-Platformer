package GameObject;

import Engine.GraphicsHandler;
import Level.*;
import Utils.Direction;
import Utils.ImageUtils;
import Utils.MathUtils;

import java.awt.*;
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

    public GameObject(float x, float y, Frame[] frames) {
        super(x, y, frames);
        this.startPositionX = x;
        this.startPositionY = y;
        this.previousX = x;
        this.previousY = y;
    }

    public GameObject(float x, float y, Frame frame) {
        super(x, y, frame);
        this.startPositionX = x;
        this.startPositionY = y;
        this.previousX = x;
        this.previousY = y;
    }

    public GameObject(float x, float y) {
        super(x, y, new Frame(ImageUtils.createSolidImage(new Color(255, 0, 255)), ImageEffect.NONE, 1, null));
        this.startPositionX = x;
        this.startPositionY = y;
        this.previousX = x;
        this.previousY = y;
    }

    @Override
    public void update() {
        // update previous position to be the current position
        previousX = x;
        previousY = y;

        // call to animation logic
        super.update();
    }

    // move game object along the x axis
    // will stop object from moving based on map collision logic (such as if it hits a solid tile)
    public float moveXHandleCollision(float dx) {
        if (map != null) {
            return handleCollisionX(dx);
        } else {
            super.moveX(dx);
            return dx;
        }
    }

    // move game object along the y axis
    // will stop object from moving based on map collision logic (such as if it hits a solid tile)
    public float moveYHandleCollision(float dy) {
        if (map != null) {
            return handleCollisionY(dy);
        } else {
            super.moveY(dy);
            return dy;
        }
    }

    // performs collision check logic for moving along the x axis against the map's tiles
    private float handleCollisionX(float moveAmountX) {
        // determines amount to move (whole number)
        int amountToMove = (int) Math.abs(moveAmountX);


        // gets decimal remainder from amount to move
        float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);

        // determines direction that will be moved in based on if moveAmountX is positive or negative
        Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;

        // moves game object one pixel at a time until total move amount is reached
        // if at any point a map tile collision is determined to have occurred from the move, adjust player position to right in front of the "solid" map tile's position, and stop attempting to move further
        // there is special logic to handle movement across slope map tiles
        float amountMoved = 0;
        boolean hasCollided = false;
        MapEntity entityCollidedWith = null;
        for (int i = 0; i < amountToMove; i++) {
            // determines if player is in proximity with a slope (needed for later if moving down a slope)
            SlopeProximityStatus slopeProximityStatus = MapCollisionHandler.getCurrentSlopeProximityStatus(this, map, direction);

            // move player in intended direction
            moveX(direction.getVelocity());

            // adjust x position if a collision occurred
            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setX(collisionCheckResult.getAdjustedLocation().x);
            }

            // adjust y position if moving down a slope
            MapCollisionCheckResult slopeCollisionMovingDownCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionSlopeCheckX(this, map, slopeProximityStatus);
            if (slopeCollisionMovingDownCheckResult.getAdjustedLocation() != null) {
                setY(slopeCollisionMovingDownCheckResult.getAdjustedLocation().y);
            }

            // adjust y position if moving up a slope
            MapCollisionCheckResult slopeCollisionMovingUpCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionSlopeCheckY(this, map);
            if (slopeCollisionMovingUpCheckResult.getAdjustedLocation() != null) {
                setY(slopeCollisionMovingUpCheckResult.getAdjustedLocation().y);
            }

            if (hasCollided) {
                break;
            }

            amountMoved = i + 1;
        }

        // if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountXRemainder)
        // it starts by moving the game object by that decimal amount
        // it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
        // if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
        // there is special logic to handle movement across slope map tiles
        SlopeProximityStatus slopeProximityStatus = MapCollisionHandler.getCurrentSlopeProximityStatus(this, map, direction);

        if (!hasCollided) {
            moveX(moveAmountXRemainder * direction.getVelocity());
            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, direction);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                float xLocationBeforeAdjustment = getX();
                setX(collisionCheckResult.getAdjustedLocation().x);
                amountMoved += Math.abs(xLocationBeforeAdjustment - getX());
            }
        }

        // adjust y position if moving down a slope
        MapCollisionCheckResult slopeCollisionMovingDownCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionSlopeCheckX(this, map, slopeProximityStatus);
        if (slopeCollisionMovingDownCheckResult.getAdjustedLocation() != null) {
            setY(slopeCollisionMovingDownCheckResult.getAdjustedLocation().y);
        }

        // adjust y position if moving up a slope
        MapCollisionCheckResult slopeCollisionMovingUpCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionSlopeCheckY(this, map);
        if (slopeCollisionMovingUpCheckResult.getAdjustedLocation() != null) {
            setY(slopeCollisionMovingUpCheckResult.getAdjustedLocation().y);
        }

        // call this method which a game object subclass can override to listen for collision events and react accordingly
        onEndCollisionCheckX(hasCollided, direction, entityCollidedWith);

        // returns the amount actually moved
        return amountMoved * direction.getVelocity();
    }

    // performs collision check logic for moving along the y axis against the map's tiles
    private float handleCollisionY(float moveAmountY) {
        // determines amount to move (whole number)
        int amountToMove = (int) Math.abs(moveAmountY);

        // gets decimal remainder from amount to move
        float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);

        // determines direction that will be moved in based on if moveAmountY is positive or negative
        Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;

        // moves game object one pixel at a time until total move amount is reached
        // if at any point a map tile collision is determined to have occurred from the move, adjust player position back to right in front of the "solid" map tile's position, and stop attempting to move further
        // there is special logic to handle movement across slope map tiles
        float amountMoved = 0;
        boolean hasCollided = false;
        MapEntity entityCollidedWith = null;
        for (int i = 0; i < amountToMove; i++) {
            moveY(direction.getVelocity());

            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setY(collisionCheckResult.getAdjustedLocation().y);
            }

            if (direction == Direction.DOWN) {
                MapCollisionCheckResult slopeCollisionMovingDownCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionSlopeCheckY(this, map);
                if (slopeCollisionMovingDownCheckResult.getAdjustedLocation() != null) {
                    hasCollided = true;
                    entityCollidedWith = slopeCollisionMovingDownCheckResult.getEntityCollidedWith();
                    setY(slopeCollisionMovingDownCheckResult.getAdjustedLocation().y);
                }
            }

            amountMoved = i + 1;
            if (hasCollided) {
                break;
            }
        }

        // if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountYRemainder)
        // it starts by moving the game object by that decimal amount
        // it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
        // if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
        // there is special logic to handle movement across slope map tiles
        if (!hasCollided) {
            moveY(moveAmountYRemainder * direction.getVelocity());
            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, direction);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setY(collisionCheckResult.getAdjustedLocation().y);
            }
        }

        if (direction == Direction.DOWN) {
            MapCollisionCheckResult slopeCollisionMovingDownCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionSlopeCheckY(this, map);
            if (slopeCollisionMovingDownCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = slopeCollisionMovingDownCheckResult.getEntityCollidedWith();
                setY(slopeCollisionMovingDownCheckResult.getAdjustedLocation().y);
            }
        }

        // call this method which a game object subclass can override to listen for collision events and react accordingly
        onEndCollisionCheckY(hasCollided, direction, entityCollidedWith);

        // returns the amount actually moved
        return amountMoved * direction.getVelocity();
    }

    // game object subclass can override this method to listen for x axis collision events and react accordingly after calling "moveXHandleCollision"
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
    }

    // game object subclass can override this method to listen for y axis collision events and react accordingly after calling "moveYHandleCollision"
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
    }

    // gets x location taking into account map camera position
    public float getCalibratedXLocation() {
        if (map != null) {
            return Math.round(x) - map.getCamera().getX();
        } else {
            return Math.round(getX());
        }
    }

    // gets y location taking into account map camera position
    public float getCalibratedYLocation() {
        if (map != null) {
            return Math.round(y) - map.getCamera().getY();
        } else {
            return Math.round(getY());
        }
    }

    // gets bounds taking into account map camera position
    public Rectangle getCalibratedBounds() {
        if (map != null) {
            Rectangle bounds = getBounds();
            return new Rectangle(
                    Math.round(bounds.getX1()) - Math.round(map.getCamera().getX()),
                    Math.round(bounds.getY1()) - Math.round(map.getCamera().getY()),
                    Math.round(bounds.getWidth()),
                    Math.round(bounds.getHeight())
            );
        } else {
            return getBounds();
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
                    currentFrame.getWidth(),
                    currentFrame.getHeight(),
                    currentFrame.getImageEffect());

            // Uncomment this to draw player's bounds to screen -- useful for debugging
            /*
            if (this instanceof Player) {
                drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
            }
            */
        } else {
            super.draw(graphicsHandler);
        }
    }

    @Override
    public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        if (map != null) {
            Rectangle calibratedBounds = getCalibratedBounds();
            calibratedBounds.setColor(color);
            calibratedBounds.draw(graphicsHandler);
        } else {
            super.drawBounds(graphicsHandler, color);
        }
    }
}
