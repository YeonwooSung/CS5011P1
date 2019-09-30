import java.util.ArrayList;

public class PolarCoordinate {
	private final int ZERO = 0;
	private final int ONE = 1;
	private final int ANGLE_UNIT = 45;
	private final int ANGLE_LIMIT = 180;
	private final int MAX_ANGLE = 315;

	private int angle;
	private int distance;
	private String path;

	private PolarCoordinate parent;
	private ArrayList<PolarCoordinate> list;

	PolarCoordinate(int distance, int angle) {
		this.angle = angle;
		this.distance = distance;
		this.path = "";

		this.list = null;
		this.parent = null;
	}

	PolarCoordinate(int distance, int angle, String path) {
		this(distance, angle);
		this.path = path;
		this.parent = null;
	}

	PolarCoordinate(int distance, int angle, String path, PolarCoordinate parent) {
		this(distance, angle, path);
		this.parent = parent;
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
	 * Check if the search algorithm reached to the goal.
	 * @return If it reached to the goal, returns true. Otherwise, returns false.
	 */
	public boolean checkIfReachedToGoal() {
		return (getDifferencesBetween(A1main.goal) == 0);
	}

	/**
	 * Getter for angle.
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * Getter for distance.
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * Returns the list of next possible nodes.
	 * @param numOfParellels - N
	 * @return The array list of next nodes.
	 */
	public ArrayList<PolarCoordinate> getListOfNextCoordinates(int numOfParallels) {
		if (list != null) {
			return list;
		}

		list = new ArrayList<PolarCoordinate>();

		// add polar coordinate node for H90(E) path
		if (angle != MAX_ANGLE) {
			list.add(new PolarCoordinate(distance, angle + ANGLE_UNIT, "H90", this));
		} else {
			list.add(new PolarCoordinate(distance, ZERO, "H90", this));
		}

		// add polar coordinate node for H270(E) path
		if (angle != ZERO) {
			list.add(new PolarCoordinate(distance, angle - ANGLE_UNIT, "H270", this));
		} else {
			list.add(new PolarCoordinate(distance, MAX_ANGLE, "H270", this));
		}

		if (distance != 1) {
			// add polar coordinate node for H360(N) path
			list.add(new PolarCoordinate(distance - ONE, angle, "H360", this));
		}

		if (distance != numOfParallels - 1) {
			// add polar coordinate node for H180(S) path
			list.add(new PolarCoordinate(distance + ONE, angle, "H180", this));
		}

		return list;
	}

	/**
	 * Returns the list of next possible nodes.
	 * @param numOfParellels - N
	 * @return The array list of next nodes - sorted by remaining distances.
	 */
	public ArrayList<PolarCoordinate> getSortedListOfNextCoordinates(int numOfParallels, PolarCoordinate parent) {
		if (list != null) return list;

		getListOfNextCoordinates(numOfParallels);
		list.sort((p1, p2) -> ((Integer)p1.getDifferencesBetween(A1main.goal)).compareTo(((Integer) p2.getDifferencesBetween(A1main.goal))));

		return list;
	}

	/**
	 * Get a list of next coordinates.
	 * But this list only contains the nodes whose remaining distance is shorter than previous node.
	 * @param numOfParallels - N
	 * @param previous_diff - previous node's remaining distance
	 * @return The list of next coordinates.
	 */
	public ArrayList<PolarCoordinate> getHeuristicListOfNextCoordinates(int numOfParallels, int previous_diff) {
		getListOfNextCoordinates(numOfParallels);

		for (int i = 0; i < list.size(); i++) {
			PolarCoordinate node = list.get(i);
			int diff = node.getDifferencesBetween(A1main.goal);

			if (diff >= previous_diff) {
				list.remove(i);
				i -= 1;
			}
		}

		return list;
	}

	/**
	 * Getter for path.
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter for path.
	 * @param path - new path.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Getter for parent.
	 * @return parent node
	 */
	public PolarCoordinate getParent() {
		return parent;
	}
}
