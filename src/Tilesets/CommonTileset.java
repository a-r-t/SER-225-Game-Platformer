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
        switch (tileNumber) {
            case 0:
                return new Tile(getSubImage(0,0), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 1:
                return new Tile(getSubImage(0,1), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 2:
                return new Tile(getSubImage(0,2), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 3:
                return new Tile(getSubImage(0,3), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 4:
                return new Tile(getSubImage(0,4), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 5:
                return new Tile(getSubImage(0,5), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 6:
                return new Tile(getSubImage(1,0), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            case 7:
                return new Tile(getSubImage(1,1), xIndex * spriteWidth * scale, yIndex * spriteHeight * scale);
            default:
                return null;
        }
    }
}
