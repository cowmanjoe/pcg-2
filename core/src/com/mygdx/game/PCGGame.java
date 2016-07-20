package com.mygdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PCGGame extends ApplicationAdapter {
	SpriteBatch batch;
	private ShapeRenderer shapeRenderer; 
	
	private Room room; 
	
	private float time; 
	
	List<Quadrant> splitResults; 
	
	
	@Override
	public void create () {
		time = 0f;
		
		
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer(); 
		shapeRenderer.setAutoShapeType(true);
		
		room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT);
		room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
		room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
		
		Quadrant q1 = new Quadrant(10, 10, 750, 750); 
		splitResults = DungeonGenerator.splitRecursively(q1, 100);  
		
		for (Quadrant q : splitResults) {
			System.out.println("x: " + q.x + ". y: " + q.y +
					". w: " + q.w + ". h: " + q.h);
			
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		time += Gdx.graphics.getDeltaTime(); 
		
		if (time > 2) {
			time = 0; 
			room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT); 
			room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
			room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
		
		}
		
		batch.begin();
		room.draw(batch);
		batch.end();
		
		shapeRenderer.begin(); 
		
		shapeRenderer.set(ShapeType.Line);
		for (Quadrant q : splitResults) {
			q.draw(shapeRenderer);
			
		}
		shapeRenderer.end(); 
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	
}
