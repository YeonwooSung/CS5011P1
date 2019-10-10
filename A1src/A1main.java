import java.io.IOException;
import java.util.ArrayList;

public class A1main {
	private static final String SEARCH_BFS = "BFS";
	private static final String SEARCH_DFS = "DFS";
	private static final String SEARCH_ASTAR = "AStar";
	private static final String SEARCH_BEST_FIRST = "BestF";
	private static final String SEARCH_BIDIRECTIONAL = "BiDir";

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

	/**
	 * Generate the array list that contains the coordinates that the aircraft cannot fly over due to the weather issue.
	 * @param args - command line arguments
	 * @param numOfParallels - N
	 * @return The array list of forecast coordinates.
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private static ArrayList<PolarCoordinate> generateForecastList(String[] args, int numOfParallels) throws NumberFormatException, IOException {
		ArrayList<PolarCoordinate> forecast = new ArrayList<PolarCoordinate>();

		for (int i = 4; i < args.length; i++) {
			String[] strArray = args[i].split(",");
			int d = Integer.parseInt(strArray[0]);
            int angle = Integer.parseInt(strArray[1]);

            validateAngle(angle);
            validateDistance(d, numOfParallels);

            forecast.add(new PolarCoordinate(d, angle));
		}

		return forecast;
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

                	// if the number of command line arguments is greater than 4, use the forecast mode.
                	if (args.length > 4) {
                		ArrayList<PolarCoordinate> forecast = generateForecastList(args, numOfParallels);
                		dfs = new DFS(numOfParallels, forecast);
                		System.out.println("Starts DFS with the forecast mode\n");
                	} else {
                		System.out.println("Starts DFS\n");
                	}

                	dfs.search(starting);
                	dfs.printOutPathsFromFrontier();

                } else if (args[0].equals(SEARCH_BFS)) {
                	// run BFS
                	System.out.println("Starts BFS\n");
                	BFS bfs = new BFS(numOfParallels);
                	bfs.search();

                } else if (args[0].equals(SEARCH_ASTAR)) {
                	// run A* search
                	System.out.println("Starts A* Search\n");
                	AStarSearch astar = new AStarSearch(numOfParallels);
                	astar.search();

                } else if (args[0].equals(SEARCH_BEST_FIRST)) {
                	//run Best First search
                	System.out.println("Starts Best-First Search\n");
                	BestFirstSearch bestF = new BestFirstSearch(numOfParallels);
                	bestF.search(starting);
                	bestF.printOutPathsFromFrontier();

                } else if (args[0].equals(SEARCH_BIDIRECTIONAL)) {
                	//run bi-directional search
                	System.out.println("Starts Bi-directional Search\n");
                	BidirectionalSearch bidir = new BidirectionalSearch(numOfParallels);
                	bidir.search();

                } else {
                	System.out.println(args[1]);
                	throw new Exception();
                }
            } catch (NumberFormatException e) {
            	e.printStackTrace();
            	System.out.println("Invalid coordinate!");
            	System.out.println("Please check the command line arguments");
            } catch (IOException e) {
            	e.printStackTrace();
            	System.out.println("Invalid coordinate!");
            	System.out.println("Please check the command line arguments");
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
}
