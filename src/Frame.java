import java.awt.image.BufferedImage;

public class Frame {
	private BufferedImage frameImage;
	private float delay;
	
	public Frame(BufferedImage frameImage, float delay) {
		super();
		this.frameImage = frameImage;
		this.delay = delay;
	}
	
	public BufferedImage getFrameImage() {
		return frameImage;
	}

	public float getDelay() {
		return delay;
	}
}
