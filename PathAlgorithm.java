
public class PathAlgorithm {
	Settings settings;
	Algorithm a;
	public PathAlgorithm(Settings settings) {
		this.settings = settings;
		switch(settings.getAlgorithm()){
			case "Breadth-first search": 
				a = new BFS(settings);
				break;
			case "Dijkstra": 
				a = new Dijkstra(settings);
				break;
			default:
				a = new BFS(settings);
				break;
		}
	}
	
	public int[][] solve() {
		int [][] res = a.solve();
		//a.printMaze();
		return res;
	}
}
