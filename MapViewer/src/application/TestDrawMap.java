package application;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TestDrawMap {
	
	@FXML Button generate;
	
	//dimension of panel
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	public static final int SCALE = 1;
	//information of tile set
	public static int TILESIZE = 30;
	public static final int numRows = 2;
	
	private BufferedImage tileset;
	private BufferedImage[][] tiles;
	private int numTiles;
	
	private BufferedImage image;
	private Graphics2D g;
	
	//map info
	private int[][] map;
	private int mapWidth;
	private int mapHeight;

	public void init() {
			
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			g = (Graphics2D) image.getGraphics();
			loadTileSet();
			loadMap();
			draw();
			
	}
	
	public void draw() {
		Graphics g2 = image.getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	public void loadTileSet() {
		String s = "/Resource/testtileset.gif";
		System.out.println("LOADING " + s);

		try {
			//IMPORTANT - load tileset
			tileset = ImageIO.read(new File(s));//read the files
			TILESIZE = tileset.getHeight() / 2;//set the size of tile
			int width = tileset.getWidth() / TILESIZE;
			setNumTiles(width * numRows);//set number of tile
			tiles = new BufferedImage[numRows][width];//array of the tile of the tileset
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
	}

	public void loadMap(){
		String str = "/testmap.map";
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
	
	

	public int getNumTiles() {return numTiles;}
	public void setNumTiles(int numTiles) {this.numTiles = numTiles;}
}
