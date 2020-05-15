package GameObject;

import Engine.Keyboard;
import Engine.Painter;

import java.awt.*;

public interface GameObject {
	public void update(Keyboard keyboard);
	public void draw(Painter painter);
}
