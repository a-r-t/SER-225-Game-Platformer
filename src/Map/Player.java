package Map;

import Engine.Key;
import Engine.Keyboard;
import Engine.Graphics;
import GameObject.*;
import GameObject.Rectangle;
import Utils.Direction;

import java.awt.*;
import java.util.HashSet;

public abstract class Player extends GameObject {
    protected float walkSpeed = 0;
    protected float gravity = 0;
    protected float jumpHeight = 0;
    protected float jumpDegrade = 0;
    protected float terminalVelocityY = 0;
    protected float momentumYIncrease = 0;
    protected float jumpForce = 0;
    protected float momentumY = 0;
    protected float moveAmountX, moveAmountY;
    protected Rectangle sceneBounds;
    protected PlayerState playerState;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    protected AirGroundState previousAirGroundState;
    protected HashSet<Key> lockedKeys = new HashSet<>();
    protected Key JUMP_KEY = Key.W;
    protected Key MOVE_LEFT_KEY = Key.A;
    protected Key MOVE_RIGHT_KEY = Key.D;
    protected Key CROUCH_KEY = Key.S;

    public Player(SpriteSheet spriteSheet, float x, float y, Rectangle sceneBounds, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        this.sceneBounds = sceneBounds;
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
    }

    public void update(Keyboard keyboard, Map map) {
        moveAmountX = 0;
        moveAmountY = 0;

        applyGravity();
        handlePlayerState(keyboard);

        previousAirGroundState = airGroundState;

        super.update();

        handleCollisionY(map);
        handleCollisionX(map);
        updateLockedKeys(keyboard);
    }

    protected void handlePlayerState(Keyboard keyboard) {
        switch (playerState) {
            case STANDING:
                playerStanding(keyboard);
                break;
            case WALKING:
                playerWalking(keyboard);
                break;
            case CROUCHING:
                playerCrouching(keyboard);
                break;
            case JUMPING:
                playerJumping(keyboard);
                break;
        }
    }

    protected void playerStanding(Keyboard keyboard) {
        currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
        if (keyboard.isKeyDown(MOVE_LEFT_KEY) || keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.WALKING;
        } else if (keyboard.isKeyDown(JUMP_KEY) && !isKeyLocked(JUMP_KEY)) {
            lockedKeys.add(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        } else if (keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
    }

    protected void playerWalking(Keyboard keyboard) {
        currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
        if (keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
        } else if (keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
        } else if (keyboard.isKeyUp(MOVE_LEFT_KEY) && keyboard.isKeyUp(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.STANDING;
        }

        if (keyboard.isKeyDown(JUMP_KEY) && !isKeyLocked(JUMP_KEY)) {
            lockedKeys.add(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        } else if (keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
    }

    protected void playerCrouching(Keyboard keyboard) {
        currentAnimationName = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
        if (keyboard.isKeyUp(CROUCH_KEY)) {
            playerState = PlayerState.STANDING;
        }
        if (keyboard.isKeyDown(JUMP_KEY) && !isKeyLocked(JUMP_KEY)) {
            lockedKeys.add(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }
    }

    protected void playerJumping(Keyboard keyboard) {
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {
            currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            airGroundState = AirGroundState.AIR;
            jumpForce = jumpHeight;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }
        }
        else if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            if (jumpForce >= gravity + momentumY) {
                currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            } else {
                currentAnimationName = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
            }

            if (keyboard.isKeyDown(MOVE_LEFT_KEY)) {
                moveAmountX -= walkSpeed;
            } else if (keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
                moveAmountX += walkSpeed;
            }
        }
        else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }
    }

    protected void applyGravity() {
        moveAmountY += gravity + momentumY;
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }
    }

    protected void updateLockedKeys(Keyboard keyboard) {
        if (keyboard.isKeyUp(JUMP_KEY)) {
            lockedKeys.remove(JUMP_KEY);
        }
    }

    protected void handleCollisionX(Map map) {
        int amountToMove = Math.abs(Math.round(moveAmountX));
        if (amountToMove != 0) {
            boolean hasCollided = false;
            int direction = moveAmountX < 0 ? -1 : 1;
            for (int i = 0; i < amountToMove; i++) {
                moveX(direction);
                hasCollided = hasCollidedWithTilesX(map);
                if (hasCollided) {
                    moveX(-direction);
                    moveAmountX = i;
                    break;
                }
            }
        }
    }

    protected void handleCollisionY(Map map) {
        int amountToMove = Math.abs(Math.round(moveAmountY));
        if (amountToMove != 0) {
            boolean hasCollided = false;
            int direction = moveAmountY < 0 ? -1 : 1;
            for (int i = 0; i < amountToMove; i++) {
                moveY(direction);
                hasCollided = hasCollidedWithTilesY(map);
                if (hasCollided) {
                    moveY(-direction);
                    moveAmountY = i;
                    break;
                }
            }
            if (direction == 1) {
                if (hasCollided) {
                    momentumY = 0;
                    airGroundState = AirGroundState.GROUND;
                } else {
                    playerState = PlayerState.JUMPING;
                    airGroundState = AirGroundState.AIR;
                }
            } else if (direction == -1) {
                if (hasCollided) {
                    jumpForce = 0;
                }
            }
        }
    }

    private boolean hasCollidedWithTilesX(Map map) {
        int numberOfTilesToCheck = getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight();
        int edgeBoundX = moveAmountX < 0 ? getScaledBounds().getX1() : getScaledBounds().getX2();
        Point tileIndex = map.getTileIndexByPosition(edgeBoundX, getScaledBounds().getY1());
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            if (hasCollidedWithTile(map, tileIndex.x, tileIndex.y + j)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCollidedWithTilesY(Map map) {
        int numberOfTilesToCheck = getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth();
        int edgeBoundY = moveAmountY < 0 ? getScaledBounds().getY() : getScaledBounds().getY2();
        Point tileIndex = map.getTileIndexByPosition(getScaledBounds().getX(), edgeBoundY);
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            if (hasCollidedWithTile(map,tileIndex.x + j, tileIndex.y)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCollidedWithTile(Map map, int xTileIndex, int yTileIndex) {
        MapTile tile = map.getTile(xTileIndex, yTileIndex);
        int movementPermission = map.getMovementPermission(xTileIndex, yTileIndex);
        return tile != null && movementPermission == 1 && intersects(tile);
    }

    public int getMoveAmountX() {
        return Math.round(moveAmountX);
    }

    public int getMoveAmountY() {
        return Math.round(moveAmountY);
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }

    public boolean isKeyLocked(Key key) {
        return lockedKeys.contains(key);
    }

    protected enum PlayerState {
        STANDING, WALKING, JUMPING, CROUCHING
    }

    protected enum AirGroundState {
        AIR, GROUND
    }
}
