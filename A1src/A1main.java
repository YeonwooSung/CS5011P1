import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class A1main {
	public static PolarCoordinate starting;
	public static PolarCoordinate goal;

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

	public static void validateDistance(int distance, int numOfParallels) throws IOException {
		if (distance < 0 || distance > numOfParallels - 1) {
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

                //TODO
                DFS dfs = new DFS(numOfParallels);
                Queue<PolarCoordinate> path = new LinkedList<PolarCoordinate>();
                boolean[][] visited = new boolean[numOfParallels - 1][8];
                dfs.dfs(starting, path, visited);
                while (!path.isEmpty()) {
                	PolarCoordinate p = path.poll();
                	String pathStr = p.getPath();
                	if (!pathStr.equals("")) {
                		System.out.println(pathStr);
                	}
                }
            } catch (NumberFormatException e) {
            	e.printStackTrace();
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
    }
}
