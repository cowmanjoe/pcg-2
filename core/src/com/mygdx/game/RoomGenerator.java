package com.mygdx.game;

import java.util.Random;

import com.mygdx.game.Item.Type;

public class RoomGenerator {
	
	
	
	public static Room newRoom(int width, int height) {
		Tile[][] tiles = newTiles(width, height); 
		
		return new Room(tiles); 
		
		
	}
	
	public static Tile[][] newTiles(int width, int height) {
		Random r = new Random(); 

		Tile[][] tiles = new Tile[width][height]; 

		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				// Generate tile randomly based on location

				// Placeholder (make all tiles floor tiles)
				if (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[i].length - 1) {		
					if (r.nextInt(20) == 0) 
						tiles[i][j] = new FloorTile();
					else 
						tiles[i][j] = new WallTile(); 
				}
				else {
					FloorTile tile = new FloorTile(); 

					for (int k = 0; k < 3; k++) {
						if (r.nextInt(10) == 0) {
							Item.Type type = Type.values()[r.nextInt(Item.Type.values().length)];
							tile.addItem(type);
						}
					}
					
					
					
					tiles[i][j] = tile; 
				}
			}
		}
		
		return tiles; 
	}
}
