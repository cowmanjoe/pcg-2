package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends AnimatedSprite{
	
	private int health; 
	
	private float moveCooldown;
	private static final float MOVE_COOLDOWN = 1.0f; 
	
	private static final float MOVE_TIME = 0.1f; 
	
	private boolean hit; 
	
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
		
		this.x = x; 
		this.y = y; 
		
		hit = false; 
	}
	
	
	
	public void draw(SpriteBatch batch) {
		 
		if (hit) {
			batch.draw(currentAnimation.getKeyFrame(time), x, y);
			if (currentAnimation.isAnimationFinished(time)) {
				hit = false; 
				currentAnimation = animations.get(0); 
			}
		} else { 
			batch.draw(currentAnimation.getKeyFrame(time), x, y); 
		}
		time += Gdx.graphics.getDeltaTime();
		moveCooldown -= Gdx.graphics.getDeltaTime(); 
	}
	
	public void hit() {
		currentAnimation = animations.get(1); 
		hit = true; 
	}
	
	public void moveLeft() {
		if (moveCooldown <= 0) {
			this.x -= PCGGame.getInstance().getCurrentRoom().getTileWidth(); 
			moveCooldown = MOVE_COOLDOWN; 
		}
	}
	
	public void moveRight() {
		if (moveCooldown <= 0){
			this.x += PCGGame.getInstance().getCurrentRoom().getTileWidth(); 
			moveCooldown = MOVE_COOLDOWN; 
		}
	}
	
	public void moveDown() {
		if (moveCooldown <= 0) {
			this.y -= PCGGame.getInstance().getCurrentRoom().getTileHeight(); 
			moveCooldown = MOVE_COOLDOWN; 
		}
	}
	
	public void moveUp() {
		if (moveCooldown <= 0) {
			this.y += PCGGame.getInstance().getCurrentRoom().getTileHeight(); 
			moveCooldown = MOVE_COOLDOWN; 
		}
	}
	
	
}
