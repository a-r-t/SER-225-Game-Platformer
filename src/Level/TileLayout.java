package Level;

import Utils.Direction;

// Represents layout data for a tile
// only used for slopes
public class TileLayout {
    protected int[][] bounds;
    protected Direction direction;

    public TileLayout(int[][] bounds, Direction direction) {
        this.bounds = bounds;
        this.direction = direction;
    }

    public int[][] getBounds() {
        return bounds;
    }

    public void setBounds(int[][] bounds) {
        this.bounds = bounds;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
