package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;

public class InputHandler {
	
	private static InputHandler instance; 
	
	private MouseState currentMouseState; 
	private MouseState previousMouseState; 
	
	private InputHandler() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY(); 
		
		boolean[] buttons = new boolean[5]; 
		
		buttons[Buttons.BACK] = Gdx.input.isButtonPressed(Buttons.BACK); 
		buttons[Buttons.FORWARD] = Gdx.input.isButtonPressed(Buttons.FORWARD); 
		buttons[Buttons.LEFT] = Gdx.input.isButtonPressed(Buttons.LEFT);
		buttons[Buttons.MIDDLE] = Gdx.input.isButtonPressed(Buttons.MIDDLE); 
		buttons[Buttons.RIGHT] = Gdx.input.isButtonPressed(Buttons.RIGHT); 
		
		
		currentMouseState = new MouseState(x, y, buttons);
		previousMouseState = currentMouseState; 
	}
	
	public static InputHandler getInstance() {
		if (instance == null) 
			instance = new InputHandler(); 
		
		return instance; 
	}
	
	public void tick() {
		previousMouseState = currentMouseState; 
		
		boolean[] buttons = new boolean[5]; 
		
		buttons[Buttons.BACK] = Gdx.input.isButtonPressed(Buttons.BACK); 
		buttons[Buttons.FORWARD] = Gdx.input.isButtonPressed(Buttons.FORWARD); 
		buttons[Buttons.LEFT] = Gdx.input.isButtonPressed(Buttons.LEFT);
		buttons[Buttons.MIDDLE] = Gdx.input.isButtonPressed(Buttons.MIDDLE); 
		buttons[Buttons.RIGHT] = Gdx.input.isButtonPressed(Buttons.RIGHT); 
		
		int x = Gdx.input.getX();
		int y = Gdx.input.getY(); 
		
		currentMouseState = new MouseState(x, y, buttons); 
		
	}
	
	
	public MouseState getCurrentMouseState() {
		return currentMouseState; 
	}
	
	public MouseState getPreviousMouseState() {
		return previousMouseState; 
	}
}
