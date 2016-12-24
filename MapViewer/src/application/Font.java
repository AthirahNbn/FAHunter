package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Font{
	
	private Image all_fonts;
	private WritableImage[][] fonts;
	private static double SCALE1 = 0.75;
	private static double SCALE2 = 0.5;
	private GraphicsContext g;
	
	public void loadFont() {
		int w = 8, h = 8;
		try {
			all_fonts = new Image("Resources/font.gif");
			int width = (int) (all_fonts.getWidth() / w);
			int height = (int) (all_fonts.getHeight() / h);
			PixelReader pr = all_fonts.getPixelReader();
			fonts = new WritableImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					fonts[i][j] = new WritableImage(pr, j * w, i * h, w, h);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:\n Couldn't load font graphics.\n");
			System.exit(0);
		}
	}
	
	public void drawString(Canvas canvas, String s, int x, int y, int j) {
		s = s.toUpperCase();
		g = canvas.getGraphicsContext2D();
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 47) c = 36; // slash
			if(c == 58) c = 37; // colon
			if(c == 32) c = 38; // space
			if(c >= 65 && c <= 90) c -= 65; // letters
			if(c >= 48 && c <= 57) c -= 22; // numbers
			int row = c / fonts[0].length;
			int col = c % fonts[0].length;
			if (j==0) g.drawImage(fonts[row][col], x + 13 * i, y, TileSetHandler.tileSize*SCALE1, TileSetHandler.tileSize*SCALE1);
			else g.drawImage(fonts[row][col], x + 14 * i, y, TileSetHandler.tileSize*SCALE2, TileSetHandler.tileSize*SCALE2);
		}
	}
}