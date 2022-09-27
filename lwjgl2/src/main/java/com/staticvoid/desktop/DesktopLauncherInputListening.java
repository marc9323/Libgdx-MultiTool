package com.staticvoid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.staticvoid.InputListeningSample;
import com.staticvoid.InputListeningSample;

public class DesktopLauncherInputListening {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new InputListeningSample(), config);
	}
}
