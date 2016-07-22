package com.mygdx.game.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.security.auth.callback.Callback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.input.InputMapping.Action;
import com.mygdx.game.input.InputMapping.Range;
import com.mygdx.game.input.InputMapping.State;
import com.mygdx.game.input.RawInputConstants.RawInputAxis;
import com.mygdx.game.input.RawInputConstants.RawInputButton;

public class InputMapper {
	
	Map<String, InputContext> inputContexts; 
	Stack<InputContext> activeContexts; 
	
	Map<Integer, List<InputCallback>> callbackTable; 
	MappedInput currentMappedInput; 
	
	public InputMapper() {
		inputContexts = new HashMap<String, InputContext>(); 
		activeContexts = new Stack<InputContext>(); 
		callbackTable = new HashMap<Integer, List<InputCallback>>(); 
		
		currentMappedInput = new MappedInput(); 
		
		int count; 
		FileHandle file = Gdx.files.internal("ContextList.txt"); 
		
		String contents = file.readString(); 
		String[] lines = contents.split("\n"); 
		
		for (int i = 0; i < lines.length; i++) 
			lines[i] = lines[i].trim(); 
		
		int lineNum = 0; 
		
		count = Integer.parseInt(lines[lineNum]);
		lineNum++; 
		
		for (int i = 0; i < count; i++) {
			String[] vals = lines[lineNum].split(" "); 
			String name = vals[0]; 
			String filePath = vals[1]; 
			inputContexts.put(name, new InputContext(filePath)); 
		}
	}
	
	public void clear() {
		currentMappedInput.actions.clear();
		currentMappedInput.ranges.clear(); 
	}
	
	public void setRawButtonState (RawInputButton button, boolean pressed, boolean previouslyPressed){
		
		
		if (pressed && !previouslyPressed) {
			Action a = mapButtonToAction(button); 
			if (a != null) {
				currentMappedInput.actions.add(a); 
				return; 
			}
		}
		
		if (pressed) {
			State s = mapButtonToState(button); 
			if (s != null) 
			{
				currentMappedInput.states.add(s); 
				return; 
			}
		}
	}
	
	
	public void setRawAxisValue(RawInputAxis axis, double value) {
		for (InputContext next : activeContexts) {
			Range r = next.mapAxisToRange(axis);  
			if (r != null) {
				currentMappedInput.ranges.put(r, next.getConversions().convert(r, value * next.getSensitivity(r))); 
			}
		}
	}
	
	public void dispatch() {
		MappedInput input = currentMappedInput; 
		for (Integer next : callbackTable.keySet()) {
			for (InputCallback ic : callbackTable.get(next)) {
				ic.execute(input); 
			}
		}
	}
	
	public void addCallback(InputCallback callback, int priority) {
		if (callbackTable.containsKey(priority)) {
			List<InputCallback> currentCallbacks = callbackTable.get(priority); 
			currentCallbacks.add(callback); 
		} else {
			List<InputCallback> newCallback = new ArrayList<InputCallback>(); 
			newCallback.add(callback); 
			
			callbackTable.put(priority, newCallback); 
		}
	}
	
	public void pushContext(String name) {
		if (inputContexts.containsKey(name)) {
			activeContexts.push(inputContexts.get(name));  
		}
		else {
			throw new RuntimeException("Invalid input context pushed"); 
		}
	}
	
	public void popContext() {
		if (activeContexts.isEmpty()) 
			throw new RuntimeException("Cannot pop input context, no contexts active!"); 
			
		activeContexts.pop(); 
		
	}
	
	private Action mapButtonToAction(RawInputButton button) {
		for (InputContext next : activeContexts) {
			Action a = next.mapButtonToAction(button); 
			if (a != null) {
				return a; 
			}
		}
		return null; 
	}
	
	private State mapButtonToState(RawInputButton button) {
		for (InputContext next : activeContexts) {
			State s = next.mapButtonToState(button); 
			if (s != null) {
				return s;
			}
		}
		return null; 
	}
}
