
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public final int box_X = 18;
	public final int box_Y = 18;
	public GUI(String title) {
		setTitle(title);
		setLayout(new FlowLayout(FlowLayout.LEFT));
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
		file.setMnemonic(KeyEvent.VK_C);
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
		
		/*
		 * Create Panels
		 */
		JPanel grid = new JPanel();
		//Border border = BorderFactory.createTitledBorder("Grid");
		//grid.setBorder(border);
		grid.setPreferredSize(new Dimension(700,700));
		grid.setLayout(new GridLayout(box_X,box_Y));
		for(int i=0;i<(box_X*box_Y);i++) {
			JLabel box = new JLabel();
			Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
			box.setBorder(blackLine);
			grid.add(box);
		}
		add(grid);
	}	
}
