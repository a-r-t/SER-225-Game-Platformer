package EnhancedMapTiles;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import Scene.EnhancedMapTile;
import Scene.Map;
import Scene.Player;
import Scene.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HorizontalMovingPlatform extends EnhancedMapTile {
    private Point startLocation;
    private Point endLocation;
    private float movementSpeed = 1f;
    private Direction direction;

    public HorizontalMovingPlatform(BufferedImage image, Point startLocation, Point endLocation, TileType tileType, float scale, Rectangle bounds, Map map) {
        super(image, startLocation.x, startLocation.y, tileType, scale, ImageEffect.NONE, bounds, map);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.initialize();
    }

    @Override
    public void initialize() {
        direction = Direction.RIGHT;
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;

        int moveAmountX = 0;
        if (direction == Direction.RIGHT) {
            moveAmountX += movementSpeed;
        } else if (direction == Direction.LEFT) {
            moveAmountX -= movementSpeed;
        }

        moveX(moveAmountX);

        if (getX1() + getScaledWidth() >= endBound) {
            float difference = endBound - (getX1() + getScaledWidth());
            moveX(-difference);
            moveAmountX -= difference;
            direction = Direction.LEFT;
        } else if (getX1() <= startBound) {
            float difference = startBound - getX1();
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

        super.update(player);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        //super.drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
    }

}
