import java.util.ArrayList;
import java.util.LinkedList;

public class BFS implements Algorithm {
	
	private LinkedList<Node> fifo;
	private ArrayList<String> visited;
	private ArrayList<Node> raw_path;
	private Boolean found = false;
	private Boolean running = true;
	private int iterations = 0;
	Node current_node;
	
	private Settings settings;
	
	/**
	 * @name BFS
	 * To get a solution of a maze with the BFS algorithm, you first have to run the
	 * initialize() function. This function will make sure the calculations start at
	 * the current starting point and will clear all the lists.
	 * To get a step by step solution, the function getNextStep() can be used for a solution
	 * of each step.
	 * To get the complete solution at once, you must use the function getSolution().
	 * Both these functions return a matrix (with the size of the current maze) with the following information:
	 * 		5: path (yellow)
	 * 		6: visited (light_gray)
	 * 		7: current node (dark_gray)
	 * 		0: nothing
	 * @param settings
	 */
	public BFS(Settings settings) {
		this.settings = settings;
	}

	@Override
	public void initialize() {
		iterations = 0;
		fifo = new LinkedList<Node>();
		visited = new ArrayList<String>();
		raw_path = new ArrayList<Node>();
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
		found = false;		
	}


	@Override
	public int[][] getNextStep() {
		step();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		ArrayList<String> path = new ArrayList<String>();
		Node parent = current_node.getParent();
		int[] current = current_node.getNode();
		while(parent!=null) {
			int[] point = parent.getNode();
			path.add(point[0]+" "+point[1]);
			parent = parent.getParent();
		}
		
		int[][] solution = new int[x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				String temp = i+" "+j;
				if(path.contains(temp) && found) {
					solution[i][j] = 5;
				} else if(visited.contains(temp)){
					solution[i][j] = 6;
				} else {
					solution[i][j] = 0;
				}
			}
		}
		
		if(!found) {
			solution[current[0]][current[1]] = 7;
		}
		
		if(fifo.isEmpty()) {
			running = false;
		}
		
		return solution;
	}


	@Override
	public int[][] getSolution() {
		
		while(!fifo.isEmpty()) {
			step();
		}
		
		running = false;
		
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		ArrayList<String> path = new ArrayList<String>();
		Node parent = current_node.getParent();
		while(parent!=null) {
			int[] point = parent.getNode();
			path.add(point[0]+" "+point[1]);
			parent = parent.getParent();
		}
		
		int[][] solution = new int[x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				String temp = i+" "+j;
				if(path.contains(temp) && found) {
					solution[i][j] = 5;
				} else if(visited.contains(temp)){
					solution[i][j] = 6;
				} else {
					solution[i][j] = 0;
				}
			}
		}
		
		return solution;
	}


	@Override
	public Boolean solved() {
		return found;
	}


	@Override
	public int getIterations() {
		return iterations;
	}


	@Override
	public void dispose() {
		fifo = null;
		visited = null;
		raw_path = null;
	}
	
	public void step() {
		int[][] matrix = settings.getMaze().getMatrix();
		current_node = fifo.remove();
		int[] current = current_node.getNode();
		String to_add = current[0]+" "+current[1];
			if(!visited.contains(to_add)) {
			visited.add(to_add);
			raw_path.add(current_node);
			if(matrix[current[0]][current[1]]==3) {
				found = true;
				fifo = new LinkedList<Node>();
			} else {
				ArrayList<int[]> n = neighbors(current);
				for(int i=0;i<n.size();i++) {
					int[] neighbor = n.get(i);
					fifo.add(new Node(neighbor,current_node));
				}
			}
		}
		matrix = null;
		iterations++;
	}

	public ArrayList<int[]> neighbors(int[] node) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int[][] matrix = settings.getMaze().getMatrix();
		if(settings.getDiagonal()) {
			int[][] dirs = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						result.add(neighbor);
					}
				}
			}
		} else {
			int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
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

	@Override
	public Boolean running() {
		return running;	
	}

}
 