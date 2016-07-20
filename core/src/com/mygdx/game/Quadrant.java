package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Quadrant {
	
	// x and y coordinates represent the bottom left point
	public int x; 
	public int y; 
	public int w; 
	public int h; 
	
	
	public Quadrant() {
		x = 0; 
		y = 0; 
		w = 0; 
		h = 0; 
	}
	
	public Quadrant(int x, int y, int w, int h) {
		this.x = x; 
		this.y = y; 
		this.w = w; 
		this.h = h; 
	}
	
	// Returns the 2 quadrants that would result if
	// this quadrant was split horizontally at splitAt
	// splitAt is in reference to the top of this quadrant
	// which means if splitAt > h, the method will not work
	public List<Quadrant> splitHorizontally(int splitAt) {
		if (splitAt < 0 || splitAt > h) 
			throw new IllegalArgumentException("splitAt must be at least 0 and at most quadrant.h"); 
		
		List<Quadrant> results = new ArrayList<Quadrant>(); 
		
		Quadrant q1 = new Quadrant(x, y, w, splitAt); 
		Quadrant q2 = new Quadrant(x, y + splitAt, w, h - splitAt); 
		
		results.add(q1); 
		results.add(q2); 
		return results; 
	}
	
	// Returns the 2 quadrants that would result if
	// this quadrant was split vertically at splitAt
	// splitAt is in reference to the left of this quadrant
	// which means if splitAt > h, the method will not work
	public List<Quadrant> splitVertically(int splitAt) {
		if (splitAt < 0 || splitAt > w) 
			throw new IllegalArgumentException("splitAt must be at least 0 and at most quadrant.w"); 

		List<Quadrant> results = new ArrayList<Quadrant>(); 

		Quadrant q1 = new Quadrant(x, y, splitAt, h); 
		Quadrant q2 = new Quadrant(x + splitAt, y, w - splitAt, h); 

		results.add(q1); 
		results.add(q2); 
		return results; 
	}
	
	public void draw(ShapeRenderer renderer) {
		renderer.rect(x, y, w, h);
	}
	
}
