package GameObject;

import Engine.GraphicsHandler;

import java.awt.*;

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

	public Rectangle() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.scale = 1;
		this.color = Color.white;
		this.borderColor = null;
		this.borderThickness = 0;
	}

	public float getXRaw() {
		return x;
	}

	public float getYRaw() {
		return y;
	}

    public int getX() {
        return (int)x;
    }

    public int getX1() {
        return (int)x;
    }

    public int getX2() {
        return (int)x + width;
    }

    public int getScaledX2() {
		return (int)x + getScaledWidth();
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

    public int getY() {
        return (int)y;
    }

    public int getY1() {
        return (int)y;
    }

    public int getY2() {
        return (int)y + height;
    }

	public int getScaledY2() {
		return (int)y + getScaledHeight();
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
	
	public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
	}

	public int getWidth() {
	    return width;
    }

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
	    return height;
    }
	
	public void setHeight(int height) {
		this.height = height;
	}

	public int getScaledWidth() {
		return (int)(width * scale);
	}

	public int getScaledHeight() {
		return (int)(height * scale);
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
		return String.format("Rectangle: x=%s y=%s width=%s height=%s", getX(), getY(), getScaledWidth(), getScaledHeight());
	}

	public void update() { }

	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawFilledRectangle(getX(), getY(), getScaledWidth(), getScaledHeight(), color);
		if (borderColor != null && !borderColor.equals(color)) {
			graphicsHandler.drawRectangle(getX(), getY(), getScaledWidth(), getScaledHeight(), borderColor, borderThickness);
		}
	}

	@Override
	public Rectangle getIntersectRectangle() {
		return new Rectangle(getX(), getY(), getScaledWidth(), getScaledHeight());
	}

	@Override
	public boolean intersects(IntersectableRectangle other) {
		Rectangle intersectRectangle = getIntersectRectangle();
		Rectangle otherIntersectRectangle = other.getIntersectRectangle();
		return intersectRectangle.getX1() < otherIntersectRectangle.getX2() && intersectRectangle.getX2() > otherIntersectRectangle.getX1() &&
				intersectRectangle.getY1() < otherIntersectRectangle.getY2() && intersectRectangle.getY2() > otherIntersectRectangle.getY1();
	}

	public boolean overlaps(IntersectableRectangle other) {
		Rectangle intersectRectangle = getIntersectRectangle();
		Rectangle otherIntersectRectangle = other.getIntersectRectangle();
		return intersectRectangle.getX1() <= otherIntersectRectangle.getX2() && intersectRectangle.getX2() >= otherIntersectRectangle.getX1() &&
				intersectRectangle.getY1() <= otherIntersectRectangle.getY2() && intersectRectangle.getY2() >= otherIntersectRectangle.getY1();
	}
}
