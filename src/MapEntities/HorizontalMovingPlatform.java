package MapEntities;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.ImageEffect;
import Scene.Map;
import Scene.MapEntity;
import Scene.Player;
import Scene.TileType;
import Utils.Direction;
import GameObject.Rectangle;
import Utils.AirGroundState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HorizontalMovingPlatform extends EnhancedMapTile {
    private Point startLocation;
    private Point endLocation;
    private float movementSpeed = 1f;
    private Direction direction = Direction.RIGHT;

    public HorizontalMovingPlatform(BufferedImage image, Point startLocation, Point endLocation, TileType tileType, float scale, Rectangle bounds) {
        super(image, startLocation.x, startLocation.y, tileType, scale, ImageEffect.NONE, bounds);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    @Override
    public void update(Keyboard keyboard, Map map, Player player) {
        moveAmountX = 0;
        moveAmountY = 0;
        if (direction == Direction.RIGHT) {
            moveAmountX += movementSpeed;
        } else if (direction == Direction.LEFT) {
            moveAmountX -= movementSpeed;
        }

        moveX(moveAmountX);

        if (getX1() + getScaledWidth() >= endLocation.x - map.getCamera().getAmountMovedX()) {
            int difference = endLocation.x - (getX1() + getScaledWidth()) - map.getCamera().getAmountMovedX();
            moveX(-difference);
            moveAmountX -= difference;
            direction = Direction.LEFT;
        } else if (getX1() <= startLocation.x - map.getCamera().getAmountMovedX()) {
            int difference = startLocation.x - getX1() - map.getCamera().getAmountMovedX();
            moveX(difference);
            moveAmountX += difference;
            direction = Direction.RIGHT;
        }

        if (tileType == TileType.NOT_PASSABLE) {
            if (intersects(player) && moveAmountX >= 0 && player.getScaledBoundsX1() <= getScaledBoundsX2()) {
                player.moveXHandleCollision(map, getScaledBoundsX2() - player.getScaledBoundsX1());
            } else if (intersects(player) && moveAmountX <= 0 && player.getScaledBoundsX2() >= getScaledBoundsX1()) {
                player.moveXHandleCollision(map, getScaledBoundsX1() - player.getScaledBoundsX2());
            }
        }

        if (overlaps(player) && player.getScaledBoundsY2() == getScaledBoundsY1() && player.getAirGroundState() == AirGroundState.GROUND) {
            player.moveXHandleCollision(map, moveAmountX);
        }




        super.update(keyboard, map, player);

    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
