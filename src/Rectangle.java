import java.awt.*;

public class Rectangle extends Bounds implements GameObject {
	protected Color color;
	protected Color borderColor;
	protected Stroke borderThickness;
	protected Bounds hurtbox;

	public Rectangle(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.color = Color.white;
		this.borderColor = null;
		this.borderThickness = new BasicStroke(0);
		this.hurtbox = new Bounds(x, y, width, height);
	}
	
	@Override
	public void setX(float x) {
		this.x = x;
		hurtbox.setX(x);
	}
	
	@Override
	public void moveX(float dx) {
		x += dx;
		hurtbox.moveX(dx);
	}

	@Override
	public void moveRight(float dx) {
		x += dx;
		hurtbox.moveRight(dx);
	}
	
	@Override
	public void moveLeft(float dx) {
		x -= dx;
		hurtbox.moveLeft(dx);
	}
	
	@Override
	public void setY(float y) {
		this.y = y;
		hurtbox.setY(y);
	}
	
	@Override
	public void moveY(float dy) {
		y += dy;
		hurtbox.moveY(dy);
	}
	
	@Override
	public void moveDown(float dy) {
		y += dy;
		hurtbox.moveDown(dy);
	}
	
	@Override
	public void moveUp(float dy) {
		y -= dy;
		hurtbox.moveUp(dy);
	}
	
	@Override
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
		this.hurtbox.setLocation(x, y);
	}
	
	@Override
	public void setWidth(float width) {
		this.width = width;
		hurtbox.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		this.height = height;
		hurtbox.setHeight(height);
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
	
	public int getHurtboxX1() {
		return Math.round(hurtbox.getX1());
	}
	
	public int getHurtboxX2() {
		return Math.round(hurtbox.getX2());
	}
	
	public int getHurtboxY1() {
		return Math.round(hurtbox.getY1());
	}
	
	public int getHurtboxY2() {
		return Math.round(hurtbox.getY2());
	}
	
	public Bounds getHurtbox(Bounds hurtBox) {
		return hurtBox;
	}
	
	public void setHurtbox(Bounds hurtBox) {
		this.hurtbox = hurtBox;
	}
	
	public void setHurtbox(float x, float y, float width, float height) {
		this.hurtbox = new Bounds(x, y, width, height);
	}
	
	@Override
	public boolean intersects(Bounds other) {
		if (other instanceof Rectangle) {
			Rectangle otherRect = (Rectangle)other;
			return getHurtboxX1() < otherRect.getHurtboxX2() && getHurtboxX2() > otherRect.getHurtboxX1() &&
					getHurtboxY1() < otherRect.getHurtboxY2() && getHurtboxY2() > otherRect.getHurtboxY1();
		} else {
			return super.intersects(other);
		}
	}

	@Override
	public String toString() {
		return String.format("Rectangle: x=%s y=%s width=%s height=%s", x, y, width, height);
	}
	

	@Override
	public void update(Keyboard keyboard) {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
		if (borderColor != null && !borderColor.equals(color)) {
			g.setColor(borderColor);
			g.setStroke(borderThickness);
			g.drawRect(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
		}

	}
}
