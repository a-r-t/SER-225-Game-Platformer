package GameObject;

import Engine.Graphics;

public class Bounds extends Rectangle implements Intersectable {

    public Bounds(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public Bounds(float x, float y, int width, int height, float scale) {
        super(x, y, width, height, scale);
    }

    public int getScaledX1() {
        return Math.round(x * scale);
    }

    public int getScaledY1() {
        return Math.round(y * scale);
    }

    @Override
    public String toString() {
        return String.format("Bounds: x=%s y=%s width=%s height=%s", getScaledX1(), getScaledY1(), getScaledWidth(), getScaledHeight());
    }

    @Override
    public Rectangle getIntersectRectangle() {
        return new Rectangle(getScaledX1(), getScaledY1(), getScaledWidth(), getScaledHeight());
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawFilledRectangle(getScaledX1(), getScaledY1(), getScaledWidth(), getScaledHeight(), color);
    }
}
