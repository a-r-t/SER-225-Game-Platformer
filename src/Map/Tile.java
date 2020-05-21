package Map;

import Engine.Graphics;
import Game.Kirby;
import GameObject.IntersectableRectangle;

public interface Tile extends IntersectableRectangle {
    void update(Map map, Kirby player);
    void draw(Graphics graphics);
}
