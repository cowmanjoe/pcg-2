package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PCGGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	private Room room; 
	
	private float time; 
	
	@Override
	public void create () {
		time = 0f;
		
		
		
		batch = new SpriteBatch();
		room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT);
		room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
		room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
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
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	
}
