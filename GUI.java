
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	public GUI(String title) {
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/*
		 * Menu Action Listeners
		 */
		ActionListener quitAction = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose(); 
			}
		};		
		
		/*
		 * Add Menu Bar
		 */
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem("New"));
		file.add(new JMenuItem("Open"));
		file.add(new JMenuItem("Save"));
		file.add(new JMenuItem("Save as..."));
		file.add(new JSeparator());
		// Quit button + action 
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(quitAction);
		file.add(quit);
		JMenu settings = new JMenu("Settings");
		settings.add(new JMenuItem("Grid width"));
		settings.add(new JMenuItem("Grid height"));
		menu.add(file);
		menu.add(settings);
		setJMenuBar(menu);		
	}	
}
