package GameObject;

import Engine.Painter;


public interface GameObject {
	public void update();
	public void draw(Painter painter);
}
