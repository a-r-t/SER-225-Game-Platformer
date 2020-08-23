package GameObject;

// This interface allows for specifying a rectangle that can be used in intersection logic by the Rectangle class
// it really only exists so an AnimatedSprite can be checked for intersection directly against by a Sprite or Rectangle (and all other combinations of that)
public interface IntersectableRectangle {
    Rectangle getIntersectRectangle();
}
