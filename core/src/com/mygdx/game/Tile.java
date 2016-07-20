package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile {
	
	protected Texture texture; 
	
	protected boolean solid; 
	
	
	public void draw(SpriteBatch batch, int x, int y) {
		batch.draw(texture, x, y);
		
	}
	
	public int getWidth() {
		return texture.getWidth(); 
	}
	
	public int getHeight() {
		return texture.getHeight(); 
	}
}
