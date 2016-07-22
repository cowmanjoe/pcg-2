package com.mygdx.game.input;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.input.InputMapping.Action;
import com.mygdx.game.input.InputMapping.State;
import com.mygdx.game.input.RawInputConstants.RawInputButton;

public class InputMapper {
	
	Map<String, InputContext> inputContexts; 
	List<InputContext> activeContexts; 
	
	Map<Integer, List<InputCallback>> callbackTable; 
	MappedInput currentMappedInput; 
	
	public InputMapper() {
		inputContexts = new HashMap<String, InputContext>(); 
		
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
			if (mapButtonToAction(button, action)) {
				currentMappedInput.actions.add(action); 
				return; 
			}
		}
		
		if (pressed) {
			if (mapButtonToState(button, state)) 
			{
				currentMappedInput.states.add(state); 
				return; 
			}
		}
	}
	
	
	// Change these helpers to return Action 
	// (return null if action button does not match up 
	// with the button)
	private boolean mapButtonToAction(RawInputButton button) {
		
		return false; 
	}
	
	private boolean mapButtonToState(RawInputButton button, State state) {
		
		return false; 
	}
}
