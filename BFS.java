import java.util.ArrayList;
import java.util.LinkedList;

public class BFS implements Algorithm {
	private Settings settings;
	public BFS(Settings settings) {
		this.settings = settings;
	}

	public int[][] solve() {
		LinkedList<Node> fifo = new LinkedList<Node>();
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<Node> raw_path = new ArrayList<Node>();
		int[][] matrix = settings.getMaze().getMatrix();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				if(matrix[i][j]==2) {
					int[] temp = {i, j};
					fifo.add(new Node(temp,null));
				}
			}
		}
		
		Boolean found = false;
		Node p = null;
		while(!fifo.isEmpty()) {
			p = fifo.remove();
			int[] current = p.getNode();
			String to_add = current[0]+" "+current[1];
 			if(!visited.contains(to_add)) {
				visited.add(to_add);
				raw_path.add(p);
				if(matrix[current[0]][current[1]]==3) {
					found = true;
					fifo = new LinkedList<Node>();
				} else {
					ArrayList<int[]> n = neighbors(current);
					for(int i=0;i<n.size();i++) {
						int[] neighbor = n.get(i);
						fifo.add(new Node(neighbor,p));
					}
				}
			}
		}
		
		ArrayList<String> path = new ArrayList<String>();
		if(found) {
			Node parent = p.getParent();
			while(parent!=null) {
				int[] point = parent.getNode();
				path.add(point[0]+" "+point[1]);
				parent = parent.getParent();
			}
		}
		
		int[][] final_path = new int[x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				String temp = i+" "+j;
				if(path.contains(temp)) {
					final_path[i][j] = 5;
				} else if(visited.contains(temp)){
					final_path[i][j] = 6;
				} else {
					final_path[i][j] = 0;
				}
			}
		}
		
		fifo = null;
		matrix = null;
		visited = null;
		raw_path = null;
		
//		for(int i=0;i<x;i++) {
//			for(int j=0;j<y;j++) {
//				System.out.print(final_path[i][j]);
//			}
//			System.out.print("\n");
//		}
		
		return final_path;
	}

	
	public ArrayList<int[]> neighbors(int[] node) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int[][] matrix = settings.getMaze().getMatrix();
		if(settings.getDiagonal()) {
			int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1},{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						result.add(neighbor);
					}
				}
			}
		} else {
			int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						result.add(neighbor);
					}
				}
			}
		}
		return result;
	}


	public void dispose() {}
	
}
 