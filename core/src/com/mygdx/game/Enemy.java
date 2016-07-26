package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends AnimatedSprite {
	
	private boolean hit; 
	
	private static final float MOVE_TIME = 0.1f; 
	private static final float MOVE_DELAY = 2.0f; 
	
	private Pool<MoveToAction> moveActions; 
	private Action followPlayer; 
	
	public Enemy(int x, int y) {
		animations = new ArrayList<Animation>(); 
		
		TextureRegion[][] sprites = TextureRegion.split(new Texture("spriteSheet1.png"), 
				32, 32);
		
		TextureRegion[] idleTextures = new TextureRegion[6]; 
		TextureRegion[] hitTextures = new TextureRegion[2]; 
		
		for (int i = 0; i < idleTextures.length; i++) {
			if (i < 2) {
				idleTextures[i] = sprites[0][6+i];
			}
			else {
				idleTextures[i] = sprites[1][i - 2];
			}
		}
		
		for (int i = 0; i < hitTextures.length; i++) {
			hitTextures[i] = sprites[5][i + 2]; 
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
		
		moveActions = new Pool<MoveToAction>() {

			@Override
			protected MoveToAction newObject() {
				return new MoveToAction();
			}
			
		};
		
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
		
		time = 0; 
		
		setX(x); 
		setY(y); 
		
		hit = false; 
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
	public void move(int dirX, int dirY) {
		Room r = PCGGame.getInstance().getCurrentRoom(); 

		if (canMoveTo(getXTile() + dirX, getYTile() + dirY)) {
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
	
	// Move one tile in the target's direction 
	// either vertically or horizontally.
	// If both are possible, only one of vertical or horizontal movement
	// is chosen randomly.
	public void moveToTarget(int targetX, int targetY) {
		Random r = new Random(); 
		
		if (getXTile() == targetX && getYTile() == targetY)
			return; 
		if (getXTile() != targetX && getYTile() == targetY) {
			if (targetX < getXTile()) 
				move(-1, 0); 
			else 
				move(1, 0); 
		}
		else if (getXTile() == targetX && getYTile() != targetY) {
			if (targetY < getYTile())
				move(0, -1); 
			else 
				move(0, 1); 
			
		}
		else if (getXTile() != targetX && getYTile() != targetY) {
			if (r.nextBoolean()) {
				if (targetX < getXTile())
					move(-1, 0); 
				else 
					move(1, 0); 
			}
			else  {
				if (targetY < getYTile()) 
					move(0, -1); 
				else 
					move(0, 1); 
			}
				
		}
		
		
	}
	
	public void moveToPlayer() {
		int playerX = PCGGame.getInstance().getPlayer().getXTile(); 
		int playerY = PCGGame.getInstance().getPlayer().getYTile(); 
		
		moveToTarget(playerX, playerY); 
	}
	
	public int getXTile() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		return (int)Math.floor((getX() - r.getX()) / r.getTileWidth()); 
	}
	
	public int getYTile() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		return (int)Math.floor((getY() - r.getY()) / r.getTileHeight()); 
	}
	
	private boolean canMoveTo(int tileX, int tileY) {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		return !r.isTileSolid(tileX, tileY); 
	}
}
