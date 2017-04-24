
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
			case "Depth-first search": 
				a = new DFS(settings);
				break;
			case "A*":
				a = new A_star(settings);
				break;
			default:
				a = new BFS(settings);
				break;
		}
	}
	
	public int[][] getSolution() {
		return a.getSolution();
	}
	
	public int getIterations() {
		return a.getIterations();
	}
	
	public void intitialze() {
		a.initialize();
	}
	
	public int[][] getNextStep() {
		return a.getNextStep();
	}
	
	public Boolean solved() {
		return a.solved();
	}
	
	public Boolean running() {
		return a.running();
	}
	
}
