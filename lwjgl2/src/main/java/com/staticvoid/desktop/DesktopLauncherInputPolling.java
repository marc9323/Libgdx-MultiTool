package com.staticvoid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.staticvoid.InputPollingSample;
import com.staticvoid.InputPollingSample;

public class DesktopLauncherInputPolling {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		// Light Weight Java Game Library
		new LwjglApplication(new InputPollingSample(), config);
	}
}
