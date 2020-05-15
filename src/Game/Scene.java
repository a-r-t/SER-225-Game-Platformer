package Game;

import Engine.Painter;
import Engine.Screen;
import Engine.Keyboard;
import GameObject.Rectangle;
import Map.Map;
import Maps.TestMap;

import java.awt.*;

public class Scene extends Screen {
	private Rectangle sceneBounds;
	private DonkeyKong donkeyKong;
	private Map testMap;
	private Kirby kirby;

	@Override
	public void initialize(Rectangle sceneBounds) {
		this.sceneBounds = sceneBounds;
		donkeyKong = new DonkeyKong(100, 100, 100, 100);
		testMap = new TestMap(sceneBounds);
		kirby = new Kirby(testMap.getPlayerStart().x, testMap.getPlayerStart().y, sceneBounds);
		kirby.setMap(testMap);
	}

	@Override
	public void update(Keyboard keyboard) {
		//donkeyKong.update(keyboard);
		testMap.update();
		kirby.update(keyboard);
	}

	@Override
	public void draw(Painter painter) {
		//donkeyKong.draw(g);
		testMap.draw(painter);
		kirby.draw(painter);
	}
	
}
