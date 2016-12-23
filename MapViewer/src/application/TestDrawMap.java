package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class TestDrawMap implements Initializable{
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 1;
	public static int TILESIZE = 30;
	public static final int numRows = 2;
	
	private Image tileset;
	private WritableImage[][] tiles;
	private int numTiles;
	
	private GraphicsContext g;
	
	private TileView[] tile;
		
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	
	@FXML Canvas canvas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadTileSets();
		loadMap();
		drawMap();
	}
	
	public void loadTileSets(){
		String s = "Resource/testtileset.gif";
		System.out.println("LOADING " + s);
		try {
			//IMPORTANT - load tile set
			tileset = new Image(s);
			TILESIZE = (int) (tileset.getHeight() / 2);//set the size of tile
			int width = (int) (tileset.getWidth() / TILESIZE);
			numTiles = width * numRows;//set number of tile

			PixelReader pr = tileset.getPixelReader();
			tiles = new WritableImage[numRows][width];//array of the tile of the tile set as image
			for(int i = 0; i < width; i++) {//get the image of the tile
				tiles[0][i] = new WritableImage(
						pr,
						TILESIZE * i,
						0,
						TILESIZE,
						TILESIZE
						);
				tiles[1][i] = new WritableImage(
						pr,
						TILESIZE * i,
						TILESIZE,
						TILESIZE,
						TILESIZE
						);
			}
			System.out.println(s + " loaded..");
		}
		catch(Exception e) {
			System.out.println("Couldn't load " + s);
			e.printStackTrace();
		}

		tile = new TileView[numTiles];
		int width = numTiles / numRows;
		for(int i = 0; i < width; i++) {
			tile[i] = new TileView(tiles[0][i], 0);
			//tile[i].setPosition(i * TILESIZE, HEIGHT - 2 * TILESIZE);
			tile[i + width] = new TileView(tiles[1][i], 1);
			//tile[i + width].setPosition(i * TILESIZE, HEIGHT - TILESIZE);
		}

	}
	
	public void loadMap(){
		String str = "Resource/testmap.map";
		System.out.println("LOADING " + str);
		try {
			BufferedReader br = new BufferedReader(new FileReader(str));//start reading .map file
			System.out.println(":D");
			mapWidth = Integer.parseInt(br.readLine());//IMPORTANT PART - loading map
			mapHeight = Integer.parseInt(br.readLine());;
			map = new int[mapHeight][mapWidth];
			String delim = "\\s+";
			for(int row = 0; row < mapHeight; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delim);
				for(int col = 0; col < mapWidth; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			br.close();
		}
		catch(Exception e) {
			System.out.println("Couldn't load " + str);
			e.printStackTrace();
		}
	}

	public void drawMap(){
		g = canvas.getGraphicsContext2D();
		//draw map
		for(int row = 0; row < mapHeight; row++) {
			for(int col = 0; col < mapWidth; col++) {
				try {
					g.drawImage(
							tile[map[row][col]].getImage(),
							col * TILESIZE,
							row * TILESIZE
							);
				}
				catch(Exception e) {}
			}
		}
	}

	
		
}
