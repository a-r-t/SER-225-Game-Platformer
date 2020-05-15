package Engine;

import GameObject.Rectangle;
import java.awt.*;

public abstract class Screen {
    public abstract  void initialize(Rectangle windowBounds);
    public abstract void update(Keyboard keyboard);
    public abstract void draw(Painter painter);
}
