import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHandler {
	private Settings settings;
	public FileHandler(Settings settings) {
		this.settings = settings;
	}
	
	/*
	 * Saving + extra functions
	 */
	public void save() {
		String path = settings.getPath();
		if(path.isEmpty()) {
			JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showSaveDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	        	File selectedFile = fileChooser.getSelectedFile();
	        	String name = selectedFile.getName();
	        	String[] register = {"txt",".png",".jpg",".jpeg",".html",".docx",".gif",".maze",".","/"};
	           	name = name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
	           	name = removeExt(name, register);
	        	name = name + ".maze";
	        	name = selectedFile.getAbsolutePath().replace(selectedFile.getName(), name);
	        	File to_write = new File(name);
	        	selectedFile = null;
	        	settings.setPath(to_write.getAbsolutePath());
	        	writeToFile(to_write);
	        	to_write = null;
	        }
		} else {
			File selectedFile = new File(path);
			writeToFile(selectedFile);
		} 
	}
	
	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = fileChooser.getSelectedFile();
        	String name = selectedFile.getName();
        	String[] register = {"txt",".png",".jpg",".jpeg",".html",".docx",".gif",".maze",".","/"};
           	name = name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
           	name = removeExt(name, register);
        	name = name + ".maze";
        	name = selectedFile.getAbsolutePath().replace(selectedFile.getName(), name);
        	File to_write = new File(name);
        	selectedFile = null;
        	settings.setPath(to_write.getAbsolutePath());
        	writeToFile(to_write);
        	to_write = null;
        }
	}
	
	public String removeExt(String string, String[] register) {
		for(String ext : register) {
			string = string.replace(ext, "");
		}
		return string;
	}
	
	public void writeToFile(File selectedFile) {
		if(!selectedFile.exists() && !selectedFile.isDirectory()) { 
	    	//Show Popup
		} else {
			//overwrite file
		}
		
		//Get all settings from Settings
    	int maze_x = settings.getMaze_x();
    	int maze_y = settings.getMaze_y();
    	Maze maze = settings.getMaze();
    	String algorithm = settings.getAlgorithm();
    	Boolean diagonal = settings.getDiagonal();
    	String path = settings.getPath();
    	
    	//Create new file
    	try {
		selectedFile.createNewFile();
		FileWriter writer = new FileWriter(selectedFile);
		writer.write("X: "+maze_x+"\r\n");
		writer.write("Y: "+maze_y+"\r\n");
		writer.write(algorithm+"\r\n");
		writer.write(diagonal+"\r\n");
		writer.write(path+"\r\n");
		
		int[][] matrix = maze.getMatrix(); 
		for(int i=0;i<maze_x;i++) {
			for(int j=0;j<maze_y;j++) {
				writer.write(matrix[i][j]+"");
			}
			writer.write("\r\n");
		}
		writer.close();
		} catch (IOException e) {
			this.saveAs();
		}
	}
	
	/*
	 * Opening + extra functions
	 */
	public boolean open() {
		Boolean error = false;
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = fileChooser.getSelectedFile();
        	String name = selectedFile.getName();
        	String ext = getExtension(name);
        	if(ext.equals("maze") || ext.equals("txt")) {
        		try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
        		    String line;
        		    int count = 0;
        		    int x = 0;int y = 0;
        		    int row = 0;
        		    while ((line = reader.readLine()) != null && !error) {
        		       if(count==0) {
        		    	   line = line.replace("X: ", "");
        		    	   x = Integer.parseInt(line);
        		    	   settings.setMaze_x(x);
        		       }
        		       if(count==1) {
        		    	   line = line.replace("Y: ", "");
        		    	   y = Integer.parseInt(line);
        		    	   settings.setMaze_y(y);
        		    	   settings.setMaze(new Maze(x,y));
        		       }
        		       if(count==2) {
        		    	   settings.setAlgorithm(line);
        		       }
        		       if(count==3) {
        		    	   Boolean temp = false;
        		    	   if(line.equals("true")) {
        		    		   temp = true;
        		    	   }
        		    	   settings.setDiagonal(temp);
        		       }
        		       if(count==4) {
        		    	   settings.setPath(line);
        		       }
        		       if(count>4) {
        		    	   if (line.length()+4 != y && row < x) {
	        		    	   for (int column = 0; column < y; column++){
	        		    		    char c = line.charAt(column);
	        		    		    if(c != '/' && c != 'n' && c != 'r') {
	        		    		    	if(c == '1') {
	        		    		    		settings.getMaze().addObstacle(row, column);
	        		    		    	} else if(c == '2') {
	        		    		    		settings.getMaze().moveStartPoint(0,0,row,column);
	        		    		    	} else if(c == '3') {
	        		    		    		settings.getMaze().moveStopPoint(x-1,y-1,row,column);
	        		    		    	}
	        		    		    }
	        		    		}
        		    	   } else {
        		    		   error = true;
        		    	   }
        		    	   row++;
        		       }
        		       count++;
        		    }
        		    if(error) {
            			JOptionPane.showMessageDialog(null, "Something went wrong while loading the file.");
            		}
        		} 
        		catch (FileNotFoundException e) {} 
        		catch (IOException e) {}
        		
        	} else {
        		error = true;
        		selectedFile = null;
        		JOptionPane.showMessageDialog(null, "Selected file type not supported.");
        	}
        }
        return error;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf(".");
        int lastUnixPos = filename.lastIndexOf("\'/\'");
        int lastWindowsPos = filename.lastIndexOf("\'\\\'");
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
}
