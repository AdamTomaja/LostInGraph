package com.adamtomaja.lostingraph.common;

import com.badlogic.gdx.math.Vector2;
/**
 * Wspólne funkcje i obiekty gry.
 * @author unborn
 *
 */
public class Common {
	public static class CameraDimensions{
		public static int x = 480;
		public static int y = 800;
	}
	
	public static float exitNodeRadius = 30;
	
	public static String gameName = "LostInGraph";
	public static String author = "Adam Tomaja";
	public static String date = "2015";
	public static String version = "v.1.2";
	
	public static String firstLevel = "A";
	public static String defaultFinishedLevelsPref = "";
	public static String levelPrefsName = "levels";
	public static String finishedLevelsPrefs = "finished";
	public static String dataDelimiter = ",";
	public static String nameVectsLevelNodeDelimiter = ":";
	
	public static String finishNodeName = "FINISH";
	public static String failNodeName = "FAIL";
	
	public static float levelNodeRadius = 50;
	
	public static boolean debug = false;
}
