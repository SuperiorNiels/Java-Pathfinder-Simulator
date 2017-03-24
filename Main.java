import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static void main(String[] args) {
		try {
	        UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} 
	    catch (UnsupportedLookAndFeelException e) {}
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}
		
		GUI window = new GUI("Pathfinder Simulator");
		window.setBounds(30,30,1000,800);
		window.setVisible(true);

	}

}
