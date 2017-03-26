
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
import java.text.DecimalFormat;
import java.util.HashMap;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private String title;
	private Settings settings = new Settings();
	HashMap<String, JLabel> grid_labels = new HashMap<String,JLabel>();
	JPanel grid_holder;
	public GUI(String title) {
		this.title = title;
		createGUI(settings);
	}
	
	/*
	 * Create GUI with the current settings
	 */
	public void createGUI(Settings settings) {
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
				new GUI("Pathfinder Simulator");
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
		
		// Matrix actions (temp)
		ActionListener printMatrix = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.getMaze().printMaze();
			}
		};
		
		ActionListener setAllObstacle = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Maze maze = settings.getMaze();
				int[][] matrix = maze.getMatrix();
				int maze_x = settings.getMaze_x();
				int maze_y = settings.getMaze_y();
				for(int i=0;i<maze_x;i++) {
					for(int j=0;j<maze_y;j++) {
						if(matrix[i][j]!=2 && matrix[i][j]!=3) {
							maze.addObstacle(i,j);
							JLabel temp = grid_labels.get(i+" "+j);
							temp.setBackground(Color.BLACK);
						}
					}
				}
			}
		};
		
		ActionListener setNoObstacle = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Maze maze = settings.getMaze();
				int[][] matrix = maze.getMatrix();
				int maze_x = settings.getMaze_x();
				int maze_y = settings.getMaze_y();
				for(int i=0;i<maze_x;i++) {
					for(int j=0;j<maze_y;j++) {
						if(matrix[i][j]!=2 && matrix[i][j]!=3) {
							maze.removeObstacle(i,j);
							JLabel temp = grid_labels.get(i+" "+j);
							temp.setBackground(Color.WHITE);
						}
					}
				}
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
		JMenu settings_tab = new JMenu("Settings");
		//settings_tab.add(new JMenuItem("Grid width"));
		//settings_tab.add(new JMenuItem("Grid height"));
		JMenuItem print = new JMenuItem("Print Matrix");
		print.addActionListener(printMatrix);
		settings_tab.add(print);
		JMenuItem setAllBlack = new JMenuItem("Full obstacle");
		setAllBlack.addActionListener(setAllObstacle);
		settings_tab.add(setAllBlack);
		JMenuItem setAllWhite = new JMenuItem("No obstacle");
		setAllWhite.addActionListener(setNoObstacle);
		settings_tab.add(setAllWhite);
		menu.add(file);
		menu.add(settings_tab);
		setJMenuBar(menu);		
		
		/*
		 * Create Grid Panel
		 */
		grid_holder = new JPanel(new GridBagLayout());
		addGrid(grid_holder,715);
		
		/*
		 * Option panel actions
		 */
		ActionListener changeAlgorithm = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton button = (AbstractButton) e.getSource();
				settings.setAlgorithm(button.getText());
			}
		};
		
		ActionListener diagonal = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
				settings.setDiagonal(selected);
			}
		};
				
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
		a_star.addActionListener(changeAlgorithm);
		algorithm.add(a_star);
		algorithm_buttons.add(a_star);
		JRadioButton dijkstra = new JRadioButton("Dijkstra");
		algorithm.add(dijkstra);
		dijkstra.addActionListener(changeAlgorithm);
		algorithm_buttons.add(dijkstra);
		JRadioButton bfs = new JRadioButton("Breadth-first search");
		algorithm.add(bfs);
		bfs.addActionListener(changeAlgorithm);
		algorithm_buttons.add(bfs);
		JRadioButton dfs = new JRadioButton("Depth-first search");
		algorithm.add(dfs);
		dfs.addActionListener(changeAlgorithm);
		algorithm_buttons.add(dfs);
		JRadioButton Bfs = new JRadioButton("Best-first search");
		algorithm.add(Bfs);
		Bfs.addActionListener(changeAlgorithm);
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
		HashMap<String, JTextField> size_fields = new HashMap<String, JTextField>();
		for(int i=0;i<labels.length;i++) {
			JLabel label = new JLabel(labels[i], JLabel.TRAILING);
			c.gridx = 1;
			c.gridy = i+1;
			grid_size.add(label,c);
		    JTextField textField = new JTextField(10);
		    c.gridx = 2;
		    size_fields.put(labels[i], textField);
		    label.setLabelFor(textField);
		    grid_size.add(textField,c);
		}
		JButton submit = new JButton("Set Size");
		c.gridx = 2;
		c.gridy = labels.length+1;
		c.insets = new Insets(10,0,0,0);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean error = false;
				int test = 0;
				for(String label : labels) {
					JTextField temp = size_fields.get(label);
					String text = temp.getText();
					try {
						int number = Integer.parseInt(text);
						if(number==settings.getMaze_x() || number==settings.getMaze_y()) {
							test++;
						}
						if(label == "X: ") {settings.setMaze_x(number);}
						if(label == "Y: ") {settings.setMaze_y(number);}
					} catch(NumberFormatException n) {
						error = true;
					}	
				}
				if(error) {JOptionPane.showMessageDialog(null, "Please input integers for X and Y.");}
				if(test!=2) {
					Dimension size = grid_holder.getBounds().getSize();
					remove(grid_holder);
					grid_holder = new JPanel(new GridBagLayout());
					grid_labels = new HashMap<String,JLabel>();
					
					if(size.width<=size.height) {
						addGrid(grid_holder,size.width-20);
					} else {
						addGrid(grid_holder,size.height-20);
					}
					revalidate();
					repaint();
				}
				
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
	
	/*
	 * MouseListener for grid, gets maze from Settings,
	 * then gets x and y coordinates
	 * This Listener adds and removes obstacles
	 * It also changes the color of the JLabels if needed.
	 */
	int x_old = 0;
	int y_old = 0;
	Color startColor;
	Boolean mouseDown = false;
	Boolean moved = false;
	Boolean movingPoint = false;
	MouseListener addObstacle = new MouseListener() {
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1) {
				mouseDown = true;
				moved = false;
				startColor = e.getComponent().getBackground();
				if (startColor == Color.RED || startColor == Color.GREEN) {
					String name = e.getComponent().getName();
					x_old = Integer.parseInt(name.substring(0,2));
					y_old = Integer.parseInt(name.substring(4,6));
					movingPoint = true;
					removePoint(e);
				} else {
					toggleObstacle(e);
				}
			}
		}
		public void mouseEntered(MouseEvent e) {
			if(mouseDown) {
				if(movingPoint==true) {
					moved = true;
					paintPoint(e);
				} else {
					toggleObstacle(e);
				}
			}
		}
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()==MouseEvent.BUTTON1) {
				mouseDown = false;				
				if(movingPoint) {
					if(!moved) {paintPoint(e);}
					movingPoint = false;
				}
			}
			repaintMatrix();
		}
		public void mouseExited(MouseEvent e) {
			if(movingPoint) {
				removePoint(e);	
			}
		}
		public void paintPoint(MouseEvent e) {
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			String name = e.getComponent().getName();
			int x = Integer.parseInt(name.substring(0,2));
			int y = Integer.parseInt(name.substring(4,6));
			if(startColor==Color.GREEN) {
				if(matrix[x][y]!=3) {
					e.getComponent().setBackground(startColor);
					maze.moveStartPoint(x_old,y_old,x,y);
				}
			}
			if(startColor==Color.RED) {
				if(matrix[x][y]!=2) {
					e.getComponent().setBackground(startColor);
					maze.moveStopPoint(x_old,y_old,x,y);
				}
			}
			x_old = x;y_old = y;
		}
		public void removePoint(MouseEvent e) {
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			String name = e.getComponent().getName();
			int x = Integer.parseInt(name.substring(0,2));
			int y = Integer.parseInt(name.substring(4,6));
			if(matrix[x][y]==1) {
				e.getComponent().setBackground(Color.BLACK);
			} else {
				e.getComponent().setBackground(Color.WHITE);
			}
		}
		public void toggleObstacle(MouseEvent e) {
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			String name = e.getComponent().getName();
			int x = Integer.parseInt(name.substring(0,2));
			int y = Integer.parseInt(name.substring(4,6));
			if(matrix[x][y]!=2 && matrix[x][y]!=3) {
				if(startColor==Color.WHITE) {
					if(matrix[x][y]==0) {
						maze.addObstacle(x,y);
						e.getComponent().setBackground(Color.BLACK);
					}
				} else {
					maze.removeObstacle(x,y);
					e.getComponent().setBackground(Color.WHITE);
				}
			}
		}
		public void repaintMatrix() {
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			int maze_x = settings.getMaze_x();
			int maze_y = settings.getMaze_y();
			for(int i=0;i<maze_x;i++) {
				for(int j=0;j<maze_y;j++) {
					JLabel temp = grid_labels.get(i+" "+j);
					if(matrix[i][j]==1) {
						temp.setBackground(Color.BLACK);
					} else if(matrix[i][j]==2) {
						temp.setBackground(Color.GREEN);
					} else if(matrix[i][j]==3) {
						temp.setBackground(Color.RED);
					} else {
						temp.setBackground(Color.WHITE);
					}
				}
			}
		}
	};
	
	
	/*
	 * Get grid from settings, and create JPanel
	 * Then add it to the JFrame
	 */
	public void addGrid(JPanel grid_holder, int width) {
		int[][] matrix = settings.getMaze().getMatrix();
		int maze_x = settings.getMaze_x();
		int maze_y = settings.getMaze_y();
		JPanel grid = new JPanel(new GridLayout(maze_x,maze_y));
		grid_holder.addComponentListener(new ResizeListener(grid));
		grid.setPreferredSize(new Dimension(width,width));
		
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		for(int i=0;i<maze_x;i++) {
			for(int j=0;j<maze_y;j++) {
				DecimalFormat format = new DecimalFormat("00");
				JLabel box = new JLabel();
				box.setName(format.format(i)+", "+format.format(j));
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
				grid_labels.put(i+" "+j, box);
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



