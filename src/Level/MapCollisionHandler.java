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
                if (entityCollidedWith.getTileType() != TileType.SLOPE) {
                    System.out.println("COLLIDE WITH SOMETHING ELSE");
                    if (direction == Direction.RIGHT) {
                        float boundsDifference = gameObject.getX2() - gameObject.getBoundsX2();
                        adjustedPositionX = mapTile.getBoundsX1() - gameObject.getWidth() + boundsDifference;
                    } else if (direction == Direction.LEFT) {
                        float boundsDifference = gameObject.getBoundsX1() - gameObject.getX();
                        adjustedPositionX = mapTile.getBoundsX2() - boundsDifference;
                    }
                }
                else {
                    System.out.println("COLLIDE WITH SLOPE");
                    int centerBoundX = Math.round(gameObject.getBoundsX2());
                    int xLocationInTile = centerBoundX - Math.round(entityCollidedWith.getX());
                    System.out.println("X LOC IN TILE: " + xLocationInTile);
                    if (xLocationInTile >= 0 && xLocationInTile < entityCollidedWith.getX2()) {
                        int centerBoundY = Math.round(gameObject.getBoundsY2());
                        int row = centerBoundY - Math.round(entityCollidedWith.getY()) - 1;
                        System.out.println("ROW start: " + row);

//                        for (int i = 0; i < entityCollidedWith.getLayout().length; i++) {
//                            for (int k = 0; k < entityCollidedWith.getLayout()[j].length; k++) {
//                                System.out.print(entityCollidedWith.getLayout()[i][k]);
//                            }
//                            System.out.print("\n");
//                        }
                        int offset = 0;
                        while (row >= 0 && row < entityCollidedWith.getLayout().length && entityCollidedWith.getLayout()[row][xLocationInTile] == 1) {
                            row--;
                            offset--;
                        }
                        System.out.println("ROW: " + row);
                        System.out.println("OFFSET: " + offset);

                        adjustedPositionY = gameObject.getY() + offset;
                        System.out.println("entity y pos: " + entityCollidedWith.getY());
                        System.out.println("ori: " + gameObject.getY());
                        System.out.println("ady: " + adjustedPositionY);
//                        System.exit(1);
                    }
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
                    float adjustedPosition = enhancedMapTile.getBoundsX2() - boundsDifference;
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
                if (entityCollidedWith.getTileType() != TileType.SLOPE) {
                    if (direction == Direction.DOWN) {
                        float boundsDifference = gameObject.getY2() - gameObject.getBoundsY2();
                        adjustedPositionY = mapTile.getBoundsY1() - gameObject.getHeight() + boundsDifference;
                    } else if (direction == Direction.UP) {
                        float boundsDifference = gameObject.getBoundsY1() - gameObject.getY();
                        adjustedPositionY = mapTile.getBoundsY2() - boundsDifference;
                    }
                }
                else {
//                    int centerBoundX = Math.round(gameObject.getBoundsX1() + (gameObject.getBounds().getWidth() / 2f));
//                    int xLocationInTile = centerBoundX - Math.round(entityCollidedWith.getX());
//                    if (xLocationInTile >= 0 && centerBoundX < entityCollidedWith.getX2()) {
//                        int row = 0;
//                        while (entityCollidedWith.getLayout()[row][xLocationInTile] != 1) {
//                            row++;
//                        }
//                        adjustedPositionY = entityCollidedWith.getY() + row;
//                    }
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
                    float adjustedPosition = enhancedMapTile.getBoundsY2() - boundsDifference;
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
                            Math.round(gameObject.getBoundsY2() - 1) == Math.round(mapTile.getBoundsY1());
                case WATER:
                    return false;
                case SLOPE:
                    return gameObject.intersects(mapTile);
                default:
                    return false;
            }
        }
        else {
            return mapEntity.intersects(gameObject);
        }
    }
}
