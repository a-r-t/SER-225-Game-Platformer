package GameObject;

import Engine.ImageLoader;
import Engine.Painter;
import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle {
	protected BufferedImage image;
    protected Rectangle bounds;
    protected float scale;
    protected ImageEffect imageEffect;

    public Sprite(float x, float y) {
        super(x, y, 0, 0);
        this.bounds = new Rectangle(x, y, 0, 0);
        this.scale = 1;
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(float x, float y, float scale) {
        super(x, y, 0, 0);
        this.bounds = new Rectangle(x, y, 0, 0);
        this.scale = scale;
        this.imageEffect = ImageEffect.NONE;
    }

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

    public int getScaledWidth() {
        return Math.round(width * scale);
    }

    public int getScaledHeight() {
        return Math.round(height * scale);
    }

    @Override
    public void setWidth(int width) {
        int oldWidth = getScaledWidth();
        super.setWidth(width);
        bounds.setWidth(bounds.getWidth() + (width - oldWidth));
    }

    @Override
    public void setHeight(int height) {
        int oldHeight = getScaledHeight();
        super.setHeight(height);
        bounds.setHeight(bounds.getHeight() + (height - oldHeight));
    }

    public float getScale() { return scale; }
    public void setScale(float scale) {
        this.scale = scale;
    }
    public ImageEffect getImageEffect() { return imageEffect; }
    public void setImageEffect(ImageEffect imageEffect) {
        this.imageEffect = imageEffect;
    }

    public int getBoundsX1() {
        return bounds.getX1();
    }

    public int getScaledBoundsX2() {
        return bounds.getX1() + Math.round(bounds.getWidth() * scale);
    }

    public int getBoundsY1() {
        return bounds.getY1();
    }

    public int getScaledBoundsY2() {
        return bounds.getY1() + Math.round(bounds.getHeight() * scale);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle getScaledBounds() {
        return new Rectangle(bounds.getX(), bounds.getY(), Math.round(bounds.getWidth() * scale), Math.round(bounds.getHeight() * scale));
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
            return getBoundsX1() < otherSprite.getScaledBoundsX2() && getScaledBoundsX2() > otherSprite.getBoundsX1() &&
                    getBoundsY1() < otherSprite.getScaledBoundsY2() && getScaledBoundsY2() > otherSprite.getBoundsY1();
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
		painter.paintImage(image, getX(), getY(), getScaledWidth(), getScaledHeight(), imageEffect);
	}

}
