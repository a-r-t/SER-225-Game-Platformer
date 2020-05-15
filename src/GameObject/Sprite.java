package GameObject;

import Engine.Config;
import Engine.Keyboard;
import Engine.Painter;
import Utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprite extends Rectangle {
	protected BufferedImage image;
    protected Rectangle bounds;

    public Sprite(float x, float y, int width, int height) {
	    super(x, y, width, height);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Sprite(float x, float y, String imageFile) {
        super(x, y, 0, 0);
        this.setImage(imageFile);
        this.setWidth(image.getWidth());
        this.setHeight(image.getHeight());
        this.bounds = new Rectangle(x, y, this.getWidth(), this.getHeight());
    }

    public Sprite(float x, float y, int width, int height, String imageFile) {
		super(x, y, width, height);
		this.setImage(imageFile);
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Sprite(float x, float y, int width, int height, BufferedImage image) {
        super(x, y, width, height);
        this.setImage(image);
        this.bounds = new Rectangle(x, y, width, height);
    }
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(String imageFileName) {
		try {
			BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
		    Image transparentImage = ImageUtils.transformColorToTransparency(initialImage, Config.TRANSPARENT_COLOR);
		    image = ImageUtils.convertImageToBufferedImage(transparentImage, transparentImage.getWidth(null), transparentImage.getHeight(null));
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
            throw new RuntimeException(e);
        }
	}

    public void setImage(BufferedImage image) {
        Image transparentImage = ImageUtils.transformColorToTransparency(image, Config.TRANSPARENT_COLOR);
        this.image = ImageUtils.convertImageToBufferedImage(transparentImage, transparentImage.getWidth(null), transparentImage.getHeight(null));
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

    public int getBoundsX1() {
        return Math.round(bounds.getX1());
    }

    public int getBoundsX2() {
        return Math.round(bounds.getX2());
    }

    public int getBoundsY1() {
        return Math.round(bounds.getY1());
    }

    public int getBoundsY2() {
        return Math.round(bounds.getY2());
    }

    public Rectangle getBounds(Rectangle bounds) {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
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
	public void update(Keyboard keyboard) {
		super.update(keyboard);
	}
	
	@Override
	public void draw(Painter painter) {
		painter.paintImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

}
