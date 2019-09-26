import java.util.ArrayList;


public class PolarCoordinate {
	private final int ZERO = 0;
	private final int ONE = 1;
	private final int ANGLE_UNIT = 45;
	private final int ANGLE_LIMIT = 180;
	private final int MAX_ANGLE = 315;

	private int angle;
	private int distance;
	private boolean marked;
	private String path;

	PolarCoordinate(int distance, int angle) {
		this.angle = angle;
		this.distance = distance;
		this.marked = false;
		this.setPath("");
	}

	PolarCoordinate(int distance, int angle, String path) {
		this.angle = angle;
		this.distance = distance;
		this.marked = false;
		this.setPath(path);
	}

	/**
	 * Get the differences between this coordinate and given coordinate.
	 * @param other - target coordinate
	 * @return The differences between this coordinate and given coordinate.
	 */
	public int getDifferencesBetween(PolarCoordinate other) {
		int otherAngle = other.getAngle();
		int otherDistance = other.getDistance();

		int angle_diff = angle - otherAngle;
		int distance_diff = distance - otherDistance;

		if (angle_diff < ZERO) {
			angle_diff = Math.abs(angle_diff);
		} else if (angle_diff > ANGLE_LIMIT) {
			angle_diff = Math.abs(otherAngle - angle);
		}

		if (distance_diff < ZERO) {
			distance_diff = Math.abs(distance_diff);
		}

		int diffs = (angle_diff / ANGLE_UNIT) + distance_diff;
		return diffs;
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

	/**
	 * Returns the list of next possible nodes.
	 * @param numOfParellels - N
	 * @return The array list of next nodes.
	 */
	public ArrayList<PolarCoordinate> getListOfNextCoordinates(int numOfParallels) {
		ArrayList<PolarCoordinate> list = new ArrayList<PolarCoordinate>();
		if (distance != 1) {
			// add polar coordinate node for H360(N) path
			list.add(new PolarCoordinate(distance - ONE, angle, "H360"));
		}

		if (distance != numOfParallels - 1) {
			// add polar coordinate node for H180(S) path
			list.add(new PolarCoordinate(distance + ONE, angle, "H180"));
		}

		// add polar coordinate node for H90(E) path
		if (angle != MAX_ANGLE) {
			list.add(new PolarCoordinate(distance, angle + ANGLE_UNIT, "H90"));
		} else {
			list.add(new PolarCoordinate(distance, ZERO, "H90"));
		}

		// add polar coordinate node for H270(E) path
		if (angle != ZERO) {
			list.add(new PolarCoordinate(distance, angle - ANGLE_UNIT, "H270"));
		} else {
			list.add(new PolarCoordinate(distance, MAX_ANGLE, "H270"));
		}

		return list;
	}

	public void mark() {
		this.marked = true;
	}

	public boolean checkIfMarked() {
		return marked;
	}

	/**
	 * Getter for path.
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter for path
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
