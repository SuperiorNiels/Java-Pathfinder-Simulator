
public class Settings {
	private int maze_x = 80;
	private int maze_y = 80;
	private Boolean diagonal = false;
	private String algorithm = "A*";
	private Maze maze;
	public Settings() {
		setMaze(new Maze(maze_x,maze_y));
	}
	
	public int getMaze_x() {
		return maze_x;
	}
	
	public void setMaze_x(int maze_x) {
		this.maze_x = maze_x;
	}
	
	public int getMaze_y() {
		return maze_y;
	}
	
	public void setMaze_y(int maze_y) {
		this.maze_y = maze_y;
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
}
