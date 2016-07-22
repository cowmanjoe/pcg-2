package com.mygdx.game.input;

import java.util.Map;
import java.util.Set;

import com.mygdx.game.input.InputMapping.Action;
import com.mygdx.game.input.InputMapping.Range;
import com.mygdx.game.input.InputMapping.State;

public class MappedInput {
	Set<Action> actions; 
	Set<State> states; 
	Map<Range, Double> ranges; 
	
	void eatAction(Action action) { actions.remove(action); }
	void eatState (State state) {states.remove(state); }
	void eatRange (Range range) {
		ranges.remove(range); 
	}
	
}
