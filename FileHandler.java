import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class FileHandler {
	private Settings settings;
	public FileHandler(Settings settings) {
		this.settings = settings;
	}
	
	public void save() {
		if(settings.getPath()=="") {
			JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showSaveDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	        	File selectedFile = fileChooser.getSelectedFile();
	        	String name = selectedFile.getName();
	        	String[] register = {"txt",".png",".jpg",".jpeg",".html",".docx",".gif",".","/"};
	           	name = name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
	           	name = removeExt(name, register);
	        	name = name + ".maze";
	        	name = selectedFile.getAbsolutePath().replace(selectedFile.getName(), name);
	        	File to_write = new File(name);
	        	settings.setPath(to_write.getAbsolutePath());
	        	writeToFile(to_write);
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
        	String[] register = {"txt",".png",".jpg",".jpeg",".html",".docx",".gif",".","/"};
           	name = name.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
           	name = removeExt(name, register);
        	name = name + ".maze";
        	name = selectedFile.getAbsolutePath().replace(selectedFile.getName(), name);
        	File to_write = new File(name);
        	settings.setPath(to_write.getAbsolutePath());
        	writeToFile(to_write);
        }
	}
	
	public String removeExt(String string, String[] register) {
		for(String ext : register) {
			string = string.replace(ext, "");
		}
		return string;
	}
	
	public void open() {
		JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        	File selectedFile = fileChooser.getSelectedFile();
        	System.out.println(selectedFile.getName());
        }
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
}
