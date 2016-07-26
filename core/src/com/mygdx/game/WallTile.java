package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WallTile extends Tile {
	
	public WallTile() {
		solid = true; 
		
		texture = new Texture("wallTile.png");
	}
	
	public String getType() {
		return "wall"; 
	}
	
	@Override
	public List<Item> getItems() {
		return null; 
	}
	
}
