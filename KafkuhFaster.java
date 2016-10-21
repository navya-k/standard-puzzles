 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Node {
	private String id;
	private final List<Node> children = new ArrayList<>();
	private final Node parent;

	public Node(Node parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Node> getChildren() {
		return children;
	}

	public Node getParent() {
		return parent;
	}

}

public class KafkuhFaster {

	public static void main(String[] args) {

		//		int[][] data = {{1, 1, 1},
		//				{100, 100, 20},
		//				{1, 1, 2},
		//				{101, 101, 4},
		//				{1, 1, 4},
		//				{100, 100, 30},
		//				{1, 1, 8}};

		int[][] data = {{3975, 2710, 33},
				{2035, 1842, 50},
				{2037, 1846, 41},
				{97, 69, 47},
				{3974, 2710, 16},
				{99, 72, 38},
				{3972, 2713, 27},
				{3974, 2710, 13},{96, 68, 50},
				{3973, 2713, 9},
				{3972, 2710, 50},
				{3974, 2714, 20},
				{3973, 2714, 40},
				{3972, 2712, 47}};

		Arrays.sort(data, new Comparator<int[]>() {
			@Override
			public int compare(final int[] entry1, final int[] entry2) {
				final int radius1 = entry1[2];
				final int radius2 = entry2[2];
				//				return Integer.compare(radius2 ,radius1);
				return Integer.compare(radius1 ,radius2);
			}
		});  

	}

	private static int CircleInsideCircle(int[] c1, int[] c2){
		if(Distance(c1[0],c1[1],c2[0],c2[1]) <= (c2[2] - c1[2])){
			return 1;
		}
		return 0;
	}

	private static double Distance(int x1, int y1, int x2, int y2){

		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
}
