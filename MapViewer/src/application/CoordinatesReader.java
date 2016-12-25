package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import application.model.TileType;

public class CoordinatesReader {
	
	public String[] specialCases = {"25 11", "26 9 10 11", "27 8 9 10", "28 8 9", "37 35 36 37", "38 36"};
	
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
	
	public boolean conditionalPos(int row, int col) {
	 	Scanner s;
	 	
	 	for(int i = 0; i < specialCases.length; i++) {
	 		s = new Scanner(specialCases[i]);
	 		
	 		if(row == s.nextInt()) {
	 			
	 			System.out.println("ROW MATCH TOKEN");
	 			
	 			while(s.hasNext()) {
	 				if(col == s.nextInt()) {
	 					System.out.println("COL MATCH TOKEN");
	 					s.close();
	 					return true;
	 				}
	 			}
	 		}
	 		
	 		s.close();
	 	}
	 	
	 	return false;
	} // end conditionalPos
}
