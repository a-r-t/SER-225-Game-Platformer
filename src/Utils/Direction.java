package Utils;

// Represents a 2D direction, which can be either left, right, up, or down
// each direction is given a property for velocity, which is based on screen coordinates (e.g. going downwards adds 1 to Y, so DOWN has a velocity of 1)
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
