import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.LinkedList;

public class BFS extends Search {

	BFS(int numOfParallels) {
		super(numOfParallels);
	}

	/**
	 * Run bfs.
	 */
	public void search() {
		Queue<PolarCoordinate> queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);
		PolarCoordinate finalNode = A1main.starting;

		int moves = -1;
		boolean checker = false;

		// iterate the queue
		while (!queue.isEmpty()) {
			PolarCoordinate currentNode = queue.poll();

			if (super.checkIfVisited(currentNode.getAngle(), currentNode.getDistance())) {
				continue;
			}

			moves += 1;
			super.visit(currentNode.getAngle(), currentNode.getDistance());

			// check if this is the node that we are looking for.
			if (currentNode.checkIfReachedToGoal()) {
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				System.out.println("Found the goal!");
				System.out.println();
				finalNode = currentNode;
				checker = true;
				break;
			} else {
				ArrayList<PolarCoordinate> list = currentNode.getListOfNextCoordinates(numOfParallels);
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				printOutCoordinatesInTheQueue(queue);
				super.printOutListOfCoordinates(list);
				super.insertIntoQueue(queue, list);
			}
		}

		if (checker) {
			this.printOutResultPaths(finalNode);
			System.out.println("The number of total moves = " + moves);
		} else {
			System.out.println("BFS failed to reach the goal");
			System.out.println("Please check if the coordinate of the goal is valid");
		}
	}

	/**
	 * Print out the result paths.
	 * @param finalNode
	 */
	private void printOutResultPaths(PolarCoordinate finalNode) {
		System.out.print("Result Path: ");
		PolarCoordinate node = finalNode;

		ArrayList<String> paths = new ArrayList<String>();

		while (node.getParent() != null) {
			paths.add(node.getPath());
			node = node.getParent();
		}

		Collections.reverse(paths);

		for (String path : paths) {
			System.out.print(path);
			System.out.print(" ");
		}
		System.out.println();
	}

	/**
	 * Print out the coordinates in the queue.
	 * @param queue - The queue that the BFS uses.
	 */
	private void printOutCoordinatesInTheQueue(Queue<PolarCoordinate> queue) {
		System.out.print("Queue : ");
		int index = 0;
		int lastIndex = queue.size();

		for (PolarCoordinate p : queue) {
			System.out.print("(" + p.getDistance() + ", " + p.getAngle() + ")");

			if (index != lastIndex) System.out.print(", ");

			index += 1;
		}

		System.out.println();
	}
}
