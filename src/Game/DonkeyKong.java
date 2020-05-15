package Game;

import Engine.Key;
import Engine.Keyboard;
import Engine.Painter;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

public class DonkeyKong extends AnimatedSprite {
	public DonkeyKong(float x, float y, int width, int height) {
		super(new SpriteSheet("DonkeyKong.png", 46, 32), x, y, width, height);
		loadAnimations();
		currentAnimation = "STAND_STILL";
		image = getCurrentFrame().getFrameImage();
	}

	@Override
	public HashMap<String, Frame[]> loadAnimations() {
		return new HashMap<String, Frame[]>() {{
			put("BEAT_CHEST", new Frame[] {
					new Frame(spriteSheet.getSprite(0, 0, false), 200),
					new Frame(spriteSheet.getSprite(0, 1, false), 200)
			});

			put("STAND_STILL", new Frame[] {
					new Frame(spriteSheet.getSprite(1, 0, false), 0)
			});
		}};
	}

	@Override
	public void update(Keyboard keyboard) {
		super.update(keyboard);
		if (keyboard.isKeyDown(Key.SPACE)) {
			currentAnimation = "BEAT_CHEST";
		} else if (keyboard.isKeyUp(Key.SPACE)) {
			currentAnimation = "STAND_STILL";
		}
	}

	@Override
	public void draw(Painter painter) {
		super.draw(painter);
	}
}