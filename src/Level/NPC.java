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
    protected boolean talkedTo = false;
    protected SpriteFont message;
    protected int talkedToTime;
    protected Stopwatch timer = new Stopwatch();

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

    // A subclass can override this method to specify what message it displays upon being talked to
    public void drawMessage(GraphicsHandler graphicsHandler) {}
}
