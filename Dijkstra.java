import java.util.ArrayList;

public class Dijkstra implements Algorithm {

	private Settings settings;
	private ArrayList<Node> unvisited;
	private ArrayList<String> visited;
	private int[][] distance;
	private ArrayList<Node> prev;
	private Boolean found = false;
	private int iterations = 0;
	Node current_node;
	
	public Dijkstra(Settings settings) {
		this.settings = settings;
	}
	
	@Override
	public void initialize() {
		iterations = 0;
		unvisited = new ArrayList<Node>();
		prev = new ArrayList<Node>();
		visited = new ArrayList<String>();
		int[][] matrix = settings.getMaze().getMatrix();
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		distance = new int [x][y];
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				if(matrix[i][j]==2) {
					int[] temp = {i, j};
					unvisited.add(new Node(temp,null));
					prev.add(new Node(temp,null));
					distance[i][j] = 0; //starting point
				}
				else {
					int[] temp = {i, j};
					unvisited.add(new Node(temp,null));
					distance[i][j] = 99999; //To represent infinity
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
			Node parent = null;
			for(int i=0;i<x;i++) {
				for(int j=0;j<y;j++) {
					if(matrix[i][j]==3) {
						parent = prev.get(findPrevNode(i,j));
					}
				}
			}
			while(parent.getParent()!=null) {
				int[] point = parent.getNode();
				path.add(point[0]+" "+point[1]);
				Node next = parent.getParent();
				if(next!=null)
					point = next.getNode();
				else parent = null;
				int index = findPrevNode(point[0],point[1]);
				parent = prev.get(index);
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
			int [] current = current_node.getNode();
			solution[current[0]][current[1]] = 7;
		}
		
		return solution;
	}

	@Override
	public int[][] getSolution() {
		int x = settings.getMaze_x();
		int y = settings.getMaze_y();
		int[][] matrix = settings.getMaze().getMatrix();
		while(!unvisited.isEmpty()) {
			step();
		}
		Node parent = null;
		ArrayList<String> path = new ArrayList<String>();
		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				if(matrix[i][j]==3) {
					parent = prev.get(findPrevNode(i,j));
				}
			}
		}
		while(parent.getParent()!=null) {
			int[] point = parent.getNode();
			path.add(point[0]+" "+point[1]);
			Node next = parent.getParent();
			if(next!=null)
				point = next.getNode();
			else parent = null;
			int index = findPrevNode(point[0],point[1]);
			parent = prev.get(index);
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
	public void step() {
		int[][] matrix = settings.getMaze().getMatrix();
		int[] current = findShortest();
		current_node = unvisited.get(findUnvisitedNode(current[0],current[1]));
		unvisited.remove(findUnvisitedNode(current[0],current[1]));
		visited.add(current[0]+" "+current[1]);
		//System.out.println(current[0]+" "+current[1]);
		if(matrix[current[0]][current[1]]==3) {
			found = true;
		} else {
			ArrayList<int[]> n = neighbors(current);
			for(int i=0;i<n.size();i++) {
				int[] neighbor = n.get(i);
				int alt = distance[current[0]][current[1]]+1;
				if(alt < distance[neighbor[0]][neighbor[1]]) {
					distance[neighbor[0]][neighbor[1]] = alt;
					Node nextNode = new Node(neighbor,current_node);
					prev.add(nextNode);
				}
			}
		}
		matrix = null;
		iterations++;
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
		int Svalue = 999999;
		for(int i=0;i<distance.length;i++) {
			for(int j=0;j<distance.length;j++) {
				int k = findUnvisitedNode(i,j);
				if(Svalue>distance[i][j] && k != -1) {
					Svalue = distance[i][j];
					shortest[0] = i;
					shortest[1] = j;
				}
			}
		}
		return shortest;
	}
	
	public int findUnvisitedNode(int i, int j) {
		int n = -1;
		for(int k=0;k<unvisited.size();k++) {
			int [] current = unvisited.get(k).getNode();
			if(current[0]==i && current[1]==j) {
				n = k;
				break;
			}
		}
		return n;
	}
	
	public int findPrevNode(int i, int j) {
		int n = -1;
		for(int k=0;k<prev.size();k++) {
			int [] current = prev.get(k).getNode();
			if(current[0]==i && current[1]==j) {
				n = k;
				break;
			}
		}
		return n;
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

	
}
