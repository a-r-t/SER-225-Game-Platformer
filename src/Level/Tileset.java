package Level;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

// This class represents a tileset, which defines a set of tiles based on a sprite sheet image
public abstract class Tileset extends SpriteSheet {
    // global scale of all tiles in the tileset
    protected float tileScale = 1f;

    // stores tiles mapped to an index
    protected HashMap<Integer, MapTileBuilder> tiles;

    // default tile defined for situations where no tile information for an index can be found (failsafe basically)
    protected MapTileBuilder defaultTile;

    public Tileset(BufferedImage image, int tileWidth, int tileHeight) {
        super(image, tileWidth, tileHeight);
        this.tiles = mapDefinedTilesToIndex();
        this.defaultTile = getDefaultTile();
    }

    public Tileset(BufferedImage image, int tileWidth, int tileHeight, int tileScale) {
        super(image, tileWidth, tileHeight);
        this.tileScale = tileScale;
        this.tiles = mapDefinedTilesToIndex();
        this.defaultTile = getDefaultTile();
    }

    // a subclass of this class must implement this method to define tiles in the tileset
    public abstract ArrayList<MapTileBuilder> defineTiles();

    // get specific tile from tileset by index, if not found the default tile is returned
    public MapTileBuilder getTile(int tileNumber) {
        return tiles.getOrDefault(tileNumber, defaultTile);
    }

    public float getTileScale() {
        return tileScale;
    }

    public int getScaledSpriteWidth() {
        return Math.round(spriteWidth * tileScale);
    }

    public int getScaledSpriteHeight() {
        return Math.round(spriteHeight * tileScale);
    }

    // maps all tiles to a tile index, which is how it is identified by the map file
    public HashMap<Integer, MapTileBuilder> mapDefinedTilesToIndex() {
        ArrayList<MapTileBuilder> mapTileBuilders = defineTiles();
        HashMap<Integer, MapTileBuilder> tilesToIndex = new HashMap<>();
        for (int i = 0; i < mapTileBuilders.size(); i++) {
            tilesToIndex.put(i, mapTileBuilders.get(i).withTileIndex(i));
        }
        return tilesToIndex;
    }

    public MapTileBuilder getDefaultTile() {
        BufferedImage defaultTileImage = ImageLoader.load("DefaultTile.png");
        return new MapTileBuilder(new FrameBuilder(ImageUtils.resizeImage(defaultTileImage, spriteWidth, spriteHeight), 0).withScale(tileScale).build());
    }
}
