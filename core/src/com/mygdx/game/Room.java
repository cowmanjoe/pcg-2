package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// INVARIANT: Tiles must be all the same width and height
public class Room {
	
	public static final int DEFAULT_WIDTH = 20; 
	public static final int DEFAULT_HEIGHT = 20; 
	
	private Tile[][] tiles; 
	
	private int x; 
	private int y; 
	
	public Room(Tile[][] tiles) {
		this.tiles = tiles; 
	}
	
	// Draws the room on the screen, using tiles[0][0] 
	// as the width and height for all the tiles
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {				
				tiles[i][j].draw(batch, x + i * tiles[0][0].getWidth(), y + j * tiles[0][0].getHeight());
			}
		}
	}
	
	// Returns number of tiles in the horizontal direction
	public int getNumXTiles() {
		return tiles.length; 
	}
	
	// Returns number of tiles in the vertical direction
	// Uses tiles[0] as the reference for height
	public int getNumYTiles() {
		return tiles[0].length; 
	}
	
	public int getTileWidth() {
		return tiles[0][0].getWidth(); 
	}
	
	public int getTileHeight() {
		return tiles[0][0].getHeight(); 
	}
	
	public int getWidth() {
		return getNumXTiles() * getTileWidth(); 
	}
	
	public int getHeight() {
		return getNumYTiles() * getTileHeight(); 
	}
	
	public void setX(int x) {
		this.x = x; 
	}
	
	public int getX() {
		return x; 
	}
	
	public void setY(int y) {
		this.y = y; 
	}
	
	public int getY() {
		return y; 
	}
}
