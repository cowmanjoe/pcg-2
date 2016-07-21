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
	
	public Player(int x, int y) {
		TextureRegion[][] sprites = TextureRegion.split(new Texture("spriteSheet1.png"), 
				32, 32);
		
		TextureRegion[] idleTextures = new TextureRegion[6]; 
		TextureRegion[] hitTextures = new TextureRegion[3]; 
		
		for (int i = 0; i < idleTextures.length; i ++) {
			idleTextures[i] = sprites[0][i]; 
		}
		
		for (int i = 0; i < hitTextures.length; i ++ ) {
			//hitTextures[i] = new TextureRegion(new Texture("playerHit" + i + ".png")); 
		}
		
		idleAnimation = new Animation(0.1f, idleTextures); 
		//hitAnimation = new Animation(0.25f, hitTextures); 
		
		
		idleAnimation.setPlayMode(PlayMode.LOOP);
		
		time = 0; 
		this.x = x; 
		this.y = y; 
	}
	
	
	
	public void draw(SpriteBatch batch) {
		time += Gdx.graphics.getDeltaTime(); 
		
		batch.draw(idleAnimation.getKeyFrame(time), x, y); 
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
}
