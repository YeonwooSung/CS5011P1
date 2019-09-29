import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BidirectionalSearch extends Search {
	private final int ANGLE_UNIT = 45;

	private boolean[][] visited1;
	private boolean[][] visited2;
	private ArrayList<PolarCoordinate> list1 = new ArrayList<>();
	private ArrayList<PolarCoordinate> list2 = new ArrayList<>();

	BidirectionalSearch(int numOfParallels) {
		super(numOfParallels);
		visited1 = new boolean[numOfParallels - 1][8];
		visited2 = new boolean[numOfParallels - 1][8];
	}

	public void search() {
		Queue<PolarCoordinate> queue = new LinkedList<PolarCoordinate>();
		queue.add(A1main.starting);
		Queue<PolarCoordinate> reverseQueue = new LinkedList<PolarCoordinate>();
		reverseQueue.add(A1main.goal);

		boolean checker1 = false;
		boolean checker2 = false;

		while (true) {
			if (queue.isEmpty()) {
				checker1 = true;
				break;
			} else {
				if (checkAndExpandNodes(queue, 1, 2)) {
					break;
				}
			}

			if (reverseQueue.isEmpty()) {
				checker2 = true;
				break;
			} else {
				if (checkAndExpandNodes(reverseQueue, 2, 1)) {
					break;
				}
			}
		}

		if (checker1) {
			System.out.println("TEST1");
			//TODO queue is the path
		} else if (checker2) {
			System.out.println("TEST2");
			//TODO reverseQueue is the path
		} else {
			//TODO merge 1 & 2
		}

		//TODO
		for (PolarCoordinate node : list1) {
			System.out.println(node.getAngle() + ", " + node.getDistance());
		}
		System.out.println();

		for (PolarCoordinate node : list2) {
			System.out.println(node.getAngle() + ", " + node.getDistance());
		}
	}

	private void appendExpandedNodesToQueue(Queue<PolarCoordinate> queue, PolarCoordinate node) {
		ArrayList<PolarCoordinate> list = node.getListOfNextCoordinates(numOfParallels);
		super.insertIntoQueue(queue, list);
	}

	public boolean checkAndExpandNodes(Queue<PolarCoordinate> queue, int n, int m) {
		PolarCoordinate node = queue.poll();
		int angle = node.getAngle();
		int distance = node.getDistance();

		if (checkIfVisited(angle, distance, n)) {
			return false;
		}

		visit(angle, distance, n);

		//TODO
		if (n == 1) {
			list1.add(node);
		} else {
			list2.add(node);
		}

		if (checkIfVisited(angle, distance, m)) {
			return true;
		} else {
			appendExpandedNodesToQueue(queue, node);
		}

		return false;
	}

	private void visit(int angle, int distance, int n) {
		int target = angle / ANGLE_UNIT;

		switch (n) {
			case 1:
				visited1[distance - 1][target] = true;
				break;
			case 2:
				visited2[distance - 1][target] = true;
				break;
		}
	}

	private boolean checkIfVisited(int angle, int distance, int n) {
		int target = angle / ANGLE_UNIT;

		if (n != 2) {
			return visited1[distance - 1] [target];
		}

		return visited2[distance - 1] [target];
	}
}
