package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AnimatedSprite extends Actor{
	protected float time; 
	
	protected List<Animation> animations; 
	protected Animation currentAnimation; 
	
	public AnimatedSprite() {
		time = 0; 
		animations = new ArrayList<Animation>(); 
	}
	
	@Override
	public float getWidth() {
		return currentAnimation.getKeyFrame(time).getRegionWidth(); 
	}
	
	@Override
	public float getHeight() {
		return currentAnimation.getKeyFrame(time).getRegionHeight(); 
	}
}
