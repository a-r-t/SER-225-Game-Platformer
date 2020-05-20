package Map;

import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;

public abstract class Tileset extends SpriteSheet {

    protected float scale = 1f;

    public Tileset(String imageFileName, int tileWidth, int tileHeight) {
        super(imageFileName, tileWidth, tileHeight);
    }

    public BufferedImage getSubImage(int row, int column) {
        return image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth, spriteHeight);
    }

    public SpriteSheet createAnimatedTileSpriteSheet(int row, int column, int numberOfSprites) {
        BufferedImage subImage = image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth * numberOfSprites, spriteHeight);
        return new SpriteSheet(subImage, spriteWidth, spriteHeight);
    }

    public abstract Tile createTile(int tileNumber, int xIndex, int yIndex);

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getScaledSpriteWidth() {
        return Math.round(spriteWidth * scale);
    }

    public int getScaledSpriteHeight() {
        return Math.round(spriteHeight * scale);
    }
}
