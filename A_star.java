import java.util.ArrayList;
import java.util.HashMap;

public class A_star implements Algorithm {
	
	private Settings settings;
	private HashMap<String, int[]> open;
	private HashMap<String, int[]> closed;
	private int[][] gScore;
	private double[][] fScore;
	private HashMap<String, int[]> prev;
	private Boolean found = false;
	private int iterations = 0;
	int[] current,start,stop;
	private Boolean running = true;
	
	private final int INF = 99999;
	/**A_star
	 * is the implementation of the A_star algorithm. It has 3 main methods.
	 * getNextStep, getSolution and step.
	 * @param settings = the settings object from GUI
	 */
	public A_star(Settings settings) {
		this.settings = settings;
	}

	@Override
	public void initialize() {
		iterations = 0;
		current = new int[2];
		start = new int[2];
		stop = new int[2];
		prev = new HashMap<String, int[]>();
		open = new HashMap<String, int[]>();
		closed = new HashMap<String, int[]>();
		int[][] matrix = settings.getMaze().getMatrix();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		gScore = new int[x][y];
		fScore = new double[x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				if(matrix[i][j]==2) {
					start[0] = i;
					start[1] = j;
					prev.put(start[0]+" "+start[1],null);
					gScore[i][j] = 0; //starting point
					open.put(start[0]+" "+start[1],start);
				}
				else if(matrix[i][j]==3){
					stop[0] = i;
					stop[1] = j;
					gScore[i][j] = INF;
					fScore[i][j] = 0;
				}
				else {
					gScore[i][j] = INF; //To represent infinity
					fScore[i][j] = INF;
				}
			}
		}
		fScore[start[0]][start[1]] = heuristic_cost_estimate(start,stop);
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
		int[][] matrix = settings.getMaze().getMatrix();
		ArrayList<String> path = new ArrayList<String>();
		if(found) {
			int[] parent = null;
			for(int i=0;i<x;i++) {
				for(int j=0;j<y;j++) {
					if(matrix[i][j]==3) {
						parent = prev.get(i+" "+j);
					}
				}
			}
			while(parent!=null) {
				path.add(parent[0]+" "+parent[1]);
				parent = prev.get(parent[0]+" "+parent[1]);
			}
		}
		
		int[][] solution = new int[x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				String temp = i+" "+j;
				if(path.contains(temp) && found) {
					solution[i][j] = 5;
				} else if(closed.containsKey(temp)){
					solution[i][j] = 6;
				} else {
					solution[i][j] = 0;
				}
			}
		}
		
		if(!found) {
			solution[current[0]][current[1]] = 7;
		}
		
		if(found || open.isEmpty()){
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
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		int[][] solution = new int[x][y];
		int[][] matrix = settings.getMaze().getMatrix();
		ArrayList<String> path = new ArrayList<String>();
		double t0 = System.nanoTime();
		while(!found && !open.isEmpty()) {
			step();
		}
		running = false;
		double t1 = System.nanoTime();
		System.out.println(t1-t0);
		t0 = System.nanoTime();
		if(found) {
			int[] parent = null;
			for(int i=0;i<x;i++) {
				for(int j=0;j<y;j++) {
					if(matrix[i][j]==3) {
						parent = prev.get(i+" "+j);
					}
				}
			}
			while(parent!=null) {
				path.add(parent[0]+" "+parent[1]);
				parent = prev.get(parent[0]+" "+parent[1]);
			}
		}
		t1 = System.nanoTime();
		System.out.println(t1-t0);
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				String temp = i+" "+j;
				if(path.contains(temp) && found) {
					solution[i][j] = 5;
				} else if(closed.containsKey(temp)){
					solution[i][j] = 6;
				} else {
					solution[i][j] = 0;
				}
			}
		}
		
		if(!found) {
			solution[current[0]][current[1]] = 7;
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
		// TODO Auto-generated method stub
		
	}

	/**Step
	 * calculates 1 iteration of the algorithm
	 */
	@Override
	public void step() {
		current  = findLowest();
		if(current[0]==stop[0] && current[1]==stop[1]){
			found = true;
		}
		open.remove(current[0]+" "+current[1]);
		closed.put(current[0]+" "+current[1],current);
		
		ArrayList<int[]> n = neighbors(current);
		for(int i=0;i<n.size();i++){
			int [] neighbor = n.get(i);
			if(closed.containsKey(neighbor[0]+" "+neighbor[1]))
				continue;
			int tentative_gScore = gScore[current[0]][current[1]] + node_to_node_cost(current,neighbor);
			if(!open.containsKey(neighbor[0]+" "+neighbor[1]))
				open.put(neighbor[0]+" "+neighbor[1],neighbor);
			else if(tentative_gScore>=gScore[current[0]][current[1]])
				continue;
			prev.put(neighbor[0]+" "+neighbor[1], current);
			gScore[neighbor[0]][neighbor[1]] = tentative_gScore;
			fScore[neighbor[0]][neighbor[1]] = gScore[neighbor[0]][neighbor[1]] + heuristic_cost_estimate(neighbor,stop);
		}
		iterations++;
	}
	
	public int[] findLowest() {
		int [] shortest = new int[2];
		double Svalue = INF;
		for(int i=0;i<fScore.length;i++) {
			for(int j=0;j<fScore[0].length;j++) {
				int[] temp = {i, j};
				if(Svalue>fScore[i][j] && open.containsKey(temp[0]+" "+temp[1])) {
					Svalue = fScore[i][j];
					shortest[0] = i;
					shortest[1] = j;
				}
			}
		}
		return shortest;
	}
	
	public double heuristic_cost_estimate(int[] start, int[] stop){
		double value = Math.sqrt(Math.pow(start[0]-stop[0],2)+Math.pow(start[1]-stop[1],2));
		return value;
	}
	
	public int node_to_node_cost(int[] start, int[] stop) {
		int value = 1;
		int[][] dirs = {{1, -1}, {-1, -1}, {-1, 1}, {1, 1}};
		for(int[] dir : dirs) {
			if(start[0]+dir[0]==stop[0] && start[1]+dir[1]==stop[1]) {
				value = 1;
				break;
			}
		}
		return value;
	}
	
	public ArrayList<int[]> neighbors(int[] node) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int[][] matrix = settings.getMaze().getMatrix();
		if(settings.getDiagonal()) {
			int[][] dirs = {{-1, 0}, {1, -1}, {0, -1}, {1, 0}, {-1, -1}, {-1, 1},{1, 1} , {0, 1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						result.add(neighbor);
					}
				}
			}
		} else {
			int[][] dirs = {{0, 1},{1, 0} , {0, -1}, {-1, 0}};
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
