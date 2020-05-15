package Game;

import Engine.Key;
import Engine.Keyboard;
import Engine.Painter;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Map.Map;
import Map.Tile;
import Utils.Direction;

import java.awt.*;
import java.util.HashMap;

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
    private float moveAmountX, moveAmountY;

    public Kirby(float x, float y, Rectangle sceneBounds) {
        super(x, y, 48, 48, new SpriteSheet("Kirby.png", 24, 24));
        this.sceneBounds = sceneBounds;
        currentAnimation = "STAND_RIGHT";
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        image = getCurrentFrame().getFrameImage();
        playerState = PlayerState.STANDING;
    }

    public void update(Keyboard keyboard) {
        super.update(keyboard);

        moveAmountY += gravity + momentumY;
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }

        if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }

        if (playerState == PlayerState.STANDING) {
            currentAnimation = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
            if (keyboard.isKeyDown(Key.A) || keyboard.isKeyDown(Key.D)) {
                playerState = PlayerState.WALKING;
            } else if (keyboard.isKeyDown(Key.W)) {
                playerState = PlayerState.JUMPING;
            } else if (keyboard.isKeyDown(Key.S)) {
                playerState = PlayerState.CROUCHING;
            }
        }
        if (playerState == PlayerState.WALKING) {
            currentAnimation = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
            if (keyboard.isKeyDown(Key.A)) {
                moveAmountX -= walkSpeed;
                facingDirection = Direction.LEFT;
            } else if (keyboard.isKeyDown(Key.D)) {
                moveAmountX += walkSpeed;
                facingDirection = Direction.RIGHT;
            } else if (keyboard.isKeyUp(Key.A) && keyboard.isKeyUp(Key.D)) {
                playerState = PlayerState.STANDING;
            }

            if (keyboard.isKeyDown(Key.W)) {
                playerState = PlayerState.JUMPING;
            } else if (keyboard.isKeyDown(Key.S)) {
                playerState = PlayerState.CROUCHING;
            }
        }
        if (playerState == PlayerState.CROUCHING) {
            currentAnimation = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
            if (keyboard.isKeyUp(Key.S)) {
                playerState = PlayerState.STANDING;
            }
            if (keyboard.isKeyDown(Key.W)) {
                playerState = PlayerState.JUMPING;
            }
        }
        if (playerState == PlayerState.JUMPING) {
            if (keyboard.isKeyDown(Key.W) && airGroundState == AirGroundState.GROUND) {
                currentAnimation = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
                airGroundState = AirGroundState.AIR;
                jumpForce = jumpHeight;
            }
            if (airGroundState == AirGroundState.AIR) {
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

                if (keyboard.isKeyDown(Key.A)) {
                    moveAmountX -= walkSpeed;
                } else if (keyboard.isKeyDown(Key.D)) {
                    moveAmountX += walkSpeed;
                }
            }
            if (airGroundState == AirGroundState.GROUND) {
                playerState = PlayerState.STANDING;
            }
        }

        if (moveAmountY > 0) {
            boolean hasCollided = false;
            for (int i = 0; i < Math.round(moveAmountY); i++) {
                setY(getY() + 1);
                int numberOfTilesToCheck = (int)Math.ceil(bounds.getWidth() / ((float) map.getTileset().getSpriteWidth() * map.getTileset().getScale()));
                for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                    Tile tile = map.getTileByPosition(Math.round(getX() + (j * getWidth())), Math.round(getY2()));
                    int movementPermission = map.getMovementPermissionByPosition(Math.round(getX() + (j * getWidth())), Math.round(getY2()));
                    if (tile != null && movementPermission == 1 && intersects(tile)) {
                        setY(getY() - 1);
                        momentumY = 0;
                        airGroundState = AirGroundState.GROUND;
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided) {
                    break;
                }
                airGroundState = AirGroundState.AIR;
            }
        } else if (moveAmountY < 0) {
            boolean hasCollided = false;
            for (int i = 0; i < Math.abs(Math.round(moveAmountY)); i++) {
                setY(getY() - 1);
                int numberOfTilesToCheck = (int)Math.ceil(bounds.getWidth() / ((float) map.getTileset().getSpriteWidth() * map.getTileset().getScale()));
                for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                    Tile tile = map.getTileByPosition(Math.round(getX() + (j * getWidth())), Math.round(getY()));
                    int movementPermission = map.getMovementPermissionByPosition(Math.round(getX() + (j * getWidth())), Math.round(getY()));
                    if (tile != null && movementPermission == 1 && intersects(tile)) {
                        setY(getY() + 1);
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided) {
                    break;
                }
            }
        }

        if (moveAmountX > 0) {
            boolean hasCollided = false;
            for (int i = 0; i < Math.round(moveAmountX); i++) {
                setX(getX() + 1);
                int numberOfTilesToCheck = (int)Math.ceil(bounds.getHeight() / ((float) map.getTileset().getSpriteHeight() * map.getTileset().getScale()));
                for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                    Tile tile = map.getTileByPosition(Math.round(getX2()), Math.round(getY() + (j * getHeight())));
                    int movementPermission = map.getMovementPermissionByPosition(Math.round(getX2()), Math.round(getY() + (j * getHeight())));
                    if (tile != null && movementPermission == 1 && intersects(tile)) {
                        setX(getX() - 1);
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided) {
                    break;
                }
            }
        } else if (moveAmountX < 0) {
            boolean hasCollided = false;
            for (int i = 0; i < Math.abs(Math.round(moveAmountX)); i++) {
                setX(getX() - 1);
                int numberOfTilesToCheck = (int)Math.ceil(bounds.getHeight() / ((float) map.getTileset().getSpriteHeight() * map.getTileset().getScale()));
                for (int j = - 1; j < numberOfTilesToCheck + 1; j++) {
                    Tile tile = map.getTileByPosition(Math.round(getX()), Math.round(getY() + (j * getHeight())));
                    int movementPermission = map.getMovementPermissionByPosition(Math.round(getX()), Math.round(getY() + (j * getHeight())));
                    if (tile != null && movementPermission == 1 && intersects(tile)) {
                        setX(getX() + 1);
                        hasCollided = true;
                        break;
                    }
                }
                if (hasCollided) {
                    break;
                }
            }
        }

        moveAmountX = 0;
        moveAmountY = 0;
        previousAirGroundState = airGroundState;
    }

    public void draw(Painter painter) {
        super.draw(painter);
    }

    public void setMap(Map map) {
        this.map = map;
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
                    new Frame(spriteSheet.getSprite(0, 0, false), 0)
            });

            put("STAND_LEFT", new Frame[] {
                    new Frame(spriteSheet.getSprite(0, 0, true), 0)
            });

            put("WALK_RIGHT", new Frame[] {
                    new Frame(spriteSheet.getSprite(1, 0, false), 200),
                    new Frame(spriteSheet.getSprite(1, 1, false), 200),
                    new Frame(spriteSheet.getSprite(1, 2, false), 200),
                    new Frame(spriteSheet.getSprite(1, 3, false), 200)
            });

            put("WALK_LEFT", new Frame[] {
                    new Frame(spriteSheet.getSprite(1, 0, true), 200),
                    new Frame(spriteSheet.getSprite(1, 1, true), 200),
                    new Frame(spriteSheet.getSprite(1, 2, true), 200),
                    new Frame(spriteSheet.getSprite(1, 3, true), 200)
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new Frame(spriteSheet.getSprite(2, 0, false), 0)
            });

            put("CROUCH_LEFT", new Frame[] {
                    new Frame(spriteSheet.getSprite(2, 0, true), 0)
            });

            put("JUMP_RIGHT", new Frame[] {
                    new Frame(spriteSheet.getSprite(3, 0, false), 0)
            });

            put("JUMP_LEFT", new Frame[] {
                    new Frame(spriteSheet.getSprite(3, 0, true), 0)
            });

            put("FALL_RIGHT", new Frame[] {
                    new Frame(spriteSheet.getSprite(4, 0, false), 0)
            });

            put("FALL_LEFT", new Frame[] {
                    new Frame(spriteSheet.getSprite(4, 0, true), 0)
            });
        }};
    }
}
