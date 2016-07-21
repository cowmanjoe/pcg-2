package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.PCGGame;
import com.mygdx.game.input.InputContext;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000; 
		config.height = 1000; 
		
		config.initialBackgroundColor = new Color(0, 0, 0, 1); 
		
		new LwjglApplication(PCGGame.getInstance(), config);
		InputContext ic = new InputContext("MainContext.txt");
	}
}
