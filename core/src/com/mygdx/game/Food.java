package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Food extends Item {
	
	private static final int APPLE_VALUE = 20; 
	private static final int BANANA_VALUE = 40; 
	private static final int CHOCOLATE_VALUE = 70; 
	
	private FoodType type; 
	
	public Food(FoodType t) {
		super(t.getTexture());
		type = t; 
	}
	
	public int getValue() {
		return type.getValue(); 
	}
	
	public FoodType getFoodType() {
		return type; 
	}
	
	public enum FoodType {
		APPLE(APPLE_VALUE, "apple.png"), 
		BANANA(BANANA_VALUE, "banana.png"), 
		CHOCOLATE(CHOCOLATE_VALUE, "chocolate.png");
		
		
		private int healingValue; 
		private String filePath; 
		
		FoodType(int healingValue, String path) {
			this.healingValue = healingValue;
			filePath = path;
		}
		
		public int getValue() {
			return healingValue; 
		}
		
		protected Texture getTexture() {
			return new Texture(filePath); 
		}
		
	}
	
	
}
