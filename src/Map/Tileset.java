package Map;

import GameObject.Sprite;
import GameObject.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tileset extends SpriteSheet {

    protected int scale = 1;

    public Tileset(String imageFileName, int tileWidth, int tileHeight) {
        super(imageFileName, tileWidth, tileHeight);
    }

    public Tileset(String imageFileName, int tileWidth, int tileHeight, Color transparentColor) {
        super(imageFileName, tileWidth, tileHeight, transparentColor);
    }

    public BufferedImage getSubImage(int row, int column) {
        return image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth, spriteHeight);
    }

    public SpriteSheet createAnimatedTileSpriteSheet(int row, int column, int numberOfSprites) {
        BufferedImage subImage = image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth * numberOfSprites, spriteHeight);
        return new SpriteSheet(subImage, spriteWidth, spriteHeight);
    }

    public abstract Tile createTile(int tileNumber, int xIndex, int yIndex);

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
