package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

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
					tiles[i][j] = new WallTile(); 
				}
				else {
					FloorTile tile = new FloorTile(); 

					for (int k = 0; k < 3; k++) {
						if (r.nextInt(10) == 0) {
							Item item = Item.randomItem(); 
							tile.addItem(item);
						}
					}
					
					
					
					tiles[i][j] = tile; 
				}
			}
		}
		
		// Generate an entrance and exit at the top and bottom
		tiles[1 + r.nextInt(tiles.length - 2)][0] = new ExitTile(); 
		tiles[1 + r.nextInt(tiles.length - 2)][tiles[0].length - 1] = new ExitTile(); 
		
		
		
		System.out.println(); 
		
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
	
	public static List<NormalEnemy> randomEnemies(Room room, int numEnemies) {
		List<NormalEnemy> enemies = new ArrayList<NormalEnemy>(); 
		List<Vector2> enemyPositions = new ArrayList<Vector2>(); 
		Random r = new Random(); 
		
		
		for(int i = 0; i < numEnemies; i++) {
			boolean foundPosition = false; 
			while(!foundPosition) {
				int x = r.nextInt(room.getNumXTiles());
				int y = r.nextInt(room.getNumYTiles()); 
				
				boolean isOccupied = false; 
				for (NormalEnemy e : enemies) {
					if (e.getXTile() == x && e.getYTile() == y)
						isOccupied = true; 
				}
				
				if (room.getTileType(x, y) == "floor" && 
						!isOccupied) {
					NormalEnemy enemy = new NormalEnemy((int)(room.getX() + x * room.getTileWidth()), 
							(int)(room.getY() + y * room.getTileHeight()), room); 
					enemy.setX(enemy.getX() + room.getTileWidth() / 2 - enemy.getWidth() / 2);
					enemy.setY(enemy.getY() + room.getTileHeight() / 2 - enemy.getHeight() / 2);
					
					enemies.add(enemy); 
					foundPosition = true; 
				}
			}
		}
		
		
		
		
		return enemies; 
	}
	
}
