package Map;

import GameObject.Sprite;
import GameObject.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Tileset extends SpriteSheet {

    protected HashMap<Integer, Sprite> tiles = defineTiles();

    public Tileset(String imageFileName, int spriteWidth, int spriteHeight) {
        super(imageFileName, spriteWidth, spriteHeight);
    }

    public Tileset(String imageFileName, int spriteWidth, int spriteHeight, Color transparentColor) {
        super(imageFileName, spriteWidth, spriteHeight, transparentColor);
    }

    public abstract HashMap<Integer, Sprite> defineTiles();

    public BufferedImage getSubImage(int row, int column) {
        return image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth, spriteHeight);
    }

    public SpriteSheet createAnimatedTileSpriteSheet(int row, int column, int numberOfSprites) {
        BufferedImage subImage = image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth * numberOfSprites, spriteHeight);
        return new SpriteSheet(subImage, spriteWidth, spriteHeight);
    }

    public HashMap<Integer, Sprite> getTiles() {
        return tiles;
    }
}
