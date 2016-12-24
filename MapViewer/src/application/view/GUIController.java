package application.view;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import application.TileSetHandler;
import application.model.TileType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class GUIController {
	
	// MUST FIND BETTER WAY TO IMPORT FILE/WRITE PATH FOR FILE
	private File file = new File("currentcoordinates.txt");
	public static final String TILESET_LOC = "Resources/testtileset.gif";
	public static final String ITEMS_LOC = "Resources/items.gif";
	public static final String MAP_LOC = "testmap.map";
	public static final int TILESET_ROWS = 2;
	
	private TileType[] mapTiles;
	private String coorString;
	
	private int[] coorInt = new int[4]; 
	private int[] axeCoorInput = new int[2];
	private int[] boatCoorInput = new int[2];
	
	@FXML private Label itemSelLabel;
	@FXML private Label mapCoorLabel;
	@FXML private Label axeCoorLabel;
    @FXML private Label boatCoorLabel;
	@FXML private Label newAxeCoorLabel;
    @FXML private Label newBoatCoorLabel;
    
    @FXML private Button resetCoor;
    @FXML private Button saveCoor;
    
    @FXML private Canvas canvas;    
    
    @FXML private ImageView axeSprite;
    @FXML private ImageView boatSprite;
    @FXML private ImageView axeSpriteOnMap;
    @FXML private ImageView boatSpriteOnMap;
    
    private Image axeImage;
    private Image boatImage;
    
    // controller constructor
    public GUIController() {
    }
    
    @FXML
    private void initialize() {  
    	System.out.println("Initialising...");
    	
    	// draws graphics for map and item sprites
    	TileSetHandler tsh = new TileSetHandler();
    	mapTiles = tsh.splitTileSet(TILESET_LOC, TILESET_ROWS);
    	tsh.loadMap(MAP_LOC);
    	tsh.drawMap(canvas, mapTiles);
	
    	TileType a = tsh.splitTileSet(ITEMS_LOC, TILESET_ROWS)[4];
    	axeImage = a.getImage();
    	axeSprite.setImage(axeImage);
    	axeSpriteOnMap.setImage(axeImage);
    	
    	TileType b = tsh.splitTileSet(ITEMS_LOC, TILESET_ROWS)[3];
    	boatImage = b.getImage();
    	boatSprite.setImage(boatImage);
    	boatSpriteOnMap.setImage(boatImage);
    
        // reads current in-game position of items from .TXT file
    	try {
    		Scanner s = new Scanner(file);
    		int i = 0;
    		
    		while(s.hasNext()) {
    			coorInt[i] = s.nextInt();
    			i++;
    		}
    		
    		s.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	// prints coordinates of items on map
    	axeCoorLabel.setText(coorInt[0] + ", " + coorInt[1]);
    	newAxeCoorLabel.setText(coorInt[0] + ", " + coorInt[1]);
    	boatCoorLabel.setText(coorInt[2] + ", " + coorInt[3]);
    	newBoatCoorLabel.setText(coorInt[2] + ", " + coorInt[3]);
    	
    	axeCoorInput[0] = coorInt[0];
        axeCoorInput[1] = coorInt[1];
        boatCoorInput[0] = coorInt[2];
        boatCoorInput[1] = coorInt[3];


    	// sets image and position of items on map
    	axeSpriteOnMap.setLayoutY(coorInt[0] * TileSetHandler.tileSize);
    	axeSpriteOnMap.setLayoutX(coorInt[1] * TileSetHandler.tileSize);
    	axeSpriteOnMap.setId("Axe" + this.getClass().getSimpleName());
    	boatSpriteOnMap.setLayoutY(coorInt[2] * TileSetHandler.tileSize);
    	boatSpriteOnMap.setLayoutX(coorInt[3] * TileSetHandler.tileSize);
    	boatSpriteOnMap.setId("Boat" + this.getClass().getSimpleName());
    	    	
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
	
	@FXML void showMapCoor(MouseEvent me) {
		//System.out.println("Mouse over canvas detected.\n" + ((int) me.getX() / 16) + ", " + ((int) me.getY() / 16));
		mapCoorLabel.setText(((int)me.getY() / TileSetHandler.tileSize) + ", " + ((int) me.getX() / TileSetHandler.tileSize));
	} // end showMapCoor

	// detects drag on axe sprite
	@FXML void moveAxeSprite(MouseEvent me) {
		System.out.println("Dragging item sprite around.");
			
		Dragboard db = axeSpriteOnMap.startDragAndDrop(TransferMode.MOVE);
		ClipboardContent cc = new ClipboardContent();
		cc.putString(axeSpriteOnMap.getId());
		db.setContent(cc);
		db.setDragView(axeImage);
			
		itemSelLabel.setText("Axe selected");
			
		me.consume();
	} // end moveAxeSprite
	
	// detects drag on boat sprite
	@FXML void moveBoatSprite(MouseEvent me) {
		System.out.println("Dragging item sprite around.");
		
		Dragboard db = boatSpriteOnMap.startDragAndDrop(TransferMode.MOVE);
		ClipboardContent cc = new ClipboardContent();
		cc.putString(boatSpriteOnMap.getId());
		db.setContent(cc);
		db.setDragView(boatImage);
		
		itemSelLabel.setText("Boat selected");
		
		me.consume();
	} // end moveBoatSprite
	
	// sets item sprites to new position
	@FXML void setSprite(DragEvent de) {
		int col = (int) de.getX() / TileSetHandler.tileSize;
		int row = (int) de.getY() / TileSetHandler.tileSize;
		
		Dragboard db = de.getDragboard();
		
		if(db.hasString()) {
			String spriteId = db.getString();
			
			//System.out.println(spriteId);
			//System.out.println(row + ", " + col);
			
			//int index = row * 40 + col;
			
			//System.out.println(index);
			//System.out.println(mapTiles[index]);
		
			//if(mapTiles[row * 40 + col].getType() == 1) {
				//return;	
			//}
			
			if(spriteId == axeSpriteOnMap.getId()) {
				newAxeCoorLabel.setText(row + ", " + col);
				axeSpriteOnMap.setLayoutY(row * TileSetHandler.tileSize);
				axeSpriteOnMap.setLayoutX(col * TileSetHandler.tileSize);
				
				axeCoorInput[0] = row;
				axeCoorInput[1] = col;
			} else if(spriteId == boatSpriteOnMap.getId()) {
				newBoatCoorLabel.setText(row + ", " + col);
				boatSpriteOnMap.setLayoutY(row * TileSetHandler.tileSize);
				boatSpriteOnMap.setLayoutX(col * TileSetHandler.tileSize);
				
				boatCoorInput[0] = row;
				boatCoorInput[1] = col;
			}				
		}
		
		itemSelLabel.setText("No item selected");
		
		de.consume();
	} // end setSprite
	
	@FXML void detectSpriteMove(DragEvent de) {
		if(de.getGestureSource() != canvas && de.getDragboard().hasString()) {
			de.acceptTransferModes(TransferMode.MOVE);
		}
		
		mapCoorLabel.setText(((int)de.getY() / TileSetHandler.tileSize) + ", " + ((int) de.getX() / TileSetHandler.tileSize));
		
		de.consume();
	} //end detectSpriteMove
	
	@FXML void resetCoor(ActionEvent ae) {
		System.out.println("Resetting item coordinates to default...");
		
		try {
			int[] coordinates = new int[4];
			int i = 0;
			Scanner s = new Scanner(new File("defaultcoordinates.txt"));
			PrintWriter pw = new PrintWriter(file);
    		
    		while(s.hasNext()) {
    			coordinates[i] = s.nextInt();
    			i++;
    		}
			pw.print(coordinates[0] + " " + coordinates[1] + " " + coordinates[2] + " " + coordinates[3]);
			
			s.close();
			pw.close();
			
			initialize();
			//axeCoorLabel.setText(coordinates[0] + ", " + coordinates[1]);
	    	//boatCoorLabel.setText(coordinates[2] + ", " + coordinates[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end resetCoor

	@FXML void saveCoor(ActionEvent ae) {
		System.out.println("Saving new item coordinates...");
		try {
			PrintWriter pw = new PrintWriter(file);
			
			pw.print(axeCoorInput[0] + " " + axeCoorInput[1] + " " + boatCoorInput[0] + " " + boatCoorInput[1]);

			pw.close();
			axeCoorLabel.setText(axeCoorInput[0] + ", " + axeCoorInput[1]);
	    	boatCoorLabel.setText(boatCoorInput[0] + ", " + boatCoorInput[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end saveCoor
}