import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BidirectionalSearch extends Search {
	private final int ANGLE_UNIT = 45;

	private boolean[][] visited1;
	private boolean[][] visited2;
	private ArrayList<PolarCoordinate> list1 = new ArrayList<>();
	private ArrayList<PolarCoordinate> list2 = new ArrayList<>();

	BidirectionalSearch(int numOfParallels) {
		super(numOfParallels);
		visited1 = new boolean[numOfParallels - 1][8];
		visited2 = new boolean[numOfParallels - 1][8];
	}

	public void search() {
		Queue<PolarCoordinate> queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);
		Queue<PolarCoordinate> reverseQueue = new LinkedList<PolarCoordinate>();
		reverseQueue.add(A1main.goal);

		int finished = 0;

		try {
			// iterate while loop until either queue1 or queue2 are empty.
			while (!queue.isEmpty() || !reverseQueue.isEmpty()) {
				if (checkAndExpandNodes(queue, visited1, visited2, list1)) {
					finished = 1;
					break;
				}

				if (checkAndExpandNodes(reverseQueue, visited2, visited1, list2)) {
					finished = 2;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please check the command line arguments");
			System.out.println("Aircraft cannot reach or fly over the pole!");
			return;
		}

		PolarCoordinate path1 = null;
		PolarCoordinate path2 = null;

		switch (finished) {
			case 1:
				path1 = list1.get(list1.size() - 1);
				path2 = findNodeFromList(path1, list2);
				break;
			case 2:
				path2 = list2.get(list2.size() - 1);
				path1 = findNodeFromList(path2, list1);
		}

		//TODO description
		reversePath(path2);

		ArrayList<PolarCoordinate> pahts = mergePath(path1, path2);

		System.out.print("Result path: ");
		for (PolarCoordinate node : pahts) {
			System.out.print(node.getPath());
			System.out.print(" ");
		}
		System.out.println();
	}

	private void appendExpandedNodesToQueue(Queue<PolarCoordinate> queue, PolarCoordinate node) {
		ArrayList<PolarCoordinate> list = node.getListOfNextCoordinates(numOfParallels);
		super.insertIntoQueue(queue, list);
	}

	private void reversePath(PolarCoordinate coordinate) {
		PolarCoordinate node = coordinate;
		while (node.getParent() != null) {
			String path = node.getPath();
			String newPath;

			if (path.equals("H360")) {
				newPath = new String("H180");
			} else if (path.equals("H180")) {
				newPath = new String("H360");
			} else if (path.equals("H90")) {
				newPath = new String("H270");
			} else {
				newPath = new String("H90");
			}

			node.setPath(newPath);
			node = node.getParent();
		}
	}

	/**
	 * Check if the current node is marked as visited.
	 * If not, expand the nodes, and add the expanded nodes to the queue.
	 * @param queue - Queue that contains the nodes.
	 * @param visited - boolean array to check if the current node is marked as visited.
	 * @param otherArr - another boolean array that contains the visiting history of the other queue.
	 * @param list - ArrayList that contains the all visited nodes.
	 * @return If the Bidirectional search find the path, returns true. Otherwise, returns false.
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public boolean checkAndExpandNodes(Queue<PolarCoordinate> queue, boolean[][] visited, boolean[][] otherArr, ArrayList<PolarCoordinate> list) throws ArrayIndexOutOfBoundsException {
		PolarCoordinate node = queue.poll();
		int angle = node.getAngle();
		int distance = node.getDistance();

		// check if the current node is visited
		if (checkIfVisited(angle, distance, visited)) {
			return false;
		}

		visit(angle, distance, visited);
		list.add(node);

		// check if the current node is visited by other queue.
		if (checkIfVisited(angle, distance, otherArr)) {
			return true;
		} else {
			appendExpandedNodesToQueue(queue, node);
		}

		return false;
	}

	/**
	 * Find the specific node from the given array list of PolarCoordinate objects.
	 * @param target - target node.
	 * @param list - Array list of PolarCoordinate objects.
	 * @return Found object.
	 */
	private PolarCoordinate findNodeFromList(PolarCoordinate target, ArrayList<PolarCoordinate> list) {
		int angle = target.getAngle();
		int distance = target.getDistance();
		PolarCoordinate found = null;

		// use for loop to iterate ArrayList
		for (PolarCoordinate node : list) {
			if (node.getAngle() == angle && node.getDistance() == distance) {
				found = node;
			}
		}

		return found;
	}

	/**
	 * Merge the path so that the program could print out the found path.
	 * @param node1 - last node of the first linked list.
	 * @param node2 - last node of the second linked list.
	 * @return ArrayList that contains the paths.
	 */
	private ArrayList<PolarCoordinate> mergePath(PolarCoordinate node1, PolarCoordinate node2) {
		ArrayList<PolarCoordinate> paths = new ArrayList<PolarCoordinate>();

		while (node1.getParent() != null) {
			paths.add(node1);
			node1 = node1.getParent();
		}

		Collections.reverse(paths);

		while (node2 != null) {
			paths.add(node2);
			node2 = node2.getParent();
		}

		return paths;
	}

	/**
	 * Mark the given coordinate as visited.
	 * @param angle - angle of the target node.
	 * @param distance - distance of the target node.
	 * @param visited - boolean array to store visit history.
	 */
	private void visit(int angle, int distance, boolean[][] visited) {
		int target = angle / ANGLE_UNIT;
		visited[distance - 1][target] = true;
	}
}
