package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ExitTile extends Tile {
	
	public ExitTile() {
		solid = false; 
		
		texture = new Texture("exitTile.png");
	}
	
	public String getType() {
		return "exit"; 
	}
	
	public List<Item> getItems() {
		return null; 
	}
}
