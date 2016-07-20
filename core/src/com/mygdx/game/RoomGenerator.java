package com.mygdx.game;

public class RoomGenerator {
	
	
	
	public static Room newRoom(int width, int height) {
		Tile[][] tiles = new Tile[width][height]; 
		
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				// Generate tile randomly based on location
				
				// Placeholder (make all tiles floor tiles)
				tiles[i][j] = new FloorTile(); 
			}
		}
		
		return new Room(tiles); 
		
		
	}
}
