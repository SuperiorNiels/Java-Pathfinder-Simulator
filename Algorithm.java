
public interface Algorithm {
	/**Algorithm
	 * is an interface. It has several methods that needs to been overridden
	 * so every path algorithm can be implemented and respond to different actions.
	 * @category Interface
	 */
	public void initialize();
	public int[][] getNextStep();
	public int[][] getSolution();
	public void step();
	public Boolean solved();
	public int getIterations();
	public void dispose();
	public Boolean running();
}
