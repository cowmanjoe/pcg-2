package com.mygdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
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
	
	Player player; 
	
	
	@Override
	public void create () {
		time = 0f;
		
		
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer(); 
		shapeRenderer.setAutoShapeType(true);
		
		room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT);
		room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
		room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
		
		player = new Player(room.getX() + room.getWidth() / 2, room.getY() + room.getHeight() / 2); 
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		time += Gdx.graphics.getDeltaTime(); 
		
		
		// For testing random map generation
		/*
		if (time > 2) {
			time = 0; 
			room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT); 
			room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
			room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
		
		}
		*/
		
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT))
			player.hit(); 
		
		batch.begin();
		room.draw(batch);
		player.draw(batch);
		batch.end();
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	
}
