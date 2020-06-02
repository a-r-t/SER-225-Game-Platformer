package Utils;

import java.awt.*;

public class PointExtension extends Point {
    public PointExtension(int x, int y) {
        super(x, y);
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point addX(int x) {
        return new Point(this.x + x, this.y);
    }

    public Point addY(int y) {
        return new Point(this.x, this.y + y);
    }

    public Point subtract(Point point) {
        return new Point(x - point.x, y - point.y);
    }

    public Point subtractX(int x) {
        return new Point(this.x - x, this.y);
    }

    public Point subtractY(int y) {
        return new Point(this.x, this.y - y);
    }
}
