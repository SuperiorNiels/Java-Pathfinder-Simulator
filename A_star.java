
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
	public void printMaze() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[][] solve(Boolean diagonal) {
		// TODO Auto-generated method stub
		return null;
	}

}
