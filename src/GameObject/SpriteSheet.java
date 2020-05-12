package GameObject;

import Engine.Config;
import Utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SpriteSheet {
	protected BufferedImage image;
	protected int spriteWidth;
	protected int spriteHeight;
	protected Color transparentColor;

	public SpriteSheet(String imageFileName, int spriteWidth, int spriteHeight) {
		loadImage(imageFileName);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public SpriteSheet(String imageFileName, int spriteWidth, int spriteHeight, Color transparentColor) {
		loadImage(imageFileName, transparentColor);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.transparentColor = transparentColor;
	}

	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		this.image = image;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight, Color transparentColor) {
		this.image = image;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.transparentColor = transparentColor;
	}

	public BufferedImage getSprite(int spriteNumber, int animationNumber, boolean flipHorizontal) {
		BufferedImage subImage = image.getSubimage((animationNumber * spriteWidth) + animationNumber, (spriteNumber * spriteHeight) + spriteNumber, spriteWidth, spriteHeight);
		if (!flipHorizontal) {
			return subImage;
		} else {
			return ImageUtils.rotate(subImage, ImageUtils.Rotation.FLIP_HORZ);
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public Color getTransparentColor() {
		return transparentColor;
	}

	public void loadImage(String imageFileName) {
		try {
			image = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
			throw new RuntimeException(e);
		}
	}

	public void loadImage(String imageFileName, Color transparentColor) {
		try {
			BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
			Image transparentImage = ImageUtils.transformColorToTransparency(initialImage, transparentColor, new Color(255, 255, 255));
			image = ImageUtils.convertImageToBufferedImage(transparentImage, initialImage.getWidth(), initialImage.getHeight());
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
			throw new RuntimeException(e);
		}
	}
}
