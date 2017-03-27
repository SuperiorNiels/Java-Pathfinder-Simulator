package application;


public class A_star implements Algorithm {
	Maze maze;
	int X_size;
	int Y_size;
	
	public A_star(Maze maze, int X, int Y) {
		this.maze = maze;
		this.X_size = X;
		this.Y_size = Y;
	}

	@Override
	public char[][] solve() {
		char [][] path = new char[X_size][Y_size];
		
		
		return path;
	}

	@Override
	public void printMaze() {
		// TODO Auto-generated method stub
		
	}

}
