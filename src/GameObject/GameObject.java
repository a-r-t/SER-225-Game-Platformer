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

    // current direction game object is attempting to move in
    protected Direction currentXDirection, currentYDirection;

    // previous direction game object attempted to move in on the last frame
    protected Direction previousXDirection, previousYDirection;

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
        previousXDirection = currentXDirection;
        currentXDirection = dx < 0 ? Direction.LEFT : Direction.RIGHT;
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
        previousYDirection = currentYDirection;
        currentYDirection = dy < 0 ? Direction.UP : Direction.DOWN;
        if (map != null) {
            return handleCollisionY(dy);
        } else {
            super.moveY(dy);
            return dy;
        }
    }

    // performs collision check logic for moving along the x axis against the map's tiles
    private float handleCollisionX(float moveAmountX) {
//        System.out.println("HANDLE COLLISION X");
        // determines amount to move (whole number)
        int amountToMove = (int) Math.abs(moveAmountX);

        // gets decimal remainder from amount to move
        float moveAmountXRemainder = MathUtils.getRemainder(moveAmountX);

//        // determines direction that will be moved in based on if moveAmountX is positive or negative
//        Direction direction = moveAmountX < 0 ? Direction.LEFT : Direction.RIGHT;

        // moves game object one pixel at a time until total move amount is reached
        // if at any point a map tile collision is determined to have occurred from the move,
        // move player back to right in front of the "solid" map tile's position, and stop attempting to move further
        float amountMoved = 0;
        boolean hasCollided = false;
        MapEntity entityCollidedWith = null;
        for (int i = 0; i < amountToMove; i++) {
            moveX(currentXDirection.getVelocity());
            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, currentXDirection);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setX(collisionCheckResult.getAdjustedLocation().x);
                setY(collisionCheckResult.getAdjustedLocation().y);
                break;
            }
            amountMoved = (i + 1) * currentXDirection.getVelocity();
        }

        // if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountXRemainder)
        // it starts by moving the game object by that decimal amount
        // it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
        // if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
        if (!hasCollided) {
            moveX(moveAmountXRemainder * currentXDirection.getVelocity());
            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckX(this, map, currentXDirection);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setX(collisionCheckResult.getAdjustedLocation().x);
                setY(collisionCheckResult.getAdjustedLocation().y);
            }
        }

//        MapTile currentTile = map.getTileByPosition(getBounds().getX2(), getBounds().getY2());
//        if (currentTile.getTileType() == TileType.SLOPE) {
//            int rightBoundX = Math.round(getBoundsX2());
//            int xLocationInTile = rightBoundX - Math.round(currentTile.getX());
//            int southBoundY = Math.round(getBoundsY2());
//            int yLocationInTile = southBoundY - Math.round(currentTile.getY());
//            int counter = 0;
//            if (xLocationInTile >= 0 && xLocationInTile < currentTile.getLayout()[0].length && yLocationInTile >= 0
//                    && yLocationInTile < currentTile.getLayout().length) {
//                //System.out.println(xLocationInTile + ", " + yLocationInTile);
//                //System.out.println(currentTile.getLayout()[yLocationInTile - counter][xLocationInTile]);
//                while (currentTile.getLayout()[yLocationInTile - counter][xLocationInTile] == 1) {
////                    System.out.println("Counter: " + counter);
//                    counter++;
//                    if (yLocationInTile - counter < 0) {
//                        break;
//                    }
//                }
//                if (counter > 0) {
//                    System.out.println("SLOPE");
//                    moveY(-counter);
////                    hasCollided = true;
////                    entityCollidedWith = currentTile;
//                }
//            }
//        }

