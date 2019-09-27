import java.util.ArrayList;

public class DFS extends Search {
	private ArrayList<PolarCoordinate> frontier;

	DFS(int numOfParallels) {
		super(numOfParallels);
		frontier = new ArrayList<PolarCoordinate>();
	}

	/**
	 * Run DFS.
	 * @param starting - starting node
	 * @return If success, returns the number of nodes that dfs passed through. Otherwise, returns -1.
	 */
	public int search(PolarCoordinate starting) {
		int angle = starting.getAngle();
		int distance = starting.getDistance();

		System.out.println("Current coordinate: " + starting.getDistance() + ", " + starting.getAngle());

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
			int size = list.size();
			int numOfPaths = -1;

			printOutFrontier(); // print out the frontier
			super.printOutListOfCoordinates(list); // print out the expanded nodes

			//use for loop to make recursive calls with all child nodes
			for (int i = 0; i < size; i++) {
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
	 * Print out paths that are stored in the frontier.
	 */
	public void printOutPathsFromFrontier() {
		PolarCoordinate lastNode = frontier.get(frontier.size() - 1);
		System.out.print("Result Path : ");

		for (PolarCoordinate node : frontier) {
			String path = node.getPath();

			if (!path.equals("")) {
				System.out.print(path);
				
				if (!node.equals(lastNode)) {
					System.out.print(", ");
				}
			}
		}

		System.out.println();
	}

	/**
	 * Print out all coordinates in the frontier.
	 */
	private void printOutFrontier() {
		System.out.println("Number of moves : " + (frontier.size() - 1));
		System.out.print("Frontier : ");
		for (PolarCoordinate node : frontier) {
			System.out.print("(" + node.getDistance() + "," + node.getAngle() + ")");
			System.out.print(" ");
		}
		System.out.println();
	}
}
