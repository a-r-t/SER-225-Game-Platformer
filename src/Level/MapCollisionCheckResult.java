package Level;

// Return type from MapTileCollisionHandler collision checks
// Contains adjusted location (where player should be moved to if a collision occurred) and the entity the player collided with (if any)
public class MapCollisionCheckResult {
    private Float adjustedLocation; // intentionally using the wrapper type due to it being nullable -- somewhat slimy but it gets the job done
    private MapEntity entityCollidedWith;

    public MapCollisionCheckResult(Float adjustedLocationAfterCollisionCheck, MapEntity entityCollidedWith) {
        this.adjustedLocation = adjustedLocationAfterCollisionCheck;
        this.entityCollidedWith = entityCollidedWith;
    }

    public Float getAdjustedLocation() {
        return adjustedLocation;
    }

    public void setAdjustedLocation(Float adjustedLocation) {
        this.adjustedLocation = adjustedLocation;
    }

    public MapEntity getEntityCollidedWith() {
        return entityCollidedWith;
    }

    public void setEntityCollidedWith(MapTile entityCollidedWith) {
        this.entityCollidedWith = entityCollidedWith;
    }
}
