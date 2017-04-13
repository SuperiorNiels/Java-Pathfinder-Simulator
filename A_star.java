
public class A_star implements Algorithm {
	Maze maze;
	int X_size;
	int Y_size;
	
	public A_star(Maze maze) {
		this.maze = maze;
		this.X_size = maze.maze[0].length;
		this.Y_size = maze.maze[1].length;
	}

	@Override
	public int[][] solve(Boolean diagonal) {
		int [][] path = new int [X_size*Y_size][X_size*Y_size];
		
		
		return path;
	}

	
}
