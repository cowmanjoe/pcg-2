package com.mygdx.game;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AnimatedSprite extends Actor{
	protected float time; 
	
	protected List<Animation> animations; 
	protected Animation currentAnimation; 
	
	@Override
	public float getWidth() {
		return currentAnimation.getKeyFrame(time).getRegionWidth(); 
	}
	
	@Override
	public float getHeight() {
		return currentAnimation.getKeyFrame(time).getRegionHeight(); 
	}
}
