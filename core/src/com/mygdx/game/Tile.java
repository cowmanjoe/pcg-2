package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
public abstract class Tile {
	
	protected Texture texture; 
	
	protected boolean solid; 
	
	
	public void draw(Batch batch, int x, int y) {
		batch.draw(texture, x, y);
		
	}
	
	public int getWidth() {
		return texture.getWidth(); 
	}
	
	public int getHeight() {
		return texture.getHeight(); 
	}
	
	
	public abstract String getType(); 
}
