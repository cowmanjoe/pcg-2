package com.mygdx.game;

import java.awt.Font;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent; 

public class MenuScreen extends AbstractScreen{
	public Button playButton; 
	
	private static final float BUTTON_WIDTH = 300f; 
	private static final float BUTTON_HEIGHT = 60f; 
	private static final float BUTTON_SPACING = 10f; 
	
	public MenuScreen(Game game) {
		super(game); 
	}
	
	@Override
	public void show() {
		super.show(); 
		
		ClickListener startListener = new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(new LevelScreen(game));
			}
		};
		
		ClickListener optionsListener = new ClickListener(); 
		ClickListener highScoresListener = new ClickListener(); 
		
		//retrieve the default table actor
		Table table = super.getTable(); 
		table.add( "PCG Game").spaceBottom(50); 
		table.row(); 
		
		
		
		// register the button "start game"
        TextButton startGameButton = new TextButton( "Start game", getSkin() );
        startGameButton.addListener(startListener);
        table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();
        
        // register the button "options"
        TextButton optionsButton = new TextButton("Options", getSkin()); 
        optionsButton.addListener(optionsListener); 
        table.add(optionsButton).uniform().fill().spaceBottom(10); 
        table.row(); 
        
        // register the button "high scores"
        TextButton highScoresButton = new TextButton("High Scores", getSkin());
        highScoresButton.addListener(highScoresListener); 
        table.add(highScoresButton).uniform().fill(); 
        
        
        System.out.println("Showing menu"); 
	}



}
