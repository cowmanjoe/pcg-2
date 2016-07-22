package com.mygdx.game.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class InputMapper {
	
	Map<String, InputContext> inputContexts; 
	
	
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
			String[] vals = Integer.parseInt(lines[lineNum]).split(" "); 
			String name = vals[0]; 
			String filePath = vals[1]; 
			inputContexts.put(name, new InputContext(filePath)); 
		}
	}
	
}
