package com.staticvoid.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.staticvoid.GdxReflectionSample;
import com.staticvoid.GdxReflectionSample;

public class DesktopLauncherGdxReflection {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GdxReflectionSample(), config);
    }
}
