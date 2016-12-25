package application;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class Font{
	
	private Image font;
	private WritableImage[][] fontCharTitle;
	private WritableImage[][] fontCharSubtitle;
	private GraphicsContext g;
	
    final int NUMCHAR = 8;
	final double SCALE = 0.625;
	
	//Split font tile set to individual characters
	public WritableImage[][] loadFont(int size) {
		
		try {
			font = new Image("Resources/font.gif", size * NUMCHAR, size * NUMCHAR, true, true);
			
			PixelReader pr = font.getPixelReader();
			WritableImage[][] fontChar = new WritableImage[NUMCHAR][NUMCHAR];
			for(int i = 0; i < NUMCHAR; i++) {
				for(int j = 0; j < NUMCHAR; j++) {
					fontChar[i][j] = new WritableImage(pr, j * size, i * size, size, size);
				}
			}
			
			return fontChar;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:\n Couldn't load font graphics.\n");
		}
		
		return null;
	}// end loadFont
	
	//Draw font
	public void drawString(Canvas canvas, String s, int xStartPos, int yStartPos, int titletype) {
		s = s.toUpperCase();
		g = canvas.getGraphicsContext2D();
		int titleCharGap = 14;
		int subtitleCharGap = 13;
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 47) c = 36; // slash
			if(c == 58) c = 37; // colon
			if(c == 32) c = 38; // space
			if(c >= 65 && c <= 90) c -= 65; // letters
			if(c >= 48 && c <= 57) c -= 22; // numbers
			
			fontCharTitle = loadFont(TileSetHandler.tileSize);
			fontCharSubtitle = loadFont(TileSetHandler.tileSize / 4 * 3);
			
			int titleRow = c / fontCharTitle[0].length;
			int titleCol = c % fontCharTitle[0].length;
			int subtitleRow = c / fontCharSubtitle[0].length;
			int subtitleCol = c % fontCharSubtitle[0].length;

			
			if (titletype == 0) g.drawImage(fontCharTitle[titleRow][titleCol], xStartPos + i * titleCharGap, yStartPos);
			else g.drawImage(fontCharSubtitle[subtitleRow][subtitleCol], xStartPos + i * subtitleCharGap, yStartPos);
		}
	}// end drawString
}