package application;


public class pathAlgorithm {
	char[][] path;
	Maze maze;
	String algorithm;
	Algorithm a;
	public pathAlgorithm(Maze maze, String algorithm) {
		this.maze = maze;
		this.algorithm = algorithm;
		switch(algorithm){
		case "Breadth-first search": a = new BFS(maze);
		}
	}
	
	public void solve(){
		a.solve();
		a.printMaze();
	}
	

}
