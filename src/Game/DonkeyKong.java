package Game;

import Engine.Key;
import Engine.Keyboard;
import Engine.Graphics;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.FrameBuilder;
import GameObject.SpriteSheet;

import java.util.HashMap;

public class DonkeyKong extends AnimatedSprite {
	public DonkeyKong(float x, float y, int width, int height) {
		super(new SpriteSheet("DonkeyKong.png", 46, 32), x, y, "STAND_STILL");
	}

	@Override
	public HashMap<String, Frame[]> getAnimations() {
		return new HashMap<String, Frame[]>() {{
			put("BEAT_CHEST", new Frame[] {
					new FrameBuilder(spriteSheet.getSprite(0, 0), 200).build(),
					new FrameBuilder(spriteSheet.getSprite(0, 1), 200).build()
			});

			put("STAND_STILL", new Frame[] {
					new FrameBuilder(spriteSheet.getSprite(1, 0), 0).build()
			});
		}};
	}

	public void update(Keyboard keyboard) {
		super.update();
		if (keyboard.isKeyDown(Key.SPACE)) {
			currentAnimationName = "BEAT_CHEST";
		} else if (keyboard.isKeyUp(Key.SPACE)) {
			currentAnimationName = "STAND_STILL";
		}
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
	}
}