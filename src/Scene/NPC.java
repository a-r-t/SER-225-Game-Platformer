package Scene;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import GameObject.*;
import GameObject.Frame;
import GameObject.Rectangle;
import Utils.Timer;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class NPC extends MapEntity {
    protected boolean talkedTo = false;
    protected SpriteFont message;
    protected int talkedToTime;
    protected Timer timer = new Timer();

    public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation, int talkedToTime) {
        super(x, y, spriteSheet, startingAnimation);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    public NPC(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int talkedToTime) {
        super(x, y, animations, startingAnimation);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    public NPC(BufferedImage image, float x, float y, String startingAnimation, int talkedToTime) {
        super(image, x, y, startingAnimation);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    public NPC(BufferedImage image, float x, float y, int talkedToTime) {
        super(image, x, y);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    public NPC(BufferedImage image, float x, float y, int talkedToTime, float scale) {
        super(image, x, y, scale);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    public NPC(BufferedImage image, float x, float y, int talkedToTime, float scale, ImageEffect imageEffect) {
        super(image, x, y, scale, imageEffect);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    public NPC(BufferedImage image, float x, float y, int talkedToTime, float scale, ImageEffect imageEffect, Rectangle bounds) {
        super(image, x, y, scale, imageEffect, bounds);
        this.message = createMessage();
        this.talkedToTime = talkedToTime;
    }

    protected SpriteFont createMessage() {
        return null;
    }

    public void update(Player player) {
        super.update();
        checkTalkedTo(player);
    }

    public void checkTalkedTo(Player player) {
        if (intersects(player) && Keyboard.isKeyDown(Key.SPACE)) {
            talkedTo = true;
            timer.setWaitTime(talkedToTime);
        };
        if (talkedTo && timer.isTimeUp()) {
            talkedTo = false;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        if (message != null && talkedTo) {
            drawMessage(graphicsHandler);
        }
    }

    public void drawMessage(GraphicsHandler graphicsHandler) {}
}
