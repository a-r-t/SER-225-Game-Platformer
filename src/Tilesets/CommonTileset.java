package Tilesets;

import Map.Tile;
import Map.TileType;
import Map.Tileset;
import Utils.Colors;

public class CommonTileset extends Tileset {

    public CommonTileset() {
        super("CommonTileset.png", 16, 16);
        this.scale = 3;
    }

    @Override
    public Tile createTile(int tileNumber, int xIndex, int yIndex) {
        switch (tileNumber) {
            case 0:
                return new Tile(xIndex * spriteWidth * scale, yIndex * spriteHeight * scale, spriteWidth * scale, spriteHeight * scale, getSubImage(0,0));
            case 1:
                return new Tile(xIndex * spriteWidth * scale, yIndex * spriteHeight * scale, spriteWidth * scale, spriteHeight * scale, getSubImage(0,1));
            case 2:
                return new Tile(xIndex * spriteWidth * scale, yIndex * spriteHeight * scale, spriteWidth * scale, spriteHeight * scale, getSubImage(0,2));
            default:
                return null;
        }
    }
}
