package com.adamtomaja.lostingraph.objects;

import com.adamtomaja.lostingraph.Level;
import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Fonts;
import com.adamtomaja.lostingraph.common.Vectors;
import com.adamtomaja.lostingraph.objects.interfaces.EventAble;
import com.adamtomaja.lostingraph.objects.interfaces.IRenderable;
import com.adamtomaja.lostingraph.objects.interfaces.IUpdatable;
import com.adamtomaja.lostingraph.objects.interfaces.Interactive;
import com.adamtomaja.lostingraph.objects.interfaces.InteractiveEventListener;
import com.adamtomaja.lostingraph.objects.interfaces.TEventType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Node extends Circle implements IRenderable, IUpdatable, Interactive, EventAble{
	protected Color color;
 	Vector2 moveVec = Vectors.random(-10, 10);
	private static final int segments = 10;
	
	Array<Object> childs = new Array<Object>();
	
	Array<InteractiveEventListener> eventListeners = new Array<InteractiveEventListener>();
	
	boolean lastTouchState = false;
	
	boolean	show = true;
	
	public void addChild(IRenderable renderable){
		childs.add(renderable);
	}
	
	public void removeChilds(){
		childs.clear();
	}
	
	public Node(){
		this(Vector2.Zero, 10, Color.WHITE);
	}
	
	public void setMoveVec(Vector2 moveVec){
		this.moveVec = moveVec;
	}
	
	public Node(Vector2 centerPos, float radius, Color color){
		 super(centerPos, radius);
		 this.color = color;
 	}
	
	public Node(Vector2 centerPos, float radius, Color color, Vector2 moveVec){
		this(centerPos, radius, color);
		this.moveVec = moveVec;
	}
	
	public Node(boolean show){
		this.show = show;
	}
 
	public void update(float delta){
		for(Object child : childs)
			if(child instanceof IUpdatable)
				((IUpdatable)child).update(delta);
		
		x += delta * moveVec.x;
		y += delta * moveVec.y;
 		
		if(x - radius < 0 || x + radius >= Gdx.graphics.getWidth())
			moveVec.x = - moveVec.x;
		
		if(y - radius < 0 || y + radius >= Gdx.graphics.getHeight())
			moveVec.y = - moveVec.y;
		
		
	}

 
	@Override
	public void render(  Batch batch, ShapeRenderer shapeRenderer) {
		for(Object child : childs)
			if(child instanceof IRenderable)
				((IRenderable)child).render(batch, shapeRenderer);
		if(show){
			shapeRenderer.begin();
			
			shapeRenderer.set(ShapeType.Filled);
			shapeRenderer.setColor(color); 
			shapeRenderer.ellipse(x - radius, y - radius, radius * 2, radius * 2, segments);
			
			shapeRenderer.set(ShapeType.Line);
			shapeRenderer.setColor(lastTouchState ? Color.WHITE : Colors.nodeStroke); 
			shapeRenderer.ellipse(x - radius, y - radius, radius * 2, radius * 2, segments);

			shapeRenderer.end();
		}	
 	}

	@Override
	public boolean checkTouch(boolean isTouched, float x, float y) {
		for(Object child : childs)
			if(child instanceof Interactive)
				((Interactive)child).checkTouch(isTouched, x, y);
		
		boolean contains = contains(x, y);
		if(lastTouchState && !isTouched && contains){
			generateEvent(TEventType.CLICK);
			lastTouchState = false;
			return true;
		}
		
		lastTouchState = contains && isTouched;
		return false;
	}
	
	@Override
	public String toString(){
		return "node: " + color;
	}

	@Override
	public void addEventListener(
			InteractiveEventListener interactiveEventListener) {
		eventListeners.add(interactiveEventListener);
	}

	@Override
	public void generateEvent(TEventType type) {
		for(InteractiveEventListener object : eventListeners)
			object.eventOccured(this, type);
	}
}
