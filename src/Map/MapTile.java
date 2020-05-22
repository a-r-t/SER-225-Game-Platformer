package Map;

import Engine.Graphics;
import Game.Kirby;

import GameObject.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapTile extends GameObject {

    public MapTile(SpriteSheet image, float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(image, x, y, animations, startingAnimation);
    }

    public void update(Map map, Kirby kirby) {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
