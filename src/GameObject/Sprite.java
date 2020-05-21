package GameObject;

import Engine.ImageLoader;
import Engine.Graphics;

import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle implements IntersectableRectangle, GameObject {
	protected BufferedImage image;
    protected Rectangle bounds;
    protected ImageEffect imageEffect;

    public Sprite (BufferedImage image) {
        super(0, 0, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, float scale) {
        super(0, 0, image.getWidth(), image.getHeight(), scale);
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, float scale, ImageEffect imageEffect) {
        super(0, 0, image.getWidth(), image.getHeight(), scale);
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = imageEffect;
    }

    public Sprite(BufferedImage image, float x, float y) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(BufferedImage image, float x, float y, float scale) {
        super(x, y, image.getWidth(), image.getHeight(), scale);
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(x, y, image.getWidth(), image.getHeight(), scale);
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight(), scale);
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

    public ImageEffect getImageEffect() { return imageEffect; }

    public void setImageEffect(ImageEffect imageEffect) {
        this.imageEffect = imageEffect;
    }

    public Rectangle getBounds() {
        return new Rectangle(getBoundsX1(), getBoundsY1(), bounds.getWidth(), bounds.getHeight(), scale);
    }

    public int getBoundsX1() {
        return getX() + bounds.getX1();
    }

    public int getBoundsX2() {
        return getX() + bounds.getX2();
    }

    public int getBoundsY1() {
        return getY() + bounds.getY1();
    }

    public int getBoundsY2() {
        return getY() + bounds.getY2();
    }

    public int getScaledBoundsX1() {
        return getX() + Math.round(bounds.getX1() * scale);
    }

    public int getScaledBoundsX2() {
        return getScaledBoundsX1() + bounds.getScaledWidth();
    }

    public int getScaledBoundsY1() {
        return getY() + Math.round(bounds.getY1() * scale);
    }

    public int getScaledBoundsY2() {
        return getScaledBoundsY1() + bounds.getScaledHeight();
    }

    public Rectangle getScaledBounds() {
        return new Rectangle(getScaledBoundsX1(), getScaledBoundsY1(), bounds.getScaledWidth(), bounds.getScaledHeight());
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), scale);
    }

    public void setBounds(float x, float y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height, scale);
    }

    public Rectangle getIntersectRectangle() {
        return getScaledBounds();
    }

    @Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, getX(), getY(), getScaledWidth(), getScaledHeight(), imageEffect);
	}

	public void drawBounds(Graphics graphics) {
        graphics.drawFilledRectangle(getScaledBoundsX1(), getScaledBoundsY1(), bounds.getScaledWidth(), bounds.getScaledHeight(), color);
    }
}
