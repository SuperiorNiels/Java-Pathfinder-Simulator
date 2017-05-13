
public class PathAlgorithm {
	Settings settings;
	Algorithm a;
	/**PathAlgoritm
	 * is the class that selects the algorithm. It connects the GUI with the algorithm interface.
	 * @param settings
	 */
	
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
			case "DFS":
				a = new DFS(settings);
				break;
			case "Greedy Best-first search":
				a = new Greedy(settings);
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
