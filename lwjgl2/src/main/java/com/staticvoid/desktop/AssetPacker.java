package com.staticvoid.desktop;

import static com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import static com.badlogic.gdx.tools.texturepacker.TexturePacker.process;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final String RAW_ASSETS_PATH = "lwjgl2/assets-raw";
    private static final String ASSETS_PATH = "assets";

    public static void main(String[] args) {
        Settings settings = new Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.maxWidth = 2048; // size of entire TextureAtlass
        settings.maxHeight = 2048;

        // call to static method - heavy reliance on static components
        // settings, input directory, output directory, target filename
        process(settings,
                RAW_ASSETS_PATH + "/images",
                ASSETS_PATH + "/images",
                "atlasSample"
        );
    }

}
