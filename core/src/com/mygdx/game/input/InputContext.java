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

/*
 * Structure for the context file is as follows : 
 * <number of range mappings>
 * <RawInputAxis index> <Range index>
 * (continue for as many range mappings as stated)
 * <number of state mappings> 
 * <RawInputButton index> <State index>
 * (continue for as many state mappings as stated)
 * <number of action mappings>
 * <RawInputButton index> <Action index> 
 * (continue for as many action mappings as stated)
 * <number of conversions>
 * <Range index> <minimum input> <maximum input> <minimum output> <maximum output>
 * (continue for as many conversions as stated)
 * <number of sensitivities>
 * <Range index> <sensitivity> 
 * (continue for as many sensitivities as stated) 
 */


public class InputContext {
	
	private Map<RawInputButton, Action> actionMap; 
	private Map<RawInputButton, State> stateMap; 
	private Map<RawInputAxis, Range> rangeMap; 
	
	private Map<Range, Double> sensitivityMap; 
	private RangeConverter conversions; 
	
	public InputContext(String fileName) {
		actionMap = new HashMap<RawInputButton, Action>(); 
		stateMap = new HashMap<RawInputButton, State>(); 
		rangeMap = new HashMap<RawInputAxis, Range>(); 
		sensitivityMap = new HashMap<Range, Double>(); 
		
		FileHandle file = Gdx.files.internal(fileName);
		String contents = file.readString(); 
		/*
		System.out.println(contents); 
		System.out.println("Length: " + contents.length());
		*/
		String[] lines = contents.split("\n");
		
		//System.out.println("Number of lines: " + lines.length);
		
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
			
			/*
			System.out.println("Axis: " + axis.toString()); 
			System.out.println("Range: " + range.toString());
			System.out.println(); 
			*/
			lineNum++; 
		}
		
		
		
		int stateCount = Integer.parseInt(lines[lineNum]); 
		lineNum++; 
		for (int i = 0; i < stateCount; i++) {
			String[] vals = lines[lineNum].split(" "); 
			RawInputButton button = RawInputButton.values()[Integer.parseInt(vals[0])]; 
			State state = State.values()[Integer.parseInt(vals[1])]; 
			stateMap.put(button, state); 
			/*
			System.out.println("Button: " + button.toString());
			System.out.println("State: " + state.toString());
			System.out.println(); 
			*/
			lineNum++; 
		}
		
		int actionCount = Integer.parseInt(lines[lineNum]); 
		lineNum++; 
		for (int i = 0; i < actionCount; i++) {
			String[] vals = lines[lineNum].split(" "); 
			RawInputButton button = RawInputButton.values()[Integer.parseInt(vals[0])]; 
			Action action = Action.values()[Integer.parseInt(vals[1])]; 
			actionMap.put(button, action); 
			/*
			System.out.println("Button: " + button.toString()); 
			System.out.println("Action: " + action.toString());
			System.out.println(); 
			*/
			lineNum++; 
		}
		
		int conversionCount = Integer.parseInt(lines[lineNum]);
		lineNum++; 
		
		String[] conversionLines = new String[conversionCount]; 
		for (int i = 0; i < conversionCount; i++) {
			conversionLines[i] = lines[lineNum]; 
			lineNum++; 
		}
		
		conversions = new RangeConverter(conversionCount, conversionLines);
		
		int sensitivityCount = Integer.parseInt(lines[lineNum]); 
		lineNum++; 
		
		for (int i = 0; i < sensitivityCount; i++) {
			String[] vals = lines[lineNum].split(" "); 
			Range range = Range.values()[Integer.parseInt(vals[0])];
			double sensitivity = Double.parseDouble(vals[1]); 
			sensitivityMap.put(range, sensitivity); 
		}
		
		
	}
	
	public Action mapButtonToAction(RawInputButton button) {
		if (actionMap.containsKey(button)) {
			return actionMap.get(button); 
		}
		
		return null; 
		
		
	}
	
	public State mapButtonToState(RawInputButton button) {
		if (stateMap.containsKey(button)) {
			return stateMap.get(button); 
		}
		
		return null; 
	}
	
	public Range mapAxisToRange(RawInputAxis axis) {
		if (rangeMap.containsKey(axis)) {
			return rangeMap.get(axis); 
		}
		
		return null; 
	}
	
	public double getSensitivity(Range range) {
		if (sensitivityMap.containsKey(range)) {
			return sensitivityMap.get(range); 
		}
		
		return 1.0; 
	}
	
	public RangeConverter getConversions() {
		return conversions; 
	}
	
}
