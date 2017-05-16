
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	private String title;
	private FileHandler fileHandler;
	private Settings settings;
	private HashMap<String, JLabel> grid_labels = new HashMap<String,JLabel>();
	private JPanel grid_holder;
	private JLabel iterations;
	private PathAlgorithm solver = null;
	private Timer timer = null;
	private Boolean running = false;
	private Boolean paused = false;
	private Boolean timer_excists = false;
	
	public GUI(String title, Settings settings) {
		this.title = title;
		this.settings = settings;
		fileHandler = new FileHandler(settings);
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
				new GUI("Pathfinder Simulator", new Settings());
			}
		};
		
		ActionListener openAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!fileHandler.open()) {
					setVisible(false);
					dispose();
					new GUI("Pathfinder Simulator - "+fileHandler.getFileName(), fileHandler.getSettings());
				}
			}
		};
		
		ActionListener saveAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileHandler.save();
				setTitle("Pathfinder Simulator - "+fileHandler.getFileName());
			}
		};
		
		ActionListener saveAsAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileHandler.saveAs();
				setTitle("Pathfinder Simulator - "+fileHandler.getFileName());
			}
		};
		
		ActionListener quitAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose(); 
			}
		};
		
		// Matrix actions 
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
				stopAnimation();
				repaintMatrix();
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
				stopAnimation();
				repaintMatrix();
			}
		};
		
		ActionListener simulationInfo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(solver!=null) {
					System.out.println("Solved: "+solver.solved());
				}
				System.out.println("Paused: "+paused);
				System.out.println("Running: "+running);
				System.out.println("Timer_excists: "+timer_excists);
				System.out.println("Speed: "+(long) Math.pow(2, 10-settings.getSpeed())+"\n");
			}
		};
		
		/*
		 * Menu Bar
		 */
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem _new = new JMenuItem("New");
		_new.addActionListener(newAction);
		file.add(_new);
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(openAction);
		file.add(open);
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(saveAction);
		file.add(save);
		JMenuItem save_as = new JMenuItem("Save as...");
		save_as.addActionListener(saveAsAction);
		file.add(save_as);
		file.add(new JSeparator());
		// Quit button + action 
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(quitAction);
		file.add(quit);
		JMenu settings_tab = new JMenu("Tools");
		JMenuItem setAllBlack = new JMenuItem("Full obstacle");
		setAllBlack.addActionListener(setAllObstacle);
		settings_tab.add(setAllBlack);
		JMenuItem setAllWhite = new JMenuItem("No obstacle");
		setAllWhite.addActionListener(setNoObstacle);
		settings_tab.add(setAllWhite);
		JMenuItem print = new JMenuItem("Print Matrix");
		print.addActionListener(printMatrix);
		settings_tab.add(print);
		JMenuItem printSettings = new JMenuItem("Print Settings");
		printSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.printSettings();
			}
		});
		settings_tab.add(printSettings);
		JMenuItem random = new JMenuItem("Fill Random");
		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Maze maze = settings.getMaze();
				maze.fillRandom();
				stopAnimation();
				repaintMatrix();
			}
		});
		settings_tab.add(random);
		JMenuItem sim_info = new JMenuItem("Simulation Info");
		sim_info.addActionListener(simulationInfo);
		settings_tab.add(sim_info);
		menu.add(file);
		menu.add(settings_tab);
		setJMenuBar(menu);
		
