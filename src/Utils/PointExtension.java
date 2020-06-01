package Utils;

import java.awt.*;

public class PointExtension extends Point {
    public PointExtension(int x, int y) {
        super(x, y);
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point subtract(Point point) {
        return new Point(x - point.x, y - point.y);
    }
}
