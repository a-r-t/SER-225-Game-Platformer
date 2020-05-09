package Tilesets;

import GameObject.Sprite;
import Map.Tileset;

import java.awt.*;
import java.util.HashMap;

public class CommonTileset extends Tileset {

    public CommonTileset(String imageFileName, int spriteWidth, int spriteHeight) {
        super(imageFileName, spriteWidth, spriteHeight);
    }

    public CommonTileset(String imageFileName, int spriteWidth, int spriteHeight, Color transparentColor) {
        super(imageFileName, spriteWidth, spriteHeight, transparentColor);
    }

    @Override
    public HashMap<Integer, Sprite> defineTiles() {
//        return new HashMap<Integer, Sprite>() {{
//            put(0, new Sprite()),
//            put(),
//            put()
//        }};
        return null;
    }
}
