package GameObject;

import Engine.ImageLoader;
import Engine.Graphics;
import Game.Kirby;

import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle {
	protected BufferedImage image;
    protected Rectangle bounds;
    protected float scale;
    protected ImageEffect imageEffect;

    public Sprite (BufferedImage image) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.scale = 1;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, float scale) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.scale = scale;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, float scale, ImageEffect imageEffect) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.scale = scale;
        this.imageEffect = imageEffect;
    }

    public Sprite(BufferedImage image, float x, float y) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.scale = 1;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(BufferedImage image, float x, float y, float scale) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.scale = scale;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.scale = scale;
        this.imageEffect = imageEffect;
    }

    public int getScaledX2() {
        return Math.round((x + width) * scale);
    }

    public int getScaledY2() {
        return Math.round((y + height) * scale);
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
        super.setX(x);
    }

    @Override
    public void moveX(float dx) {
        super.moveX(dx);
    }

    @Override
    public void moveRight(float dx) {
        super.moveRight(dx);
    }

    @Override
    public void moveLeft(float dx) {
        super.moveLeft(dx);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    @Override
    public void moveY(float dy) {
        super.moveY(dy);
    }

    @Override
    public void moveDown(float dy) {
        super.moveDown(dy);
    }

    @Override
    public void moveUp(float dy) {
        super.moveUp(dy);
    }

    @Override
    public void setLocation(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public int getScaledWidth() {
        return Math.round(width * scale);
    }

    public int getScaledHeight() {
        return Math.round(height * scale);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
    }

    public float getScale() { return scale; }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public ImageEffect getImageEffect() { return imageEffect; }

    public void setImageEffect(ImageEffect imageEffect) {
        this.imageEffect = imageEffect;
    }

    public int getScaledBoundsX1() {
        return getX() + Math.round(bounds.getX1() * scale);
    }

    public int getScaledBoundsX2() {
        return getScaledBoundsX1() + Math.round(bounds.getWidth() * scale);
    }

    public int getScaledBoundsY1() {
        return getY() + Math.round(bounds.getY1() * scale);
    }

    public int getScaledBoundsY2() {
        return getScaledBoundsY1() + Math.round(bounds.getHeight() * scale);
    }

    public Rectangle getBounds() {
        return new Rectangle(getX() + bounds.getX(), getY() + bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public Rectangle getScaledBounds() {
        return new Rectangle(getScaledBoundsX1(), getScaledBoundsY1(), Math.round(bounds.getWidth() * scale), Math.round(bounds.getHeight() * scale));
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
            return getScaledBoundsX1() < otherSprite.getScaledBoundsX2() && getScaledBoundsX2() > otherSprite.getScaledBoundsX1() &&
                    getScaledBoundsY1() < otherSprite.getScaledBoundsY2() && getScaledBoundsY2() > otherSprite.getScaledBoundsY1();
        } else {
            return super.intersects(other);
        }
    }

    @Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, getX(), getY(), getScaledWidth(), getScaledHeight(), imageEffect);
	}

}
