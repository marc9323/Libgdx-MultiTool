package com.staticvoid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.staticvoid.ApplicationListenerSample;
import com.staticvoid.ApplicationListenerSample;

public class DesktopLauncherApplicationListenerSample {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ApplicationListenerSample(), config);
	}
}
