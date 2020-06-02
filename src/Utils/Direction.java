package Utils;

public enum Direction {
	LEFT(-1), RIGHT(1), UP(-1), DOWN(1);

	private int velocity;

	Direction(int velocity) {
		this.velocity = velocity;
	}

	public int getVelocity() {
		return velocity;
	}


}
