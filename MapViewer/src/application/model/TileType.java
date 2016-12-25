package application.model;

import javafx.scene.image.Image;

public class TileType {
	
	private Image image;
	private int type;
	
	// tile types
	public static final int SAFE = 2;
	public static final int BOAT = 1;
	public static final int BLOCKED = 0;
	
	public TileType(Image image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public Image getImage() { 
		return image; 
	}
	
	public void setType (int type) { 
		this.type = type; 
	}
	
	
	public int getType() { 
		return type; 
	}
	
}
