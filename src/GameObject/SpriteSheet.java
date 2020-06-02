package GameObject;

import java.awt.image.BufferedImage;


public class SpriteSheet {
	protected BufferedImage image;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int rowLength;
	protected int columnLength;

	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		this.image = image;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.rowLength = image.getHeight() / spriteHeight;
		this.columnLength = image.getWidth() / spriteWidth;
	}

	public BufferedImage getSprite(int spriteNumber, int animationNumber) {
		return image.getSubimage((animationNumber * spriteWidth) + animationNumber, (spriteNumber * spriteHeight) + spriteNumber, spriteWidth, spriteHeight);
	}

	public BufferedImage getSubImage(int row, int column) {
		return image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth, spriteHeight);
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
}
