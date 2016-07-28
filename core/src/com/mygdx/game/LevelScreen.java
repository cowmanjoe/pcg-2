package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.input.InputHandler;

public class LevelScreen extends AbstractScreen{
	
	private float time; 
	private Room room; 
	
	public LevelScreen(Game game) {
		super(game);
		time = 0; 
		
		generateNewRoom(); 
		
		System.out.println("Room dimensions: " + room.getWidth() + " x " + room.getHeight());
		
		List<Enemy> enemies = new ArrayList<Enemy>(); 
		enemies = RoomGenerator.randomEnemies(room, 10); 
		room.addEnemies(enemies);
		
		Player player = new Player((int)(room.getX() + room.getWidth() / 2), (int)(room.getY() + room.getHeight() / 2), room); 
		
		// Correct position so player is centered in tile
		player.setX(player.getX() - player.getWidth() / 2 + room.getTileWidth() / 2);
		player.setY(player.getY() - player.getHeight() / 2 + room.getTileHeight() / 2); 
		
		room.setPlayer(player);
	}
	
	@Override
	protected boolean isGameScreen() {
		return true; 
	}
	
	@Override
	public void show() {
		super.show(); 
		
		stage.addActor(room);
		
		
	}
	
	@Override 
	public void render(float delta) {
		super.render(delta);
		InputHandler.getInstance().tick();
		time += delta;
		if (room.getPlayer().getHealth() <= 0) this.dispose(); 
	}
	
	public Room getRoom() {
		return room; 
	}
	
	private void generateNewRoom() {
		room = RoomGenerator.newRoom(Room.DEFAULT_WIDTH, Room.DEFAULT_HEIGHT); 
		room.setX(Gdx.graphics.getWidth() / 2 - room.getWidth() / 2);
		room.setY(Gdx.graphics.getHeight() / 2 - room.getHeight() / 2);
	}
}