import java.awt.*;

public class Bounds {
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	public Bounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getX() {
		return x;
	}

	public float getX1() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX2() {
		return x + width;
	}
	
	public void moveRight(float dx) {
		x += dx;
	}
	
	public void moveLeft(float dx) {
		x -= dx;
	}
	
	public void moveX(float dx) {
		x += dx;
	}

	public float getY() {
		return y;
	}

	public float getY1() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY2() {
		return y + height;
	}
	
	public void moveDown(float dy) {
		y += dy;
	}
	
	public void moveUp(float dy) {
		y -= dy;
	}
	
	public void moveY(float dy) {
		y += dy;
	}
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point getTopLeftPoint() {
		return new Point(Math.round(x), Math.round(y));
	}

	public Point getTopRightPoint() {
		return new Point(Math.round(x + width), Math.round(y + width));
	}

	public Point getBottomLeftPoint() {
		return new Point(Math.round(x + height), Math.round(y + height));
	}

	public Point getBottomRightPoint() {
		return new Point(Math.round(x + width + height), Math.round(y + width + height));
	}
	
	public boolean intersects(Bounds other) {
		return getX1() < other.getX2() && getX2() > other.getX1() && 
				getY1() < other.getY2() && getY2() > other.getY1();
	}

	@Override
	public String toString() {
		return "Bounds [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}
}
