package Utils;

import Level.TileLayout;

// This class has some helpful methods for quickly creating a slope layout that scales correctly
// Due to the way slopes work, traditionally scaling the tile layout as if it were a standard matrix will not work correctly
public class SlopeTileLayoutUtils {

    // left 45 degree slope
    public static TileLayout createLeft45SlopeLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int col = slopeLayout[0].length - 1;
        for (int i = 0; i < slopeLayout.length; i++) {
            for (int j = col; j >= col - i; j--) {
                slopeLayout[i][j] = 1;
            }
        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }

    // right 45 degree slope
    public static TileLayout createRight45SlopeLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int col = 0;
        for (int i = 0; i < slopeLayout.length; i++) {
            for (int j = 0; j <= col + i; j++) {
                slopeLayout[i][j] = 1;
            }
        }
        return new TileLayout(slopeLayout, Direction.RIGHT);
    }

    // bottom piece of left 30 degree slope
    public static TileLayout createBottomLeft30SlopeLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int colCounter = 0;
        for (int i = slopeLayout.length - 1; i >= slopeLayout.length / 2; i--) {
            for (int j = colCounter; j < slopeLayout[i].length; j++) {
                slopeLayout[i][j] = 1;
            }
            colCounter += 2;
        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }

    // top piece of left 30 degree slope
    public static TileLayout createTopLeft30SlopeLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int colCounter = 0;
        for (int i = slopeLayout.length - 1; i >= 0; i--) {
            for (int j = colCounter; j < slopeLayout[i].length; j++) {
                slopeLayout[i][j] = 1;
            }
            if (i < slopeLayout.length / 2) {
                colCounter += 2;
            }
        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }
}
