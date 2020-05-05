package GameObject;

public class Animation {
	private String animationName;
	private Frame[] frames;
	
	public Animation(String animationName, Frame[] frames) {
		this.animationName = animationName;
		this.frames = frames;
	}

	public int getNumberOfFrames() {
		return frames.length;
	}
	
	public String getAnimationName() {
		return animationName;
	}

	public Frame[] getFrames() {
		return frames;
	}
}
