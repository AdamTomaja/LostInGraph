package com.adamtomaja.lostingraph.common;

import com.adamtomaja.lostingraph.objects.Node;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Vectors {
	public static Vector2 random(float a, float b){
		return new Vector2((float)Math.random() * ( b - a) + a, (float)Math.random() * ( b - a) + a);
	}
	
	public static Vector2 randomNodePosition(float radius){
		return new Vector2((float)Math.random() * (Gdx.graphics.getWidth() - radius * 2) + radius,(float) Math.random() * (Gdx.graphics.getHeight() - radius * 2 ) + radius);
	}
}
