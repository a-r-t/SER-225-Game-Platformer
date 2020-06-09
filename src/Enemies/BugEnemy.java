package Enemies;

import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.Frame;
import Builders.FrameBuilder;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Scene.Enemy;
import Scene.Map;
import Scene.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.awt.*;
import java.util.HashMap;

public class BugEnemy extends Enemy {

    private float gravity = .5f;
    private float movementSpeed = .5f;
    private Direction facingDirection;
    private AirGroundState airGroundState;

    public BugEnemy(Point location, Map map) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BugEnemy.png"), 24, 15), "WALK_RIGHT", map);
        initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = Direction.RIGHT;
        currentAnimationName = "WALK_RIGHT";
        airGroundState = AirGroundState.GROUND;
    }

    @Override
    public void update(Keyboard keyboard, Player player) {
        float moveAmountX = 0;
        float moveAmountY = 0;
        moveAmountY += gravity;

        if (airGroundState == AirGroundState.GROUND) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }
        }

        moveYHandleCollision(map, moveAmountY);
        moveXHandleCollision(map, moveAmountX);

        super.update(keyboard, player);
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
