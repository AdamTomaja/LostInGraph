package com.adamtomaja.lostingraph.screens;

import java.io.IOException;

import com.adamtomaja.lostingraph.Level;
import com.adamtomaja.lostingraph.LostInGraph;
import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Common;
import com.adamtomaja.lostingraph.common.Fonts;
import com.adamtomaja.lostingraph.common.Vectors;
import com.adamtomaja.lostingraph.objects.Connection;
import com.adamtomaja.lostingraph.objects.Node;
import com.adamtomaja.lostingraph.objects.interfaces.EventAble;
import com.adamtomaja.lostingraph.objects.interfaces.IObject;
import com.adamtomaja.lostingraph.objects.interfaces.IRenderable;
import com.adamtomaja.lostingraph.objects.interfaces.IUpdatable;
import com.adamtomaja.lostingraph.objects.interfaces.Interactive;
import com.adamtomaja.lostingraph.objects.interfaces.InteractiveEventListener;
import com.adamtomaja.lostingraph.objects.interfaces.TEventType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class MenuScreen extends AbstractScreen{
	
	
	private static final String logTag = MenuScreen.class.getSimpleName();
	
	InteractiveEventListener eventListener = new InteractiveEventListener() {
		@Override
		public void eventOccured(EventAble target, TEventType eventType) {
			System.out.println("Event: " + eventType + " target: " + target);
			if(target.equals(exitNode))
				Gdx.app.exit();
			else if(target instanceof Level){
				soundtrack.stop();
				game.setScreen(new GameScreen(game, (Level)target));
			}
		}
	};
 
	@SuppressWarnings("serial")
	Node exitNode = new Node(
				new Vector2(halfWidth, Common.exitNodeRadius / 2),
				Common.exitNodeRadius,
				Colors.fromRgbaBytes(0, 26, 32, 255),
				Vector2.Zero
			){{
				addEventListener(eventListener);
			}}; 
	
	/**
	 * Ukryty wierzcho³ek przechowuj¹cy po³¹czenia miêdzy innymi wierzcho³kami
	 * Zapobieganie rysowania po³¹czeñ na wierzcho³kach.
	 */
	Node connectionsNode = new Node(false);
	Node levelNodes = new Node(false);
	
	 
	Array<Level> levels = new Array<Level>();
 
	Music soundtrack;
	boolean soundtrackStarted = false;
	
	public MenuScreen(LostInGraph game) {
		super(game);
		
		soundtrack = Gdx.audio.newMusic(Gdx.files.internal("menu_soundtrack.ogg"));		
		soundtrack.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(Music music) {
				Timer.schedule(new Task(){

					@Override
					public void run() {
						soundtrack.play();
					}
					
				}, (float) (Math.random() * 5));
			}
		});
		
 		 
	 
		objects.add(connectionsNode);
		
		objects.add(exitNode); 
	 
		objects.add(levelNodes);
		
		loadLevels();
		
		Gdx.app.log(logTag, "Loaded levels: ");
		for(Level level : levels){
			Gdx.app.log(logTag, level.toString());
		}
	}

	private void loadLevels(){
		FileHandle [] levelsFiles = Level.getLevels();
		Gdx.app.log(logTag, "Levels in db: " + levelsFiles.length); 
		Level lastLevel = null;

		Array<String> levelsFinished = new Array<String>(Level.getFinishedLevels());
		
		System.out.println("Levels finished: ");
		for(String level : levelsFinished){
			System.out.println(level);
		}
		
		for(FileHandle fh : levelsFiles){
			try {
				 Level level = new Level(fh);
				 level.setPosition(Vectors.randomNodePosition(level.radius));
				 level.addEventListener(eventListener);
				 
				 levels.add(level);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		addLevel(levelsFinished, getLevel(Common.firstLevel));
		
		
//		for(Level level : levels){
//			
//			Connection connection = lastLevel != null ? new Connection(lastLevel, level, Colors.levelsConnections) : new Connection(startNode, level, Colors.levelsConnections);
//			
//			connection.setColor(Colors.levelsConnections);
//			
//			 startNode.addChild(connection);
//			 lastLevel = level;
//		}	
		
	}
	
 
	protected void addLevel(Array<String> finishedLevels, Level level){
		levelNodes.addChild(level);
		if(finishedLevels.contains(level.getName(),false))
			for(String levelName : level.getNextLevels()){
				Level nextLevel = getLevel(levelName);
				if(nextLevel != null){
					addLevel(finishedLevels, nextLevel);
					connectionsNode.addChild(new Connection(level, nextLevel));
				}
			}
	}
	
	protected Level getLevel(String name){
		for(Level level : levels)
			if(level.getName().equals(name))
				return level;
		
		return null;
	}
	
	@Override
	protected void beforeRender(float delta) {		
		int height = Gdx.graphics.getHeight();
		
		game.batch.begin();	
	 		Fonts.lily30.setColor(Color.WHITE);
			Fonts.lily30.drawMultiLine(game.batch, Common.gameName + "\n" + Common.version, 10, height - 5);
			 
			Fonts.lily25.drawMultiLine(game.batch, "by " + Common.author + " " + Common.date, 10, Fonts.lily25.getLineHeight());
		game.batch.end();
	}	
	
	@Override
	public void resume() {
		levelNodes.removeChilds();
		
		Array<String> levelsFinished = new Array<String>(Level.getFinishedLevels());
		addLevel(levelsFinished, getLevel(Common.firstLevel));
		
		soundtrack.play();
	}
	
	@Override
	protected void afterRender(float delta){
		if(!soundtrackStarted){
			soundtrack.play();
			soundtrackStarted = true;
		}
	}
}
