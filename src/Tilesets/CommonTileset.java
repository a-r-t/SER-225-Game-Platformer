package Tilesets;

import Map.Tile;
import Map.TileType;
import Map.Tileset;
import Utils.Colors;

public class CommonTileset extends Tileset {

    public CommonTileset() {
        super("CommonTileset.png", 16, 16, Colors.MAGENTA);
        this.scale = 3;
    }

    @Override
    public Tile createTile(int tileNumber, int xIndex, int yIndex) {
        switch (tileNumber) {
            case 0:
                return new Tile(xIndex * spriteWidth * scale, yIndex * spriteHeight * scale, spriteWidth * scale, spriteHeight * scale, getSubImage(0,0), Colors.MAGENTA, TileType.NOT_PASSABLE);
            case 1:
                return new Tile(xIndex * spriteWidth * scale, yIndex * spriteHeight * scale, spriteWidth * scale, spriteHeight * scale, getSubImage(0,1), Colors.MAGENTA, TileType.PASSABLE);
            case 2:
                return new Tile(xIndex * spriteWidth * scale, yIndex * spriteHeight * scale, spriteWidth * scale, spriteHeight * scale, getSubImage(0,2), Colors.MAGENTA, TileType.NOT_PASSABLE);
            default:
                return null;
        }
    }
}
