import java.io.File;
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
		if(settings.getPath()=="") {
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
			String path = settings.getPath();
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
	    	System.out.println("you are creating a new file.");
	    	
	    	System.out.println(settings.getPath());
	    	
		} else {
			//overwrite file
		}
		
		//Get all settings from Settings
    	int maze_x = settings.getMaze_x();
    	int maze_y = settings.getMaze_y();
    	Maze maze = settings.getMaze();
    	String algorithm = settings.getAlgorithm();
    	Boolean diagonal = settings.getDiagonal();
    	
    	//Create new file
    	try {
		selectedFile.createNewFile();
		FileWriter writer = new FileWriter(selectedFile);
		writer.write("X: "+maze_x+"\r\n");
		writer.write("Y: "+maze_y+"\r\n");
		writer.write(algorithm+"\r\n");
		writer.write(diagonal+"\r\n");
		
		int[][] matrix = maze.getMatrix(); 
		for(int i=0;i<maze_x;i++) {
			for(int j=0;j<maze_y;j++) {
				writer.write(matrix[i][j]+"");
			}
			writer.write("\r\n");
		}
		writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Opening + extra functions
	 */
	public void open() {
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = fileChooser.getSelectedFile();
        	String name = selectedFile.getName();
        	String ext = getExtension(name);
        	if(ext=="maze" || ext=="txt") {
        		
        	} else {
        		selectedFile = null;
        		JOptionPane.showMessageDialog(null, "Selected file type not supported.");
        	}
        }
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
