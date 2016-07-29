package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Weapon extends Item {

	private WeaponType type; 
	
	public Weapon(WeaponType t) {
		super(t.getTexture());
		type = t; 
		
		
	}
	
	
	public int getDamage() {
		return type.getDamage(); 
	}
	
	public enum WeaponType {
		SWORD(50, "sword.png"), 
		HAMMER(30, "hammer.png"), 
		SHOTGUN(60, "shotgun.png"); 
		
		private int damage; 
		private String filePath; 
		
		WeaponType(int damage, String path) {
			this.damage = damage; 
			filePath = path; 
		}
		
		public int getDamage() {
			return damage; 
		}
		
		protected Texture getTexture() {
			return new Texture(filePath); 
		}
	}
}
