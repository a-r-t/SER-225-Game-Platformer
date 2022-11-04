package Utils;

public class SlopeTileLayoutUtils {
//    public static final int[] LEFT_45 = new int[] {
//            0, 0, 1,
//            0, 1, 0,
//            1, 0, 0
//    };
//
//    public static final int[] RIGHT_45 = new int[] {
//            1, 0, 0,
//            0, 1, 0,
//            0, 0, 1
//    };

public static int[][] createLeft45SlopeLayout(int tileSize, int scale) {
    int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
    int col = slopeLayout[0].length - 1;
    for (int i = 0; i < slopeLayout.length; i++) {
        for (int j = col; j >= col - i; j--) {
            slopeLayout[i][j] = 1;
        }
    }
    return slopeLayout;
}

    public static int[][] createRight45SlopeLayout(int tileSize, int scale) {
        int[][] slopeLayout = new int[(tileSize * scale)][(tileSize * scale)];
        int col = 0;
        for (int i = 0; i < slopeLayout.length; i++) {
            for (int j = 0; j <= col + i; j++) {
                slopeLayout[i][j] = 1;
            }
        }
        return slopeLayout;
    }
    
}
