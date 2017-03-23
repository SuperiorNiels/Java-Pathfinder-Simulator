
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public final int box_X = 10;
	public final int box_Y = 10;
	public GUI(String title) {
		setTitle(title);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/*
		 * Menu Action Listeners
		 */
		ActionListener quitAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		/*
		 * Grid Actions
		 */
		MouseListener addObstacle = new MouseListener() {
			// Red and green can be removed!!!
			public void mouseClicked(MouseEvent e) {
				if(e.getComponent().getBackground()==Color.BLACK) {
					e.getComponent().setBackground(UIManager.getColor("Label.background"));
					// Remove obstacle from maze
				} else {
					e.getComponent().setBackground(Color.BLACK);
					// Add obstacle to Maze
				}
				
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		};
		
		/*
		 * Create Grid Panel
		 */
		JPanel grid = new JPanel();
		grid.addComponentListener(new ResizeListener());
		grid.setPreferredSize(new Dimension(700,700));
		grid.setLayout(new GridLayout(box_X,box_Y));
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		for(int i=0;i<(box_X*box_Y);i++) {
			JLabel box = new JLabel();
			box.setBorder(blackLine);
			box.setOpaque(true);
			if(i==0){box.setBackground(Color.GREEN);}
			if(i==(box_X*box_Y)-1){box.setBackground(Color.RED);}
			box.addMouseListener(addObstacle);
			grid.add(box);
		}
		add(grid,BorderLayout.WEST);
		
		/*
		 * Create Option Panel
		 */
		JPanel options = new JPanel(new BorderLayout());
		JPanel grid_options = new JPanel(new BorderLayout());
		Border grid_options_title = BorderFactory.createTitledBorder("Grid Options");
	    /*JLabel label = new JLabel("Name: ");
	    label.setDisplayedMnemonic(KeyEvent.VK_N);
	    JTextField textField = new JTextField();
	    label.setLabelFor(textField);
	    grid_options.add(label, BorderLayout.WEST);
	    grid_options.add(textField, BorderLayout.CENTER);*/
		grid_options.setBorder(grid_options_title);
		
		JPanel algorithm_options = new JPanel();
		Border algorithm_options_title = BorderFactory.createTitledBorder("Algorithm");
		algorithm_options.add(new JButton("button"));
		algorithm_options.setBorder(algorithm_options_title);
		
		options.add(algorithm_options,BorderLayout.NORTH);
		options.add(grid_options);
		add(options,BorderLayout.CENTER);
	}	
}

class ResizeListener extends ComponentAdapter {
    public void componentResized(ComponentEvent e) {
    	int height = e.getComponent().getSize().height;
        e.getComponent().setPreferredSize(new Dimension(height,height));
    }
}

