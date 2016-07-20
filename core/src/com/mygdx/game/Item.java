package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite{
	
	private Type type;
	
	public Item(Type t) {
		super(getTextureFromType(t)); 
		type = t; 
		
	}
	
	
	private static Texture getTextureFromType(Type t) {
		switch (t) {
		case APPLE: 
			return new Texture("apple.png"); 
		case BANANA:
			return new Texture("banana.png"); 
		case CHOCOLATE:
			return new Texture("chocolate.png"); 
		default: 
			return null; 
		}
	}
	
	public Type getType() {
		return type; 
	}
	
	public enum Type {
		APPLE, BANANA, CHOCOLATE
	}
}
