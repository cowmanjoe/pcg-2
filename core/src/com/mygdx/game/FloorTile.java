package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public class FloorTile extends Tile {
	private List<Item> items; 
	
	public FloorTile() {
		items = new ArrayList<Item>(); 
		solid = false; 
		
		texture = new Texture("floorTile.png");
	}
	
	@Override
	public void draw(SpriteBatch batch, int x, int y) {
		super.draw(batch, x, y);
		for (Item i : items) {
			i.setX(x + texture.getWidth() / 2 - i.getWidth() / 2);
			i.setY(y + texture.getHeight() / 2 - i.getHeight() / 2);
			i.draw(batch);
		}
	}
	
	public void addItem(Item.Type type) {
		items.add(new Item(type));
		if (items.size() > 1) {
			System.out.println(items.size() + " items on the tile.");
		}
	}
}