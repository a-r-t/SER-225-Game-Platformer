package Scene;

import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Enemy extends MapEntity {

    public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation, Map map) {
        super(x, y, spriteSheet, startingAnimation, map);
    }

    public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, Map map) {
        super(x, y, animations, startingAnimation, map);
    }

    public Enemy(BufferedImage image, float x, float y, String startingAnimation, Map map) {
        super(image, x, y, startingAnimation, map);
    }

    public Enemy(BufferedImage image, float x, float y, Map map) {
        super(image, x, y, map);
    }

    public Enemy(BufferedImage image, float x, float y, float scale, Map map) {
        super(image, x, y, scale, map);
    }

    public Enemy(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Map map) {
        super(image, x, y, scale, imageEffect, map);
    }

    public Enemy(BufferedImage image, float x, float y, float scale, ImageEffect imageEffect, Rectangle bounds, Map map) {
        super(image, x, y, scale, imageEffect, bounds, map);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(Keyboard keyboard, Player player) {
        super.update();
        if (intersects(player)) {
            touchedPlayer(player);
        }
    }

    public void touchedPlayer(Player player) {
        System.out.println("PLAYER KILL");
    }
}
