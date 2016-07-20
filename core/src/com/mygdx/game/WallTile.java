package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WallTile extends Tile {
	
	public WallTile() {
		solid = false; 
		
		texture = new Texture("wallTile.png");
	}
	
	
	
}
