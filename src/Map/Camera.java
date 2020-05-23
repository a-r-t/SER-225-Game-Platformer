package Map;

import GameObject.Rectangle;

public class Camera extends Rectangle {

    private int tileSizeX, tileSizeY;
    private int leftoverSpaceX, leftoverSpaceY;

    public Camera(int startX, int startY, Rectangle screenBounds, int tileSizeX, int tileSizeY) {
        super(startX, startY, screenBounds.getWidth() / tileSizeX, screenBounds.getHeight() / tileSizeY);
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
        this.leftoverSpaceX = screenBounds.getWidth() % tileSizeX;
        this.leftoverSpaceY = screenBounds.getHeight() % tileSizeY;
    }

    public int getStartBoundX() {
        return getX();
    }

    public int getStartBoundY() {
        return getY();
    }

    public int getEndBoundX() {
        return getX1() + (width * tileSizeX) + leftoverSpaceX;
    }

    public int getEndBoundY() {
        return getY1() + (height * tileSizeY) + leftoverSpaceY;
    }
}
