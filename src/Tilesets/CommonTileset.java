package Tilesets;

import Map.MapTile.MapTileBuilder;
import Map.Tileset;

import java.util.HashMap;

public class CommonTileset extends Tileset {

    public CommonTileset() {
        super("CommonTileset.png", 16, 16);
        this.scale = 3;
    }

    @Override
    public MapTileBuilder getTileBuilder(int tileNumber) {
        return tiles.getOrDefault(tileNumber, null);
    }

    @Override
    public HashMap<Integer, MapTileBuilder> createTiles() {
        return new HashMap<Integer, MapTileBuilder>() {{
           put(0, new MapTileBuilder(getSubImage(0, 0)).hasCollision(true));
           put(1, new MapTileBuilder(getSubImage(0, 1)).hasCollision(false));
           put(2, new MapTileBuilder(getSubImage(0, 2)).hasCollision(false));
           put(6, new MapTileBuilder(getSubImage(1, 0)).hasCollision(true));
           put(7, new MapTileBuilder(getSubImage(1, 1)).hasCollision(true));
        }};
    }
}
