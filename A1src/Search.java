import java.util.Queue;
import java.util.ArrayList;

/**
 * Super class of all search algorithms.
 * @author 160021429
 *
 */
public class Search {
	private final int ANGLE_UNIT = 45;

	protected int numOfParallels;
	private boolean[][] visited;

	Search(int numOfParallels) {
		this.setNumOfParalles(numOfParallels);
		visited = new boolean[numOfParallels - 1][8];
	}

	/**
	 * Setter for numOfParallels.
	 * @param numOfParallels
	 */
	public void setNumOfParalles(int numOfParallels) {
		this.numOfParallels = numOfParallels;
	}

	/**
	 * Getter for numOfParallels.
	 * @return numOfParallels
	 */
	public int getNumOfParallels() {
		return numOfParallels;
	}

	/**
	 * Mark the given coordinate as visited.
	 * @param angle - angle of the coordinate.
	 * @param distance - distance of the coordinate.
	 */
	public void visit(int angle, int distance) {
		int target = angle / ANGLE_UNIT;
		visited[distance - 1][target] = true;
	}

	/**
	 * Check if the given coordinate is marked as visited.
	 * @param angle - angle of the coordinate.
	 * @param distance - distance of the coordinate.
	 * @return If visited, returns true. Otherwise, returns false.
	 */
	public boolean checkIfVisited(int angle, int distance) {
		int target = angle / ANGLE_UNIT;
		return visited[distance - 1] [target];
	}

	/**
	 * Check if the given coordinate is marked as visited.
	 * @param angle - angle of the coordinate.
	 * @param distance - distance of the coordinate.
	 * @param visited - booelan 2D array
	 * @return If visited, returns true. Otherwise, returns false.
	 */
	public boolean checkIfVisited(int angle, int distance, boolean[][] visited) {
		int target = angle / ANGLE_UNIT;
		return visited[distance - 1][target];
	}

	/**
	 * Insert the list of expanded nodes to the queue.
	 * @param queue - queue to store the expanded nodes
	 * @param list - list of expanded nodes
	 */
	public void insertIntoQueue(Queue<PolarCoordinate> queue, ArrayList<PolarCoordinate> list) {
		while (!list.isEmpty()) {
			PolarCoordinate pc = list.get(0);
			queue.add(pc);
			list.remove(0);
		}
	}

	/**
	 * Print out the list of coordinates.
	 * @param list - array list of coordinates
	 */
	public void printOutListOfCoordinates(ArrayList<PolarCoordinate> list) {
		System.out.println("Expanded nodes: ");
		for (int i = 0; i < list.size(); i++) {
			PolarCoordinate p = list.get(i);
			int index = i + 1;
			System.out.print("	Node" + index + " : Coordinate = " + p.getDistance() + ", " + p.getAngle());
			System.out.println(" & Path = " + p.getPath());
		}
		System.out.println();
	}
}
