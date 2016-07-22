package com.mygdx.game;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AnimatedSprite {
	protected float time; 
	
	protected List<Animation> animations; 
	protected Animation currentAnimation; 
	
	protected float x; 
	protected float y; 
	
	public abstract void draw(SpriteBatch batch); 
	
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
	
	public float getWidth() {
		return currentAnimation.getKeyFrame(time).getRegionWidth(); 
	}
	
	public float getHeight() {
		return currentAnimation.getKeyFrame(time).getRegionHeight(); 
	}
}
