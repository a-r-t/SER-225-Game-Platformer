package Level;

// Return type from MapTileCollisionHandler collision checks
// Contains adjusted location (where player should be moved to if a collision occurred) and the tile the player collided with (if any)
public class MapTileCollisionCheckResult {
    private Float adjustedLocation; // intentionally using the wrapper type due to it being nullable -- somewhat slimy but it gets the job done
    private MapTile tileCollidedWith;

    public MapTileCollisionCheckResult(Float adjustedLocationAfterCollisionCheck, MapTile tileCollidedWith) {
        this.adjustedLocation = adjustedLocationAfterCollisionCheck;
        this.tileCollidedWith = tileCollidedWith;
    }

    public Float getAdjustedLocation() {
        return adjustedLocation;
    }

    public void setAdjustedLocation(Float adjustedLocation) {
        this.adjustedLocation = adjustedLocation;
    }

    public MapTile getTileCollidedWith() {
        return tileCollidedWith;
    }

    public void setTileCollidedWith(MapTile tileCollidedWith) {
        this.tileCollidedWith = tileCollidedWith;
    }
}
