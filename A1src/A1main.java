import java.io.IOException;

public class A1main {
	private static final String SEARCH_BFS = "BFS";
	private static final String SEARCH_DFS = "DFS";
	private static final String SEARCH_ASTAR = "AStar";
	private static final String SEARCH_BEST_FIRST = "BestF";

	public static PolarCoordinate starting;
	public static PolarCoordinate goal;

	/**
	 * Check if the value of given angle is valid.
	 * @param angle - angle to validate
	 * @throws IOException
	 */
	public static void validateAngle(int angle) throws IOException {
		switch (angle) {
			case 0 :
			case 45 :
			case 90 :
			case 135 :
			case 180 :
			case 225 :
			case 270 :
			case 315 :
				break;
			default:
				throw new IOException();
		}
	}

	/**
	 * Check if the value of given distance is valid.
	 * @param distance - distance to validate
	 * @param numOfParallels - N
	 * @throws IOException
	 */
	public static void validateDistance(int distance, int numOfParallels) throws IOException {
		if (distance < 0 || distance >= numOfParallels) {
        	throw new IOException();
        }
	}

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java A1main <DFS|BFS|AStar|BestF|...> <N> <d_s,angle_s> <d_g,angle_g> [any param]");
        } else {
            try {
                int numOfParallels = Integer.parseInt(args[1]);

                if (!(args[2].contains(",") && args[3].contains(","))) throw new IOException();
                String[] strArray_s = args[2].split(",");
                String[] strArray_g = args[3].split(",");

                int d_s = Integer.parseInt(strArray_s[0]);
                int angle_s = Integer.parseInt(strArray_s[1]);

                int d_g = Integer.parseInt(strArray_g[0]);
                int angle_g = Integer.parseInt(strArray_g[1]);

                // validate angle -> angle should be one of {0, 45, 90, 135, 180, 225, 270, 315}
                validateAngle(angle_s);
                validateAngle(angle_g);

                // validate distance
                validateDistance(d_s, numOfParallels);
                validateDistance(d_g, numOfParallels);

                starting = new PolarCoordinate(d_s, angle_s);
                goal = new PolarCoordinate(d_g, angle_g);

                if (args[0].equals(SEARCH_DFS)) {
                	// run DFS
                	DFS dfs = new DFS(numOfParallels);
                	dfs.search(starting);
                	dfs.printOutPathsFromFrontier();

                } else if (args[0].equals(SEARCH_BFS)) {
                	// run BFS
                	BFS bfs = new BFS(numOfParallels);
                	bfs.search();

                } else if (args[0].equals(SEARCH_ASTAR)) {
                	// run A* search
                	AStarSearch astar = new AStarSearch(numOfParallels);
                	astar.search();

                } else if (args[0].equals(SEARCH_BEST_FIRST)) {
                	//run Best First search
                	BestFirstSearch bestF = new BestFirstSearch(numOfParallels);
                	bestF.search(starting);
                	bestF.printOutPathsFromFrontier();

                } else {
                	System.out.println(args[1]);
                	throw new Exception();
                }
            } catch (NumberFormatException e) {
            	e.printStackTrace();
            } catch (IOException e) {
            	e.printStackTrace();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
}
