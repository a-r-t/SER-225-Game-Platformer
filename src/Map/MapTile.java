package Map;

import Engine.Graphics;
import Game.Kirby;

import GameObject.*;
import GameObject.Frame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapTile extends GameObject {

    public MapTile(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public void update(Map map, Player player) {
        super.update();
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
    }
}
