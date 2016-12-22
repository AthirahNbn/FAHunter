package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.imageio.ImageIO;
import javafx.scene.canvas.GraphicsContext;

public class TestDrawMap {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 1;
	public static int TILESIZE = 30;
	public static final int numRows = 2;
	
	private BufferedImage tileset;
	private BufferedImage[][] tiles;
	private int numTiles;
	
	private BufferedImage image;
	private GraphicsContext g;
	
	private TileView[] tile;
		
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	
	private void loadTileSets(String s){
		System.out.println("LOADING " + s);
				try {
					//IMPORTANT - load tileset
					tileset = ImageIO.read(new File(s));//read the files
					TILESIZE = tileset.getHeight() / 2;//set the size of tile
					int width = tileset.getWidth() / TILESIZE;
					numTiles = width * numRows;//set number of tile
					tiles = new BufferedImage[numRows][width];//array of the tile of the tile set as image
					for(int i = 0; i < width; i++) {//get the image of the tile
						tiles[0][i] = tileset.getSubimage(
							TILESIZE * i,
							0,
							TILESIZE,
							TILESIZE
						);
						tiles[1][i] = tileset.getSubimage(
							TILESIZE * i,
							TILESIZE,
							TILESIZE,
							TILESIZE
						);
					}
					
				}
				catch(Exception e) {
					System.out.println("Couldn't load " + s);
					e.printStackTrace();
				}
				
				tile = new TileView[numTiles];
				int width = numTiles / numRows;
				for(int i = 0; i < width; i++) {
					tile[i] = new TileView(tiles[0][i]);
					//tile[i].setPosition(i * TILESIZE, HEIGHT - 2 * TILESIZE);
					tile[i + width] = new TileView(tiles[1][i]);
					//tile[i + width].setPosition(i * TILESIZE, HEIGHT - TILESIZE);
				}
	}
	
	private void loadMap(String s){
		String str = "testmap.map";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(str));//start reading .map file
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
		}
		catch(Exception e) {
			System.out.println("Couldn't load maps/" + str);
			e.printStackTrace();
		}
	}

	private void drawMap(){
		
	}
}
