package application;

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
	public char[][] solve() {
		char [][] path = new char[X_size][Y_size];
		int max = X_size*Y_size;
		int X_stop = getStopPos(maze.maze);
		int X_start = getStartPos(maze.maze);
		int[] visited = new int[max];
		adjMatrix = createAdjMatrix(maze.maze);
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
				break;
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
		System.out.println("\r\n");
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
 