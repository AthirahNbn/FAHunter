package application.view;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GUIController {
	
	// MUST FIND BETTER WAY TO IMPORT FILE/WRITE PATH FOR FILE
	private File file = new File("C:\\Users\\User\\Desktop\\SWM P2\\Git Repo Demo\\FAHunter\\currentcoordinates.txt");
	
	private String coorString;
	
	private int[] coorInt = new int[4]; 
	private int[] axeCoorInput = new int[2];
	private int[] boatCoorInput = new int[2];
	
	@FXML private Label axeCoorLabel;
    @FXML private Label boatCoorLabel;
    
    @FXML private Button resetCoor;
    @FXML private Button saveCoor;
    
    @FXML private Canvas map;    
    
    // controller constructor
    public GUIController() {
    }
    
    @FXML
    private void initialize() {  
    	System.out.println("Initialising...");
    	
        //DUMMY DATA
        axeCoorInput[0] = 18;
        axeCoorInput[1] = 19;
        boatCoorInput[0] = 20;
        boatCoorInput[1] = 42;

    	try {
    		Scanner scanner = new Scanner(file);
    		int i = 0;
    		
    		while(scanner.hasNext()) {
    			coorInt[i] = scanner.nextInt();
    			i++;
    		}
    		
    		scanner.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	axeCoorLabel.setText(coorInt[0] + ", " + coorInt[1]);
    	boatCoorLabel.setText(coorInt[2] + ", " + coorInt[3]);
    	
    	System.out.println("Application initialised.\n");
    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getCoorString() {
		return coorString;
	}

	public void setCoorString(String coorString) {
		this.coorString = coorString;
	}

	public int[] getCoorInt() {
		return coorInt;
	}

	public void setCoorInt(int[] coorInt) {
		this.coorInt = coorInt;
	}
	
	@FXML void resetCoor(ActionEvent ae) {
		System.out.println("Resetting item coordinates to default...");
		try {
			int[] coordinates = new int[4];
			int i = 0;
			Scanner scanner = new Scanner(new File("C:\\Users\\User\\Desktop\\SWM P2\\Git Repo Demo\\FAHunter\\defaultcoordinates.txt"));
			PrintWriter pwriter = new PrintWriter(file);
    		
    		while(scanner.hasNext()) {
    			coordinates[i] = scanner.nextInt();
    			i++;
    		}
			pwriter.print(coordinates[0] + " " + coordinates[1] + " " + coordinates[2] + " " + coordinates[3]);
			
			scanner.close();
			pwriter.close();
			axeCoorLabel.setText(coordinates[0] + ", " + coordinates[1]);
	    	boatCoorLabel.setText(coordinates[2] + ", " + coordinates[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML void saveCoor(ActionEvent ae) {
		System.out.println("Saving new item coordinates...");
		try {
			PrintWriter pwriter = new PrintWriter(file);
			
			pwriter.print(axeCoorInput[0] + " " + axeCoorInput[1] + " " + boatCoorInput[0] + " " + boatCoorInput[1]);

			pwriter.close();
			axeCoorLabel.setText(axeCoorInput[0] + ", " + axeCoorInput[1]);
	    	boatCoorLabel.setText(boatCoorInput[0] + ", " + boatCoorInput[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}