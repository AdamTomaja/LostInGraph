package com.adamtomaja.lostingraph.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
/**
 * Wspó³dzielone obiekty czcionek
 * @author unborn
 *
 */
public class Fonts {
	public static BitmapFont lily30;
	public static BitmapFont lily25;
	
	public static void init(){
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("lilyupc.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		
		param.size = 50;
		Fonts.lily30 = gen.generateFont(param);
		
		param.size = 25;
		Fonts.lily25 = gen.generateFont(param);
		
		gen.dispose();
		
	}
}
