package GameObject;

import Engine.GraphicsHandler;
import Engine.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

// This class is for representing a Sprite, which is essentially a Rectangle with an image attached
// it also includes an attribute for "bounds", which can be thought of a sub rectangle on the image where it can be interacted with (like for collisions)
public class Sprite extends Rectangle {
	protected BufferedImage image;
    protected ImageEffect imageEffect;

    private Rectangle bounds;

    public Sprite (BufferedImage image) {
        super(0, 0, image.getWidth(), image.getHeight(), 1);
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = ImageEffect.NONE;
    }

    public Sprite (BufferedImage image, float x, float y) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
        this.bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight(), scale);
        this.imageEffect = ImageEffect.NONE;;
    }

    public Sprite(BufferedImage image, float x, float y, ImageEffect imageEffect) {
        super(x, y, image.getWidth(), image.getHeight());
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
        return new Rectangle(getX() + (bounds.getX1() * scale), getY() + (bounds.getY1() * scale), bounds.getWidth(), bounds.getHeight(), scale);
    }

    public Rectangle getBoundsDimensions() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds.getX1(), bounds.getY1(), bounds.getWidth(), bounds.getHeight(), 1);
    }

    public void setBounds(float x, float y, int width, int height) {
        setBounds(new Rectangle(x, y, width, height, 1));
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public Rectangle getIntersectRectangle() {
        return getBounds();
    }

    @Override
	public void update() {
		super.update();
	}
	
	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		graphicsHandler.drawImage(image, Math.round(getX()), Math.round(getY()), getWidth(), getHeight(), imageEffect);
	}

	public void drawBounds(GraphicsHandler graphicsHandler, Color color) {
        Rectangle scaledBounds = getBounds();
        scaledBounds.setColor(color);
        scaledBounds.draw(graphicsHandler);
    }

    @Override
    public String toString() {
        return String.format("Sprite: x=%s y=%s width=%s height=%s bounds=(%s, %s, %s, %s)", getX(), getY(), getWidth(), getHeight(), getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());
    }
}
