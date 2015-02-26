package com.adamtomaja.lostingraph.objects.interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public interface IRenderable extends IObject{
	public void render(Batch batch, ShapeRenderer shapeRenderer);
}
