package application;

import java.io.BufferedReader;
import java.io.FileReader;

import application.model.TileType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class TileSetHandler{
	
	public static int tileSize = 30;
	
	private GraphicsContext g;
		
	private int[][] mapCoor;
	private int mapWidth;
	private int mapHeight;
	
	public TileType[] splitTileSet(String loc, int numRows){
		System.out.println("Loading tile set...");
		
		int numTiles = 0; 
		int tileSetWidth;
		WritableImage[][] tileImages = null;
		
		try {
			Image tileSet = new Image(loc);
			tileSize = (int) (tileSet.getHeight() / numRows);
			tileSetWidth = (int) (tileSet.getWidth() / tileSize);
			numTiles = tileSetWidth * numRows;
			
			PixelReader pr = tileSet.getPixelReader();
			tileImages = new WritableImage[numRows][tileSetWidth];
			
			// separates tile set image into individual tile images
			for(int i = 0; i < tileSetWidth; i++) {
				tileImages[0][i] = new WritableImage(pr, tileSize * i, 0, tileSize, tileSize);
				tileImages[1][i] = new WritableImage(pr, tileSize * i, tileSize, tileSize, tileSize);
			}
			
			System.out.println("Tile set loaded.");
		} catch(Exception e) {
			System.out.println("ERROR:\nCouldn't load tileset.\n");
			e.printStackTrace();
		}

		TileType[] tiles = new TileType[numTiles];
		int width = numTiles / numRows;
		for(int i = 0; i < width; i++) {
			tiles[i] = new TileType(tileImages[0][i], 0);
			//tile[i].setPosition(i * TILESIZE, HEIGHT - 2 * TILESIZE);
			tiles[i + width] = new TileType(tileImages[1][i], 1);
			//tile[i + width].setPosition(i * TILESIZE, HEIGHT - TILESIZE);
		}
		
		return tiles;
	} // end loadTileSet
	
	public void loadMap(String loc){
		System.out.println("Loading map...");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(loc));
			
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
	public void drawMap(Canvas canvas, TileType[] tiles){
		g = canvas.getGraphicsContext2D();
		
		for(int row = 0; row < mapHeight; row++) {
			for(int col = 0; col < mapWidth; col++) {
				try {
					g.drawImage(tiles[mapCoor[row][col]].getImage(), col * tileSize, row * tileSize);
				} catch(Exception e) {
					//e.printStackTrace();
				}
			}
		}
		
	} // end drawMap
}