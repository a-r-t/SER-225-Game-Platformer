package GameObject;

import Engine.ImageLoader;
import Engine.Keyboard;
import Engine.Painter;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public abstract class Sprite extends Rectangle {
	protected BufferedImage image;
    protected Rectangle bounds;
    protected int scale;
    protected ImageEffect imageEffect;

    public Sprite (BufferedImage image) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.bounds = new Rectangle(x, y, getWidth(), getHeight());
        this.scale = 1;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, int scale) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.bounds = new Rectangle(x, y, getWidth(), getHeight());
        this.scale = scale;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, int scale, ImageEffect imageEffect) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.bounds = new Rectangle(x, y, getWidth(), getHeight());
        this.scale = scale;
        this.imageEffect = imageEffect;
    }

    public Sprite(BufferedImage image, float x, float y) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, getWidth(), getHeight());
        this.scale = 1;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(BufferedImage image, float x, float y, int scale) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, getWidth(), getHeight());
        this.scale = scale;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(BufferedImage image, float x, float y, int scale, ImageEffect imageEffect) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, getWidth(), getHeight());
        this.scale = scale;
        this.imageEffect = imageEffect;
    }
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(String imageFileName) {
		image = ImageLoader.load(imageFileName);
	}

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void setX(float x) {
        int oldX = getX();
        super.setX(x);
        bounds.moveX(x - oldX);
    }

    @Override
    public void moveX(float dx) {
        super.moveX(dx);
        bounds.moveX(dx);
    }

    @Override
    public void moveRight(float dx) {
        super.moveRight(dx);
        bounds.moveRight(dx);
    }

    @Override
    public void moveLeft(float dx) {
        super.moveLeft(dx);
        bounds.moveLeft(dx);
    }

    @Override
    public void setY(float y) {
        int oldY = getY();
        super.setY(y);
        bounds.moveY(y - oldY);
    }

    @Override
    public void moveY(float dy) {
        super.moveY(dy);
        bounds.moveY(dy);
    }

    @Override
    public void moveDown(float dy) {
        super.moveDown(dy);
        bounds.moveDown(dy);
    }

    @Override
    public void moveUp(float dy) {
        super.moveUp(dy);
        bounds.moveUp(dy);
    }

    @Override
    public void setLocation(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public int getWidth() {
        return width * scale;
    }

    @Override
    public int getHeight() {
        return height * scale;
    }

    @Override
    public void setWidth(int width) {
        int oldWidth = getWidth();
        super.setWidth(width);
        bounds.setWidth(bounds.getWidth() + (width - oldWidth));
    }

    @Override
    public void setHeight(int height) {
        int oldHeight = getHeight();
        super.setHeight(height);
        bounds.setHeight(bounds.getHeight() + (height - oldHeight));
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setImageEffect(ImageEffect imageEffect) {
        this.imageEffect = imageEffect;
    }

    public int getBoundsX1() {
        return bounds.getX1();
    }

    public int getBoundsX2() {
        return bounds.getX1() + (bounds.getWidth() * scale);
    }

    public int getBoundsY1() {
        return bounds.getY1();
    }

    public int getBoundsY2() {
        return bounds.getY1() + (bounds.getHeight() * scale);
    }

    public Rectangle getBounds(Rectangle bounds) {
        return new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth() * scale, bounds.getHeight() * scale);
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public void setBounds(float x, float y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    @Override
    public boolean intersects(Rectangle other) {
        if (other instanceof Sprite) {
            Sprite otherSprite = (Sprite) other;
            return getBoundsX1() < otherSprite.getBoundsX2() && getBoundsX2() > otherSprite.getBoundsX1() &&
                    getBoundsY1() < otherSprite.getBoundsY2() && getBoundsY2() > otherSprite.getBoundsY1();
        } else {
            return super.intersects(other);
        }
    }

    @Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(Painter painter) {
		painter.paintImage(image, getX(), getY(), getWidth(), getHeight());
	}

}
