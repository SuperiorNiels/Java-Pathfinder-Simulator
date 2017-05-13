import java.util.ArrayList;
import java.util.HashMap;

public class Dijkstra implements Algorithm {
	
	
	private Settings settings;
	private ArrayList<String> visited;
	private int[][] distance;
	private HashMap<String, int[]> prev;
	private Boolean found = false;
	private Boolean done = false;
	private int iterations = 0;
	int[] current;
	private Boolean running = true;
	
	private final int INF = 99999;
	
	public Dijkstra(Settings settings) {
		this.settings = settings;
	}
	
	@Override
	public void initialize() {
		iterations = 0;
		current = new int[2];
		prev = new HashMap<String, int[]>();
		visited = new ArrayList<String>();
		int[][] matrix = settings.getMaze().getMatrix();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		distance = new int [x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				if(matrix[i][j]==2) {
					int[] temp = {i, j};
					prev.put(temp[0]+" "+temp[1],null);
					distance[i][j] = 0; //starting point
				}
				else {
					distance[i][j] = INF; //To represent infinity
				}
			}
		}		
	}

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
		
		return solution;
	}

	@Override
	public int[][] getSolution() {
		
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		int[][] solution = new int[x][y];
		int[][] matrix = settings.getMaze().getMatrix();
		ArrayList<String> path = new ArrayList<String>();
		double t0 = System.nanoTime();
		while(!done) {
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
		
		return solution;
	}

	@Override
	public void step() {
		if(!done){
			int[][] matrix = settings.getMaze().getMatrix();
			current = findShortest();
			if(visited.contains(current[0]+" "+current[1])){
				done = true;
			}
			visited.add(current[0]+" "+current[1]);
			if(matrix[current[0]][current[1]]==3) {
				found = true;
				done = true;
			}
			ArrayList<int[]> n = neighbors(current);
			for(int i=0;i<n.size();i++) {
				int[] neighbor = n.get(i);
				int alt = distance[current[0]][current[1]]+1;
				if(alt < distance[neighbor[0]][neighbor[1]]) {
					distance[neighbor[0]][neighbor[1]] = alt;
					prev.put(neighbor[0]+" "+neighbor[1], current);
				}
			}
			matrix = null;
			iterations++;
		}
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
		
		
	}
	
	public int[] findShortest() {
		int [] shortest = new int[2];
		int Svalue = INF;
		for(int i=0;i<distance.length;i++) {
			for(int j=0;j<distance[0].length;j++) {
				int[] temp = {i, j};
				if(Svalue>distance[i][j] && !visited.contains(temp[0]+" "+temp[1])) {
					Svalue = distance[i][j];
					shortest[0] = i;
					shortest[1] = j;
				}
			}
		}
		return shortest;
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
			int[][] dirs = {{0, 1},{0, -1} , {1, 0}, {-1, 0}};
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