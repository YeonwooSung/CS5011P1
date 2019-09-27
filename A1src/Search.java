import java.util.Queue;
import java.util.ArrayList;

public class Search {
	private final int ANGLE_UNIT = 45;

	protected int numOfParallels;
	private boolean[][] visited;

	Search(int numOfParallels) {
		this.setNumOfParalles(numOfParallels);
		visited = new boolean[numOfParallels - 1][8];
	}

	public void setNumOfParalles(int numOfParallels) {
		this.numOfParallels = numOfParallels;
	}

	public int getNumOfParallels() {
		return numOfParallels;
	}

	public void visit(int angle, int distance) {
		int target = angle / ANGLE_UNIT;
		visited[distance - 1][target] = true;
	}

	public boolean checkIfVisited(int angle, int distance) {
		int target = angle / ANGLE_UNIT;
		return visited[distance - 1] [target];
	}

	public boolean checkIfVisited(int angle, int distance, boolean[][] visited) {
		int target = angle / ANGLE_UNIT;
		return visited[distance - 1] [target];
	}

	public void insertIntoQueue(Queue<PolarCoordinate> queue, ArrayList<PolarCoordinate> list) {
		while (!list.isEmpty()) {
			PolarCoordinate pc = list.get(0);
			queue.add(pc);
			list.remove(0);
		}
	}

	public void printOutListOfCoordinates(ArrayList<PolarCoordinate> list) {
		System.out.println("Expanded nodes: ");
		for (int i = 0; i < list.size(); i++) {
			PolarCoordinate p = list.get(i);
			int index = i + 1;
			System.out.print("	Node" + index + " : Coordinate = " + p.getDistance() + ", " + p.getAngle());
			System.out.println(" & Path = " + p.getPath());
		}
		System.out.println();
	}
}
