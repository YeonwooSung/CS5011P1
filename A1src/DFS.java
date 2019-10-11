import java.util.ArrayList;

/**
 * Implementation of DFS.
 * @author 160021429
 */
public class DFS extends Search {
	private ArrayList<PolarCoordinate> frontier;
	private ArrayList<PolarCoordinate> forecast;
	private boolean isForecastMode;

	DFS(int numOfParallels) {
		super(numOfParallels);
		isForecastMode = false;
		frontier = new ArrayList<PolarCoordinate>();
	}

	DFS(int numOfParallels, ArrayList<PolarCoordinate> forecast) {
		super(numOfParallels);
		isForecastMode = true;
		this.forecast = forecast;
		frontier = new ArrayList<PolarCoordinate>();
	}

	/**
	 * Check if the given coordinate is a weather obstacle.
	 * @param coordinate - polar coordinate
	 * @return
	 */
	private boolean checkIfStartingPointIsObstacle(PolarCoordinate coordinate) {
		if (this.isForecastMode) {
			for (PolarCoordinate p : this.forecast) {
				if (p.getDifferencesBetween(coordinate) == 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Run DFS.
	 * @param starting - starting node
	 * @return If success, returns the number of nodes that dfs passed through. Otherwise, returns -1.
	 */
	public int search(PolarCoordinate starting) {
		int angle = starting.getAngle();
		int distance = starting.getDistance();

		if (distance == 0) {
			System.out.println("The aircraft cannot reach or fly over the pole, such as (0,0)");
			return -1;
		}

		System.out.println("Current coordinate: " + starting.getDistance() + ", " + starting.getAngle());

		if (checkIfStartingPointIsObstacle(starting)) {
			System.out.println("Aircraft cannot fly over this coordinate due to the weather issue!");
			System.out.println();
			return -1;
		}

		// check if visited
		if (super.checkIfVisited(angle, distance)) {
			System.out.println("Already visited this coordinate...");
			printOutFrontier();
			System.out.println();
			return -1;
		}

		// check visited nodes to prevent infinite loop
		super.visit(starting.getAngle(), starting.getDistance());
		frontier.add(starting);

		// check if current node is a goal
		if (starting.checkIfReachedToGoal()) {

			System.out.println("Found the goal!");
			printOutFrontier();
			System.out.println();
			return 1;

		} else {
			ArrayList<PolarCoordinate> list = starting.getListOfNextCoordinates(numOfParallels);
			int numOfPaths = -1;

			if (this.isForecastMode) {
				checkForcast(list);
			}

			printOutFrontier(); // print out the frontier
			super.printOutListOfCoordinates(list); // print out the expanded nodes

			//use for loop to make recursive calls with all child nodes
			for (int i = 0; i < list.size(); i++) {
				int result = search(list.get(i)); //call this function recursively

				// check the result value to find the shortest path.
				if (result != -1) {
					numOfPaths = result;
					break;
				}
			}

			if (numOfPaths == -1) {
				frontier.remove(frontier.size() - 1); //remove current node from frontier
			}

			return numOfPaths;
		}
	}

	/**
	 * Iterate the list of expanded nodes to check if the child node cannot be crossed due to weather.
	 * It removes the node from the list if the node cannot be crossed.
	 *
	 * @param children - list of expanded children.
	 */
	private void checkForcast(ArrayList<PolarCoordinate> children) {
		for (int i = 0; i < children.size(); i++) {
			PolarCoordinate child = children.get(i);
			if (checkIfPointIsAbleToCross(child)) {
				children.remove(i);
				i -= 1;
			}
		}
	}

	/**
	 * Check if the given node cannot be crossed due to weather issue.
	 * @param point
	 * @return
	 */
	private boolean checkIfPointIsAbleToCross(PolarCoordinate point) {
		boolean checker = false;

		for (PolarCoordinate node : forecast) {
			if (node.getAngle() == point.getAngle() && node.getDistance() == point.getDistance()) {
				checker = true;
				break;
			}
		}

		return checker;
	}

	/**
	 * Print out paths that are stored in the frontier.
	 */
	public void printOutPathsFromFrontier() {
		if (frontier.size() == 0) {
			System.out.println("DFS failed to find the path!");
			return;
		}

		PolarCoordinate lastNode = frontier.get(frontier.size() - 1);
		System.out.print("Result Path : ");
		int total = 0;

		for (PolarCoordinate node : frontier) {
			String path = node.getPath();

			if (!path.equals("")) {
				System.out.print(path);
				total += 1;

				if (!node.equals(lastNode)) {
					System.out.print(", ");
				}
			}
		}

		System.out.println();
		System.out.println("Total moves = " + total);
	}

	/**
	 * Print out all coordinates in the frontier.
	 */
	private void printOutFrontier() {
		if (frontier.size() == 0) {
			System.out.println("DFS failed to find the goal..");
			System.out.println("Please check if the goal's coordinate is valid!");
			return;
		}

		System.out.println("Number of moves : " + (frontier.size() - 1));
		System.out.print("Frontier : ");

		for (PolarCoordinate node : frontier) {
			System.out.print("(" + node.getDistance() + "," + node.getAngle() + ")");
			System.out.print(" ");
		}
		System.out.println();
	}
}
