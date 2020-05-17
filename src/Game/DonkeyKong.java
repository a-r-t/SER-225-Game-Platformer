package Game;

import Engine.Key;
import Engine.Keyboard;
import Engine.Graphics;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

public class DonkeyKong extends AnimatedSprite {
	public DonkeyKong(float x, float y, int width, int height) {
		super(new SpriteSheet("DonkeyKong.png", 46, 32), x, y);
		loadAnimations();
	}

	@Override
	public HashMap<String, Frame[]> loadAnimations() {
		return new HashMap<String, Frame[]>() {{
			put("BEAT_CHEST", new Frame[] {
					new Frame(spriteSheet.getSprite(0, 0), 200),
					new Frame(spriteSheet.getSprite(0, 1), 200)
			});

			put("STAND_STILL", new Frame[] {
					new Frame(spriteSheet.getSprite(1, 0), 0)
			});
		}};
	}

	@Override
	public String getStartingAnimation() {
		return "STAND_STILL";
	}

	public void update(Keyboard keyboard) {
		super.update();
		if (keyboard.isKeyDown(Key.SPACE)) {
			currentAnimation = "BEAT_CHEST";
		} else if (keyboard.isKeyUp(Key.SPACE)) {
			currentAnimation = "STAND_STILL";
		}
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
	}
}