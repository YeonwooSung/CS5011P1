import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of Bidirectional Search
 * @author 160021429
 */
public class BidirectionalSearch extends Search {
	private final int ANGLE_UNIT = 45;

	private boolean[][] visited1;
	private boolean[][] visited2;
	private ArrayList<PolarCoordinate> list1 = new ArrayList<>();
	private ArrayList<PolarCoordinate> list2 = new ArrayList<>();
	private Queue<PolarCoordinate> queue;
	private Queue<PolarCoordinate> reverseQueue;

	BidirectionalSearch(int numOfParallels) {
		super(numOfParallels);
		visited1 = new boolean[numOfParallels - 1][8];
		visited2 = new boolean[numOfParallels - 1][8];
	}

	/**
	 * Run bidirectional search.
	 */
	public void search() {
		queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);

		reverseQueue = new LinkedList<PolarCoordinate>();
		reverseQueue.add(A1main.goal);

		// check if the aircraft could fly over or reach the starting point
		if (A1main.starting.getDistance() == 0) {
			System.out.println("The aircraft cannot reach or fly over the pole, such as (0,0)");
			System.exit(0);
		}

		int finished = 0;
		int numOfMoves1 = 0;
		int numOfMoves2 = 0;

		try {
			// iterate while loop until either queue1 or queue2 are empty.
			while (!queue.isEmpty() || !reverseQueue.isEmpty()) {
				numOfMoves1 += 1;

				// check the node and expand the children nodes if required.
				if (checkAndExpandNodes(1)) {
					finished = 1;
					break;
				}

				numOfMoves2 += 1;

				// check the node and expand the children nodes if required.
				if (checkAndExpandNodes(2)) {
					finished = 2;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please check the command line arguments");
			System.out.println("Aircraft cannot reach or fly over the pole such as (0,0)!");
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

		/*
		 * The queue2 starts from the goal, thus, we need to reverse the paths of the queue2.
		 * For example, if the path from the goal to starting point is H180 H180, then the
		 * path from the starting point to the goal will be H360 H360.
		 *
		 * To print out the correct result path, we need to reverse the paths of the nodes in queue2.
		 */
		reversePath(path2);

		ArrayList<PolarCoordinate> pahts = mergePath(path1, path2);

		// print out the result path
		System.out.print("\nResult path: ");
		for (PolarCoordinate node : pahts) {
			System.out.print(node.getPath());
			System.out.print(" ");
		}
		System.out.println("\nThe total number of moves = " + (numOfMoves1 + numOfMoves2));
	}

	/**
	 * Expand the child nodes, and append those nodes to the given queue.
	 * @param queue - queue to store expanded nodes
	 * @param node - current node
	 */
	private void appendExpandedNodesToQueue(Queue<PolarCoordinate> queue, PolarCoordinate node) {
		ArrayList<PolarCoordinate> list = node.getListOfNextCoordinates(numOfParallels);
		super.printOutListOfCoordinates(list);
		super.insertIntoQueue(queue, list);
	}

	/**
	 * The queue2 starts from the goal, thus, we need to reverse the paths of the queue2.
	 * For example, if the path from the goal to starting point is H180 H180, then the
	 * path from the starting point to the goal will be H360 H360.
	 *
	 * To print out the correct result path, we need to reverse the paths of the nodes in queue2.
	 *
	 * @param coordinate
	 */
	private void reversePath(PolarCoordinate coordinate) {
		PolarCoordinate node = coordinate;

		// use while loop to iterate the linked list
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
	 * @param num - 1 for queue1, and 2 for queue2
	 * @return If the Bidirectional search find the path, returns true. Otherwise, returns false.
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public boolean checkAndExpandNodes(int num) throws ArrayIndexOutOfBoundsException {
		//Queue<PolarCoordinate> queue, boolean[][] visited, boolean[][] otherArr, ArrayList<PolarCoordinate> list
		Queue<PolarCoordinate> queue;
		boolean[][] visited;
		boolean[][] otherArr;
		ArrayList<PolarCoordinate> list;

		switch (num) {
			case 1:
				queue = this.queue;
				visited = this.visited1;
				otherArr = this.visited2;
				list = this.list1;
				break;
			default:
				queue = this.reverseQueue;
				visited = this.visited2;
				otherArr = this.visited1;
				list = this.list2;
		}

		PolarCoordinate node = queue.poll();
		int angle = node.getAngle();
		int distance = node.getDistance();

		// check if the current node is visited
		if (checkIfVisited(angle, distance, visited)) {
			return false;
		}

		System.out.println("Current coordinate = " + distance + ", " + angle);

		if (num != 1) {
			System.out.print("Queue2 : ");
		} else {
			System.out.print("Queue1 : ");
		}

		printOutCoordinatesInTheQueue(queue);

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
	 * Print out the coordinates in the queue.
	 * @param queue - The queue that the BFS uses.
	 */
	private void printOutCoordinatesInTheQueue(Queue<PolarCoordinate> queue) {
		int index = 0;
		int lastIndex = queue.size() - 1;

		for (PolarCoordinate p : queue) {
			System.out.print("(" + p.getDistance() + ", " + p.getAngle() + ")");

			if (index != lastIndex) System.out.print(", ");

			index += 1;
		}

		System.out.println();
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
