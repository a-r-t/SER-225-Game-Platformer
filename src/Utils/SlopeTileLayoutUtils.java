package Utils;

import Level.TileLayout;

public class SlopeTileLayoutUtils {

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

    public static TileLayout createLeft45HalfSlopeBottomLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int col = slopeLayout[0].length - 1;
        for (int i = slopeLayout.length / 2; i < slopeLayout.length; i++) {
            for (int j = col; j >= col - i; j--) {
                slopeLayout[i][j] = 1;
            }
        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }

    public static TileLayout createLeft45HalfSlopeTopLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int col = slopeLayout[0].length - 1;
        for (int i = 0; i < (slopeLayout.length / 2) + 1; i++) {
            for (int j = col; j >= col - i; j--) {
                slopeLayout[i][j] = 1;
            }
        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }

    public static TileLayout createBottomStairsLeft(int tileSize, int scale) {
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

    public static TileLayout createTopStairsLeft(int tileSize, int scale) {
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
