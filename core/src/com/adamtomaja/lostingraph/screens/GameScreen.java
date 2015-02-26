package com.adamtomaja.lostingraph.screens;

import com.adamtomaja.lostingraph.Level;
import com.adamtomaja.lostingraph.LostInGraph;
import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Common;
import com.adamtomaja.lostingraph.common.Fonts;
import com.adamtomaja.lostingraph.common.Vectors;
import com.adamtomaja.lostingraph.objects.Connection;
import com.adamtomaja.lostingraph.objects.LevelNode;
import com.adamtomaja.lostingraph.objects.Node;
import com.adamtomaja.lostingraph.objects.interfaces.EventAble;
import com.adamtomaja.lostingraph.objects.interfaces.Interactive;
import com.adamtomaja.lostingraph.objects.interfaces.InteractiveEventListener;
import com.adamtomaja.lostingraph.objects.interfaces.TEventType;
import com.adamtomaja.lostingraph.objects.Message;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends AbstractScreen {

	Level level;
	
	Node connectionsNode = new Node(false);
	Node levelNodesNode = new Node(false);
	Node entryNodesConnections = new Node(false);
	
	InteractiveEventListener eventListener = new InteractiveEventListener(){

		@Override
		public void eventOccured(EventAble target, TEventType eventType) {
			switch(eventType){
			case MESSAGE_EXPIRED:
				Level.addFinishedLevel(level.getName());
				soundtrack.stop();
				soundtrack.dispose();

				game.menuScreen.resume();
				game.setScreen(game.menuScreen);
				break;
			case CLICK:
				if( target.equals(exitNode)){
					soundtrack.stop();
					soundtrack.dispose();
					game.setScreen(game.menuScreen);
				}
				else if(target instanceof LevelNode){
					LevelNode levelNode = (LevelNode)target;
					Array<LevelNode> nextNodes = levelNode.getNextNodes();
					
					levelNode.setMoveVec(Vector2.Zero);

					if(levelNode.containsNextNode(Common.finishNodeName)){
 						Message message = new Message("Route ok.\t"
 								+ "Next node acquired.", 0, halfHeight, Colors.goodMessage);
						message.addEventListener(eventListener);
						objects.add(message);
						ok.play();
					} else if(levelNode.containsNextNode(Common.failNodeName)){
						System.out.println("Failed.\tTry another route");
						levelNodesNode.removeChilds();
						connectionsNode.removeChilds();
						bad.play();
						objects.add(new Message("Failed.\tTry another route", 0, height/ 2, Colors.badMessage));
 					} else {
 						for(LevelNode nextNode : nextNodes){
 							nextNode.setMoveVec(Vectors.random(-20, 20));
							connectionsNode.addChild(new Connection(nextNode, levelNode));
							levelNodesNode.addChild(nextNode);
						}
 					}
				}
				break;
			
			}
		}
		
	};

	Array<LevelNode> levelNodes = new Array<LevelNode>();
	
	@SuppressWarnings("serial")
	Node exitNode = new Node(
				new Vector2(halfWidth, Common.exitNodeRadius / 2),
				Common.exitNodeRadius,
				Colors.fromRgbaBytes(0, 26, 32, 255),
				Vector2.Zero
			){{
				addEventListener(eventListener);
			}}; 
	
	public LevelNode getLevelNode(String name){
		for(LevelNode node : levelNodes)
			if(node.getName().equals(name))
				return node;
		return null;
	}
			
	Music soundtrack;
	Sound ok;
	Sound bad;
	
	public GameScreen(LostInGraph game, Level level) {
		super(game);
		this.level = level;
		
		objects.add(entryNodesConnections);
		objects.add(connectionsNode);
		objects.add(levelNodesNode);
		
		objects.add(exitNode);
		objects.add(level);
		
		objects.add(new Message("Find route to last vertex", 0, halfHeight));
		
		entryNodesConnections.addChild(new Connection(level, exitNode));
		
		for(String levelNodeString : level.getLevelNodes()){
			String [] split = levelNodeString.split(Common.nameVectsLevelNodeDelimiter);
			LevelNode levelNode = new LevelNode(split[0], Vector2.Zero);
			
			if(split.length == 2)
				levelNode.setNextNodesStrings(split[1].split(Common.dataDelimiter));
			else
				levelNode.setNextNodesStrings(new String[0]);
			
			levelNode.setPosition(Vectors.randomNodePosition(levelNode.radius));
			
			levelNode.addEventListener(eventListener);
			levelNodes.add(levelNode);
		}
		
		String [] entryPoints = level.getEntryPoints();
		for(String entryPoint : entryPoints){
			LevelNode node = getLevelNode(entryPoint);
			if(node != null){
				entryNodesConnections.addChild(new Connection(node, level));
				objects.add(node);
			}
		}
		
		for(int i = 0 ; i < levelNodes.size; i++ ){
			LevelNode levelNode = levelNodes.get(i);
			String [] nextNodesStrings = levelNode.getNextNodesStrings();
			for(int a = 0; a < nextNodesStrings.length; a++ ){
				LevelNode nextLevelNode = getLevelNode(nextNodesStrings[a]);
				if(nextLevelNode != null)
					levelNode.addNextNode(nextLevelNode);
			}
		}
		
		soundtrack = Gdx.audio.newMusic(Gdx.files.internal("game_soundtrack.ogg"));
		soundtrack.setLooping(true);
		soundtrack.setVolume(0);
		soundtrack.play();
		
		bad = Gdx.audio.newSound(Gdx.files.internal("bad.ogg"));
		ok = Gdx.audio.newSound(Gdx.files.internal("ok.ogg"));
	}
	
	@Override
	protected void afterRender(float delta){
		game.batch.begin();
		Fonts.lily25.draw(game.batch, "Level: " + level.getName(), 0, Gdx.graphics.getHeight());
		game.batch.end();
		
		soundtrack.setVolume(soundtrack.getVolume() + delta * 0.05f);
	}

}
