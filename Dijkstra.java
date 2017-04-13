
public class Dijkstra implements Algorithm {
	Maze maze;
	int X_size;
	int Y_size;
	int[][] adjMatrix;
	Boolean found = false;
	public Dijkstra(Maze maze) {
		this.maze = maze;
		this.X_size = maze.maze.length;
		this.Y_size = maze.maze[0].length;
	}
	
		
	@Override
	public int[][] solve(Boolean diagonal) {
		int length = X_size*Y_size;
		int[] best = new int[length];
		int[] previous = new int[length];
		int max = 10000; //to represent infinity
		int[] visited = new int[length];
		Boolean found = false;
		int X_stop = getStopPos(maze.maze);
		int X_start = getStartPos(maze.maze);
		adjMatrix = createAdjMatrix(maze.maze,diagonal);
		
		for(int i=0;i<length;i++) {
			best[i] = max;
			visited[i] = 0;
		}
		best[X_start] = 0;
		
		for(int i=0;i<length;i++) {
			int min = max;
			int currentNode = 0;
			for(int j=0;j<length;j++) {
				if(visited[j]!=1 && best[j] <= min) {
					currentNode = j;
					min = best[j];
				}
			}
			visited[currentNode] = 1;
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

	public void printMaze(int current, int[] visited) {
		int[][] visitedMat = toMatrix(visited,8,X_size);
	}
	
	@Override
	public void dispose() {
		try {
			this.finalize();
			maze = null;
			adjMatrix = null;
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	
}
