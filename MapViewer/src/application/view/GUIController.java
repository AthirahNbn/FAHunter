package application.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import application.CoordinatesReader;
import application.Font;
import application.TileSetHandler;
import application.model.TileType;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class GUIController {
	
	private File coorFile = new File("currentcoordinates.txt");
	private String nonSafeFile = "nonsafetiles.txt";
	public static final String TILESET_LOC = "Resources/testtileset.gif";
	public static final String ITEMS_LOC = "Resources/items.gif";
	public static final String MAP_LOC = "testmap.map";
	public static final int TILESET_ROWS = 2;
	public static TileType[][] mapTileData;
	
	private TileType[] mapTileImageKey;
	private int[][] mapTokens;
	
	private int[] coorInt = new int[4]; 
	private int[] axeCoorInput = new int[2];
	private int[] boatCoorInput = new int[2];
	
	@FXML private Label itemSelLabel;
	@FXML private Label mapCoorLabel;
	@FXML private Label axeCoorLabel;
    @FXML private Label boatCoorLabel;
	@FXML private Label newAxeCoorLabel;
    @FXML private Label newBoatCoorLabel;
    @FXML private Label message;
    
    @FXML private Button playButton;
    @FXML private Button resetCoor;
    @FXML private Button saveCoor;
    
    @FXML private Canvas map;
    @FXML private Canvas title;
    @FXML private Canvas axeFont;
    @FXML private Canvas boatFont;
    @FXML private Canvas defaultFont;
    @FXML private Canvas saveFont;
    @FXML private Canvas messageTitle;
    
    @FXML private ImageView axeSprite;
    @FXML private ImageView boatSprite;
    @FXML private ImageView axeSpriteOnMap;
    @FXML private ImageView boatSpriteOnMap;
    
    @FXML private HBox messageBox;
    
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
    	mapTileImageKey = tsh.splitTileSet(TILESET_LOC, TILESET_ROWS);
    	mapTokens = tsh.loadMap(MAP_LOC);
    	mapTileData = tsh.getTileData(mapTileImageKey, mapTokens);
    	tsh.drawMap(map, mapTileData);
	
    	TileType a = tsh.splitTileSet(ITEMS_LOC, TILESET_ROWS)[4];
    	axeImage = a.getImage();
    	axeSprite.setImage(axeImage);
    	axeSpriteOnMap.setImage(axeImage);
    	
    	TileType b = tsh.splitTileSet(ITEMS_LOC, TILESET_ROWS)[3];
    	boatImage = b.getImage();
    	boatSprite.setImage(boatImage);
    	boatSpriteOnMap.setImage(boatImage);
    	
    	CoordinatesReader cr = new CoordinatesReader();
		mapTileData = cr.loadCoor(nonSafeFile, mapTileData);
		
    	Font f = new Font();
  
    	f.drawString(title, "Diamond", 0, 0, 0);	
		f.drawString(title, "Hunter", 8, 15, 0);
		f.drawString(title, "Map", 30, 35, 0);
		f.drawString(title, "Viewer", 8, 50, 0);
		f.drawString(axeFont, "Axe", 0, 4, 1);
		f.drawString(boatFont, "Boat", 0, 4, 1);
		f.drawString(defaultFont, "Default", 0, 6, 1);
		f.drawString(saveFont, "Save", 20, 6, 1);
    
        // reads current in-game position of items from .TXT file
    	try {
    		Scanner s = new Scanner(coorFile);
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
    	
    	messageBox.setVisible(false);
    	    	
    	System.out.println("Application initialised.\n");
    }
	
	@FXML void showMapCoor(MouseEvent me) {
		mapCoorLabel.setText(((int)me.getY() / TileSetHandler.tileSize) + ", " + ((int) me.getX() / TileSetHandler.tileSize));
	} // end showMapCoor

	// detects drag on axe sprite
	@FXML void moveAxeSprite(MouseEvent me) {
		System.out.println("Dragging axe sprite around.");
			
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
		System.out.println("Dragging boat sprite around.");
		
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
			
			if(mapTileData[row][col].getType() > 0) {
				if(spriteId == axeSpriteOnMap.getId() && mapTileData[row][col].getType() == 2) {
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
			}else {
				showMessage(0);
			}
		}
		
		itemSelLabel.setText("No item selected");
		
		de.consume();
	} // end setSprite
	
	@FXML void detectSpriteMove(DragEvent de) {
		if(de.getGestureSource() != map && de.getDragboard().hasString()) {
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
			PrintWriter pw = new PrintWriter(coorFile);
    		
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
		    showMessage(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end resetCoor

	@FXML void saveCoor(ActionEvent ae) {
		System.out.println("Saving new item coordinates...");
		try {
			PrintWriter pw = new PrintWriter(coorFile);
			
			pw.print(axeCoorInput[0] + " " + axeCoorInput[1] + " " + boatCoorInput[0] + " " + boatCoorInput[1]);

			pw.close();
			axeCoorLabel.setText(axeCoorInput[0] + ", " + axeCoorInput[1]);
	    	boatCoorLabel.setText(boatCoorInput[0] + ", " + boatCoorInput[1]);
	    	showMessage(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end saveCoor
	
	@FXML void playButton(){
        try {
            System.out.println("Calling jar");

            Process p = Runtime.getRuntime().exec("java -jar DiamondHunter.jar arg1 arg2");
            showMessage(3);

            BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
            synchronized (p) {
              p.waitFor();
            }
            System.out.println(p.exitValue());

            int b=0;
            while((b=bis.read()) >0){
              System.out.print((char)b);   
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    } // end playButtton
	
	public void showMessage(int event) {
		Font f = new Font();
		
		switch(event) {
		// error message
		case 0:
			f.drawString(messageTitle, "Error:", 0, 4, 1);
			message.setText("Invalid position. Will result in an impossible game.");
			break;
		// save message
		case 1:
			f.drawString(messageTitle, "Success:", 0, 4, 1);
			message.setText("New item positions successfully saved.");
			break;
		// reset message
		case 2:
			f.drawString(messageTitle, "Success:", 0, 4, 1);
			message.setText("Item positions successfully reset and saved.");
			break;
		// game launch message
		case 3:
			f.drawString(messageTitle, "Success:", 0, 4, 1);
			message.setText("Game successfully launched.");
			break;
		}
		
		messageBox.setVisible(true);
		
		PauseTransition p = new PauseTransition(Duration.seconds(4));
		p.setOnFinished(ActionEvent -> {
			messageBox.setVisible(false);
		});
		p.play();
	}// end warning box
	
}