package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntityStatus;
import Level.Player;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.HashMap;

// This class is for the Flying enemies attack
// it will travel in a straight line down for a set time before disappearing
// it will disappear early if it collides with a solid map tile
public class FlyingProjectile extends Enemy {
    private float movementSpeed;
    private Stopwatch existenceTimer = new Stopwatch();

    public FlyingProjectile(Point location, float movementSpeed, int existenceTime) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Lazer.png"), 7, 7), "DEFAULT");
        this.movementSpeed = movementSpeed;

        // how long the fireball will exist for before disappearing
        existenceTimer.setWaitTime(existenceTime);

        // this enemy will not respawn after it has been removed
        isRespawnable = false;

        initialize();
    }

    @Override
    public void update(Player player) {
        // if timer is up, set map entity status to REMOVED
        // the camera class will see this next frame and remove it permanently from the map
        if (existenceTimer.isTimeUp()) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            // move fireball forward
            //moveXHandleCollision(movementSpeed);
            moveYHandleCollision(movementSpeed);
            super.update(player);
        }
    }

//    @Override
//    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
//        // if fireball collides with anything solid on the x axis, it is removed
//        if (hasCollided) {
//            this.mapEntityStatus = MapEntityStatus.REMOVED;
//        }
//    }

    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        // if fireball collides with anything solid on the Y axis, it is removed
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    @Override
    public void touchedPlayer(Player player) {
        // if fireball touches player, it disappears
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
