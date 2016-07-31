package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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

public class PCGGame extends Game {
	private static PCGGame instance = null; 
	
	
	
	private SpriteBatch batch;
	private Stage stage; 
	
	private ShapeRenderer shapeRenderer; 
	
	private Room room; 
	
	private float time; 
	
	private Player player; 
	
	
	MenuScreen menu; 
	
	private enum State {
		MENU, 
		GAME
	}
	
	private State state = State.MENU; 
	
	private PCGGame() {}
	
	public static PCGGame getInstance() {
		if (instance == null) 
			instance = new PCGGame(); 
		
		return instance; 
	}
	
	/*
	@Override
	public void create () {
		time = 0f;
		
		stage = new Stage();
		
		menu = new MenuScreen(this); 
		
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
	*/
	
	@Override 
	public void create() {
		
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		
		if (getScreen() == null) {
			setScreen(new MenuScreen(this)); 
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose(); 
	}
	
	
	public Room getCurrentRoom() {
		if (getScreen().getClass() == LevelScreen.class)
			return ((LevelScreen) getScreen()).getRoom(); 
		return null; 
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
	
	public void backToMain() {
		setScreen(new MenuScreen(this)); 
	}
}
