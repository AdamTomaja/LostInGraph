package com.adamtomaja.lostingraph.screens;

import com.adamtomaja.lostingraph.LostInGraph;
import com.adamtomaja.lostingraph.common.Colors;
import com.adamtomaja.lostingraph.common.Common;
import com.adamtomaja.lostingraph.common.Fonts;
import com.adamtomaja.lostingraph.objects.interfaces.IObject;
import com.adamtomaja.lostingraph.objects.interfaces.IRenderable;
import com.adamtomaja.lostingraph.objects.interfaces.IUpdatable;
import com.adamtomaja.lostingraph.objects.interfaces.Interactive;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
/**
 * Abstrakcyjny ekran gry. Odpowiada za renderowanie, obs³ugê myszy podstawowych obiektów.
 * 
 * @author unborn
 *
 */
public abstract class AbstractScreen implements Screen{
	/**
	 * Podstawowy obiekt gry
	 */
	protected LostInGraph game;
	/**
	 * Obiekty na scenie
	 */
	protected Array<IObject> objects = new Array<IObject>();
	/**
	 * Renderowanie kszta³tów
	 */
	ShapeRenderer shapeRenderer;
	
	protected float height = Gdx.graphics.getHeight();
	protected float width = Gdx.graphics.getWidth();
	protected float halfWidth = width / 2;
	protected float halfHeight = height / 2;
	
	/**
	 * Kamera ortho
	 */
	protected OrthographicCamera cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()){{
		setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}};

	/**
	 * Konstruktor
	 * @param game
	 */
	public AbstractScreen(LostInGraph game) {
		this.game = game;
		shapeRenderer = new ShapeRenderer(10000);
		shapeRenderer.setAutoShapeType(true);
	}
	@Override
	public void show() {
		
	}
	
	/**
	 * G³ówna metoda renderuj¹ca
	 */
	@Override
	public void render(float delta) {
		int height = Gdx.graphics.getHeight();
//
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

			
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(0, 0, width, height,  Colors.menuBackgroundColors[2], Colors.menuBackgroundColors[3], Colors.menuBackgroundColors[0], Colors.menuBackgroundColors[1]);
		shapeRenderer.end();
		
		beforeRender(delta);
		
		
		
		for(IObject renderable : objects)
			if(renderable instanceof IRenderable)
				((IRenderable)renderable).render( game.batch, shapeRenderer);
		
		for(IObject updatable : objects)
			if(updatable instanceof IUpdatable)
				((IUpdatable)updatable).update(delta);
		
		
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			cam.unproject(touchPos);
			boolean isTouched = Gdx.input.isTouched();
			
			
			for(int i = objects.size - 1; i >= 0; i--){
				IObject object = objects.get(i);
				if(object instanceof Interactive)
					if(((Interactive)object).checkTouch(isTouched, touchPos.x, touchPos.y))
						break;						
			}
		
		afterRender(delta);
	}
	/**
	 * Metoda renderuj¹ca klasy dziedzicz¹cej, powinna zostaæ nadpisana. Jest uruchamiana po renderze g³ównym
	 */
	protected void afterRender(float delta){
		
	}
	/**
	 * Metoda renderuj¹ca klasy dziedzicz¹cej, powinna zostaæ nadpisana. Jest uruchamiana przed renderem g³ównym
	 */
	protected void beforeRender(float delta){
		
	}
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		
		halfHeight = this.height / 2;
		halfWidth = this.width / 2;
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
