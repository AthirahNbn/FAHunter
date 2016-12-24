package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import application.model.TileType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class MapLoader{
	
	public static int tileSize = 30;
	public static final int TILESET_ROWS = 2;
	public static final String TILESET_LOC = "/Resources/testtileset.gif";
	public static final String MAP_LOC = "testmap.map";
	
	private Image tileSet;
	private WritableImage[][] tiles;
	private int numTiles;
	
	private GraphicsContext g;
	
	private TileType[] tile;
		
	private int[][] mapCoor;
	private int mapWidth;
	private int mapHeight;
	
	public void loadTileSet(){
		System.out.println("Loading " + TILESET_LOC + "...");
		
		try {
			tileSet = new Image(TILESET_LOC);
			tileSize = (int) (tileSet.getHeight() / TILESET_ROWS);
			int tileSetWidth = (int) (tileSet.getWidth() / tileSize);
			numTiles = tileSetWidth * TILESET_ROWS;

			PixelReader pr = tileSet.getPixelReader();
			tiles = new WritableImage[TILESET_ROWS][tileSetWidth];
			
			// separates tile set image into individual tile images
			for(int i = 0; i < tileSetWidth; i++) {
				tiles[0][i] = new WritableImage(pr, tileSize * i, 0, tileSize, tileSize);
				tiles[1][i] = new WritableImage(pr, tileSize * i, tileSize, tileSize, tileSize);
			}
			
			System.out.println("Tile set loaded.");
		} catch(Exception e) {
			System.out.println("ERROR:\nCouldn't load tileset.\n");
			e.printStackTrace();
		}

		tile = new TileType[numTiles];
		int width = numTiles / TILESET_ROWS;
		for(int i = 0; i < width; i++) {
			tile[i] = new TileType(tiles[0][i], 0);
			//tile[i].setPosition(i * TILESIZE, HEIGHT - 2 * TILESIZE);
			tile[i + width] = new TileType(tiles[1][i], 1);
			//tile[i + width].setPosition(i * TILESIZE, HEIGHT - TILESIZE);
		}
	} // end loadTileSet
	
	public void loadMap(){
		System.out.println("Loading " + MAP_LOC + "...");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(MAP_LOC));
			
			// first two integers in .MAP file represent map width and height respectively
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());;
			mapCoor = new int[mapHeight][mapWidth];
			String delim = "\\s+"; //one or more whitespace characters
			
			// reads tile types for tiles in map
			for(int row = 0; row < mapHeight; row++) {
				// splits line of tokens by whitespace-character delimiter
				String line = br.readLine();
				String[] tokens = line.split(delim); // tokens represent tile types
				
				// sets tile type for each tile according to map coordinates (row, col) for map drawing
				for(int col = 0; col < mapWidth; col++) {
					mapCoor[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			br.close();
			System.out.println("Map loaded.");
		}
		catch(Exception e) {
			System.out.println("ERROR:\nCouldn't load map.\n");
			e.printStackTrace();
		}
	} // end loadMap

	// draws map tile by tile
	public void drawMap(Canvas canvas){
		g = canvas.getGraphicsContext2D();
		
		for(int row = 0; row < mapHeight; row++) {
			for(int col = 0; col < mapWidth; col++) {
				try {
					g.drawImage(tile[mapCoor[row][col]].getImage(), col * tileSize, row * tileSize);
				} catch(Exception e) {
					//e.printStackTrace();
				}
			}
		}
		
	} // end drawMap
}
