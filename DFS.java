import java.util.ArrayList;

public class DFS implements Algorithm {
	
	private int iterations = 0;
	
	private Settings settings;
	public DFS(Settings settings) {
		this.settings = settings;
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int[][] getNextStep() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int[][] getSolution() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean solved() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getIterations() {
		return iterations;
	}
	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<int[]> neighbors(int[] node) {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int[][] matrix = settings.getMaze().getMatrix();
		if(settings.getDiagonal()) {
			int[][] dirs = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {-1, -1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						result.add(neighbor);
					}
				}
			}
		} else {
			int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
			for(int[] dir : dirs) {
				int[] neighbor = {node[0] + dir[0], node[1] + dir[1]};
				if(neighbor[0] >= 0 && neighbor[0] < settings.getMaze_x() && neighbor[1] >= 0 && neighbor[1] < settings.getMaze_y()) {
					if(matrix[neighbor[0]][neighbor[1]]==0 || matrix[neighbor[0]][neighbor[1]]==3) {
						result.add(neighbor);
					}
				}
			}
		}
		return result;
	}

}
