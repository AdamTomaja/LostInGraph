package com.adamtomaja.lostingraph.objects;

import com.adamtomaja.lostingraph.objects.interfaces.IRenderable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Connection implements IRenderable{
	Node node1;
	Node node2;
	Color color = Color.BLACK;
	
	public Node getNode1() {
		return node1;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public Node getNode2() {
		return node2;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Connection(Node node1, Node node2){
		this.node1 = node1;
		this.node2 = node2;
	}
	
	public Connection(Node node1, Node node2, Color color) {
		this(node1, node2);
		this.color = color;
	}
	
	@Override
	public void render(Batch batch, ShapeRenderer shapeRenderer) {
		
		shapeRenderer.begin();
		
		shapeRenderer.set(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.line(node1.x, node1.y, node2.x, node2.y);
		
		shapeRenderer.end();
	}
}
