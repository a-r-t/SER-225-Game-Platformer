package GameObject;

import Engine.Keyboard;

import java.awt.*;

public class Rectangle implements GameObject {
    private float x;
    private float y;
    private int width;
    private int height;
    private Color color;
    private Color borderColor;
    private Stroke borderThickness;

	public Rectangle(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = Color.white;
		this.borderColor = null;
		this.borderThickness = new BasicStroke(0);
	}

	public Rectangle() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.color = Color.white;
		this.borderColor = null;
		this.borderThickness = new BasicStroke(0);
	}

    public int getX() {
        return Math.round(x);
    }

    public int getX1() {
        return Math.round(x);
    }

    public int getX2() {
        return Math.round(x + width);
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
        return Math.round(y);
    }

    public int getY1() {
        return Math.round(y);
    }

    public int getY2() {
        return Math.round(y + height);
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
		this.borderThickness = new BasicStroke(borderThickness);
	}

    public Point getTopLeftPoint() {
        return new Point(getX(), getY());
    }

    public Point getTopRightPoint() {
        return new Point(getX2(), Math.round(y + width));
    }

    public Point getBottomLeftPoint() {
        return new Point(Math.round(x + height), getY2());
    }

    public Point getBottomRightPoint() {
        return new Point(Math.round(x + width + height), Math.round(y + width + height));
    }

	public boolean intersects(Rectangle other) {
        return getX1() < other.getX2() && getX2() > other.getX1() &&
                getY1() < other.getY2() && getY2() > other.getY1();
	}

	@Override
	public String toString() {
		return String.format("GameObject.Rectangle: x=%s y=%s width=%s height=%s", getX(), getY(), width, height);
	}
	

	@Override
	public void update(Keyboard keyboard) { }

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(getX(), getY(), width, height);
		if (borderColor != null && !borderColor.equals(color)) {
			g.setColor(borderColor);
			g.setStroke(borderThickness);
			g.drawRect(getX(), getY(), width, height);
		}

	}
}
