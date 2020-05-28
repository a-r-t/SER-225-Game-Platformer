package MapEntities;

import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Map.MapEntity;
import Map.Player;
import Map.Map;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Enemy extends MapEntity {

    public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public Enemy(BufferedImage image, float x, float y, String startingAnimation) {
        super(image, x, y, startingAnimation);
    }

    public Enemy(BufferedImage image, float x, float y) {
        super(image, x, y);
    }

    public Enemy(BufferedImage image, float x, float y, float scale) {
        super(image, x, y, scale);
    }

    public Enemy(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
    }

    public Enemy(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
    }

    @Override
    public void update(Keyboard keyboard, Map map, Player player) {
        super.update(keyboard, map, player);
        if (intersects(player)) {
            touchedPlayer(player);
        }
    }

    public void touchedPlayer(Player player) {
        player.kill();
    }
}
