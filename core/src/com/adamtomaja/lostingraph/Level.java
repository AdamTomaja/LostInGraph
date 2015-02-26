package com.adamtomaja.lostingraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Common;
import com.adamtomaja.lostingraph.objects.Node;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class Level extends Node{
	static final String directory = "levels";
	static final String extension = ".txt";
	
	protected String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected String [] nextLevels;
	
	public String [] getNextLevels(){
		return nextLevels;
	}
	
	protected String [] entryPoints;
	public String[] getEntryPoints() {
		return entryPoints;
	}

	public void setEntryPoints(String[] entryPoints) {
		this.entryPoints = entryPoints;
	}

	public Array<String> getLevelNodes() {
		return levelNodes;
	}

	public void setLevelNodes(Array<String> levelNodes) {
		this.levelNodes = levelNodes;
	}

	protected Array<String> levelNodes = new Array<String>();
	
	public Level(FileHandle fileHandle) throws Exception {
		super();
		
		BufferedReader bf = fileHandle.reader(512, "UTF-8");
		String line;
		
		name = bf.readLine();
		color = Colors.fromIntString(bf.readLine());
		radius = Float.parseFloat(bf.readLine());
		nextLevels = bf.readLine().split(Common.dataDelimiter);
		entryPoints = bf.readLine().split(Common.dataDelimiter);
		while((line = bf.readLine()) != null){
			levelNodes.add(line);
		}
			
		bf.close();
 	}
	
	@Override
	public String toString(){
		return String.format("%s %s %f", getName(),color.toString(), radius);
	}
	
	public void build(){
		
		
	}
	
	/**
	 * Pobiera tablicê z uchwytami do plików leveli
	 * @return
	 */
	public static FileHandle [] getLevels(){
		FileHandle dirHandle = Gdx.app.getType() == ApplicationType.Android ?  Gdx.files.internal(directory) : Gdx.files.internal("./bin/" + directory);
		return dirHandle.list();
	}
	
	public static String [] getFinishedLevels(){
		return Gdx.app.getPreferences(Common.levelPrefsName).getString(Common.finishedLevelsPrefs, Common.defaultFinishedLevelsPref).split(",");
	}
	public static void resetFinishedLevels(){
		Preferences prefs = Gdx.app.getPreferences(Common.levelPrefsName);
		prefs.remove(Common.finishedLevelsPrefs);
		prefs.flush();
	}
	public static void addFinishedLevel(String name){
		Preferences prefs = Gdx.app.getPreferences(Common.levelPrefsName);
		prefs.putString(Common.finishedLevelsPrefs, prefs.getString(Common.finishedLevelsPrefs, Common.defaultFinishedLevelsPref) + Common.dataDelimiter + name);
		prefs.flush();		
 	}
}
