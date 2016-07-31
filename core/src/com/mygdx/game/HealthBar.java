package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class HealthBar {
	
	private int maxHealth; 
	private int health; 
	
	public static final int WIDTH = 200; 
	public static final int HEIGHT = 20; 
	
	private static final Color MAX_COLOR = new Color(1, 0, 0, 1); 
	private static final Color CUR_COLOR = new Color(0, 1, 0, 1);
	
	private int x; 
	private int y; 
	
	ShapeRenderer shapeRenderer; 
	
	public HealthBar(int x, int y, int maxHealth) {
		this.x = x; 
		this.y = y; 
		
		this.maxHealth = maxHealth; 
		this.health = maxHealth; 
		
		
		shapeRenderer = new ShapeRenderer(); 
	}
	
	public void draw() {
		float fractionHealth = (float) health / maxHealth; 
		
		shapeRenderer.begin(ShapeType.Filled); 
		shapeRenderer.setColor(MAX_COLOR);
		shapeRenderer.rect(x, y, WIDTH, HEIGHT);
		shapeRenderer.setColor(CUR_COLOR);
		shapeRenderer.rect(x, y, WIDTH * fractionHealth, HEIGHT); 
		shapeRenderer.end(); 
	}
	
	public void setHealth(int health) {
		this.health = health;  
	}
}
