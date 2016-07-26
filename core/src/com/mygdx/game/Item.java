package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite{
	
	private Type type;
	
	private static final int APPLE_VALUE = 20; 
	private static final int BANANA_VALUE = 40; 
	private static final int CHOCOLATE_VALUE = 70; 
	
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
	
	public int getValue() {
		switch(type) {
		case APPLE:
			return APPLE_VALUE; 
		case BANANA:
			return BANANA_VALUE;
		case CHOCOLATE:
			return CHOCOLATE_VALUE; 
		default:
			return 0; 
		}
	}
}
