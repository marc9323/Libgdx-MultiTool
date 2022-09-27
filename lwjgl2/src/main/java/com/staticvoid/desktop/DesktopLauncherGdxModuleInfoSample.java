package com.staticvoid.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.staticvoid.GdxModuleInfoSample;
import com.staticvoid.GdxModuleInfoSample;

public class DesktopLauncherGdxModuleInfoSample {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();



		System.out.println("app= " + Gdx.app);
		System.out.println("input= " + Gdx.input);

		new LwjglApplication(new GdxModuleInfoSample(), config);

		System.out.println("after app= " + Gdx.app);
		System.out.println("after input= " + Gdx.input);
	}
}
