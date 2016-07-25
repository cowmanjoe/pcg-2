package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

// INVARIANT: Tiles must be all the same width and height
public class Room extends Actor{
	
	public static final int DEFAULT_WIDTH = 20; 
	public static final int DEFAULT_HEIGHT = 20; 
	
	private Tile[][] tiles; 
	
	
	private Color tint; 
	
	public Room(Tile[][] tiles) {
		this.tiles = tiles; 
		tint = new Color(1, 1, 1, 1); 
	}
	
	// Draws the room on the screen, using tiles[0][0] 
	// as the width and height for all the tiles
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Color prevColor = batch.getColor(); 
		batch.setColor(tint);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {				
				tiles[i][j].draw(batch, (int)(getX() + i * tiles[0][0].getWidth()), (int)(getY() + j * tiles[0][0].getHeight()));
			}
		}
		batch.setColor(prevColor);
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
	
	public float getWidth() {
		return getNumXTiles() * getTileWidth(); 
	}
	
	public float getHeight() {
		return getNumYTiles() * getTileHeight(); 
	}
	
	public void setRGB(float r, float g, float b) {
		tint = new Color(r, g, b, 1); 
	}
	
	public String getTileType(int x, int y) {
		return tiles[x][y].getType(); 
	}
	
	public boolean isTileSolid(int x, int y) {
		return  tiles[x][y].solid; 
	}
}
