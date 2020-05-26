package Game;

import Engine.Graphics;
import Engine.Screen;
import Engine.Keyboard;
import GameObject.Rectangle;
import Map.Map;
import Maps.TestMap;
import Maps.TestMap2;

public class Scene extends Screen {
	private Rectangle sceneBounds;
	private Map testMap;
	private Kirby kirby;

	@Override
	public void initialize(Rectangle sceneBounds) {
		this.sceneBounds = sceneBounds;
		testMap = new TestMap(sceneBounds);
		kirby = new Kirby(testMap.getPlayerStartPosition().x, testMap.getPlayerStartPosition().y, sceneBounds);
	}

	@Override
	public void update(Keyboard keyboard) {
		testMap.update(kirby);
		kirby.update(keyboard, testMap);
	}

	@Override
	public void draw(Graphics graphics) {
		testMap.draw(graphics);
		kirby.draw(graphics);
	}
	
}
