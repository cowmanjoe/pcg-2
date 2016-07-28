package com.mygdx.game;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class AbstractScreen implements Screen{
	
	public static final int GAME_VIEWPORT_WIDTH = 1000, GAME_VIEWPORT_HEIGHT = 1000; 
	public static final int MENU_VIEWPORT_WIDTH = 800, MENU_VIEWPORT_HEIGHT = 480; 
	
	private Skin skin; 
	private SpriteBatch batch; 
	private BitmapFont font; 
	private TextureAtlas atlas; 
	private Table table; 
	
	
	protected final Stage stage; 
	protected final Game game; 
	
	public AbstractScreen(Game game) {
		FileHandle skinFile = Gdx.files.internal("uiskin.json"); 
		skin = new Skin(skinFile);
		
		int width = (isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH); 
		stage = new Stage(); 
		this.game = game; 
		
		
	}
	
	protected String getName()
	{
		return getClass().getSimpleName(); 
	}
	
	protected boolean isGameScreen() 
	{
		return false; 
	}
	
	protected Skin getSkin() {
		return skin; 
	}
	
	public BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont(); 
		}
		return font; 
	}
	
	public SpriteBatch getBatch() 
	{
		if (batch == null) {
			batch = new SpriteBatch(); 
		}
		return batch; 
	}
	
	protected Table getTable() {
		if (table == null) {
			table = new Table(getSkin()); 
			table.setFillParent(true);
			stage.addActor(table);
		}
		return table; 
	}
	
	
	
	@Override
	public void show() {
		System.out.println("Showing screen: " + getName()); 
		
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		stage.act(delta); 
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw(); 
		
		//Table.drawDebug(stage); 
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("Resizing screen: " + getName() + " to: " + width + " x " + height); 
		
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose(); 
		
	}

	@Override
	public void dispose() {
		if (font != null) font.dispose();
		if (batch != null) batch.dispose(); 
		if (skin != null) skin.dispose();
		if (atlas != null) atlas.dispose(); 
		
	}
}
