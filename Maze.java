

public class Maze {
	int[][] maze;
	int X_size;
	int Y_size;
	public Maze(int X_size, int Y_size) {
		this.X_size = X_size;
		this.Y_size = Y_size;
		maze = new int[X_size][Y_size];
		initializeMaze();
	}
		
	public void initializeMaze() {
		for(int i=0;i<X_size;i++) {
			for(int j=0;j<Y_size;j++) {
				maze[i][j] = 0; //NO OBSTACLE
			}
		}
		maze[0][0] = 2; //START point
		maze[X_size-1][Y_size-1] = 3; //STOP point
	}
	
	public void addObstacle(int X,int Y) {
		maze[X][Y] = 1;
	}
	
	public void removeObstacle(int X,int Y) {
		maze[X][Y] = 0;
	}
	
	public int[][] getMatrix() {
		return maze;
	}
	
	public void moveStartPoint(int X_old, int Y_old, int X_new, int Y_new) {
		maze[X_old][Y_old] = 0;
		maze[X_new][Y_new] = 2;
	}
	
	public void moveStopPoint(int X_old, int Y_old, int X_new, int Y_new) {
		maze[X_old][Y_old] = 0;
		maze[X_new][Y_new] = 3;
	}
	
	public void setGridSize(int X_newsize, int Y_newsize) {
		this.X_size = X_newsize;
		this.Y_size = Y_newsize;
		int[][] newMaze = new int[X_newsize][Y_newsize];
		maze = newMaze;
		initializeMaze();
	}
	
	public void printMaze() {
		for(int i=0;i<X_size;i++) {
			for(int j=0;j<Y_size;j++) {
				System.out.print(maze[i][j]);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}