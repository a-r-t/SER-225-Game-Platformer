package Tilesets;

import Map.Tile;
import Map.Tile.TileBuilder;
import Map.Tileset;

import java.util.HashMap;

public class CommonTileset extends Tileset {

    public CommonTileset() {
        super("CommonTileset.png", 16, 16);
        this.scale = 3;
    }

    @Override
    public Tile createTile(int tileNumber, int xIndex, int yIndex) {
        int tileRowIndex = tileNumber / this.columnLength;
        int tileColumnIndex = tileNumber % this.rowLength;
        return new Tile(getSubImage(tileRowIndex, tileColumnIndex), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
    }

    @Override
    public HashMap<Integer, TileBuilder> createTiles() {
        return new HashMap<Integer, TileBuilder>() {{
           put(0, new TileBuilder(getSubImage(0, 0)).hasCollision(true));
           put(1, new TileBuilder(getSubImage(0, 1)).hasCollision(false));
           put(2, new TileBuilder(getSubImage(0, 2)).hasCollision(false));
           put(3, new TileBuilder(getSubImage(1, 0)).hasCollision(true));
           put(4, new TileBuilder(getSubImage(1, 1)).hasCollision(true));
        }};
    }
}
