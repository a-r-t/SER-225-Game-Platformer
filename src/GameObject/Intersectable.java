package GameObject;

public interface Intersectable {
    public Rectangle getIntersectRectangle();
    public boolean intersects(Intersectable other);
}
