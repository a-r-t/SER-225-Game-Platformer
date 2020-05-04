import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class AnimatedSprite extends Rectangle {

	protected SpriteSheet spriteSheet;
	protected HashMap<String, Frame[]> animations = new HashMap<>();
	protected String currentAnimation = "";
	protected String previousAnimation = "";
	protected int currentFrame;
	protected long beforeTime = System.currentTimeMillis();
	protected boolean hasAnimationFinished;
	protected BufferedImage image;

	public AnimatedSprite(float x, float y, float width, float height, SpriteSheet spriteSheet) {
		super(x, y, width, height);
		this.spriteSheet = spriteSheet;
	}

	@Override
	public void update(Keyboard keyboard) {		
		if (!previousAnimation.equals(currentAnimation)) {
			currentFrame = 0;
			beforeTime = System.currentTimeMillis();
			hasAnimationFinished = false;
		}
		else {
			long currentMillis = System.currentTimeMillis() - beforeTime;
			if (currentMillis > animations.get(currentAnimation)[currentFrame].getDelay()) {
				beforeTime = System.currentTimeMillis();
				currentFrame++;
				if (currentFrame >= animations.get(currentAnimation).length) {
					currentFrame = 0;
					hasAnimationFinished = true;
				}

			}
		}
		previousAnimation = currentAnimation;
		image = getCurrentFrame().getFrameImage();

	}
	
	public Frame getCurrentFrame() {
		return animations.get(currentAnimation)[currentFrame];
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, Math.round(getX()), Math.round(getY()), Math.round(width), Math.round(height), null);
	}

}
