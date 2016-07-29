package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Food.FoodType;
import com.mygdx.game.Weapon.WeaponType;

public class Item extends Sprite{
	
	
	public Item(Texture texture) {
		super(texture); 
	}
	
	
	public static Item randomItem() {
		Random r = new Random(); 
		if (r.nextBoolean()) {
			// random weapon
			int numVals = WeaponType.values().length; 
			WeaponType type = WeaponType.values()[r.nextInt(numVals)];
			Weapon weapon = new Weapon(type); 
			return weapon; 
		}
		else {
			// random food
			int numVals = FoodType.values().length; 
			FoodType type = FoodType.values()[r.nextInt(numVals)]; 
			Food food = new Food(type); 
			return food; 
		}
	}
	
}