//		/* Memory output */
//		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
//		exec.scheduleAtFixedRate(new Runnable() {
//		  public void run() {
//			  MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
//			  System.out.println(heapMemoryUsage.getUsed());
//		  }
//		}, 0, 2, TimeUnit.SECONDS);
		
		/*
		 * Create Grid 
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
				stopAnimation();
				repaintMatrix();
			}
		};
		
		ActionListener diagonal = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
				settings.setDiagonal(selected);
				stopAnimation();
				repaintMatrix();
			}
		};
		
		ActionListener solveAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(solver==null) {
					repaintMatrix();
					createSolver();
					int[][] solution = solver.getSolution();
					int it = solver.getIterations();
					iterations.setText("Iterations: "+it);
					drawSolution(solution);
				} else {
					int[][] solution = solver.getSolution();
					int it = solver.getIterations();
					iterations.setText("Iterations: "+it);
					drawSolution(solution);
				}
				solver = null;
			}
		};		
		
		ActionListener stop_action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(running) {
					stopAnimation();
					repaintMatrix();
				}
			}
		};
		
		ActionListener pause_action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(running) {
					try {
						timer.cancel();
					}
					catch (NullPointerException e1) {} // No timer set
					paused = true;
					timer_excists = false;
				}
			}
		};
		
		ActionListener start_action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!running) {
					repaintMatrix();
					createSolver();
					running = true;		
				}
				if(running && !timer_excists) {
					timer = new Timer();
					TimerTask step = new TimerTask() {
					    public void run() {
					    	if(!solver.solved() && solver.running()) {
					    		int[][] solution = solver.getNextStep();
					    		int it = solver.getIterations();
					    		iterations.setText("Iterations: "+it);
					    		drawSolution(solution);
					    	} else {
					    		stopAnimation();
					    	}
					    }
					};
					paused = false;
					timer_excists = true;
					timer.schedule(step, 0, (long) Math.pow(2, 10-settings.getSpeed()));
				}
			}
		};
		
		ChangeListener changeSpeed = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider temp = (JSlider) e.getSource();
				settings.setSpeed(temp.getValue());
				if(running && !paused) {
					try {
						timer.cancel();
					}
					catch (NullPointerException e1) {} // No Timer Set
					timer = new Timer();
					TimerTask step = new TimerTask() {
					    public void run() {
					    	if(!solver.solved() && solver.running()) {
					    		int[][] solution = solver.getNextStep();
					    		int it = solver.getIterations();
					    		iterations.setText("Iterations: "+it);
					    		drawSolution(solution);
					    	} else {
					    		stopAnimation();
					    	}
					    }
					};
					timer.schedule(step, 0, (long) Math.pow(2, 10-settings.getSpeed()));
				}
			}
		};
		
		ActionListener step_action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!running || paused) {
					if(solver!=null && !solver.solved()) {
						int[][] solution = solver.getNextStep();
						int it = solver.getIterations();
						iterations.setText("Iterations: "+it);
						drawSolution(solution);
						if(solver.solved()) {
							running = false;
							paused = false;
						}
					} else {
						repaintMatrix();
						createSolver();
						int[][] solution = solver.getNextStep();
						int it = solver.getIterations();
						iterations.setText("Iterations: "+it);
						drawSolution(solution);
					}
				}
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
		if(settings.getAlgorithm().equals("A*")) {a_star.setSelected(true);}
		a_star.addActionListener(changeAlgorithm);
		algorithm.add(a_star);
		algorithm_buttons.add(a_star);
		JRadioButton dijkstra = new JRadioButton("Dijkstra");
		if(settings.getAlgorithm().equals("Dijkstra")) {dijkstra.setSelected(true);}
		algorithm.add(dijkstra);
		dijkstra.addActionListener(changeAlgorithm);
		algorithm_buttons.add(dijkstra);
		JRadioButton bfs = new JRadioButton("Breadth-first search");
		if(settings.getAlgorithm().equals("Breadth-first search")) {bfs.setSelected(true);}
		algorithm.add(bfs);
		bfs.addActionListener(changeAlgorithm);
		algorithm_buttons.add(bfs);
		JRadioButton dfs = new JRadioButton("Depth-first search");
		if(settings.getAlgorithm().equals("Depth-first search")) {dfs.setSelected(true);}
		algorithm.add(dfs);
		dfs.addActionListener(changeAlgorithm);
		algorithm_buttons.add(dfs);
		JRadioButton Bfs = new JRadioButton("Greedy Best-first search");
		if(settings.getAlgorithm().equals("Greedy Best-first search")) {Bfs.setSelected(true);}
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
		HashMap<String, JSpinner> size_fields = new HashMap<String, JSpinner>();
		for(int i=0;i<labels.length;i++) {
			JLabel label = new JLabel(labels[i], JLabel.TRAILING);
			c.gridx = 1;
			c.gridy = i+1;
			grid_size.add(label,c);
			SpinnerNumberModel spinnerNumbers = null;
			if(i == 0) {
				spinnerNumbers = new SpinnerNumberModel(settings.getMaze_x(), 1, 99, 1);
			} else if(i == 1) {
				spinnerNumbers = new SpinnerNumberModel(settings.getMaze_y(), 1, 99, 1);
			}
			JSpinner spinner = new JSpinner(spinnerNumbers);
		    c.gridx = 2;
		    size_fields.put(labels[i], spinner);
		    label.setLabelFor(spinner);
		    grid_size.add(spinner,c);
		}
		JButton submit = new JButton("Set Size");
		c.gridx = 2;
		c.gridy = labels.length+1;
		c.insets = new Insets(10,0,0,0);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean changed = false;
				for(String label : labels) {
					JSpinner temp = size_fields.get(label);
					int number = (int) temp.getValue();
					if(label == "X: ") {
						if(number!=settings.getMaze_x()) {
							changed = true;
							settings.setMaze_x(number);
						}
					}
					if(label == "Y: ") {
						if(number!=settings.getMaze_y()) {
							changed = true;
							settings.setMaze_y(number);
						}
					}
				}
				if(changed) {
					stopAnimation();
					Dimension size = grid_holder.getBounds().getSize();
					remove(grid_holder);
					grid_holder = new JPanel(new GridBagLayout());
					Iterator<Entry<String, JLabel>> it = grid_labels.entrySet().iterator();
				    while (it.hasNext()) {
				        Entry<String, JLabel> pair = it.next();
				        remove(pair.getValue());
				        it.remove();
				    }
					
				    grid_labels = null;
					grid_labels = new HashMap<String,JLabel>();
					if(size.width<=size.height) {
						addGrid(grid_holder,size.width-20);
					} else {
						addGrid(grid_holder,size.height-20);
					}
					revalidate();
					repaint();
					solver = null;
					changed = false;
				}
				
			}
		});
		grid_size.add(submit,c);
		wrapper.add(grid_size,BorderLayout.WEST);
		JCheckBox diagonal_movement = new JCheckBox("Diagonal movement");
		diagonal_movement.addActionListener(diagonal);
		diagonal_movement.setSelected(settings.getDiagonal());
		wrapper.add(diagonal_movement,BorderLayout.SOUTH);
		grid_options.add(wrapper);
		
		// SIMULATION OPTIONS
		JPanel simulation_options = new JPanel(new FlowLayout());
		Border simulation_options_title = BorderFactory.createTitledBorder("Simulation");
		simulation_options.setBorder(simulation_options_title);
		JButton stop = new JButton("Stop");
		stop.addActionListener(stop_action);
		simulation_options.add(stop);
		JButton pause = new JButton("Pause");
		pause.addActionListener(pause_action);
		simulation_options.add(pause);
		JButton play = new JButton("Start");
		play.addActionListener(start_action);
		simulation_options.add(play);
		JSlider speed = new JSlider(0, 10, settings.getSpeed());
		speed.setMinorTickSpacing(1);
		speed.setMajorTickSpacing(5);
		speed.setPaintTicks(true);
		speed.setSnapToTicks(true);
		speed.addChangeListener(changeSpeed);
		simulation_options.add(speed);
		JButton solve = new JButton("Solve");
		solve.addActionListener(solveAction);
		simulation_options.add(solve);
		JButton next = new JButton("Step by Step");
		next.addActionListener(step_action);
		simulation_options.add(next);
		JPanel simulation_info = new JPanel(); 
		iterations = new JLabel("Iterations: 0");
		simulation_info.add(iterations);	
		simulation_options.add(simulation_info);
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
	 * Solver actions
	 */
	public void createSolver() {
		solver = new PathAlgorithm(settings);
		solver.intitialze();
	}
	
	public void drawSolution(int[][] solution) {
		int[][] maze = settings.getMaze().getMatrix();
		int maze_x = settings.getMaze_x();
		int maze_y = settings.getMaze_y();
		for (int i=0;i<maze_x;i++) {
			for (int j=0;j<maze_y;j++) {
				JLabel temp = grid_labels.get(i+" "+j);
				if(maze[i][j]==0 && solution[i][j]==5) {
					temp.setBackground(Color.YELLOW);
				} else if(maze[i][j]==0 && solution[i][j]==6) {
					temp.setBackground(Color.LIGHT_GRAY);
				} else if(maze[i][j]==0 && solution[i][j]==7) {
					temp.setBackground(Color.DARK_GRAY);
				}
			}
		}
	}
	
	public void stopAnimation() {
		try {
			timer.cancel();
		} catch(NullPointerException e) {}
		running = false;
		paused = false;
		timer_excists = false;
		solver = null;
	}
	
	/*
	 * MouseListener for grid, gets maze from Settings,
	 * then gets x and y coordinates
	 * This Listener adds and removes obstacles
	 * It also changes the color of the JLabels if needed.
	 */
	int x_old = 0;
	int y_old = 0;
	int x_new = 0;
	int y_new = 0;
	Color startColor;
	Boolean mouseDown = false;
	Boolean rightClick = false;
	Boolean movingPoint = false;
	MouseListener addObstacle = new MouseListener() {
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			stopAnimation();
			repaintMatrix();
			if(e.getButton()==MouseEvent.BUTTON1) {
				mouseDown = true;
				startColor = e.getComponent().getBackground();
				if (startColor == Color.RED || startColor == Color.GREEN) {
					String name = e.getComponent().getName();
					x_old = x_new = Integer.parseInt(name.substring(0,2));
					y_old = y_new = Integer.parseInt(name.substring(4,6));
					movingPoint = true;
					e.getComponent().setBackground(Color.WHITE);
				} else {
					toggleObstacle(e);
				}
			} else {
				mouseDown = true;
				rightClick = true;
				toggleObstacle(e);
			}
		}
		public void mouseEntered(MouseEvent e) {
			if(mouseDown) {
				if(movingPoint==true) {
					paintPoint(e);
				} else {
					toggleObstacle(e);
				}
			}
		}
		public void mouseReleased(MouseEvent e) {
			mouseDown = false;
			rightClick = false;
			if(e.getButton()==MouseEvent.BUTTON1) {
				if(movingPoint) {
					changePoint(e);
					movingPoint = false;
					repaintMatrix();
				}
			}
		}
		public void mouseExited(MouseEvent e) {
			if(movingPoint) {
				removePoint(e);	
			}
		}
		public void changePoint(MouseEvent e) {
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			String name = e.getComponent().getName();
			int x = Integer.parseInt(name.substring(0,2));
			int y = Integer.parseInt(name.substring(4,6));
			if(startColor==Color.GREEN) {
				if(matrix[x][y]!=3) {
					maze.moveStartPoint(x_old,y_old,x_new,y_new);
				}
			}
			if(startColor==Color.RED) {
				if(matrix[x][y]!=2) {
					maze.moveStopPoint(x_old,y_old,x_new,y_new);
				}
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
					x_new = x;y_new =y;
				}
			}
			if(startColor==Color.RED) {
				if(matrix[x][y]!=2) {
					e.getComponent().setBackground(startColor);
					x_new = x;y_new =y;
				}
			}
		}
		public void removePoint(MouseEvent e) {
			Maze maze = settings.getMaze();
			int[][] matrix = maze.getMatrix();
			String name = e.getComponent().getName();
			int x = Integer.parseInt(name.substring(0,2));
			int y = Integer.parseInt(name.substring(4,6));
			if(matrix[x][y]==1) {
				e.getComponent().setBackground(Color.BLACK);
			} else if(matrix[x][y]==2){
				e.getComponent().setBackground(Color.GREEN);
			} else if(matrix[x][y]==3){
				e.getComponent().setBackground(Color.RED);
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
				if(!rightClick) {
					maze.addObstacle(x,y);
					e.getComponent().setBackground(Color.BLACK);
				} else {
					maze.removeObstacle(x,y);
					e.getComponent().setBackground(Color.WHITE);
				}
			}
		}
	};
	
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



