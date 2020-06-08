package Scene;

import GameObject.GameObject;
import Utils.Direction;
import Utils.Point;

public class MapTileCollisionHandler {
    public static boolean hasCollidedWithTilesX(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getScaledBounds().getHeight() / map.getTileset().getScaledSpriteHeight(), 1);
        float edgeBoundX = direction == Direction.LEFT ? gameObject.getScaledBounds().getX1() : gameObject.getScaledBounds().getX2();
        Point tileIndex = map.getTileIndexByPosition(Math.round(edgeBoundX), Math.round(gameObject.getScaledBounds().getY1()));
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x), Math.round(tileIndex.y + j));
            if (mapTile != null && (hasCollidedWithMapTile(gameObject, mapTile, direction) || hasCollidedWithEnhancedTile(gameObject, map, direction))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCollidedWithTilesY(GameObject gameObject, Map map, Direction direction) {
        int numberOfTilesToCheck = Math.max(gameObject.getScaledBounds().getWidth() / map.getTileset().getScaledSpriteWidth(), 1);
        float edgeBoundY = direction == Direction.UP ? gameObject.getScaledBounds().getY() : gameObject.getScaledBounds().getY2();
        Point tileIndex = map.getTileIndexByPosition(Math.round(gameObject.getScaledBounds().getX1()), Math.round(edgeBoundY));
        for (int j = -1; j <= numberOfTilesToCheck + 1; j++) {
            MapTile mapTile = map.getMapTile(Math.round(tileIndex.x) + j, Math.round(tileIndex.y));
            if (mapTile != null && (hasCollidedWithMapTile(gameObject, mapTile, direction) || hasCollidedWithEnhancedTile(gameObject, map, direction))) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasCollidedWithEnhancedTile(GameObject gameObject, Map map, Direction direction) {
        for (EnhancedMapTile enhancedMapTile : map.getActiveEnhancedMapTiles()) {
            if (hasCollidedWithMapTile(gameObject, enhancedMapTile, direction)) {
                return true;
            }
        }
        return false;
    }

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
