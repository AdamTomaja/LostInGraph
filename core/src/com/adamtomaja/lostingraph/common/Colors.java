package com.adamtomaja.lostingraph.common;

import java.util.StringTokenizer;

import com.badlogic.gdx.graphics.Color;
/**
 * Wspó³dzielone obiekty i funkcje dotycz¹ce kolorów w grze
 * @author unborn
 *
 */
public class Colors {
	public static Color [] menuBackgroundColors = new Color[]{
		fromRgbaBytes(0, 94, 108, 255),
		fromRgbaBytes(0, 107, 123, 255),
		fromRgbaBytes(0, 140, 160, 255),
		fromRgbaBytes(0, 152, 175, 255)
	};
	
	public static Color menuConnections = fromHexString("767676F0");
	public static Color levelsConnections = fromHexString("474746F0");
	public static Color nodeStroke = fromHexString("006a84FF");
	
	public static Color badMessage = Color.RED;
	public static Color goodMessage = Color.GREEN;
	public static Color neutralMessage = Color.WHITE;
	
	public static Color levelNode = fromHexString("004b5bFF");
	
	public static Color fromRgbaBytes(int r, int g, int b, int a){
		return new Color(r/255.f, g/255.f, b/255.f, a/255.f);
	}
	
	public static Color fromHexString(String hexColor){
		return new Color(Integer.parseInt(hexColor, 16));
	}
	
	public static Color fromIntString(String color){
		StringTokenizer tokenizer = new StringTokenizer(color, ",");
		return fromRgbaBytes(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
	}
}
