package com.adamtomaja.lostingraph.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.adamtomaja.lostingraph.LostInGraph;
import com.adamtomaja.lostingraph.screens.MenuScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.samples = 4;
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new LostInGraph(), config);
	}
}
