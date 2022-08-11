package Level;

import GameObject.GameObject;
import Utils.Direction;
import Utils.Point;

// This class has methods to check if a game object has collided with a map tile
// it is used by the game object class to determine if a collision occurred
public class MapTileCollisionHandler {

    public static MapTileCollisionCheckResult getAdjustedPositionAfterCollisionCheckX(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
        float edgeBoundX = direction == Direction.LEFT ? gameObject.getScaledBounds().getX1() : gameObject.getScaledBounds().getX2();
        Point tileIndex = map.getTileIndexByPosition(edgeBoundX, gameObject.getScaledBounds().getY1());
        if (direction == Direction.LEFT) {
            tileIndex = tileIndex.subtractX(1);
        }
        MapTile tileCollidedWith = null;
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y + j));
            if (mapTile != null && hasCollidedWithMapTile(gameObject, mapTile, direction)) {
                tileCollidedWith = mapTile;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getScaledX2() - gameObject.getScaledBoundsX2();
                    float adjustedPosition = mapTile.getScaledBoundsX1() - gameObject.getScaledWidth() + boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getScaledBoundsX1() - gameObject.getX();
                    float adjustedPosition = mapTile.getScaledBoundsX2() - boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                }
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (hasCollidedWithMapTile(gameObject, enhancedMapTile, direction)) {
                tileCollidedWith = enhancedMapTile;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getScaledX2() - gameObject.getScaledBoundsX2();
                    float adjustedPosition = enhancedMapTile.getScaledBoundsX1() - gameObject.getScaledWidth() + boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getScaledBoundsX1() - gameObject.getX();
                    float adjustedPosition = enhancedMapTile.getScaledBoundsX2() - boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                }
            }
        }

        // no collision occurred
        return new MapTileCollisionCheckResult(null, null);
    }

    public static MapTileCollisionCheckResult getAdjustedPositionAfterCollisionCheckY(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
        float edgeBoundY = direction == Direction.UP ? gameObject.getScaledBounds().getY() : gameObject.getScaledBounds().getY2();
        Point tileIndex = map.getTileIndexByPosition(gameObject.getScaledBounds().getX1(), edgeBoundY);
        if (direction == Direction.UP) {
            tileIndex = tileIndex.subtractY(1);
        }
        MapTile tileCollidedWith = null;
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x) + j, Math.round(tileIndex.y));
            if (mapTile != null && hasCollidedWithMapTile(gameObject, mapTile, direction)) {
                tileCollidedWith = mapTile;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getScaledY2() - gameObject.getScaledBoundsY2();
                    float adjustedPosition = mapTile.getScaledBoundsY1() - gameObject.getScaledHeight() + boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getScaledBoundsY1() - gameObject.getY();
                    float adjustedPosition = mapTile.getScaledBoundsY2() - boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                }
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (hasCollidedWithMapTile(gameObject, enhancedMapTile, direction)) {
                tileCollidedWith = enhancedMapTile;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getScaledY2() - gameObject.getScaledBoundsY2();
                    float adjustedPosition = enhancedMapTile.getScaledBoundsY1() - gameObject.getScaledHeight() + boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getScaledBoundsY1() - gameObject.getY();
                    float adjustedPosition = enhancedMapTile.getScaledBoundsY2() - boundsDifference;
                    return new MapTileCollisionCheckResult(adjustedPosition, tileCollidedWith);
                }
            }
        }

        // no collision occurred
        return new MapTileCollisionCheckResult(null, null);
    }

    // based on tile type, perform logic to determine if a collision did occur with an intersecting tile or not
    private static boolean hasCollidedWithMapTile(GameObject gameObject, MapTile mapTile, Direction direction) {
        switch (mapTile.getTileType()) {
            case PASSABLE:
                return false;
            case NOT_PASSABLE:
                return gameObject.intersects(mapTile);
            case JUMP_THROUGH_PLATFORM:
                return direction == Direction.DOWN && gameObject.intersects(mapTile) &&
                        Math.round(gameObject.getScaledBoundsY2() - 1) == Math.round(mapTile.getScaledBoundsY1());
            default:
                return false;
        }
    }
}
