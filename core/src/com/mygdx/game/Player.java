package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
	
	private int health; 
	
	private Animation idleAnimation; 
	private Animation hitAnimation; 
	
	private float time; 
	
	private float x; 
	private float y; 
	
	private boolean hit; 
	
	public Player(int x, int y) {
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
		
		idleAnimation = new Animation(0.1f, idleTextures); 
		hitAnimation = new Animation(0.15f, hitTextures); 
		
		
		idleAnimation.setPlayMode(PlayMode.LOOP);
		hitAnimation.setPlayMode(PlayMode.NORMAL); 
		
		time = 0; 
		
		this.x = x; 
		this.y = y; 
		
		hit = false; 
	}
	
	
	
	public void draw(SpriteBatch batch) {
		 
		if (hit) {
			batch.draw(hitAnimation.getKeyFrame(time), x, y);
			if (hitAnimation.isAnimationFinished(time)) 
				hit = false; 
		} else { 
			batch.draw(idleAnimation.getKeyFrame(time), x, y); 
		}
		time += Gdx.graphics.getDeltaTime();
	}
	
	
	public void setX(float x) {
		this.x = x; 
	}
	
	public float getX() {
		return x; 
	}
	
	public void setY(float y) {
		this.y = y; 
	}
	
	public float getY() {
		return y; 
	}
	
	public void hit() {
		hit = true; 
	}
}
