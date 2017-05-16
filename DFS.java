import java.util.ArrayList;
import java.util.Stack;

public class DFS implements Algorithm {
	
	private Stack<Node> s;
	private ArrayList<String> visited;
	private Boolean found = false;
	private Boolean running = true;
	private int iterations = 0;
	Node current;
	private Settings settings;
	
	/**DFS
	 * is the implementation of the depth first search algorithm. It has 3 main methods.
	 * getNextStep, getSolution and step.
	 * @param settings = the settings object from GUI
	 */
	public DFS(Settings settings) {
		this.settings = settings;
	}
	
	@Override
	public void initialize() {
		iterations = 0;
		s = new Stack<Node>();
		visited = new ArrayList<String>();
		new ArrayList<Node>();
		int[][] matrix = settings.getMaze().getMatrix();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				if(matrix[i][j]==2) {
					int[] temp = {i, j};
					s.push(new Node(temp,null));
				}
			}
		}
		found = false;
	}

	
	/**getNextStep
	 * is a method that calculates the next step in the algorithm.
	 * @return solution = a matrix with the results of the next step
	 */
	@Override
	public int[][] getNextStep() {
		step();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		ArrayList<String> path = new ArrayList<String>();
		Node parent = current.getParent();
		int[] current_node = current.getNode();
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
			solution[current_node[0]][current_node[1]] = 7;
		}
		
		if(s.isEmpty()) {
			running = false;
		}
		
		return solution;
	}
	
	/**getSolution
	 * is a method that calculates all steps to the end of the algrithm. This means a path has been found,
	 * or no path has been found but everything is searched.
	 * @return solution = a matrix with the end result
	 */
	@Override
	public int[][] getSolution() {
		while(!s.isEmpty()) {
			step();
		}
		
		running = false;
		
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		ArrayList<String> path = new ArrayList<String>();
		Node parent = current.getParent();
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
	

	/**Step
	 * calculates 1 iteration of the algorithm
	 */
	@Override
	public void step() {
		int[][] matrix = settings.getMaze().getMatrix();
		current = s.pop();
		String temp = current.getNode()[0]+" "+current.getNode()[1];
		if(!visited.contains(temp)){
			visited.add(temp);
			if(matrix[current.getNode()[0]][current.getNode()[1]]==3){
				found = true;
				s = new Stack<Node>();
			}
			else{
				ArrayList<int[]> n = neighbors(current.getNode());
				for(int i=0;i<n.size();i++) {
					int[] neighbor = n.get(i);
					s.push(new Node(neighbor,current));
				}
			}
		}
		matrix = null;
		iterations++;
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<int[]> neighbors(int[] node) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int[][] matrix = settings.getMaze().getMatrix();
		if(settings.getDiagonal()) {
			int[][] dirs = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {-1, -1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						if(!visited.contains(neighbor[0]+" "+neighbor[1])) {
							result.add(neighbor);
						}
					}
				}
			}
		} else {
			int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						if(!visited.contains(neighbor[0]+" "+neighbor[1])) {
							result.add(neighbor);
						}
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
