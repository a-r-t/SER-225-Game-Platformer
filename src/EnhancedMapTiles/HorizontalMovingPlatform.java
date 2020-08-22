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

// This class is for a horizontal moving platform
// the platform will move back and forth between its start location and end location
// if the player is standing on top of it, the player will be moved the same amount as the platform is moving (so the platform will not slide out from under the player)
public class HorizontalMovingPlatform extends EnhancedMapTile {
    private Point startLocation;
    private Point endLocation;
    private float movementSpeed = 1f;
    private Direction startDirection;
    private Direction direction;

    public HorizontalMovingPlatform(BufferedImage image, Point startLocation, Point endLocation, TileType tileType, float scale, Rectangle bounds, Direction startDirection) {
        super(image, startLocation.x, startLocation.y, tileType, scale, ImageEffect.NONE, bounds);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startDirection = startDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        direction = startDirection;
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;

        // move platform left or right based on its current direction
        int moveAmountX = 0;
        if (direction == Direction.RIGHT) {
            moveAmountX += movementSpeed;
        } else if (direction == Direction.LEFT) {
            moveAmountX -= movementSpeed;
        }

        moveX(moveAmountX);

        // if platform reaches the start or end location, it turns around
        // platform may end up going a bit past the start or end location depending on movement speed
        // this calculates the difference and pushes the platform back a bit so it ends up right on the start or end location
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

        // if tile type is NOT PASSABLE, if the platform is moving and hits into the player (x axis), it will push the player
        if (tileType == TileType.NOT_PASSABLE) {
            if (intersects(player) && moveAmountX >= 0 && player.getScaledBoundsX1() <= getScaledBoundsX2()) {
                player.moveXHandleCollision(getScaledBoundsX2() - player.getScaledBoundsX1());
            } else if (intersects(player) && moveAmountX <= 0 && player.getScaledBoundsX2() >= getScaledBoundsX1()) {
                player.moveXHandleCollision(getScaledBoundsX1() - player.getScaledBoundsX2());
            }
        }

        // if player is on standing on top of platform, move player by the amount the platform is moving
        // this will cause the player to "ride" with the moving platform
        // without this code, the platform would slide right out from under the player
        if (overlaps(player) && player.getScaledBoundsY2() == getScaledBoundsY1() && player.getAirGroundState() == AirGroundState.GROUND) {
            player.moveXHandleCollision(moveAmountX);
        }

        super.update(player);
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        //super.drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
    }

}
