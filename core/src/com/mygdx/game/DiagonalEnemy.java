package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Pool;

public class DiagonalEnemy extends Enemy{

	
	private boolean hit; 
	
	private static final int DEFAULT_HEALTH = 100; 
	
	
	private Action followPlayer; 
	
	public DiagonalEnemy(int x, int y, Room room) {
		super(x, y, room); 
		TextureRegion[][] sprites = TextureRegion.split(new Texture("spriteSheet1.png"), 
				32, 32);
		
		TextureRegion[] idleTextures = new TextureRegion[6]; 
		TextureRegion[] hitTextures = new TextureRegion[2]; 
		
		for (int i = 0; i < idleTextures.length; i++) {
			if (i < 4) {
				idleTextures[i] = sprites[1][4 + i];
			}
			else {
				idleTextures[i] = sprites[2][i - 4];
			}
		}
		
		for (int i = 0; i < hitTextures.length; i++) {
			hitTextures[i] = sprites[5][i + 4]; 
		}
		
		Animation idleAnimation; 
		Animation hitAnimation; 
		
		idleAnimation = new Animation(0.1f, idleTextures); 
		hitAnimation = new Animation(0.15f, hitTextures); 
		
		idleAnimation.setPlayMode(PlayMode.LOOP); 
		hitAnimation.setPlayMode(PlayMode.NORMAL); 
		
		animations.add(idleAnimation); 
		animations.add(hitAnimation); 
		
		currentAnimation = animations.get(0); 
		
		targetXTile = getXTile(); 
		targetYTile = getYTile(); 
		
		SequenceAction sequence = new SequenceAction(); 
		RunnableAction stepToPlayer = 
				Actions.run(new Runnable() {
					@Override
					public void run() {
						moveToPlayer(); 
					}
				}); 
		
		DelayAction delay = new DelayAction(MOVE_DELAY); 
		
		sequence.addAction(stepToPlayer);
		sequence.addAction(delay);
		
		followPlayer = Actions.forever(sequence); 
		
		addAction(followPlayer); 
		
		
		health = DEFAULT_HEALTH; 
		hit = false; 
		damage = 20; 
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		 
		if (hit) {
			batch.draw(currentAnimation.getKeyFrame(time), getX(), getY());
			if (currentAnimation.isAnimationFinished(time)) {
				hit = false; 
				currentAnimation = animations.get(0); 
			}
		} else { 
			batch.draw(currentAnimation.getKeyFrame(time), getX(), getY()); 
		}
		time += Gdx.graphics.getDeltaTime();
	}
	
	// dirX, dirY = -1, 0, 1
	// only one of dirX and dirY should not be 0
	// Attacks the player if he is in the way
	public void move(int dirX, int dirY) {
		Room r = room; 
		
		boolean willHitPlayer = r.getPlayer().getTargetXTile() == getXTile() + dirX &&
				r.getPlayer().getTargetYTile() == getYTile() + dirY;
		
		boolean willHitEnemy = false; 
		
		for (Enemy e : r.getEnemies()) {
			if (e.getTargetXTile() == getXTile() + dirX &&
					e.getTargetYTile() == getYTile() + dirY)
				willHitEnemy = true; 
		}
		
		
		if (willHitPlayer) {
			attackPlayer();
			return; 
		}
		
		if (canMoveTo(getXTile() + dirX, getYTile() + dirY) && !willHitEnemy) {
			targetXTile = getXTile() + dirX; 
			targetYTile = getYTile() + dirY; 
			
			
			MoveToAction moveAction = moveActions.obtain(); 
			moveAction.setDuration(MOVE_TIME); 
			moveAction.setPosition(getX() + r.getTileWidth() * dirX, getY() + r.getTileHeight() * dirY);

			addAction(moveAction); 

		}
		else {
			SequenceAction sequence = new SequenceAction(); 

			MoveToAction moveThere = moveActions.obtain(); 
			MoveToAction moveBack = moveActions.obtain(); 
			moveThere.setDuration(MOVE_TIME / 2);
			moveThere.setPosition(getX() + r.getTileWidth() * dirX / 5, getY() + r.getTileHeight() * dirY / 5);
			moveBack.setDuration(MOVE_TIME / 2);
			moveBack.setPosition(getX(0), getY());

			sequence.addAction(moveThere);
			sequence.addAction(moveBack);

			addAction(sequence); 
		}
	}
	
	// Move diagonally in the direction of the target
	// May only move 2 spaces (so 1 diagonal space) at a time
	public void moveToTarget(int targetX, int targetY) {
		Random r = new Random(); 
		
		if (getXTile() == targetX && getYTile() == targetY)
			return; 
		if (getXTile() != targetX && getYTile() == targetY) {
			if (targetX < getXTile()) 
				move(-1, r.nextInt(2) * 2 - 1); 
			else 
				move(1, r.nextInt(2) * 2 - 1); 
		}
		else if (getXTile() == targetX && getYTile() != targetY) {
			if (targetY < getYTile())
				move(r.nextInt(2) * 2 - 1, -1); 
			else 
				move(r.nextInt(2) * 2 - 1, 1); 
			
		}
		else if (getXTile() != targetX && getYTile() != targetY) {
			int xDir; 
			int yDir; 
			
			if (targetX < getXTile())
				xDir = -1; 
			else 
				xDir = 1; 
			
			if (targetY < getYTile())
				yDir = -1; 
			else 
				yDir = 1; 
			
			move(xDir, yDir);
		}
		
		
	}
	
	public void moveToPlayer() {
		int playerX = room.getPlayer().getXTile(); 
		int playerY = room.getPlayer().getYTile(); 
		
		moveToTarget(playerX, playerY); 
	}
	
	public int getXTile() {
		Room r = room; 
		return (int)Math.floor((getX() - r.getX()) / r.getTileWidth()); 
	}
	
	public int getYTile() {
		Room r = room; 
		return (int)Math.floor((getY() - r.getY()) / r.getTileHeight()); 
	}
	
	private boolean canMoveTo(int tileX, int tileY) {
		Room r = room; 
		return !r.isTileSolid(tileX, tileY); 
	}
	
	private void attackPlayer() {
		Room r = room;
		
		SequenceAction sequence = new SequenceAction(); 

		MoveToAction moveThere = moveActions.obtain(); 
		RunnableAction hit = Actions.run(new Runnable() {
			@Override
			public void run() {
				hit(); 
			}
		});
		
		MoveToAction moveBack = moveActions.obtain(); 
		moveThere.setDuration(MOVE_TIME / 2);
		moveThere.setPosition(getX() + (r.getPlayer().getX() - getX()) / 5, getY() + (r.getPlayer().getY() - getY()) / 5);
		
		
		
		moveBack.setDuration(MOVE_TIME / 2);
		moveBack.setPosition(getX(), getY());

		sequence.addAction(moveThere);
		sequence.addAction(hit);
		sequence.addAction(moveBack);

		addAction(sequence); 
		
		room.getPlayer().damage(damage);
	}
	
	private void hit() {
		currentAnimation = animations.get(1); 
		hit = true; 
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
	
	
}
