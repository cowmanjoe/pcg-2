package com.mygdx.game.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.PCGGame;
import com.mygdx.game.input.InputMapping.Action;
import com.mygdx.game.input.RawInputConstants.RawInputButton;

public class InputHandler {
	
	private static InputHandler instance; 
	
	private MouseState currentMouseState; 
	private MouseState previousMouseState; 
	
	private InputMapper mapper; 
	
	/*
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
	*/
	
	private InputHandler() {
		mapper = new InputMapper(); 
		
		InputCallback defaultCallback = new DefaultCallback(); 
		
		mapper.pushContext("maincontext"); 
		mapper.addCallback(defaultCallback, 0);
		
		mapper.setRawButtonState(convertKeyToRawButton(Keys.LEFT), true, false);
		mapper.setRawButtonState(convertKeyToRawButton(Keys.RIGHT), true, false); 
		mapper.setRawButtonState(convertKeyToRawButton(Keys.UP), true, false);
		mapper.setRawButtonState(convertKeyToRawButton(Keys.DOWN), true, false);
		
	}
	
	public static InputHandler getInstance() {
		if (instance == null) 
			instance = new InputHandler(); 
		
		return instance; 
	}
	
	/*
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
	*/
	
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
		
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			mapper.setRawButtonState(RawInputButton.KEY_LEFT, true, false); 
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			mapper.setRawButtonState(RawInputButton.KEY_RIGHT, true, false);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			mapper.setRawButtonState(RawInputButton.KEY_UP, true, false);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			mapper.setRawButtonState(RawInputButton.KEY_DOWN, true, false);
		}
		
		if (currentMouseState.isButtonDown(Buttons.LEFT) &&
				!previousMouseState.isButtonDown(Buttons.LEFT)) {
			mapper.setRawButtonState(RawInputButton.MOUSE_LEFT, true, false);
		}
		
		mapper.dispatch();
		mapper.clear(); 
		
	}
	
	private RawInputButton convertKeyToRawButton(int key) {
		
		switch (key) {
		case Keys.LEFT : return RawInputButton.KEY_LEFT; 
		case Keys.RIGHT : return RawInputButton.KEY_RIGHT; 
		case Keys.UP : return RawInputButton.KEY_UP;
		case Keys.DOWN : return RawInputButton.KEY_DOWN; 
		default : return null; 
		}
	}
	
	
	public MouseState getCurrentMouseState() {
		return currentMouseState; 
	}
	
	public MouseState getPreviousMouseState() {
		return previousMouseState; 
	}
	
	private class DefaultCallback implements InputCallback {

		@Override
		public void execute(MappedInput inputs) {
			if (inputs.actions.contains(Action.PLAYER_MOVE_LEFT))
				PCGGame.getInstance().getPlayer().moveLeft(); 
			
			if (inputs.actions.contains(Action.PLAYER_MOVE_RIGHT))
				PCGGame.getInstance().getPlayer().moveRight();
			
			if (inputs.actions.contains(Action.PLAYER_MOVE_UP))
				PCGGame.getInstance().getPlayer().moveUp();
			
			if (inputs.actions.contains(Action.PLAYER_MOVE_DOWN))
				PCGGame.getInstance().getPlayer().moveDown(); 
			
			if (inputs.actions.contains(Action.PLAYER_HIT))
				PCGGame.getInstance().getPlayer().hit(); 
		}
		
	}
}
