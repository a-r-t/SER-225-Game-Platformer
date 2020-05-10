package Game;

import Engine.Key;
import Engine.Keyboard;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Utils.Colors;

import java.awt.*;
import java.util.HashMap;

public class Kirby extends AnimatedSprite {

    private final float gravity = .5f;
    private float momentumY = 0f;
    private final float terminalVelocityY = 4f;
    private final float jumpHeight = 20f;
    private final float jumpDegrade = 1f;
    private float jumpForce = 0;
    private boolean isJumping = false;
    private Rectangle sceneBounds;
    private int xDirection;
    private boolean isWalking = false;
    private boolean isMoving = false;
    private float walkSpeed = 2f;

    public Kirby(float x, float y, Rectangle sceneBounds) {
        super(x, y, 48, 48, new SpriteSheet("Kirby.png", 24, 24, Colors.MAGENTA));
        this.sceneBounds = sceneBounds;
        currentAnimation = "STAND_RIGHT";
        image = getCurrentFrame().getFrameImage();
    }

    public void update(Keyboard keyboard) {
        super.update(keyboard);

        if (keyboard.isKeyDown(Key.LEFT)) {
            currentAnimation = "WALK_LEFT";
            xDirection = -1;
            isWalking = true;
        }
        if (keyboard.isKeyDown(Key.RIGHT)) {
            currentAnimation = "WALK_RIGHT";
            xDirection = 1;
            isWalking = true;
        }

        if (keyboard.isKeyUp(Key.LEFT) && keyboard.isKeyUp(Key.RIGHT) && isWalking) {
            isWalking = false;
        }

        if (keyboard.isKeyDown(Key.W) && !isJumping) {
            isJumping = true;
            jumpForce = jumpHeight;
            if (xDirection == -1) {
                currentAnimation = "JUMP_LEFT";
            } else {
                currentAnimation = "JUMP_RIGHT";
            }
        }

        if (isJumping && jumpForce > 0) {
            moveUp(jumpForce);
            jumpForce -= jumpDegrade;
        }

        if (getY2() >= sceneBounds.getY2() && isJumping) {
            isJumping = false;
        }

        moveDown(gravity + momentumY);
        if (keyboard.isKeyDown(Key.LEFT) && isJumping) {
            isMoving = true;
            xDirection = -1;
        }
        if (keyboard.isKeyDown(Key.RIGHT) && isJumping) {
            isMoving = true;
            xDirection = 1;
        }
        if (isJumping) {
            if (xDirection == 1) {
                if (jumpForce < gravity + momentumY) {
                    currentAnimation = "FALL_RIGHT";
                } else {
                    currentAnimation = "JUMP_RIGHT";
                }
            } else {
                if (jumpForce < gravity + momentumY) {
                    currentAnimation = "FALL_LEFT";
                } else {
                    currentAnimation = "JUMP_LEFT";
                }
            }
        }

        if (keyboard.isKeyUp(Key.RIGHT) && keyboard.isKeyUp(Key.LEFT) && isMoving) {
            isMoving = false;
        }

        if (momentumY <= terminalVelocityY) {
            momentumY += 2f;
        }

        if (getY2() > sceneBounds.getY2()) {
            setY(sceneBounds.getY2() - getHeight());
            momentumY = 0;
        }

        if (isWalking || isMoving) {
            moveX(walkSpeed * xDirection);
        } else if (!isJumping){
            if (xDirection == 1) {
                currentAnimation = "STAND_RIGHT";
            } else {
                currentAnimation = "STAND_LEFT";
            }
        }
    }

    public void draw(Graphics2D g) {
        super.draw(g);
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
