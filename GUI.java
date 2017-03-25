
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private String title;
	private Settings settings = new Settings();
	public GUI(String title) {
		this.title = title;
	}
	
	public void createGUI() {
		setTitle(title);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/*
		 * Menu Action Listeners
		 */
		ActionListener newAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				GUI window = new GUI("Pathfinder Simulator");
				window.createGUI();
			}
		};
		ActionListener openAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          System.out.println(selectedFile.getName());
		        }
			}
		};
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
		JMenuItem _new = new JMenuItem("New");
		_new.addActionListener(newAction);
		file.add(_new);
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(openAction);
		file.add(open);
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
		 * Create Grid Panel
		 */
		addGrid();
				
		/*
		 * Create Option Panel
		 */
		JPanel options = new JPanel(new GridLayout(3,0));
		
		// ALGORITHM OPTIONS
		JPanel algorithm_options = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel algorithm_buttons = new JPanel(new GridLayout(0,1));
		Border algorithm_options_title = BorderFactory.createTitledBorder("Algorithm");
		algorithm_options.setBorder(algorithm_options_title);
		ButtonGroup algorithm = new ButtonGroup();
		JRadioButton a_star = new JRadioButton("A*");
		algorithm.add(a_star);
		algorithm_buttons.add(a_star);
		JRadioButton dijkstra = new JRadioButton("Dijkstra");
		algorithm.add(dijkstra);
		algorithm_buttons.add(dijkstra);
		JRadioButton bfs = new JRadioButton("Breadth-first search");
		algorithm.add(bfs);
		algorithm_buttons.add(bfs);
		JRadioButton dfs = new JRadioButton("Depth-first search");
		algorithm.add(dfs);
		algorithm_buttons.add(dfs);
		JRadioButton Bfs = new JRadioButton("Best-first search");
		algorithm.add(Bfs);
		algorithm_buttons.add(Bfs);
		algorithm_options.add(algorithm_buttons);
		
		// GRID OPTIONS
		JPanel grid_options = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel wrapper = new JPanel(new BorderLayout());
		Border grid_options_title = BorderFactory.createTitledBorder("Grid Options");
		grid_options.setBorder(grid_options_title);
		JPanel grid_size = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		grid_size.add(new JLabel("Grid size: "),c);
		String[] labels = {"X: ", "Y: "};
		for(int i=0;i<labels.length;i++) {
			JLabel label = new JLabel(labels[i], JLabel.TRAILING);
			c.gridx = 1;
			c.gridy = i+1;
			grid_size.add(label,c);
		    JTextField textField = new JTextField(10);
		    c.gridx = 2;
		    label.setLabelFor(textField);
		    grid_size.add(textField,c);
		}
		JButton submit = new JButton("Set Size");
		c.gridx = 2;
		c.gridy = labels.length+1;
		c.insets = new Insets(10,0,0,0);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		grid_size.add(submit,c);
		wrapper.add(grid_size,BorderLayout.WEST);
		JCheckBox diagonal_movement = new JCheckBox("Diagonal movement");
		diagonal_movement.addActionListener(diagonal);
		wrapper.add(diagonal_movement,BorderLayout.SOUTH);
		grid_options.add(wrapper);
		
		// SIMULATION OPTIONS
		JPanel simulation_options = new JPanel(new FlowLayout());
		Border simulation_options_title = BorderFactory.createTitledBorder("Simulation");
		simulation_options.setBorder(simulation_options_title);
		JButton stop = new JButton("Stop");
		simulation_options.add(stop);
		JButton play = new JButton("Simulate");
		simulation_options.add(play);
		JButton next = new JButton("Next Step");
		simulation_options.add(next);
		options.add(algorithm_options);
		options.add(grid_options);
		options.add(simulation_options);
		options.setPreferredSize(new Dimension(250,0));
		add(options,BorderLayout.EAST);
		
		/*
		 * Create window and set window bounds
		 */
		setBounds(30,30,1000,800);
		setVisible(true);
	}
	
	ActionListener diagonal = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			AbstractButton abstractButton = (AbstractButton) e.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
			settings.setDiagonal(selected);
			System.out.println(settings.getDiagonal());
		}
	};
	
	/*
	 * MouseListener for grid, gets maze from Settings,
	 * then gets x and y coordinates
	 * This Listener adds and removes obstacles
	 * It also changes the color of the JLabels if needed.
	 */
	MouseListener addObstacle = new MouseListener() {
		public void mouseClicked(MouseEvent e) {
			String name = e.getComponent().getName();
			int x = Integer.parseInt(name.substring(0,1));
			int y = Integer.parseInt(name.substring(3,4));
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			if(matrix[x][y]!=2 && matrix[x][y]!=3) {
				if(matrix[x][y]==0) {
					maze.addObstacle(x,y);
					e.getComponent().setBackground(Color.BLACK);
				} else {
					maze.removeObstacle(x,y);
					e.getComponent().setBackground(Color.WHITE);
				}
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	};
	
	/*
	 * Get grid from settings, and create JPanel
	 * Then add it to the JFrame
	 */
	public void addGrid() {
		int[][] matrix = settings.getMaze().getMatrix();
		int maze_x = settings.getMaze_x();
		int maze_y = settings.getMaze_y();
		JPanel grid_holder = new JPanel(new GridBagLayout());
		JPanel grid = new JPanel(new GridLayout(maze_x,maze_y));
		grid_holder.addComponentListener(new ResizeListener(grid));
		grid.setPreferredSize(new Dimension(715,715));
		
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		for(int i=0;i<maze_y;i++) {
			for(int j=0;j<maze_x;j++) {
				JLabel box = new JLabel();
				box.setName(i+", "+j);
				box.setBorder(blackLine);
				box.setOpaque(true);
				if(matrix[i][j]==1) {
					box.setBackground(Color.BLACK);
				} else if(matrix[i][j]==2) {
					box.setBackground(Color.GREEN);
				} else if(matrix[i][j]==3) {
					box.setBackground(Color.RED);
				} else {
					box.setBackground(Color.WHITE);
				}
				box.addMouseListener(addObstacle);
				grid.add(box);
			}
		}
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		grid_holder.add(grid,c);
		add(grid_holder,BorderLayout.CENTER);
	}
}

class ResizeListener extends ComponentAdapter {
	private JPanel grid;
	public ResizeListener(JPanel grid) {
		this.grid = grid;
	}
    public void componentResized(ComponentEvent e) {
    	int height = e.getComponent().getSize().height;
    	int width = e.getComponent().getSize().width;
    	if(width<height) {
    		grid.setPreferredSize(new Dimension(width-20,width-20));
    	} else {
    		grid.setPreferredSize(new Dimension(height-20,height-20));
    	}
    }
}



