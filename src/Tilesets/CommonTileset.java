package Tilesets;

import GameObject.Frame;
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
           put(3, new MapTileBuilder(new Frame[] {
                   new FrameBuilder(getSubImage(2, 0), 400).withScale(scale).build(),
                   new FrameBuilder(getSubImage(2, 1), 400).withScale(scale).build(),
           }));
           put(4, new MapTileBuilder(new FrameBuilder(getSubImage(2, 2), 0).withScale(scale).build()));
           put(5, new MapTileBuilder(new FrameBuilder(getSubImage(1, 5), 0)
                   .withScale(scale)
                   .withBounds(0, 11, 16, 3)
                   .build()));
           put(6, new MapTileBuilder(new FrameBuilder(getSubImage(1, 0), 0).withScale(scale).build()));
           put(7, new MapTileBuilder(new FrameBuilder(getSubImage(1, 1), 0).withScale(scale).build()));
           put(8, new MapTileBuilder(new Frame[] {
                   new FrameBuilder(getSubImage(1, 2), 500).withScale(scale).build(),
                   new FrameBuilder(getSubImage(1, 3), 500).withScale(scale).build(),
                   new FrameBuilder(getSubImage(1, 2), 500).withScale(scale).build(),
                   new FrameBuilder(getSubImage(1, 4), 500).withScale(scale).build()
           }));
            put(9, new MapTileBuilder(new Frame[] {
                    new FrameBuilder(getSubImage(0, 3), 500).withScale(scale).build(),
                    new FrameBuilder(getSubImage(0, 4), 500).withScale(scale).build(),
                    new FrameBuilder(getSubImage(0, 3), 500).withScale(scale).build(),
                    new FrameBuilder(getSubImage(0, 5), 500).withScale(scale).build()
            }));
            put(10, new MapTileBuilder(new FrameBuilder(getSubImage(2, 3), 0)
                    .withScale(scale)
                    .withBounds(0, 11, 16, 3)
                    .build()));
            put(11, new MapTileBuilder(new FrameBuilder(getSubImage(2, 4), 0).withScale(scale).build()));
            put(12, new MapTileBuilder(new FrameBuilder(getSubImage(2, 5), 0).withScale(scale).build()));
        }};
    }
}
