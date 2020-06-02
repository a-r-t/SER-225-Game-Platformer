package Tilesets;

import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.FrameBuilder;
import GameObject.ImageEffect;
import Scene.MapTileBuilder;
import Scene.TileType;
import Scene.Tileset;

import java.util.ArrayList;

public class CommonTileset extends Tileset {

    public CommonTileset() {
        super(ImageLoader.load("CommonTileset.png"), 16, 16, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        return new ArrayList<MapTileBuilder>() {{
            // grass
            add(new MapTileBuilder(new FrameBuilder(getSubImage(0, 0), 0)
                   .withScale(tileScale)
                   .build())
                   .withTileType(TileType.NOT_PASSABLE)
            );

            // sky
            add(new MapTileBuilder(new FrameBuilder(getSubImage(0, 1), 0).withScale(tileScale).build()));

            // dirt
            add(new MapTileBuilder(new FrameBuilder(getSubImage(0, 2), 0)
                   .withScale(tileScale)
                   .build())
                   .withTileType(TileType.NOT_PASSABLE)
            );

            // sun
            add(new MapTileBuilder(new Frame[] {
                   new FrameBuilder(getSubImage(2, 0), 400).withScale(tileScale).build(),
                   new FrameBuilder(getSubImage(2, 1), 400).withScale(tileScale).build(),
            }));

            // tree trunk with hole
            add(new MapTileBuilder(new FrameBuilder(getSubImage(2, 2), 0)
                   .withScale(tileScale)
                   .build())
                   .withTileType(TileType.NOT_PASSABLE)
            );

            // left end branch
            add(new MapTileBuilder(new FrameBuilder(getSubImage(1, 5), 0)
                   .withScale(tileScale)
                   .withBounds(0, 6, 16, 3)
                   .build())
                   .withTileType(TileType.JUMP_THROUGH_PLATFORM)
            );

            // tree trunk
            add(new MapTileBuilder(new FrameBuilder(getSubImage(1, 0), 0)
                   .withScale(tileScale)
                   .build())
                   .withTileType(TileType.NOT_PASSABLE)
            );

            // tree top leaves
            add(new MapTileBuilder(new FrameBuilder(getSubImage(1, 1), 0)
                   .withScale(tileScale)
                   .build())
                   .withTileType(TileType.NOT_PASSABLE)
            );

            // yellow flower
            add(new MapTileBuilder(new Frame[] {
                   new FrameBuilder(getSubImage(1, 2), 500).withScale(tileScale).build(),
                   new FrameBuilder(getSubImage(1, 3), 500).withScale(tileScale).build(),
                   new FrameBuilder(getSubImage(1, 2), 500).withScale(tileScale).build(),
                   new FrameBuilder(getSubImage(1, 4), 500).withScale(tileScale).build()
            }));

            // purple flower
            add(new MapTileBuilder(new Frame[] {
                    new FrameBuilder(getSubImage(0, 3), 500).withScale(tileScale).build(),
                    new FrameBuilder(getSubImage(0, 4), 500).withScale(tileScale).build(),
                    new FrameBuilder(getSubImage(0, 3), 500).withScale(tileScale).build(),
                    new FrameBuilder(getSubImage(0, 5), 500).withScale(tileScale).build()
            }));

            // tree branch
            add(new MapTileBuilder(new FrameBuilder(getSubImage(2, 3), 0)
                    .withScale(tileScale)
                    .withBounds(0, 6, 16, 3)
                    .build())
                    .withTileType(TileType.JUMP_THROUGH_PLATFORM)
            );

            // tree trunk opening top
            add(new MapTileBuilder(new FrameBuilder(getSubImage(2, 4), 0)
                    .withScale(tileScale)
                    .build())
                    .withTileType(TileType.NOT_PASSABLE)
            );

            // tree trunk opening bottom
            add(new MapTileBuilder(new FrameBuilder(getSubImage(2, 5), 0)
                    .withScale(tileScale)
                    .build())
                    .withTileType(TileType.NOT_PASSABLE)
            );

            // right end branch
            add(new MapTileBuilder(new FrameBuilder(getSubImage(1, 5), 0)
                    .withScale(tileScale)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(0, 6, 16, 3)
                    .build())
                    .withTileType(TileType.JUMP_THROUGH_PLATFORM)
            );
        }};
    }
}
