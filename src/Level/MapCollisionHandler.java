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


//                    System.out.println("COLLIDE WITH SLOPE");
//                    int centerBoundX = Math.round(gameObject.getBoundsX2());
//                    int xLocationInTile = centerBoundX - Math.round(entityCollidedWith.getX());
//                    System.out.println("X LOC IN TILE: " + xLocationInTile);
//                    if (xLocationInTile >= 0 && xLocationInTile < entityCollidedWith.getLayout().length) {
//                        int centerBoundY = Math.round(gameObject.getBoundsY2());
//                        int row = centerBoundY - Math.round(entityCollidedWith.getY()) - 1;
//                        System.out.println("ROW start: " + row);
//
////                        for (int i = 0; i < entityCollidedWith.getLayout().length; i++) {
////                            for (int k = 0; k < entityCollidedWith.getLayout()[j].length; k++) {
////                                System.out.print(entityCollidedWith.getLayout()[i][k]);
////                            }
////                            System.out.print("\n");
////                        }
//                        int offset = 0;
//                        while (row >= 0 && row < entityCollidedWith.getLayout().length && entityCollidedWith.getLayout()[row][xLocationInTile] == 1) {
//                            row--;
//                            offset--;
//                        }
//                        System.out.println("ROW: " + row);
//                        System.out.println("OFFSET: " + offset);
//
//                        adjustedPositionY = gameObject.getY() + offset;
//                        System.out.println("entity y pos: " + entityCollidedWith.getY());
//                        System.out.println("ori: " + gameObject.getY());
//                        System.out.println("ady: " + adjustedPositionY);
//                    }


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

//                    System.out.println("COLLIDE WITH SLOPE");
//                    int centerBoundX = Math.round(gameObject.getBoundsX2());
//                    int xLocationInTile = centerBoundX - Math.round(entityCollidedWith.getX());
//                    System.out.println("X LOC IN TILE: " + xLocationInTile);
//                    if (xLocationInTile >= 0 && xLocationInTile < entityCollidedWith.getLayout().length) {
//                        int centerBoundY = Math.round(gameObject.getBoundsY2());
//                        int row = centerBoundY - Math.round(entityCollidedWith.getY()) - 1;
//                        System.out.println("ROW start: " + row);
//
////                        for (int i = 0; i < entityCollidedWith.getLayout().length; i++) {
////                            for (int k = 0; k < entityCollidedWith.getLayout()[j].length; k++) {
////                                System.out.print(entityCollidedWith.getLayout()[i][k]);
////                            }
////                            System.out.print("\n");
////                        }
//                        int offset = 0;
//                        while (row >= 0 && row < entityCollidedWith.getLayout().length && entityCollidedWith.getLayout()[row][xLocationInTile] == 1) {
//                            row--;
//                            offset--;
//                        }
//                        System.out.println("ROW: " + row);
//                        System.out.println("OFFSET: " + offset);
//
//                        adjustedPositionY = gameObject.getY() + offset;
//                        System.out.println("entity y pos: " + entityCollidedWith.getY());
//                        System.out.println("ori: " + gameObject.getY());
//                        System.out.println("ady: " + adjustedPositionY);
////                        System.exit(1);
//                    }
//                    int centerBoundX = Math.round(gameObject.getBoundsX1() + (gameObject.getBounds().getWidth() / 2f));
//                    int xLocationInTile = centerBoundX - Math.round(entityCollidedWith.getX());
//                    if (xLocationInTile >= 0 && centerBoundX < entityCollidedWith.getX2()) {
//                        int row = 0;
//                        while (entityCollidedWith.getLayout()[row][xLocationInTile] != 1) {
//                            row++;
//                        }
//                        adjustedPositionY = entityCollidedWith.getY() + row;
//                    }

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

    // special collision logic handling for slopes
//    public static void getAdjustedPositionAfterCollisionSlopeCheckY(GameObject gameObject, Map map, Direction currentYDirection, Direction currentXDirection) {
//        if (currentYDirection == Direction.DOWN) {
//            int yBound = Math.round(gameObject.getBounds().getY2());
//            int xBound = 0;
//            if (currentXDirection == Direction.LEFT) {
//                xBound = Math.round(gameObject.getBounds().getX1());
//            }
//            else if (currentXDirection == Direction.RIGHT) {
//                xBound = Math.round(gameObject.getBounds().getX2());
//            }
//            MapTile currentTile = map.getTileByPosition(xBound, yBound);
//            if (currentTile != null && currentTile.getTileType() == TileType.SLOPE) {
//                int xLocationInTile = xBound - Math.round(currentTile.getX());
//                int yLocationInTile = yBound - Math.round(currentTile.getY());
//                int counter = 0;
//                if (xLocationInTile >= 0 && xLocationInTile < currentTile.getLayout().getBounds()[0].length && yLocationInTile >= 0
//                        && yLocationInTile < currentTile.getLayout().getBounds().length) {
//                    System.out.println("LOCATION IN TILE: " + xLocationInTile + ", " + yLocationInTile);
//                    //System.out.println(currentTile.getLayout()[yLocationInTile - counter][xLocationInTile]);
//                    while (currentTile.getLayout().getBounds()[yLocationInTile - counter][xLocationInTile] == 1) {
//                        counter++;
//                        if (yLocationInTile - counter < 0) {
//                            break;
//                        }
//                    }
//                    if (counter > 0) {
//                        System.out.println("SLOPE LAST CHANCE");
//                        float currentTileYLocation = currentTile.getBoundsY1();
//                        System.out.println("Current Tile Y Location: " + currentTileYLocation);
//                        int targetSlopeLocationIndex = yLocationInTile - counter;
//                        System.out.println("Target slope location index: " + targetSlopeLocationIndex);
//                        float targetSlopeYLocation = currentTileYLocation + targetSlopeLocationIndex;
//                        System.out.println("Target slope y location: " + targetSlopeYLocation);
//                        float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
//                        System.out.println("Bounds difference: " + boundsDifference);
//                        float targetYLocation = targetSlopeYLocation - (gameObject.getHeight() - 1) + boundsDifference;
//                        System.out.println("Target Y Location: " + targetYLocation);
//                        gameObject.setY(targetYLocation);
//                        System.out.println("PLAYER Y2 AFTER ADJUSTMENT: " + gameObject.getBounds().getY2());
//                        hasCollided = true;
//                        entityCollidedWith = currentTile;
//                    }
//
//                }
//            }
//        }

    //}
}
