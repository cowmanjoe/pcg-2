package com.mygdx.game.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.PCGGame;
import com.mygdx.game.input.InputMapping.Action;
import com.mygdx.game.input.InputMapping.Range;
import com.mygdx.game.input.RawInputConstants.RawInputAxis;
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
		
		
		
		mapper.setRawAxisValue(RawInputAxis.MOUSE_X, currentMouseState.getX());
		mapper.setRawAxisValue(RawInputAxis.MOUSE_Y, currentMouseState.getY());
		
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
				PCGGame.getInstance().getCurrentRoom().getPlayer().moveLeft(); 
			
			if (inputs.actions.contains(Action.PLAYER_MOVE_RIGHT))
				PCGGame.getInstance().getCurrentRoom().getPlayer().moveRight();
			
			if (inputs.actions.contains(Action.PLAYER_MOVE_UP))
				PCGGame.getInstance().getCurrentRoom().getPlayer().moveUp();
			
			if (inputs.actions.contains(Action.PLAYER_MOVE_DOWN))
				PCGGame.getInstance().getCurrentRoom().getPlayer().moveDown(); 
			
			if (inputs.actions.contains(Action.PLAYER_HIT))
				PCGGame.getInstance().getCurrentRoom().getPlayer().hit(); 
			
			if (inputs.ranges.containsKey(Range.RED_RANGE)) 
				PCGGame.getInstance().setRGB(1, 
						(float)(1 - inputs.ranges.get(Range.RED_RANGE)),
						(float)(1 - inputs.ranges.get(Range.RED_RANGE)));
		}
		
	}
}
