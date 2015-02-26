package com.adamtomaja.lostingraph;

import java.io.BufferedReader;
import java.io.IOException;

import com.adamtomaja.lostingraph.common.Common;
import com.adamtomaja.lostingraph.common.Fonts;
import com.adamtomaja.lostingraph.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Punkt wejœciowy gry. Zawiera globalne obiekty.
 * @author unborn
 *
 */
public class LostInGraph extends Game {

	public MenuScreen menuScreen;
 	public SpriteBatch batch;
	
	@Override
	public void create() {
		
		if(Common.debug)
 			Level.resetFinishedLevels();
		
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
 		Fonts.init();
 		
 		
		
		setScreen(menuScreen);
	}

}
