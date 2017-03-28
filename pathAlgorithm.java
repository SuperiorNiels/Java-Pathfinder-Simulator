
public class PathAlgorithm {
	Maze maze;
	String algorithm;
	Algorithm a;
	public PathAlgorithm(Maze maze, String algorithm) {
		this.maze = maze;
		this.algorithm = algorithm;
		switch(algorithm){
		case "Breadth-first search": a = new BFS(maze);
		}
	}
	
	public int[][] solve() {
		return  a.solve();
	}
}