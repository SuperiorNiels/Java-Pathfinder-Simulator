
public class Dijkstra implements Algorithm {
	Maze maze;
	int X_size;
	int Y_size;
	int[][] adjMatrix;
	Boolean found = false;
	public Dijkstra(Maze maze) {
		this.maze = maze;
		this.X_size = maze.maze[0].length;
		this.Y_size = maze.maze[1].length;
	}
	
		
	@Override
	public int[][] solve(Boolean diagonal) {
		int length = X_size*Y_size;
		int[] best = new int[length];
		int[] previous = new int[length];
		int max = 10000; //to represent infinity
		boolean[] visited = new boolean[length];
		Boolean found = false;
		int X_stop = getStopPos(maze.maze);
		int X_start = getStartPos(maze.maze);
		adjMatrix = createAdjMatrix(maze.maze,diagonal);
		
		for(int i=0;i<length;i++) {
			best[i] = max;
			visited[i] = false;
		}
		best[X_start] = 0;
		
		for(int i=0;i<length;i++) {
			int min = max;
			int currentNode = 0;
			for(int j=0;j<length;j++) {
				if(!visited[j] && best[j] <= min) {
					currentNode = j;
					min = best[j];
				}
			}
			visited[currentNode] = true;
			if(currentNode==X_stop) {
				found = true;
			}
			for(int j=0;j<length;j++) {
				if(adjMatrix[currentNode][j] > 0 && best[currentNode] + adjMatrix[currentNode][j] < best[j]) {
					best[j] = best[currentNode] + adjMatrix[currentNode][j];
					previous[j]=currentNode;
				}
			}
		}

		
		int[] pathArray = new int[length];
		if(found){
			int j = X_stop;
			pathArray[0]=X_stop;
			for(int i=1;i<previous.length;i++){
				int next = previous[j];
				pathArray[i] = next;
				if(next==X_start){
					break;
				}
				j = next;
			}
		}
		int [][] path = toMatrix(pathArray,5,X_size);
		
		return path;
	}

	@Override
	public void printMaze() {
		int max = X_size*Y_size;
		for(int i=0;i<max;i++) {
			for(int j=0;j<max;j++) {
				System.out.print(adjMatrix[i][j]);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}	
}
