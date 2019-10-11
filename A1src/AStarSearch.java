import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of A* Search.
 * @author 160021429
 */
public class AStarSearch extends Search {

	AStarSearch(int numOfParallels) {
		super(numOfParallels);
	}

	/**
	 * Run A* search.
	 */
	public void search() {
		Queue<PolarCoordinate> queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);
		PolarCoordinate finalNode = A1main.starting;

		boolean checker = false;

		// iterate the queue
		while (!queue.isEmpty()) {
			PolarCoordinate currentNode = queue.poll();

			if (currentNode.getDistance() == 0) {
				System.out.println("The aircraft cannot reach or fly over the pole, such as (0,0)");
				continue;
			}

			if (super.checkIfVisited(currentNode.getAngle(), currentNode.getDistance())) {
				continue;
			}

			super.visit(currentNode.getAngle(), currentNode.getDistance());

			double diff = currentNode.getDifferencesBetween(A1main.goal);

			// check if the A* search reached to the goal
			if (diff != 0) {
				ArrayList<PolarCoordinate> list = currentNode.getHeuristicListOfNextCoordinates(numOfParallels, diff);
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				printOutCoordinatesInTheQueue(queue);
				super.printOutListOfCoordinates(list);
				super.insertIntoQueue(queue, list);
			} else {
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				System.out.println("Found the goal!");
				System.out.println();
				finalNode = currentNode;
				checker = true;
				break;
			}
		}

		if (checker) {
			this.printOutResultPaths(finalNode);
		} else {
			System.out.println("A* Search failed to reach the goal");
			System.out.println("Please check if the coordinate of the goal is valid");
		}
	}

	/**
	 * Prints out the result path that the A* Search found.
	 * @param finalNode - the final node.
	 */
	private void printOutResultPaths(PolarCoordinate finalNode) {
		System.out.print("Result Path: ");
		PolarCoordinate node = finalNode;

		ArrayList<String> paths = new ArrayList<String>();

		// use while loop to iterate the linked list of PolarCoodrinate objects.
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
	 * @param queue - The queue that the A* search uses.
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
