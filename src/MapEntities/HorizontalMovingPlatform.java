package MapEntities;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.ImageEffect;
import Scene.Map;
import Scene.MapEntity;
import Scene.Player;
import Utils.Direction;
import GameObject.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HorizontalMovingPlatform extends MapEntity {
    private Point startLocation;
    private Point endLocation;
    private float movementSpeed = 1;
    private Direction direction = Direction.RIGHT;

    public HorizontalMovingPlatform(BufferedImage image, Point startLocation, Point endLocation, float scale, Rectangle bounds) {
        super(image, startLocation.x, startLocation.y, scale, ImageEffect.NONE, bounds);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

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
            System.out.println("Hit end");
            int difference = endLocation.x - (getX1() + getScaledWidth()) - map.getCamera().getAmountMovedX();
            moveX(-difference);
            moveAmountX -= difference;
            direction = Direction.LEFT;
        } else if (getX1() <= startLocation.x - map.getCamera().getAmountMovedX()) {
            System.out.println("Hit beginning");
            int difference = startLocation.x - getX1() - map.getCamera().getAmountMovedX();
            moveX(difference);
            moveAmountX += difference;
            direction = Direction.RIGHT;
        }

        //if (intersects(player) && player.getScaledBoundsY2() )

        super.update(keyboard, map, player);

    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

}
