package MapEntities;

import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.FrameBuilder;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Scene.Map;
import Scene.MapTile;
import Scene.Player;
import Scene.PlayerState;
import Utils.Direction;

import java.awt.*;
import java.util.HashMap;

public class BugEnemy extends Enemy {

    private float gravity = .5f;
    private float movementSpeed = 2f;
    private Direction facingDirection = Direction.RIGHT;

    public BugEnemy(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BugEnemy.png"), 24, 15), "WALK_RIGHT");
    }

    @Override
    public void update(Keyboard keyboard, Map map, Player player) {
        moveAmountX = 0;
        moveAmountY = 0;
        moveAmountY += gravity;

        if (facingDirection == Direction.RIGHT) {
            moveAmountX += movementSpeed;
        } else {
            moveAmountX -= movementSpeed;
        }

        handleCollisionX(map);
        handleCollisionY(map);
        super.update(keyboard, map, player);

        if (intersects(player)) {
            touchedPlayer(player);
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
                    moveAmountX = i * direction;
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
                    moveAmountY = i * direction;
                    break;
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
        MapTile tile = map.getMapTile(xTileIndex, yTileIndex);

        if (tile == null) {
            return false;
        } else {
            switch (tile.getTileType()) {
                case PASSABLE:
                    return false;
                case NOT_PASSABLE:
                    return intersects(tile);
                case JUMP_THROUGH_PLATFORM:
                    return moveAmountY >= 0 && intersects(tile) && getScaledBoundsY2() >= tile.getScaledBoundsY1();
                default:
                    return false;
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
