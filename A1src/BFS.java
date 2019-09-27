import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BFS extends Search {

	BFS(int numOfParallels) {
		super(numOfParallels);
	}

	/**
	 * Run bfs.
	 */
	public void bfs() {
		Queue<PolarCoordinate> queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);
		PolarCoordinate finalNode = A1main.starting;

		// iterate the queue
		while (!queue.isEmpty()) {
			PolarCoordinate currentNode = queue.poll();

			if (super.checkIfVisited(currentNode.getAngle(), currentNode.getDistance())) {
				continue;
			}

			super.visit(currentNode.getAngle(), currentNode.getDistance());

			// check if this is the node that we are looking for.
			if (currentNode.checkIfReachedToGoal()) {
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				System.out.println("Found the goal!");
				System.out.println();
				finalNode = currentNode;
				break;
			} else {
				ArrayList<PolarCoordinate> list = currentNode.getListOfNextCoordinates(numOfParallels, currentNode);
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				super.printOutListOfCoordinates(list);
				super.insertIntoQueue(queue, list);
			}
		}

		System.out.print("Result Path: ");
		PolarCoordinate node = finalNode;

		while (node.getParent() != null) {
			if (node.getParent().equals(A1main.starting)) {
				System.out.print(node.getPath());
			} else {
				System.out.print(node.getPath() + ", ");
			}
			node = node.getParent();
		}
	}
}