//        if (direction == Direction.RIGHT) {
//            MapTile currentTile = map.getTileByPosition(getBounds().getX2(), getBounds().getY2());
//            //System.out.println("NORM: " + getBounds().getX2() + ", " + (getBounds().getY() + getBounds().getHeight()));
//            //System.out.println("ROUND: " + Math.round(getBounds().getX2()) + ", " + (Math.round(getBounds().getY1()) + getBounds().getHeight()));
////            System.out.println("BOUNDS: "  + getBounds());
////            System.out.println("CAL BOUNDS: " + getCalibratedBounds());
////            System.out.println("LOCATION X: " + getX());
//            if (currentTile.getTileType() == TileType.SLOPE) {
//                int rightBoundX = Math.round(getBoundsX2());
//                int xLocationInTile = rightBoundX - Math.round(currentTile.getX());
//                int southBoundY = Math.round(getBoundsY2());
//                int yLocationInTile = southBoundY - Math.round(currentTile.getY());
//                int counter = 0;
//                if (xLocationInTile >= 0 && xLocationInTile < currentTile.getLayout()[0].length && yLocationInTile >= 0
//                        && yLocationInTile < currentTile.getLayout().length) {
//                    System.out.println(xLocationInTile + ", " + yLocationInTile);
//                    System.out.println(currentTile.getLayout()[yLocationInTile - counter][xLocationInTile]);
//                    while (currentTile.getLayout()[yLocationInTile + counter][xLocationInTile] == 1) {
//                        counter--;
//                        if (counter < 0) {
//                            break;
//                        }
//                    }
//                    System.out.println("SLOPE");
//                    moveY(counter);
//                    hasCollided = true;
//                    entityCollidedWith = currentTile;
//                }
//            }
//        }

        // call this method which a game object subclass can override to listen for collision events and react accordingly
        onEndCollisionCheckX(hasCollided, currentXDirection, entityCollidedWith);

        // returns the amount actually moved -- this isn't really used by the game, but I have it here for debug purposes
        return amountMoved + (moveAmountXRemainder * currentXDirection.getVelocity());
    }

    // performs collision check logic for moving along the y axis against the map's tiles
    private float handleCollisionY(float moveAmountY) {
//        System.out.println("HANDLE COLLISION Y");
        // determines amount to move (whole number)
        int amountToMove = (int) Math.abs(moveAmountY);

        // gets decimal remainder from amount to move
        float moveAmountYRemainder = MathUtils.getRemainder(moveAmountY);

//        // determines direction that will be moved in based on if moveAmountY is positive or negative
//        Direction direction = moveAmountY < 0 ? Direction.UP : Direction.DOWN;

        // moves game object one pixel at a time until total move amount is reached
        // if at any point a map tile collision is determined to have occurred from the move,
        // move player back to right in front of the "solid" map tile's position, and stop attempting to move further
        float amountMoved = 0;
        boolean hasCollided = false;
        MapEntity entityCollidedWith = null;
//        System.out.println("AMOUNT TO MOVE: " + moveAmountY);
        for (int i = 0; i < amountToMove; i++) {
            moveY(currentYDirection.getVelocity());

            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, currentYDirection);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setX(collisionCheckResult.getAdjustedLocation().x);
                setY(collisionCheckResult.getAdjustedLocation().y);
            }

            for (int j = 0; j < 2; j++) {
                int yBound = Math.round(getBounds().getY2());
                int xBound = 0;
                if (j == 0) {
                    xBound = Math.round(getBounds().getX1());
                }
                else if (j == 1) {
                    xBound = Math.round(getBounds().getX2());
                }
                MapTile currentTile = map.getTileByPosition(xBound, yBound);
                if (currentTile != null && currentTile.getTileType() == TileType.SLOPE) {
                    int xLocationInTile = xBound - Math.round(currentTile.getX());
                    int yLocationInTile = yBound - Math.round(currentTile.getY());
                    int counter = 0;
                    if (xLocationInTile >= 0 && xLocationInTile < currentTile.getLayout().getBounds()[0].length && yLocationInTile >= 0
                            && yLocationInTile < currentTile.getLayout().getBounds().length) {
                        System.out.println("LOCATION IN TILE: " + xLocationInTile + ", " + yLocationInTile);
                        //System.out.println(currentTile.getLayout()[yLocationInTile - counter][xLocationInTile]);
                        while (currentTile.getLayout().getBounds()[yLocationInTile - counter][xLocationInTile] == 1) {
                            counter++;
                            if (yLocationInTile - counter < 0) {
                                break;
                            }
                        }
                        if (counter > 0) {
                            System.out.println("SLOPE LAST CHANCE");
                            float currentTileYLocation = currentTile.getBoundsY1();
                            System.out.println("Current Tile Y Location: " + currentTileYLocation);
                            int targetSlopeLocationIndex = yLocationInTile - counter;
                            System.out.println("Target slope location index: " + targetSlopeLocationIndex);
                            float targetSlopeYLocation = currentTileYLocation + targetSlopeLocationIndex;
                            System.out.println("Target slope y location: " + targetSlopeYLocation);
                            float boundsDifference = getY2() - getBoundsY2();
                            System.out.println("Bounds difference: " + boundsDifference);
                            float targetYLocation = targetSlopeYLocation - (getHeight() - 1) + boundsDifference;
                            System.out.println("Target Y Location: " + targetYLocation);
                            setY(targetYLocation);
                            System.out.println("PLAYER Y2 AFTER ADJUSTMENT: " + getBounds().getY2());
                            hasCollided = true;
                            entityCollidedWith = currentTile;
                        }
                    }
                }
            }

            amountMoved = (i + 1) * currentYDirection.getVelocity();
            if (hasCollided) {
                break;
            }
        }

        // if no collision occurred in the above steps, this deals with the decimal remainder from the original move amount (stored in moveAmountYRemainder)
        // it starts by moving the game object by that decimal amount
        // it then does one more check for a collision in the case that this added decimal amount was enough to change the rounding and move the game object to the next pixel over
        // if a collision occurs from this move, the player is moved back to right in front of the "solid" map tile's position
        if (!hasCollided) {
            moveY(moveAmountYRemainder * currentYDirection.getVelocity());
            MapCollisionCheckResult collisionCheckResult = MapCollisionHandler.getAdjustedPositionAfterCollisionCheckY(this, map, currentYDirection);
            if (collisionCheckResult.getAdjustedLocation() != null) {
                hasCollided = true;
                entityCollidedWith = collisionCheckResult.getEntityCollidedWith();
                setX(collisionCheckResult.getAdjustedLocation().x);
                setY(collisionCheckResult.getAdjustedLocation().y);
            }
        }

        System.out.println("REMAINDER AMOUNT TO MOVE: " + moveAmountYRemainder * currentYDirection.getVelocity());
        System.out.println("PLAYER Y2: " + getBounds().getY2());
        if (currentYDirection == Direction.DOWN) {
            for (int i = 0; i < 2; i++) {
                int yBound = Math.round(getBounds().getY2());
                int xBound = 0;
                if (i == 0) {
                    xBound = Math.round(getBounds().getX1());
                }
                else if (i == 1) {
                    xBound = Math.round(getBounds().getX2());
                }
                MapTile currentTile = map.getTileByPosition(xBound, yBound);
                if (currentTile != null && currentTile.getTileType() == TileType.SLOPE) {
                    int xLocationInTile = xBound - Math.round(currentTile.getX());
                    int yLocationInTile = yBound - Math.round(currentTile.getY());
                    int counter = 0;
                    if (xLocationInTile >= 0 && xLocationInTile < currentTile.getLayout().getBounds()[0].length && yLocationInTile >= 0
                            && yLocationInTile < currentTile.getLayout().getBounds().length) {
                        System.out.println("LOCATION IN TILE: " + xLocationInTile + ", " + yLocationInTile);
                        //System.out.println(currentTile.getLayout()[yLocationInTile - counter][xLocationInTile]);
                        while (currentTile.getLayout().getBounds()[yLocationInTile - counter][xLocationInTile] == 1) {
                            counter++;
                            if (yLocationInTile - counter < 0) {
                                break;
                            }
                        }
                        if (counter > 0) {
                            System.out.println("SLOPE LAST CHANCE");
                            float currentTileYLocation = currentTile.getBoundsY1();
                            System.out.println("Current Tile Y Location: " + currentTileYLocation);
                            int targetSlopeLocationIndex = yLocationInTile - counter;
                            System.out.println("Target slope location index: " + targetSlopeLocationIndex);
                            float targetSlopeYLocation = currentTileYLocation + targetSlopeLocationIndex;
                            System.out.println("Target slope y location: " + targetSlopeYLocation);
                            float boundsDifference = getY2() - getBoundsY2();
                            System.out.println("Bounds difference: " + boundsDifference);
                            float targetYLocation = targetSlopeYLocation - (getHeight() - 1) + boundsDifference;
                            System.out.println("Target Y Location: " + targetYLocation);
                            setY(targetYLocation);
                            System.out.println("PLAYER Y2 AFTER ADJUSTMENT: " + getBounds().getY2());
                            hasCollided = true;
                            entityCollidedWith = currentTile;
                        }
                    }
                }
            }
        }

        // call this method which a game object subclass can override to listen for collision events and react accordingly
        onEndCollisionCheckY(hasCollided, currentYDirection, entityCollidedWith);

        // returns the amount actually moved -- this isn't really used by the game, but I have it here for debug purposes
        return amountMoved + (moveAmountYRemainder * currentYDirection.getVelocity());
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

            if (this instanceof Player) {
                //drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
                //graphicsHandler.drawFilledRectangle(Math.round(getBounds().getX1()), Math.round(getBounds().getY1()), getBounds().getWidth(), getBounds().getHeight(), new Color(255, 0, 0, 100));
                //graphicsHandler.drawFilledRectangle(Math.round(getBounds().getX1()), Math.round(getBounds().getY1()), getBounds().getWidth(), 1, Color.BLUE);
//                System.out.println("X + W: " + (Math.round(getBounds().getX1()) + getBounds().getWidth()));
//                System.out.println("X2: " + (Math.round(getBounds().getX2())));
                //graphicsHandler.drawFilledRectangle(Math.round(getBounds().getX2()), Math.round(getBounds().getY2()), 1, 1, Color.yellow);
                //graphicsHandler.drawRectangle(Math.round(getBounds().getX1()), Math.round(getBounds().getY1()), getBounds().getWidth(), 1, Color.YELLOW);
                //graphicsHandler.drawRectangle(Math.round(getBounds().getX2()), Math.round(getBounds().getY2()), 1, 1, Color.YELLOW);
            }

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
