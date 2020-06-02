package Game;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Maps.TestMap;
import Scene.Map;

public class Scene extends Screen {
	private Map testMap;
	private Kirby kirby;

	@Override
	public void initialize() {
		testMap = new TestMap();
		kirby = new Kirby(testMap.getPlayerStartPosition().x, testMap.getPlayerStartPosition().y);
	}

	@Override
	public void update(Keyboard keyboard) {
		testMap.update(keyboard, kirby);
		kirby.update(keyboard, testMap);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		testMap.draw(graphicsHandler);
		kirby.draw(graphicsHandler);
	}
	
}
