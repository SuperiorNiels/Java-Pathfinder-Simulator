
public class PathAlgorithm {
	Settings settings;
	Algorithm a;
	public PathAlgorithm(Settings s) {
		this.settings = s;
		switch(settings.getAlgorithm()){
		case "Breadth-first search": a = new BFS(settings.getMaze());
		}
	}
	
	public int[][] solve() {
		int [][] res =a.solve(settings.getDiagonal());
		//a.printMaze();
		return res;
	}
}
