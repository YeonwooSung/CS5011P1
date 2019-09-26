import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class DFS extends Search {
	private final int ANGLE_UNIT = 45;

	DFS(int numOfParallels) {
		super(numOfParallels);
	}

	/**
	 * Run DFS recursively.
	 * @param starting - starting node
	 * @param path - Queue that contains the path.
	 * @param visited - boolean array to check the visited nodes
	 * @return If success, returns the number of nodes that dfs passed through. Otherwise, returns -1.
	 */
	public int dfs(PolarCoordinate starting, Queue<PolarCoordinate> path, boolean[][] visited) {
		int angle = starting.getAngle();
		int distance = starting.getDistance();

		// check if visited
		if (super.checkIfVisited(angle, distance, visited)) {
			return -1;
		}

		// check visited nodes to prevent infinite loop
		super.visit(starting.getAngle(), starting.getDistance());
		int targetIndex = angle / ANGLE_UNIT;
		visited[distance - 1][targetIndex] = true;

		// get difference between current node and goal
		int diff = starting.getDifferencesBetween(A1main.goal);

		// check if current node is a goal
		if (diff != 0) {
			path.add(starting);

			ArrayList<PolarCoordinate> list = starting.getListOfNextCoordinates(numOfParallels);
			int size = list.size();

			int min = -1;
			Queue<PolarCoordinate> minPath = null;

			for (int i = 0; i < size; i++) {
				boolean[][] visitedArr = new boolean[numOfParallels - 1][8];
				for (int a = 0; a < visitedArr.length; a++) {
					visitedArr[a] = visited[a].clone();
				}

				Queue<PolarCoordinate> paths = new LinkedList<PolarCoordinate>();

				int result = dfs(list.get(i), paths, visitedArr);

				// check the result value to find the shortest path.
				if (result != -1) {
					if (min != -1) {
						if (min > result) {
							min = result;
							minPath = paths;
						}
					} else {
						min = result;
						minPath = paths;
					}
				}

				// clean the visit history
				super.cleanVisitedHistory(visitedArr, visited);
			}

			mergePaths(path, minPath);

			if (min != -1) {
				return min + 1;
			} else {
				return min;
			}
		} else {
			path.add(starting);
			return 1;
		}
	}

	/**
	 * Merge 2 queues where each queue contains the path.
	 * @param path - The queue.
	 * @param targetPath - The target queue that should be merged to path.
	 */
	public void mergePaths(Queue<PolarCoordinate> path, Queue<PolarCoordinate> targetPath) {
		if (targetPath == null) return;

		while (!targetPath.isEmpty()) {
			PolarCoordinate p = targetPath.poll();
			path.add(p);
		}
	}
}
