package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Pool;

public abstract class Enemy extends AnimatedSprite {
	
	protected int damage; 
	protected int health; 
	
	protected int targetXTile; 
	protected int targetYTile; 
	
	protected Room room; 
	
	protected static final float MOVE_TIME = 0.1f; 
	protected static final float MOVE_DELAY = 2.0f; 
	
	protected Pool<MoveToAction> moveActions; 
	
	
	public Enemy(int x, int y, Room room) {
		super(); 
		this.room = room; 
		
		setX(x); 
		setY(y); 
		
		
		
		moveActions = new Pool<MoveToAction>() {
			@Override
			protected MoveToAction newObject() {
				return new MoveToAction(); 
			}
		};
		
		
	}
	
	public void moveToPlayer() {
		int playerX = room.getPlayer().getXTile(); 
		int playerY = room.getPlayer().getYTile(); 
		
		moveToTarget(playerX, playerY); 
	}
	
	public void damage(int damage) {
		health -= damage; 
	}
	
	public int getHealth() {
		return health; 
	}
	
	public boolean isDead() {
		return health <= 0; 
	}
	
	public int getTargetXTile() {
		return targetXTile; 
	}
	
	public int getTargetYTile() {
		return targetYTile; 
	}
	
	public int getXTile() {
		return (int)Math.floor((getX() - room.getX() + getWidth() / 2) / room.getTileWidth()); 
	}
	
	public int getYTile() {
		return (int)Math.floor((getX() - room.getX() + getHeight() / 2) / room.getTileHeight());
	}
	
	public abstract void draw(Batch batch, float parentAlpha);
	
	public abstract void move(int dirX, int dirY);
	
	public abstract void moveToTarget(int targetX, int targetY); 
	
	
}
