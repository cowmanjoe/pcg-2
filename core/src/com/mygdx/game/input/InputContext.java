package com.mygdx.game.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.input.InputMapping.Action;
import com.mygdx.game.input.InputMapping.Range;
import com.mygdx.game.input.InputMapping.State;
import com.mygdx.game.input.RawInputConstants.RawInputAxis;
import com.mygdx.game.input.RawInputConstants.RawInputButton;

public class InputContext {
	
	private Map<RawInputButton, Action> actionMap; 
	private Map<RawInputButton, State> stateMap; 
	private Map<RawInputAxis, Range> rangeMap; 
	
	
	public InputContext(String fileName) {
		actionMap = new HashMap<RawInputButton, Action>(); 
		stateMap = new HashMap<RawInputButton, State>(); 
		rangeMap = new HashMap<RawInputAxis, Range>(); 
		
		FileHandle file = Gdx.files.internal(fileName);
		String contents = file.readString(); 
		
		System.out.println(contents); 
		System.out.println("Length: " + contents.length());
		
		String[] lines = contents.split("\n");
		System.out.println("Number of lines: " + lines.length);
		
		for (int i = 0; i < lines.length; i++) {
			//lines[i].replaceAll("\n", ""); 
			lines[i] = lines[i].trim(); 
		}
		
		int lineNum = 0; 
		
		int rangeCount = Integer.parseInt(lines[lineNum]); 
		lineNum++; 
		for (int i = 0; i < rangeCount; i++) {
			String[] vals = lines[lineNum].split(" ");
			//System.out.println("Number of vals: " + vals.length);
			RawInputAxis axis = RawInputAxis.values()[Integer.parseInt(vals[0])];
			Range range = Range.values()[Integer.parseInt(vals[1])];
			rangeMap.put(axis, range); 
			
			System.out.println("Axis: " + axis.toString()); 
			System.out.println("Range: " + range.toString()); 
			lineNum++; 
		}
		
		
		
		int stateCount = Integer.parseInt(lines[lineNum]); 
		lineNum++; 
		for (int i = 0; i < stateCount; i++) {
			String[] vals = lines[lineNum].split(" "); 
			RawInputButton button = RawInputButton.values()[Integer.parseInt(vals[0])]; 
			State state = State.values()[Integer.parseInt(vals[1])]; 
			stateMap.put(button, state); 
			
			System.out.println("Button: " + button.toString());
			System.out.println("State: " + state.toString());
			lineNum++; 
		}
	}
	
	
	public static void main(String[] args) {
		
	}
}
