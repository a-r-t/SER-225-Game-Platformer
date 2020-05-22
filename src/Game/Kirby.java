package Game;

import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import Engine.Graphics;
import GameObject.*;
import GameObject.Frame;
import GameObject.FrameBuilder;
import GameObject.Rectangle;
import Map.Map;
import Map.MapTile;
import Utils.Direction;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class Kirby extends AnimatedSprite {

    private final float gravity = .5f;
    private float momentumY = 0f;
    private final float terminalVelocityY = 6f;
    private final float jumpHeight = 20f;
    private final float jumpDegrade = .5f;
    private float jumpForce = 0;
    private Rectangle sceneBounds;
    private final float walkSpeed = 2f;
    private final float momentumYIncrease = 2f;
    private PlayerState playerState;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private AirGroundState previousAirGroundState;
    private Map map;
    private HashSet<Key> lockedKeys = new HashSet<>();
    private float moveAmountX, moveAmountY;
    private final Key JUMP_KEY = Key.W;
    private final Key MOVE_LEFT_KEY = Key.A;
    private final Key MOVE_RIGHT_KEY = Key.D;
    private final Key CROUCH_KEY = Key.S;

    public Kirby(float x, float y, Rectangle sceneBounds) {
        super(new SpriteSheet(ImageLoader.load("Kirby.png"), 24, 24), x, y, "STAND_RIGHT");
        this.sceneBounds = sceneBounds;
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
    }

    public void update(Keyboard keyboard) {
        applyGravity();

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

        previousAirGroundState = airGroundState;

        super.update();

        handleCollisionY();
        handleCollisionX();
//        if (moveAmountY < 0) {
//            handleCollisionUp();
//        } else if (moveAmountY > 0) {
//            handleCollisionDown();
//        }

//        if (moveAmountX < 0) {
//            handleCollisionLeft();
//        } else if (moveAmountX > 0) {
//            handleCollisionRight();
//        }

        moveAmountX = 0;
        moveAmountY = 0;
        updateLockedKeys(keyboard);
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
        if (keyboard.isKeyDown(JUMP_KEY)) {
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

    private boolean hasCollidedWithTile(int xTileIndex, int yTileIndex) {
        MapTile tile = map.getTile(xTileIndex, yTileIndex);
        int movementPermission = map.getMovementPermission(xTileIndex, yTileIndex);
        return tile != null && movementPermission == 1 && intersects(tile);
    }

    protected void handleCollisionX() {
        int amountToMove = Math.abs(Math.round(moveAmountX));
        if (amountToMove != 0) {
            boolean hasCollided = false;
            int direction = moveAmountX < 0 ? -1 : 1;
            for (int i = 0; i < amountToMove; i++) {
                setX(getX() + direction);
                int numberOfTilesToCheck = getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight();
                int edgeBoundX = moveAmountX < 0 ? getScaledBounds().getX1() : getScaledBounds().getX2();
                Point tileIndex = map.getTileIndexByPosition(edgeBoundX, getScaledBounds().getY1());
                for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
                    if (hasCollidedWithTile(tileIndex.x, tileIndex.y + j)) {
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided) {
                    setX(getX() - direction);
                    break;
                }
            }
        }
    }

    protected void handleCollisionY() {
        int amountToMove = Math.abs(Math.round(moveAmountY));
        if (amountToMove != 0) {
            boolean hasCollided = false;
            int direction = moveAmountY < 0 ? -1 : 1;
            for (int i = 0; i < amountToMove; i++) {
                setY(getY() + direction);
                int numberOfTilesToCheck = getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth();
                int edgeBoundY = moveAmountY < 0 ? getScaledBounds().getY() : getScaledBounds().getY2();
                Point tileIndex = map.getTileIndexByPosition(getScaledBounds().getX(), edgeBoundY);
                for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
                    if (hasCollidedWithTile(tileIndex.x + j, tileIndex.y)) {
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided) {
                    setY(getY() - direction);
                    break;
                }
            }
            if (moveAmountY > 0) {
                if (hasCollided) {
                    momentumY = 0;
                    airGroundState = AirGroundState.GROUND;
                } else {
                    playerState = playerState.JUMPING;
                    airGroundState = AirGroundState.AIR;
                }
            } else if (moveAmountY < 0) {
                if (hasCollided) {
                    jumpForce = 0;
                }
            }
        }
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
        // getIntersectRectangle().draw(graphics);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isKeyLocked(Key key) {
        return lockedKeys.contains(key);
    }

    private enum PlayerState {
        STANDING, WALKING, JUMPING, CROUCHING
    }

    private enum AirGroundState {
        AIR, GROUND
    }

    @Override
    public HashMap<String, Frame[]> getAnimations() {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 2)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 2)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 200)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 200)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 200)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 0)
                            .withScale(2)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("CROUCH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0), 0)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 12, 12, 7)
                            .build()
            });

            put("JUMP_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 0)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("JUMP_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 0)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("FALL_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0), 0)
                            .withScale(2)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });

            put("FALL_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(4, 0), 0)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 5, 12, 14)
                            .build()
            });
        }};
    }
}
