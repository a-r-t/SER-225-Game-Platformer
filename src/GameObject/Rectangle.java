package GameObject;

import Engine.GraphicsHandler;
import Utils.Point;

import java.awt.*;

// This class represents a rectangle, which at its core is (x, y, width, height)
// it has some properties, rectangle math methods, and draw logic
// the methods here are pretty self explanatory
public class Rectangle implements IntersectableRectangle {
    protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected float scale;
	protected Color color;
	protected Color borderColor;
	protected int borderThickness;

	public Rectangle(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scale = 1;
		this.color = Color.white;
		this.borderColor = null;
		this.borderThickness = 0;
	}

	public Rectangle(float x, float y, int width, int height, float scale) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.color = Color.white;
		this.borderColor = null;
		this.borderThickness = 0;
	}

    public float getX() {
        return x;
    }

    public float getX1() {
        return x;
    }

    public float getX2() {
		return (x + getWidth()) - 1;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void moveX(float dx) {
		this.x += dx;
	}

	public void moveRight(float dx) {
		this.x += dx;
	}
	
	public void moveLeft(float dx) {
		this.x -= dx;
	}

    public float getY() {
        return y;
    }

    public float getY1() {
        return y;
    }

	public float getY2() {
		return (y + getHeight()) - 1;
	}

    public void setY(float y) {
		this.y = y;
	}
	
	public void moveY(float dy) {
		this.y += dy;
	}
	
	public void moveDown(float dy) {
		this.y += dy;
	}
	
	public void moveUp(float dy) {
		this.y -= dy;
	}

	public Point getLocation() { return new Point(x, y); }

	public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
	}

	public int getWidth() {
		return Math.round(width * scale);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return Math.round(height * scale);
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() { return scale; }

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
	}

	@Override
	public String toString() {
		return String.format("Rectangle: x=%s y=%s width=%s height=%s", getX(), getY(), getWidth(), getHeight());
	}

	public void update() { }

	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawFilledRectangle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight(), color);
		if (borderColor != null && !borderColor.equals(color)) {
			graphicsHandler.drawRectangle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight(), borderColor, borderThickness);
		}
	}

	@Override
	public Rectangle getIntersectRectangle() {
		return new Rectangle(x, y, getWidth(), getHeight());
	}

	// check if this intersects with another rectangle
	public boolean intersects(IntersectableRectangle other) {
		Rectangle intersectRectangle = getIntersectRectangle();
		Rectangle otherIntersectRectangle = other.getIntersectRectangle();
		return Math.round(intersectRectangle.getX1()) < Math.round(otherIntersectRectangle.getX2() + 1) && Math.round(intersectRectangle.getX2() + 1) > Math.round(otherIntersectRectangle.getX1()) &&
				Math.round(intersectRectangle.getY1()) < Math.round(otherIntersectRectangle.getY2() + 1) && Math.round(intersectRectangle.getY2() + 1) > Math.round(otherIntersectRectangle.getY1());
	}

	// check if this overlaps with another rectangle
	public boolean overlaps(IntersectableRectangle other) {
		Rectangle intersectRectangle = getIntersectRectangle();
		Rectangle otherIntersectRectangle = other.getIntersectRectangle();
		return Math.round(intersectRectangle.getX1()) <= Math.round(otherIntersectRectangle.getX2() + 1) && Math.round(intersectRectangle.getX2() + 1) >= Math.round(otherIntersectRectangle.getX1()) &&
				Math.round(intersectRectangle.getY1()) <= Math.round(otherIntersectRectangle.getY2() + 1) && Math.round(intersectRectangle.getY2() + 1) >= Math.round(otherIntersectRectangle.getY1());
	}
}
