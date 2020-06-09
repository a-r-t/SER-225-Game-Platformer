package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Maps.TestMap;
import Scene.Map;

public class Scene extends Screen {
	private Map testMap;
	private Cat cat;

	@Override
	public void initialize() {
		testMap = new TestMap();
		cat = new Cat(testMap.getPlayerStartPosition().x, testMap.getPlayerStartPosition().y, testMap);
	}

	@Override
	public void update(Keyboard keyboard) {
		testMap.update(keyboard, cat);
		cat.update(keyboard, testMap);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		testMap.draw(graphicsHandler);
		cat.draw(graphicsHandler);
	}
	
}
