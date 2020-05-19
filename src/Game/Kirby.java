package Game;

import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import Engine.Graphics;
import GameObject.*;
import GameObject.Frame;
import GameObject.Frame.FrameBuilder;
import GameObject.Rectangle;
import Map.Map;
import Map.Tile;
import Utils.Direction;

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
        super(new SpriteSheet(ImageLoader.load("Kirby.png"), 24, 24), x, y);
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

        if (moveAmountY > 0) {
            handleCollisionDown();
        } else if (moveAmountY < 0) {
            handleCollisionUp();
        }

        if (moveAmountX > 0) {
            handleCollisionRight();
        } else if (moveAmountX < 0) {
            handleCollisionLeft();
        }

        moveAmountX = 0;
        moveAmountY = 0;
        handleLockedKeys(keyboard);
    }

    protected void playerStanding(Keyboard keyboard) {
        currentAnimation = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
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
        currentAnimation = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
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
        currentAnimation = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
        if (keyboard.isKeyUp(CROUCH_KEY)) {
            playerState = PlayerState.STANDING;
        }
        if (keyboard.isKeyDown(JUMP_KEY)) {
            playerState = PlayerState.JUMPING;
        }
    }

    protected void playerJumping(Keyboard keyboard) {
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {
            currentAnimation = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
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
                currentAnimation = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            } else {
                currentAnimation = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
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

    protected void handleLockedKeys(Keyboard keyboard) {
        if (keyboard.isKeyUp(JUMP_KEY)) {
            lockedKeys.remove(JUMP_KEY);
        }
    }

    protected void handleCollisionLeft() {
        boolean hasCollided = false;
        for (int i = 0; i < Math.abs(Math.round(moveAmountX)); i++) {
            setX(getX() - 1);
            int numberOfTilesToCheck = (int)Math.ceil(getScaledBounds().getHeight() / ((float) map.getTileset().getSpriteHeight() * map.getTileset().getScale()));
            for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                Tile tile = map.getTileByPosition(getScaledBounds().getX(), Math.round(getScaledBounds().getY() + (j * getScaledBounds().getHeight())));
                int movementPermission = map.getMovementPermissionByPosition(getScaledBounds().getX(), Math.round(getScaledBounds().getY() + (j * getScaledBounds().getHeight())));
                if (tile != null && movementPermission == 1 && intersects(tile)) {
                    hasCollided = true;
                    break;
                }
            }
            if (hasCollided) {
                setX(getX() + 1);
                break;
            }
        }
    }

    protected void handleCollisionRight() {
        boolean hasCollided = false;
        for (int i = 0; i < Math.round(moveAmountX); i++) {
            setX(getX() + 1);
            int numberOfTilesToCheck = (int)Math.ceil(getScaledBounds().getHeight() / ((float) map.getTileset().getSpriteHeight() * map.getTileset().getScale()));
            for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                Tile tile = map.getTileByPosition(Math.round(getScaledBounds().getX2()), Math.round(getScaledBounds().getY() + (j * getScaledBounds().getHeight())));
                int movementPermission = map.getMovementPermissionByPosition(Math.round(getScaledBounds().getX2()), Math.round(getScaledBounds().getY() + (j * getScaledBounds().getHeight())));
                if (tile != null && movementPermission == 1 && intersects(tile)) {
                    hasCollided = true;
                    break;
                }
            }
            if (hasCollided) {
                setX(getX() - 1);
                break;
            }
        }
    }

    protected void handleCollisionUp() {
        boolean hasCollided = false;
        for (int i = 0; i < Math.abs(Math.round(moveAmountY)); i++) {
            setY(getY() - 1);
            int numberOfTilesToCheck = (int)Math.ceil(getScaledBounds().getWidth() / ((float) map.getTileset().getSpriteWidth() * map.getTileset().getScale()));
            for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                Tile tile = map.getTileByPosition(Math.round(getScaledBounds().getX() + (j * getScaledBounds().getWidth())), Math.round(getScaledBounds().getY()));
                int movementPermission = map.getMovementPermissionByPosition(Math.round(getScaledBounds().getX() + (j * getScaledBounds().getWidth())), Math.round(getScaledBounds().getY()));
                if (tile != null && movementPermission == 1 && intersects(tile)) {
                    hasCollided = true;
                    break;
                }
            }
            if (hasCollided) {
                setY(getY() + 1);
                jumpForce = 0;
                break;
            }
        }
    }

    protected void handleCollisionDown() {
        boolean hasCollided = false;
        for (int i = 0; i < Math.round(moveAmountY); i++) {
            setY(getY() + 1);
            int numberOfTilesToCheck = (int)Math.ceil(getScaledBounds().getWidth() / ((float) map.getTileset().getSpriteWidth() * map.getTileset().getScale()));
            for (int j = -1; j < numberOfTilesToCheck + 1; j++) {
                Tile tile = map.getTileByPosition(Math.round(getScaledBounds().getX() + (j * getScaledBounds().getWidth())), Math.round(getScaledBounds().getY2()));
                int movementPermission = map.getMovementPermissionByPosition(Math.round(getScaledBounds().getX() + (j * getScaledBounds().getWidth())), Math.round(getScaledBounds().getY2()));
                if (tile != null && movementPermission == 1 && intersects(tile)) {
                    hasCollided = true;
                    break;
                }
            }
            if (hasCollided) {
                setY(getY() - 1);
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
                break;
            }
            airGroundState = AirGroundState.AIR;
        }
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
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
    public HashMap<String, Frame[]> loadAnimations() {
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

    @Override
    public String getStartingAnimation() {
        return "STAND_RIGHT";
    }
}
