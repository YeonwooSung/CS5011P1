import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AStarSearch extends Search {

	AStarSearch(int numOfParallels) {
		super(numOfParallels);
	}

	public void aStartSearch() {
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

			int diff = currentNode.getDifferencesBetween(A1main.goal);

			// check if the A* search reached to the goal
			if (diff != 0) {
				ArrayList<PolarCoordinate> list = currentNode.getHeuristicListOfNextCoordinates(numOfParallels, currentNode, diff);
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				super.printOutListOfCoordinates(list);
				super.insertIntoQueue(queue, list);
			} else {
				System.out.println("Current coordinate: " + currentNode.getDistance() + ", " + currentNode.getAngle());
				System.out.println("Found the goal!");
				System.out.println();
				finalNode = currentNode;
				break;
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
