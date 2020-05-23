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
	protected int rowLength;
	protected int columnLength;

	public SpriteSheet(String imageFileName, int spriteWidth, int spriteHeight) {
		loadImage(imageFileName);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.rowLength = image.getHeight() / spriteHeight;
		this.columnLength = image.getWidth() / spriteWidth;
	}

	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		this.image = image;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public BufferedImage getSprite(int spriteNumber, int animationNumber) {
		return image.getSubimage((animationNumber * spriteWidth) + animationNumber, (spriteNumber * spriteHeight) + spriteNumber, spriteWidth, spriteHeight);
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

	public void loadImage(String imageFileName) {
		try {
			BufferedImage initialImage = ImageIO.read(new File(Config.RESOURCES_PATH + imageFileName));
			Image transparentImage = ImageUtils.transformColorToTransparency(initialImage, Config.TRANSPARENT_COLOR);
			image = ImageUtils.convertImageToBufferedImage(transparentImage, initialImage.getWidth(), initialImage.getHeight());
		} catch (IOException e) {
			System.out.println("Unable to find file " + Config.RESOURCES_PATH + imageFileName);
			throw new RuntimeException(e);
		}
	}
}
