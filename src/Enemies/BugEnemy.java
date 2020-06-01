package Enemies;

import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.FrameBuilder;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Scene.*;
import Utils.AirGroundState;
import Utils.Direction;

import java.awt.*;
import java.util.HashMap;

public class BugEnemy extends Enemy {

    private float gravity = .5f;
    private float movementSpeed = .5f;
    private Direction facingDirection = Direction.RIGHT;
    private AirGroundState airGroundState = AirGroundState.GROUND;

    public BugEnemy(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BugEnemy.png"), 24, 15), "WALK_RIGHT");
    }

    @Override
    public void update(Keyboard keyboard, Map map, Player player) {
        moveAmountX = 0;
        moveAmountY = 0;
        moveAmountY += gravity;

        if (airGroundState == AirGroundState.GROUND) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }
        }

        moveAmountY = super.moveYHandleCollision(map, moveAmountY);
        moveAmountX = super.moveXHandleCollision(map, moveAmountX);

        super.update(keyboard, map, player);
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                airGroundState = AirGroundState.GROUND;
            } else {
                airGroundState = AirGroundState.AIR;
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 100)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 100)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build()
            });
            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 100)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 100)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build()
            });
        }};
    }
}
