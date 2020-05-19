package Tilesets;

import Map.Tile;
import Map.Tileset;

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
}
