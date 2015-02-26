package com.adamtomaja.lostingraph.objects;

import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Fonts;
import com.adamtomaja.lostingraph.objects.interfaces.EventAble;
import com.adamtomaja.lostingraph.objects.interfaces.IRenderable;
import com.adamtomaja.lostingraph.objects.interfaces.IUpdatable;
import com.adamtomaja.lostingraph.objects.interfaces.InteractiveEventListener;
import com.adamtomaja.lostingraph.objects.interfaces.TEventType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Message extends Rectangle implements IRenderable, IUpdatable, EventAble{
	private static final long serialVersionUID = -8553282935179642340L;
	Array<InteractiveEventListener> eventListeners = new Array<InteractiveEventListener>();
	String text;
	float time;
	boolean running = true;
	Color color = Colors.neutralMessage;
	public Message(String text, float x, float y){
		super(x, y, 2, Fonts.lily30.getLineHeight());
		this.text = text;
		time = 2;
	}
	public Message(String text, float x, float y, Color color){
		this(text, x, y);
		this.color = color;
	}
	@Override
	public void render(Batch batch, ShapeRenderer shapeRenderer) {
		if(time > 0){
			batch.begin();
			Fonts.lily30.setColor(color);
			Fonts.lily30.drawMultiLine(batch, text, x, y);
			batch.end();
		}
	}
	@Override
	public void update(float delta) {
		if(running){
			time -= delta;
			if(time < 0){
				running = false;
				generateEvent(TEventType.MESSAGE_EXPIRED);
			}
		}
	}
	@Override
	public void addEventListener(
			InteractiveEventListener interactiveEventListener) {
 		eventListeners.add(interactiveEventListener);
	}
	@Override
	public void generateEvent(TEventType type) {
 		for(InteractiveEventListener listener : eventListeners){
 			listener.eventOccured(this, type);
 		}
	}
	
}
