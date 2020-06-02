package Game;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.Keyboard;
import GameObject.Rectangle;
import GameObject.SpriteFont;
import Map.Map;
import Scene.Map;
import Maps.TestMap;
import Maps.TestMap2;

public class Scene extends Screen {
	private Map testMap;
	private Kirby kirby;
	private SpriteFont font;

	@Override
	public void initialize() {
		testMap = new TestMap();
		kirby = new Kirby(testMap.getPlayerStartPosition().x, testMap.getPlayerStartPosition().y);
	}

	@Override
	public void update(Keyboard keyboard) {
		testMap.update(kirby);
		kirby.update(keyboard, testMap);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		testMap.draw(graphicsHandler);
		kirby.draw(graphicsHandler);
	}
	
}
