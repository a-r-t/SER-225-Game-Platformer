package GameObject;

public interface IntersectableRectangle {
    Rectangle getIntersectRectangle();
    boolean intersects(IntersectableRectangle other);
}
