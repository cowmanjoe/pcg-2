package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Pool;

public class Player extends AnimatedSprite{
	
	private int health; 
	
	private float moveCooldown;
	private static final float MOVE_COOLDOWN = 1.0f; 
	
	private static final float MOVE_TIME = 0.1f; 
	
	private boolean hit; 
	
	private Pool<MoveToAction> moveActions; 
	
	public Player(int x, int y) {
		animations = new ArrayList<Animation>();
		
		TextureRegion[][] sprites = TextureRegion.split(new Texture("spriteSheet1.png"), 
				32, 32);
		
		TextureRegion[] idleTextures = new TextureRegion[6]; 
		TextureRegion[] hitTextures = new TextureRegion[2]; 
		
		for (int i = 0; i < idleTextures.length; i ++) {
			idleTextures[i] = sprites[0][i]; 
		}
		
		for (int i = 0; i < hitTextures.length; i ++ ) {
			hitTextures[i] = sprites[5][i]; 
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
		
		time = 0; 
		moveCooldown = 0; 
		
		setX(x);
		setY(y); 
		
		hit = false; 
		
		moveActions = new Pool<MoveToAction>() {

			@Override
			protected MoveToAction newObject() {
				return new MoveToAction();
			}
			
		};
		
		
		
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
		moveCooldown -= Gdx.graphics.getDeltaTime(); 
	}
	
	public void hit() {
		currentAnimation = animations.get(1); 
		hit = true; 
	}
	
	public void moveLeft() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		if (moveCooldown <= 0 && 
				canMoveTo(getXTile() - 1, getYTile())) { 
			MoveToAction moveAction = moveActions.obtain(); 
			moveAction.setDuration(MOVE_TIME); 
			moveAction.setPosition(getX() - PCGGame.getInstance().getCurrentRoom().getTileWidth(), getY());
			this.addAction(moveAction);
			moveCooldown = MOVE_COOLDOWN; 
		}
		else {
			SequenceAction sequence = new SequenceAction(); 
			
			MoveToAction moveThere = moveActions.obtain(); 
			MoveToAction moveBack = moveActions.obtain();
			moveThere.setDuration(MOVE_TIME / 2);
			moveThere.setPosition(getX() - r.getTileWidth() / 5, getY());
			moveBack.setDuration(MOVE_TIME / 2);
			moveBack.setPosition(getX(), getY());
			
			sequence.addAction(moveThere);
			sequence.addAction(moveBack); 
			
			addAction(sequence); 
		}
	}
	
	public void moveRight() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		if (moveCooldown <= 0 && 
				canMoveTo(getXTile() + 1, getYTile())){
			MoveToAction moveAction = moveActions.obtain(); 
			moveAction.setDuration(MOVE_TIME); 
			moveAction.setPosition(getX() + PCGGame.getInstance().getCurrentRoom().getTileWidth(), getY());
			this.addAction(moveAction);
			moveCooldown = MOVE_COOLDOWN; 
		}
		else {
			SequenceAction sequence = new SequenceAction(); 
			
			MoveToAction moveThere = moveActions.obtain(); 
			MoveToAction moveBack = moveActions.obtain();
			moveThere.setDuration(MOVE_TIME / 2);
			moveThere.setPosition(getX() + r.getTileWidth() / 5, getY());
			moveBack.setDuration(MOVE_TIME / 2);
			moveBack.setPosition(getX(), getY());
			
			sequence.addAction(moveThere);
			sequence.addAction(moveBack); 
			
			addAction(sequence); 
		}
	}
	
	public void moveDown() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		if (moveCooldown <= 0 && 
				canMoveTo(getXTile(), getYTile() - 1)) {
			MoveToAction moveAction = moveActions.obtain(); 
			moveAction.setDuration(MOVE_TIME); 
			moveAction.setPosition(getX(), getY() - r.getTileHeight());
			this.addAction(moveAction);
			moveCooldown = MOVE_COOLDOWN; 
		}
		else {
			SequenceAction sequence = new SequenceAction(); 
			
			MoveToAction moveThere = moveActions.obtain(); 
			MoveToAction moveBack = moveActions.obtain();
			moveThere.setDuration(MOVE_TIME / 2);
			moveThere.setPosition(getX(), getY() - r.getTileHeight() / 5);
			moveBack.setDuration(MOVE_TIME / 2);
			moveBack.setPosition(getX(), getY());
			
			sequence.addAction(moveThere);
			sequence.addAction(moveBack); 
			
			addAction(sequence); 
		}
	}
	
	public void moveUp() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		if (moveCooldown <= 0 && 
				canMoveTo(getXTile(), getYTile() + 1)) {
			MoveToAction moveAction = moveActions.obtain(); 
			moveAction.setDuration(MOVE_TIME); 
			moveAction.setPosition(getX(), getY() + r.getTileHeight());
			this.addAction(moveAction);
			moveCooldown = MOVE_COOLDOWN; 
		}
		else {
			SequenceAction sequence = new SequenceAction(); 
			
			MoveToAction moveThere = moveActions.obtain(); 
			MoveToAction moveBack = moveActions.obtain();
			moveThere.setDuration(MOVE_TIME / 2);
			moveThere.setPosition(getX(), getY() + r.getTileHeight() / 5);
			moveBack.setDuration(MOVE_TIME / 2);
			moveBack.setPosition(getX(), getY());
			
			sequence.addAction(moveThere);
			sequence.addAction(moveBack); 
			
			addAction(sequence); 
		}
	}
	
	private boolean canMoveTo(int tileX, int tileY) {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		return !r.isTileSolid(tileX, tileY); 
	}
	
	public int getXTile() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		return (int)Math.floor((getX() - r.getX()) / r.getTileWidth()); 
	}
	
	public int getYTile() {
		Room r = PCGGame.getInstance().getCurrentRoom(); 
		return (int)Math.floor((getY() - r.getY()) / r.getTileHeight()); 
	}
}
