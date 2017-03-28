
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {
	Maze maze;
	int X_size;
	int Y_size;
	int[][] adjMatrix;
	public BFS(Maze maze) {
		this.maze = maze;
		this.X_size = maze.maze[0].length;
		this.Y_size = maze.maze[1].length;
	}

	@Override
	public int[][] solve() {
		int max = X_size*Y_size;
		int[] pathArray = new int[max];
		int X_stop = getStopPos(maze.maze);
		int X_start = getStartPos(maze.maze);
		int[] visited = new int[max];
		int[] previous = new int[max];
		adjMatrix = createAdjMatrix(maze.maze);
		
		/*BFS algorithm
		 * This will place a vertex in the queue en mark it as visited
		 * then check its neighbourghs and place the unvisited ones in the queue.
		 * Then take the next item from the queue(remove) and do algorithm again
		 * This until the queue is empty
		 * 
		 */
		Queue<Integer> bfsq = new LinkedList<Integer>();
		bfsq.add(X_start);
		while(bfsq.peek()!=null){
			int current = bfsq.remove();
			if(visited[current]==1){
				continue;
			}
			if(visited[current]==0){
				visited[current] = 1;
			}
			if(current==X_stop){
				//previous[X_stop] = X_stop;
				break;
			}
			for(int j=0;j<adjMatrix[0].length;j++){
				
				if(adjMatrix[current][j]==1 && j!=current){
					if(visited[j]==0){
						bfsq.add(j);
						previous[j] = current;
						System.out.print(" "+j);
					}
					
				}
			}
			
		}
		
		/*Path array
		 * Create path array backtracking method: from dest to source
		 * in path you will find the number of the thile wich to go to next
		 */
		int j = X_stop;
		pathArray[0]=X_stop;
		for(int i=1;i<previous.length;i++){
			int next = previous[j];
			pathArray[i] = next;
			j = next;
		}
		int [][] path = toMatrix(pathArray);
		//Debugging 
		System.out.println();
		for(int i=0;i<previous.length;i++){
			System.out.print(i);
		}System.out.println();
		for(int i=0;i<previous.length;i++){
			System.out.print(visited[i]);
		}System.out.println();
		for(int i=0;i<previous.length;i++){
			System.out.print(previous[i]);
		}System.out.println();
		for(int i=0;i<previous.length;i++){
			System.out.print(pathArray[i]+" ");
		}
		System.out.println("\r\n");
		for(int i=0;i<X_size;i++) {
			for(int k=0;k<X_size;k++) {
				System.out.print(path[i][k]);
			}
			System.out.print("\n");
		}
		return path;
		
	}
	
	
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
 