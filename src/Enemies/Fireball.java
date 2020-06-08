package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Scene.Enemy;
import Scene.Map;
import Scene.MapEntityStatus;
import Scene.Player;
import Utils.Direction;
import Utils.Timer;
import Utils.Point;
import java.awt.*;
import java.util.HashMap;

public class Fireball extends Enemy {
    private float movementSpeed;
    private Timer existenceTimer = new Timer();

    public Fireball(Point location, float movementSpeed, int existenceTime, Map map) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Fireball.png"), 7, 7), "DEFAULT", map);
        this.movementSpeed = movementSpeed;
        existenceTimer.setWaitTime(existenceTime);
        isRespawnable = false;
        initialize();
    }

    @Override
    public void update(Keyboard keyboard, Player player) {
        if (existenceTimer.isTimeUp()) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            moveXHandleCollision(map, movementSpeed);
            super.update(keyboard, player);
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    @Override
    public void touchedPlayer(Player player) {
        super.touchedPlayer(player);
        this.mapEntityStatus = MapEntityStatus.REMOVED;
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                            .withScale(3)
                            .withBounds(1, 1, 5, 5)
                            .build()
            });
        }};
    }
}
