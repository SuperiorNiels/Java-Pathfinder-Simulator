import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {
	Maze maze;
	int X_size;
	int Y_size;
	int[][] adjMatrix;
	public BFS(Maze maze, int X, int Y) {
		this.maze = maze;
		this.X_size = X;
		this.Y_size = Y;
	}

	@Override
	public char[][] solve() {
		char [][] path = new char[X_size][Y_size];
		int max = X_size*Y_size;
		int X_stop = 0;
		int X_start = 0;
		int[] visited = new int[max];
		int[] mazeArray = toArray(maze.maze);
		for(int i=0;i<mazeArray.length;i++){
			if(mazeArray[i]==2){X_start = i;}
			if(mazeArray[i]==3){X_stop = i;}
		}
		adjMatrix = new int[max][max];
		Queue<Integer> bfsq = new LinkedList<Integer>();
		
		//De for lus hieronder maakt de adj matrix
		for(int i=0;i<mazeArray.length;i++) {			
			if(i<max-X_size){
				if(mazeArray[i+X_size]==0 || mazeArray[i+X_size]==2 || mazeArray[i+X_size]==3){
					adjMatrix[i][i+X_size]=1;
				}
			}
			if(i>=X_size){
				if(mazeArray[i-X_size]==0 || mazeArray[i-X_size]==2 || mazeArray[i-X_size]==3){
					adjMatrix[i][i-X_size]=1;
				}	
			}
			if(i!=0 && (i % X_size) !=0){
				if(mazeArray[i-1]==0 || mazeArray[i-1]==2 || mazeArray[i-1]==3){
					adjMatrix[i][i-1]=1;
				}
			}
			if(i!=(mazeArray.length-1) && (i % X_size)==0){
				if(mazeArray[i+1]==0 || mazeArray[i+1]==2 || mazeArray[i+1]==3){
					adjMatrix[i][i+1]=1;
				}
			}
		}
		
		//Hieronder staat het eigenlijke algoritme om de weg te zoeken
		bfsq.add(X_start);
		while(bfsq.peek()!=null){
			int current = bfsq.remove();
			if(visited[current]==1){
				continue;
			}
			if(visited[current]==0){
				visited[current] = 1;
			}
			for(int j=0;j<adjMatrix[0].length;j++){
				
				if(adjMatrix[j][current]==1 && j!=current){
					if(visited[j]==0){
						bfsq.add(j);
						System.out.print(" "+j);
					}
				}
			}
		}
		
		System.out.println();
		for(int i=0;i<visited.length;i++){
			System.out.print(visited[i]);
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
 