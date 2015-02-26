package com.adamtomaja.lostingraph.objects;

import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Common;
import com.adamtomaja.lostingraph.common.Fonts;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelNode extends Node {
	String name;
	private static final long serialVersionUID = 6569955177647626643L;
	
	Array<LevelNode> nextNodes = new Array<LevelNode>();
	
	public Array<LevelNode> getNextNodes() {
		return nextNodes;
	}

	String [] nextNodesStrings;
	
	public boolean containsNextNode(String name){
		for(String nextNode : nextNodesStrings)
			if(nextNode.equals(name))
				return true;
		return false;
	}
	
	public String [] getNextNodesStrings() {
		return nextNodesStrings;
	}

	public void setNextNodesStrings(String [] nextNodesStrings) {
		this.nextNodesStrings = nextNodesStrings;
	}

	public void addNextNode(LevelNode levelNode){
		nextNodes.add(levelNode);
	}
	
	public LevelNode(String name, Vector2 centerPos) {
		super(centerPos, Common.levelNodeRadius, Colors.levelNode);
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return "LevelNode: " + name;
	}
	

	@Override
	public void render(  Batch batch, ShapeRenderer shapeRenderer) {
		super.render(batch, shapeRenderer);
		batch.begin();
		Fonts.lily30.setColor(Colors.neutralMessage);
		Fonts.lily30.draw(batch, name, x - 10, y + 10);
		batch.end();
	}
}
