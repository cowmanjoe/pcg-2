package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;

public class MouseState {
	
	private int x; 
	private int y; 
	
	private boolean[] buttonDown; 
	
	
	public MouseState(int x, int y, boolean[] buttonDown) {
		this.x = x; 
		this.y = y; 
		this.buttonDown = buttonDown; 
	}
	
	
	public int getX() {
		return x; 
	}
	
	public int getY() {
		return y; 
	}
	
	public boolean isButtonDown(int button){
		return buttonDown[button]; 
	}
}
