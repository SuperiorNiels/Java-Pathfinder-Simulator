
public interface Algorithm {
	public void initialize();
	public int[][] getNextStep();
	public int[][] getSolution();
	public void step();
	public Boolean solved();
	public int getIterations();
	public void dispose();
	public Boolean running();
}
