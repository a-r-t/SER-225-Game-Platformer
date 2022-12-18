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

    public static TileLayout createBottomStairs(int scale) {
        int[][] slopeLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
        };
        if (scale > 1) {
            int[][] scaledLayout = new int[slopeLayout.length * scale][slopeLayout[0].length * scale];
            for(int i = 0; i < scaledLayout.length; i++) {
                for (int j = 0; j < scaledLayout[0].length; j++) {
                    scaledLayout[i][j] = slopeLayout[i / scale][j / scale];
                }
            }
            slopeLayout = scaledLayout;
        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }

    public static TileLayout createTopStairs(int scale) {
        int[][] slopeLayout = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        };
        if (scale > 1) {
            int[][] scaledLayout = new int[slopeLayout.length * scale][slopeLayout[0].length * scale];
            for(int i = 0; i < scaledLayout.length; i++) {
                for (int j = 0; j < scaledLayout[0].length; j++) {
                    scaledLayout[i][j] = slopeLayout[i / scale][j / scale];
                }
            }
            slopeLayout = scaledLayout;
        }
//        for(int i = 0; i < slopeLayout.length; i++) {
//            for (int j = 0; j < slopeLayout[0].length; j++) {
//                System.out.print(slopeLayout[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
        return new TileLayout(slopeLayout, Direction.LEFT);
    }

}
