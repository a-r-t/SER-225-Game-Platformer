package Tilesets;

import GameObject.FrameBuilder;
import Map.MapTileBuilder;
import Map.Tileset;

import java.util.HashMap;

public class CommonTileset extends Tileset {

    public CommonTileset() {
        super("CommonTileset.png", 16, 16, 3);
    }

    @Override
    public HashMap<Integer, MapTileBuilder> createTiles() {
        return new HashMap<Integer, MapTileBuilder>() {{
           put(0, new MapTileBuilder(new FrameBuilder(getSubImage(0, 0), 0).withScale(scale).build()));
           put(1, new MapTileBuilder(new FrameBuilder(getSubImage(0, 1), 0).withScale(scale).build()));
           put(2, new MapTileBuilder(new FrameBuilder(getSubImage(0, 2), 0).withScale(scale).build()));
           put(6, new MapTileBuilder(new FrameBuilder(getSubImage(1, 0), 0).withScale(scale).build()));
           put(7, new MapTileBuilder(new FrameBuilder(getSubImage(1, 1), 0).withScale(scale).build()));
        }};
    }
}
