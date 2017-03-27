
public class Settings {
	private int maze_x = 20;
	private int maze_y = 20;
	private Boolean diagonal = false;
	private String algorithm = "A*";
	private int speed = 1;
	private Maze maze;
<<<<<<< HEAD
	private int[] path;
	private pathAlgorithm p;
=======
	private String path = "";
>>>>>>> origin/master
	public Settings() {
		setMaze(new Maze(maze_x,maze_y));
	}
	
	public int getMaze_x() {
		return maze_x;
	}
	
	public void setMaze_x(int maze_x) {
		this.maze_x = maze_x;
		maze.setGridSize(maze_x, maze_y);
	}
	
	public int getMaze_y() {
		return maze_y;
	}
	
	public void setMaze_y(int maze_y) {
		this.maze_y = maze_y;
		maze.setGridSize(maze_x, maze_y);
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public Boolean getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(Boolean diagonal) {
		this.diagonal = diagonal;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
<<<<<<< HEAD
	public void solve(){
		path = p.solve();
=======
	public void printSettings() {
		System.out.println(maze_x+" "+maze_y);
		System.out.println(algorithm);
		System.out.println(diagonal);
		System.out.println(speed);
		System.out.println(path+"\n");
>>>>>>> origin/master
	}
}
