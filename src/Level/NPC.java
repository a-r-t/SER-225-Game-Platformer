package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

import java.util.HashMap;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {
    protected boolean isInteractable = false;
    protected boolean talkedTo = false;
    protected SpriteFont message;
    protected int talkedToTime; // how long after talking to NPC will textbox stay open -- use negative number to have it be infinite time
    protected Stopwatch timer = new Stopwatch();
    protected Textbox textbox = new Textbox("");
    protected int textboxOffsetX = 0;
    protected int textboxOffsetY = 0;

    public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
        this.message = createMessage();
    }

    public NPC(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
        this.message = createMessage();
    }

    public NPC(float x, float y, Frame[] frames) {
        super(x, y, frames);
        this.message = createMessage();
    }

    public NPC(float x, float y, Frame frame) {
        super(x, y, frame);
        this.message = createMessage();
    }

    public NPC(float x, float y) {
        super(x, y);
        this.message = createMessage();
    }

    protected SpriteFont createMessage() {
        return null;
    }

    public void update(Player player) {
        super.update();
        checkTalkedTo(player);
        textbox.setLocation((int)getCalibratedXLocation() + textboxOffsetX, (int)getCalibratedYLocation() + textboxOffsetY);
    }

    public void checkTalkedTo(Player player) {
        if (isInteractable && intersects(player) && Keyboard.isKeyDown(Key.SPACE)) {
            talkedTo = true;
            if (talkedToTime >= 0) {
                timer.setWaitTime(talkedToTime);
            }
        }

        if (talkedTo && talkedToTime >= 0 && timer.isTimeUp()) {
            talkedTo = false;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        if (talkedTo) {
            textbox.draw(graphicsHandler);
        }
    }
}
