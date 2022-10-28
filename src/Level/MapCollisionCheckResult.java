package Level;

import Utils.Point;

// Return type from MapTileCollisionHandler collision checks
// Contains adjusted location (where player should be moved to if a collision occurred) and the entity the player collided with (if any)
public class MapCollisionCheckResult {
    private Point adjustedLocation;
    private MapEntity entityCollidedWith;

    public MapCollisionCheckResult(Point adjustedLocationAfterCollisionCheck, MapEntity entityCollidedWith) {
        this.adjustedLocation = adjustedLocationAfterCollisionCheck;
        this.entityCollidedWith = entityCollidedWith;
    }

    public Point getAdjustedLocation() {
        return adjustedLocation;
    }

    public void setAdjustedLocation(Point adjustedLocation) {
        this.adjustedLocation = adjustedLocation;
    }

    public MapEntity getEntityCollidedWith() {
        return entityCollidedWith;
    }

    public void setEntityCollidedWith(MapTile entityCollidedWith) {
        this.entityCollidedWith = entityCollidedWith;
    }
}
