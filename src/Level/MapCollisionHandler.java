package Level;

import GameObject.GameObject;
import Utils.Direction;
import Utils.Point;

// This class has methods to check if a game object has collided with a map tile or an enhanced map tile
// it is used by the game object class to determine if and where a collision occurred
public class MapCollisionHandler {

    public static MapCollisionCheckResult getAdjustedPositionAfterCollisionCheckX(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
        float edgeBoundX = direction == Direction.LEFT ? gameObject.getBounds().getX1() : gameObject.getBounds().getX2();
        Point tileIndex = map.getTileIndexByPosition(edgeBoundX, gameObject.getBounds().getY1());
        MapTile entityCollidedWith = null;
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y + j));
            if (mapTile != null && hasCollidedWithMapEntity(gameObject, mapTile, direction)) {
                entityCollidedWith = mapTile;
                float adjustedPositionX = gameObject.getX();
                float adjustedPositionY = gameObject.getY();
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                    adjustedPositionX = mapTile.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                    adjustedPositionX = (mapTile.getBoundsX2() + 1) - boundsDifference;
                }
                return new MapCollisionCheckResult(new Point(adjustedPositionX, adjustedPositionY), entityCollidedWith);

            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (!gameObject.equals(enhancedMapTile) && hasCollidedWithMapEntity(gameObject, enhancedMapTile, direction)) {
                entityCollidedWith = enhancedMapTile;
                if (direction == Direction.RIGHT) {
                    float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                    float adjustedPosition = enhancedMapTile.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                    return new MapCollisionCheckResult(new Point(adjustedPosition, gameObject.getY()), entityCollidedWith);
                } else if (direction == Direction.LEFT) {
                    float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                    float adjustedPosition = (enhancedMapTile.getBoundsX2() + 1) - boundsDifference;
                    return new MapCollisionCheckResult(new Point(adjustedPosition, gameObject.getY()), entityCollidedWith);
                }
            }
        }

        // no collision occurred
        return new MapCollisionCheckResult(null, null);
    }

    public static MapCollisionCheckResult getAdjustedPositionAfterCollisionCheckY(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
        float edgeBoundY = direction == Direction.UP ? gameObject.getBounds().getY() : gameObject.getBounds().getY2();
        Point tileIndex = map.getTileIndexByPosition(gameObject.getBounds().getX1(), edgeBoundY);
        MapTile entityCollidedWith = null;
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x) + j, Math.round(tileIndex.y));
            if (mapTile != null && hasCollidedWithMapEntity(gameObject, mapTile, direction)) {
                entityCollidedWith = mapTile;
                float adjustedPositionX = gameObject.getX();
                float adjustedPositionY = gameObject.getY();
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    adjustedPositionY = mapTile.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                    adjustedPositionY = (mapTile.getBoundsY2() + 1) - boundsDifference;
                }
                return new MapCollisionCheckResult(new Point(adjustedPositionX, adjustedPositionY), entityCollidedWith);
            }
        }
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (!gameObject.equals(enhancedMapTile) && hasCollidedWithMapEntity(gameObject, enhancedMapTile, direction)) {
                entityCollidedWith = enhancedMapTile;
                if (direction == Direction.DOWN) {
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    float adjustedPosition = enhancedMapTile.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                    return new MapCollisionCheckResult(new Point(gameObject.getX(), adjustedPosition), entityCollidedWith);
                } else if (direction == Direction.UP) {
                    float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                    float adjustedPosition = (enhancedMapTile.getBoundsY2() + 1) - boundsDifference;
                    return new MapCollisionCheckResult(new Point(gameObject.getX(), adjustedPosition), entityCollidedWith);
                }
            }
        }

        // no collision occurred
        return new MapCollisionCheckResult(null, null);
    }

    // based on tile type, perform logic to determine if a collision did occur with an intersecting tile or not
    private static boolean hasCollidedWithMapEntity(GameObject gameObject, MapEntity mapEntity, Direction direction) {
        if (mapEntity instanceof MapTile) {
            MapTile mapTile = (MapTile)mapEntity;
            switch (mapTile.getTileType()) {
                case PASSABLE:
                    return false;
                case NOT_PASSABLE:
                    return gameObject.intersects(mapTile);
                case JUMP_THROUGH_PLATFORM:
                    return direction == Direction.DOWN && gameObject.intersects(mapTile) &&
                            Math.round(gameObject.getBoundsY2()) == Math.round(mapTile.getBoundsY1());
                case WATER:
                    return false;
                case SLOPE:
                    return false;
                default:
                    return false;
            }
        }
        else {
            return mapEntity.intersects(gameObject);
        }
    }

    // special collision logic handling for detecting collision with slopes in the y direction
    public static MapCollisionCheckResult getAdjustedPositionAfterCollisionSlopeCheckY(GameObject gameObject, Map map) {
        for (int j = 0; j < 2; j++) {
            int yBound = Math.round(gameObject.getBounds().getY2());
            int xBound = 0;
            if (j == 0) {
                xBound = Math.round(gameObject.getBounds().getX1());
            } else if (j == 1) {
                xBound = Math.round(gameObject.getBounds().getX2());
            }
            MapTile currentTile = map.getTileByPosition(xBound, yBound);
            if (currentTile != null && currentTile.getTileType() == TileType.SLOPE) {
                int xLocationInTile = xBound - Math.round(currentTile.getX());
                int yLocationInTile = yBound - Math.round(currentTile.getY());
                int counter = 0;
                if (xLocationInTile >= 0 && xLocationInTile < currentTile.getLayout().getBounds()[0].length && yLocationInTile >= 0
                        && yLocationInTile < currentTile.getLayout().getBounds().length) {
                    while (currentTile.getLayout().getBounds()[yLocationInTile - counter][xLocationInTile] == 1) {
                        counter++;
                        if (yLocationInTile - counter < 0) {
                            break;
                        }
                    }
                    if (counter > 0) {
                        float currentTileYLocation = currentTile.getBoundsY1();
                        int targetSlopeLocationIndex = yLocationInTile - counter;
                        float targetSlopeYLocation = currentTileYLocation + targetSlopeLocationIndex;
                        float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                        float targetYLocation = targetSlopeYLocation - (gameObject.getHeight() - 1) + boundsDifference;
                        return new MapCollisionCheckResult(new Point(gameObject.getX(), targetYLocation), currentTile);
                    }
                }
            }
        }
        return new MapCollisionCheckResult(null, null);
    }

    // get proximity status to where game object is in relation to a slope
    public static SlopeProximityStatus getCurrentSlopeProximityStatus(GameObject gameObject, Map map, Direction xDirection) {
        SlopeProximityStatus slopeProximityStatus = SlopeProximityStatus.NONE;
        MapTile currentTile = map.getTileByPosition(gameObject.getBounds().getX2(), gameObject.getBounds().getY2());
        if (currentTile != null && currentTile.getTileType() == TileType.SLOPE) {
            if (currentTile.getLayout().getDirection() == Direction.LEFT && xDirection == Direction.LEFT) {
                int xLocationInTile = Math.round(gameObject.getBounds().getX2()) - Math.round(currentTile.getX());
                int yLocationInTile = Math.round(gameObject.getBounds().getY2()) - Math.round(currentTile.getY());
//                System.out.println("first: " + currentTile.getLayout().getBounds()[yLocationInTile + 1][xLocationInTile]);
//                System.out.println("second: " + currentTile.getLayout().getBounds()[yLocationInTile + 2][xLocationInTile]);
                if (currentTile.getLayout().getBounds()[yLocationInTile][xLocationInTile] == 0 && currentTile.getLayout().getBounds()[yLocationInTile + 1][xLocationInTile] == 1) {
                    slopeProximityStatus = SlopeProximityStatus.IN_SLOPE;
                }

                //slopeProximityStatus = SlopeProximityStatus.IN_SLOPE;

            }
        }
        if (slopeProximityStatus == SlopeProximityStatus.NONE) {
            Point currentTile2p = map.getTileIndexByPosition(gameObject.getBounds().getX2(), gameObject.getBounds().getY2());
            currentTile = map.getMapTile(Math.round(currentTile2p.x), Math.round(currentTile2p.y + 1));
            if (currentTile != null && currentTile.getTileType() == TileType.SLOPE) {
                if (currentTile.getLayout().getDirection() == Direction.LEFT && xDirection == Direction.LEFT) {
                    if (currentTile.getLayout().getBounds()[0][currentTile.getLayout().getBounds()[0].length - 1] == 1 && currentTile.getBoundsY1() == gameObject.getBounds().getY2() + 1) {
                        slopeProximityStatus = SlopeProximityStatus.ON_TOP_OF_SLOPE;
                    }
                }
            }
        }
        return slopeProximityStatus;
    }

    // special logic for adjusting game object location when going down a slope
    public static MapCollisionCheckResult getAdjustedPositionAfterCollisionSlopeCheckX(GameObject gameObject, Map map, SlopeProximityStatus slopeProximityStatus) {
        // adjust y position if moving down a slope
        if (slopeProximityStatus == SlopeProximityStatus.IN_SLOPE) {
            MapTile slopeTile = map.getTileByPosition(gameObject.getBounds().getX2(), gameObject.getBounds().getY2());
            int xLocationInTile = Math.round(gameObject.getBounds().getX2()) - Math.round(slopeTile.getX());
            int yLocationInTile = Math.round(gameObject.getBounds().getY2()) - Math.round(slopeTile.getY());
            int counter = 0;
            if (slopeTile.getLayout() == null) { // this means you are leaving the slope with this newest x update
                Point newTileIndex = map.getTileIndexByPosition(gameObject.getBounds().getX2(), gameObject.getBounds().getY2());
                MapTile newMapTile = map.getMapTile(Math.round(newTileIndex.x), Math.round(newTileIndex.y) + 1);
                float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                float targetYLocation = newMapTile.getBounds().getY() - (gameObject.getHeight()) + boundsDifference;
                return new MapCollisionCheckResult(new Point(gameObject.getX(), targetYLocation), null);
            }
            if (xLocationInTile >= 0 && xLocationInTile < slopeTile.getLayout().getBounds()[0].length && yLocationInTile >= 0
                    && yLocationInTile < slopeTile.getLayout().getBounds().length) {
                while (slopeTile.getLayout().getBounds()[yLocationInTile + counter][xLocationInTile] == 0) {
                    counter++;
                    if (yLocationInTile + counter > slopeTile.getLayout().getBounds().length - 1) {
                        break;
                    }
                }
                if (counter > 0) {
                    float currentTileYLocation = slopeTile.getBoundsY1();
                    int targetSlopeLocationIndex = yLocationInTile + counter;
                    //System.out.println("TARGET INDEX: " + targetSlopeLocationIndex);
                    float targetSlopeYLocation = currentTileYLocation + targetSlopeLocationIndex;
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    float targetYLocation = targetSlopeYLocation - (gameObject.getHeight()) + boundsDifference;
                    return new MapCollisionCheckResult(new Point(gameObject.getX(), targetYLocation), null);
                }
            }
        }
        else if (slopeProximityStatus == SlopeProximityStatus.ON_TOP_OF_SLOPE) {
            MapTile slopeTile = map.getTileByPosition(gameObject.getBounds().getX2(), gameObject.getBounds().getY2() + 1);

            int xLocationInTile = Math.round(gameObject.getBounds().getX2()) - Math.round(slopeTile.getX());
            int yLocationInTile = Math.round(gameObject.getBounds().getY2() + 1) - Math.round(slopeTile.getY());
            int counter = 0;
            if (xLocationInTile >= 0 && xLocationInTile < slopeTile.getLayout().getBounds()[0].length && yLocationInTile >= 0
                    && yLocationInTile < slopeTile.getLayout().getBounds().length) {
                while (slopeTile.getLayout().getBounds()[yLocationInTile + counter][xLocationInTile] == 0) {
                    counter++;
                    if (yLocationInTile + counter > slopeTile.getLayout().getBounds().length - 1) {
                        break;
                    }
                }
                if (counter > 0) {
                    float currentTileYLocation = slopeTile.getBoundsY1();
                    int targetSlopeLocationIndex = yLocationInTile + counter;
                    //System.out.println("TARGET INDEX: " + targetSlopeLocationIndex);
                    float targetSlopeYLocation = currentTileYLocation + targetSlopeLocationIndex;
                    float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                    float targetYLocation = targetSlopeYLocation - (gameObject.getHeight() - 1) + boundsDifference;
                    return new MapCollisionCheckResult(new Point(gameObject.getX(), targetYLocation), null);
                }
            }
//            float currentTileYLocation = slopeTile.getBoundsY1();
//            float targetSlopeYLocation = currentTileYLocation;
//            float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
//            float targetYLocation = targetSlopeYLocation - (gameObject.getHeight() - 1) + boundsDifference;
//            return new MapCollisionCheckResult(new Point(gameObject.getX(), targetYLocation), null);
        }
        return new MapCollisionCheckResult(null, null);
    }
}
