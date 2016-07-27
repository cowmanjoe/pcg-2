package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.mygdx.game.input.InputHandler;
import com.mygdx.game.input.MouseState;

public class PCGGame extends ApplicationAdapter {
	private static PCGGame instance = null; 
	
	
	
	private SpriteBatch batch;
	private Stage stage; 
	
	private ShapeRenderer shapeRenderer; 
	
	private Room room; 
	
	private float time; 
	
	Player player; 
	
	
	private PCGGame() {}
	
	public static PCGGame getInstance() {
		if (instance == null) 
			instance = new PCGGame(); 
		
		return instance; 
	}
	
	@Override
	public void create () {
		time = 0f;
		
		stage = new Stage();
		
		List<Enemy> enemies = new ArrayList<Enemy>(); 
		
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer(); 
		shapeRenderer.setAutoShapeType(true);
		
		room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT);
		room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
		room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
		
		player = new Player((int)(room.getX() + room.getWidth() / 2), (int)(room.getY() + room.getHeight() / 2)); 
		
		// Correct position so player is centered in tile
		player.setX(player.getX() - player.getWidth() / 2 + room.getTileWidth() / 2);
		player.setY(player.getY() - player.getHeight() / 2 + room.getTileHeight() / 2); 
	
		enemies = RoomGenerator.randomEnemies(room, 10); 
		room.addEnemies(enemies);
		
		stage.addActor(room);
		stage.addActor(player);
	}
	
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime(); 
		
		time += dt;
		
		 
		
		// For testing random map generation
		/*
		if (time > 2) {
			time = 0; 
			generateNewRoom(); 
		}
		*/
		
		InputHandler.getInstance().tick();
		
		String currentTile = room.getTileType(player.getXTile(), player.getYTile()); 
		//System.out.println("X: " + player.getXTile() + ". Y: " + player.getYTile() + ". Type: " + currentTile);
		
		room.act(dt);
		for (Action a : player.getActions()) {
			System.out.println(a.toString());
		}
		player.act(dt);
		
		stage.draw(); 
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose(); 
	}
	
	
	public Room getCurrentRoom() {
		return room; 
	}
	
	public Player getPlayer() {
		return player; 
	}
	
	public void setRGB(float r, float g, float b) {
		room.setRGB(r, g, b); 
	}
	
	private void generateNewRoom() {
		room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT); 
		room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
		room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
	}
}
