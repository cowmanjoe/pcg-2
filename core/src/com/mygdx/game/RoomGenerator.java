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

				// If to check borders to make walls borders
				// and 1/20 border tiles exits (except corners)
				if (i == 0 || j == 0 || i == tiles.length - 1 || j == tiles[i].length - 1) {		
					if (r.nextInt(20) == 0 && 
							!(i == 0 && j == 0) && 
							!(i == tiles.length - 1 && j == 0) &&
							!(i == 0 && j == tiles[i].length - 1) && 
							!(i == tiles.length - 1 && j == tiles[i].length - 1))  {
						tiles[i][j] = new ExitTile();
						System.out.println("Exit placed at i = " + i + " and j = " + j);
					}
						
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
		System.out.println("Score: " + evaluateRoom(tiles)); 
		
		return tiles; 
	}
	
	public static float evaluateRoom(Tile[][] tile) {
		int numExits = 0; 
		int numItems = 0; 
		
		float score = 0; 
		
		for (int i = 0; i < tile.length; i++ ) {
			for (int j = 0; j < tile[i].length; j++) {
				if (tile[i][j].getType() == "exit")
					numExits++; 
				
				if (tile[i][j].getType() == "floor") {
					numItems += ((FloorTile) tile[i][j]).getItems().size(); 
				}
			}
		}
		
		System.out.println("Room has " + numExits + " exits and " + numItems + " items.");
		
		score = 5 * numItems * (1f / numExits); 
		
		if (numExits == 0) 
			score = 0; 
		
		return score;
	}
}
