
public class PolarCoordinate {
	private int angle;
	private int distance;

	PolarCoordinate(int distance, int angle) {
		this.angle = angle;
		this.distance = distance;
	}

	/**
	 * Getter for angle.
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * Setter for angle.
	 * @param angle the angle to set
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}

	/**
	 * Getter for distance.
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Setter for distance
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

}
