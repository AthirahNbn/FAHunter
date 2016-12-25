package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import application.model.TileType;

public class CoordinatesReader {
	
	public ArrayList<String> genFreeTiles(TileType[][] tileData) {
		ArrayList<String> freeTiles = new ArrayList<String>();
		
		for(int row = 0; row < TileSetHandler.mapHeight; row++) {
			freeTiles.add(row, "");
			
			for(int col = 0; col < TileSetHandler.mapWidth; col++) {
				if(tileData[row][col].getType() == TileType.SAFE) {
					freeTiles.add(row, freeTiles.get(row).concat(String.valueOf(col)).concat(" "));
				}
			}
		}
		
		return freeTiles;
	}
	
	public TileType[][] loadCoor(String loc, TileType[][] tileData) {
		try {
    		Scanner s = new Scanner(new File(loc));
    		String delim = "\\s+"; //one or more whitespace characters
			ArrayList<String> freeTiles = genFreeTiles(tileData);
    		
    		while(s.hasNextLine()) {
    			// splits line of tokens by whitespace-character delimiter
    			String line = s.nextLine();
				String[] tokens = line.split(delim); // tokens represent tile types
				
				for (int row = 0; row < TileSetHandler.mapHeight; row++) {
					
					if(freeTiles.get(row) == "") {
						continue;
					} else if(Integer.parseInt(tokens[1]) < row) {
						for(int i = 0; i < TileSetHandler.mapWidth; i++) {
							tileData[row][i].setType(TileType.BLOCKED);
						}
						
						continue;
					} else if(Integer.parseInt(tokens[1]) == row) {
						for(int i = 2; i < tokens.length; i++) {
							tileData[row][Integer.parseInt(tokens[i])].setType(Integer.parseInt(tokens[0]));
						}
						
						break;
					}
				}
    		}
    		
    		s.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		
		return tileData;
	} // end loadMap
}
