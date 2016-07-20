package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class DungeonGenerator {
	
	
	
	public static Room newDungeon(int width, int height) {
		Random r = new Random(); 
		Tile[][] tiles = new Tile[width][height]; 
		 
		List<Quadrant> quadrants = new ArrayList<Quadrant>();
		Quadrant original = new Quadrant(0, 0, width, height); 
		
		if (r.nextBoolean()) {
			List<Quadrant> splitResults = original.splitHorizontally(r.nextInt(height));
		}
		else {
			//Change to split vertical
			List<Quadrant> splitResults = original.splitHorizontally(r.nextInt(height));
		}
		
		return new Room(tiles); 
	}
	
	public static List<Quadrant> splitRecursively(Quadrant q, int minSize) {
		List<Quadrant> result = new ArrayList<Quadrant>(); 
		Random r = new Random(); 
		
		if (q.w > minSize && q.h > minSize) {
			List<Quadrant> splitResult;
			if (r.nextBoolean())
				splitResult = q.splitHorizontally(r.nextInt(q.h));
			else 
				splitResult = q.splitVertically(r.nextInt(q.w));
			for (Quadrant next : splitResult) {
				result.addAll(splitRecursively(next, minSize)); 
			}
		}
		else {
			result.add(q);
		}
		
		return result; 
	}
	
}
