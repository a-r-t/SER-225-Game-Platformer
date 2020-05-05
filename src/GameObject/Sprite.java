package GameObject;

import Engine.Config;
import Engine.Keyboard;
import Utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite extends Rectangle {
	protected BufferedImage image;
    protected Rectangle hurtbox;

    public Sprite(float x, float y, int width, int height) {
	    super(x, y, width, height);
        this.hurtbox = new Rectangle(x, y, width, height);
    }
	
	public Sprite(float x, float y, int width, int height, String imageFile) {
		super(x, y, width, height);
		this.setImage(imageFile);
        this.hurtbox = new Rectangle(x, y, width, height);
    }
	
	public Sprite(float x, float y, int width, int height, String imageFile, Color transparentColor) {
		super(x, y, width, height);
		this.setImage(imageFile, transparentColor);
        this.hurtbox = new Rectangle(x, y, width, height);
    }
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(String imageFileName) {
		try {
			image = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
            throw new RuntimeException(e);
        }
	}
	
	public void setImage(String imageFileName, Color transparentColor) {
		try {
			BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
		    Image transparentImage = ImageUtils.transformColorToTransparency(initialImage, transparentColor, new Color(255, 255, 255));
		    image = ImageUtils.convertImageToBufferedImage(transparentImage, transparentImage.getWidth(null), transparentImage.getHeight(null));
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
            throw new RuntimeException(e);
        }
	}

    @Override
    public void setX(float x) {
        int oldX = getX();
        super.setX(x);
        hurtbox.moveX(x - oldX);
    }

    @Override
    public void moveX(float dx) {
        super.moveX(dx);
        hurtbox.moveX(dx);
    }

    @Override
    public void moveRight(float dx) {
        super.moveRight(dx);
        hurtbox.moveRight(dx);
    }

    @Override
    public void moveLeft(float dx) {
        super.moveLeft(dx);
        hurtbox.moveLeft(dx);
    }

    @Override
    public void setY(float y) {
        int oldY = getY();
        super.setY(y);
        hurtbox.moveY(y - oldY);
    }

    @Override
    public void moveY(float dy) {
        super.moveY(dy);
        hurtbox.moveY(dy);
    }

    @Override
    public void moveDown(float dy) {
        super.moveDown(dy);
        hurtbox.moveDown(dy);
    }

    @Override
    public void moveUp(float dy) {
        super.moveUp(dy);
        hurtbox.moveUp(dy);
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
        hurtbox.setWidth(hurtbox.getWidth() + (width - oldWidth));
    }

    @Override
    public void setHeight(int height) {
        int oldHeight = getHeight();
        super.setHeight(height);
        hurtbox.setHeight(hurtbox.getHeight() + (height - oldHeight));
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

    public Rectangle getHurtbox(Rectangle hurtBox) {
        return hurtBox;
    }

    public void setHurtbox(Rectangle hurtBox) {
        this.hurtbox = hurtBox;
    }

    public void setHurtbox(float x, float y, int width, int height) {
        this.hurtbox = new Rectangle(x, y, width, height);
    }

    @Override
    public boolean intersects(Rectangle other) {
        if (other instanceof Sprite) {
            Sprite otherSprite = (Sprite) other;
            return getHurtboxX1() < otherSprite.getHurtboxX2() && getHurtboxX2() > otherSprite.getHurtboxX1() &&
                    getHurtboxY1() < otherSprite.getHurtboxY2() && getHurtboxY2() > otherSprite.getHurtboxY1();
        } else {
            return super.intersects(other);
        }
    }

    @Override
	public void update(Keyboard keyboard) {
		super.update(keyboard);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

}
