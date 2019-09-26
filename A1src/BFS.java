import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BFS extends Search {
	BFS(int numOfParallels) {
		super(numOfParallels);
	}

	public void bfs() {
		Queue<PolarCoordinate> queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);

		//TODO checking visited

		while (!queue.isEmpty()) {
			PolarCoordinate currentNode = queue.poll();

			super.visit(currentNode.getAngle(), currentNode.getDistance());

			int diff = currentNode.getDifferencesBetween(A1main.goal);

			if (diff != 0) {
				ArrayList<PolarCoordinate> list = currentNode.getListOfNextCoordinates(numOfParallels);
				super.insertIntoQueue(queue, list);

				//TODO
			} else {
				//TODO
				break;
			}
		}

		System.out.println("BFS found the goal!");
	}
}
