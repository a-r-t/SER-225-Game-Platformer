package Engine;


import GameObject.Rectangle;
import GameObject.Sprite;

import java.awt.*;

public class GameScene {
	private Rectangle sceneBounds;
	private DonkeyKong donkeyKong;
	private Sprite notFound;

	public void initialize(Rectangle sceneBounds) {
		this.sceneBounds = sceneBounds;
		donkeyKong = new DonkeyKong(100, 100, 100, 100);
	}

	public void update(Keyboard keyboard) {
		donkeyKong.update(keyboard);
	}

	public void draw(Graphics2D g) {
		donkeyKong.draw(g);
	}
	
}
