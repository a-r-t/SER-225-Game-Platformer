package Map;

import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import Map.MapTileBuilder;

public abstract class Tileset extends SpriteSheet {

    protected float scale = 1f;
    protected HashMap<Integer, MapTileBuilder> tiles;

    public Tileset(String imageFileName, int tileWidth, int tileHeight) {
        super(imageFileName, tileWidth, tileHeight);
        this.tiles = createTiles();
    }

    public Tileset(String imageFileName, int tileWidth, int tileHeight, int scale) {
        super(imageFileName, tileWidth, tileHeight);
        this.scale = scale;
        this.tiles = createTiles();
    }

    public BufferedImage getSubImage(int row, int column) {
        return image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth, spriteHeight);
    }

    public SpriteSheet createAnimatedTileSpriteSheet(int row, int column, int numberOfSprites) {
        BufferedImage subImage = image.getSubimage((column * spriteWidth) + column, (row * spriteHeight) + row, spriteWidth * numberOfSprites, spriteHeight);
        return new SpriteSheet(subImage, spriteWidth, spriteHeight);
    }

    public MapTileBuilder getTile(int tileNumber) {
        return tiles.getOrDefault(tileNumber, null);
    }

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

    public abstract HashMap<Integer, MapTileBuilder> createTiles();
}
