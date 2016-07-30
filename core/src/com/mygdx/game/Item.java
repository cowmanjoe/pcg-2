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
		Random r = new Random() ;
		if (r.nextBoolean()) {
			// random weapon
			return randomWeapon();
		}
		else {
			// random food
			return randomFood();
		}
	}
	
	// Returns either a weapon, food or null
	// based on the fraction of chance of each 
	public static Item randomItem(float foodChance, float weaponChance) {
		Random r = new Random(); 
		if (foodChance + weaponChance > 1)
			throw new IllegalArgumentException("foodChance + weaponChance must be at most 1"); 
		
		float num = r.nextFloat();
		if (num < foodChance) {
			return randomFood(); 
		}
		else if (num < foodChance + weaponChance) 
			return randomWeapon(); 
		else 
			return null; 
	}
	
	public static Weapon randomWeapon() {
		Random r = new Random(); 
		int numVals = WeaponType.values().length; 
		WeaponType type = WeaponType.values()[r.nextInt(numVals)];
		Weapon weapon = new Weapon(type); 
		return weapon; 
	}
	
	public static Food randomFood() {
		Random r = new Random(); 
		int numVals = FoodType.values().length; 
		FoodType type = FoodType.values()[r.nextInt(numVals)]; 
		Food food = new Food(type); 
		return food; 
	}
	
}
