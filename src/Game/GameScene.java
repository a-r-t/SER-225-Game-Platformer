package Game;

import Engine.GameScreen;
import Engine.Keyboard;
import GameObject.Rectangle;

import java.awt.*;

public class GameScene extends GameScreen {
	private Rectangle sceneBounds;
	private DonkeyKong donkeyKong;

	@Override
	public void initialize(Rectangle sceneBounds) {
		this.sceneBounds = sceneBounds;
		donkeyKong = new DonkeyKong(100, 100, 100, 100);
	}

	@Override
	public void update(Keyboard keyboard) {
		donkeyKong.update(keyboard);
	}

	@Override
	public void draw(Graphics2D g) {
		donkeyKong.draw(g);
	}
	
}
