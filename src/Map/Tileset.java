package Map;

import Engine.ImageLoader;
import GameObject.FrameBuilder;
import GameObject.SpriteSheet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import Map.MapTileBuilder;
import Utils.ImageUtils;

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

    public MapTileBuilder getTile(int tileNumber) {
        return tiles.getOrDefault(tileNumber, getDefaultTile());
    }

    public float getScale() {
        return scale;
    }

    public int getScaledSpriteWidth() {
        return Math.round(spriteWidth * scale);
    }

    public int getScaledSpriteHeight() {
        return Math.round(spriteHeight * scale);
    }

    public abstract HashMap<Integer, MapTileBuilder> createTiles();

    public MapTileBuilder getDefaultTile() {
        BufferedImage defaultTileImage = ImageLoader.load("DefaultTile.png");
        return new MapTileBuilder(new FrameBuilder(ImageUtils.resizeImage(defaultTileImage, spriteWidth, spriteHeight), 0).withScale(scale).build());
    }
}
