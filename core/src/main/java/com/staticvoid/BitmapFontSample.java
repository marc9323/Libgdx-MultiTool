package com.staticvoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;

public class BitmapFontSample extends SampleBase {

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(BitmapFontSample.class);

    private static final float WIDTH = 1080f; // world units
    private static final float HEIGHT = 720f; // but pixel ratio is 1:1 !!

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private BitmapFont effectFont;
    private BitmapFont uiFont;

    private GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();

        effectFont = new BitmapFont(Gdx.files.internal("fonts/effect_font_32.fnt"));
        uiFont = new BitmapFont(Gdx.files.internal("fonts/ui_font_32.fnt"));

        // enable markup language for user interface fonts
        uiFont.getData().markupEnabled = true;
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        //effectFont.draw(batch, text1, 0, HEIGHT);
        batch.end();
    }

    private void draw() {
        String text1 = "USING BITMAP FONT!";

        effectFont.draw(batch, text1, 20, HEIGHT, 100, 0, true);

        String text2 = "[#FF0000] BITMAP [GREEN]FONTS [YELLOW]Are";
        glyphLayout.setText(uiFont, text2);

        uiFont.draw(batch, text2,
                (WIDTH - glyphLayout.width) / 2f,
                (HEIGHT - glyphLayout.height) / 2f
        );
        // fonts are rendered from top left corner
        // textures are rendered from bottom left corner
//        String text1 = "[#FF0000]CRAPPY [GREEN]FONT HAS NO LOWER CASE SUPPORT!";
//        effectFont.draw(batch, text1, 20, HEIGHT);
//
//        String text2 = "IS tHis FoNt anY BeTter?";
//        glyphLayout.setText(uiFont, text1);
//      //  uiFont.draw(batch, text2, 40, HEIGHT - 25);
//
//        // render to center of screen
//        uiFont.draw(batch, text2,
//                (WIDTH - glyphLayout.width) / 2f,
//                (HEIGHT - glyphLayout.height) / 2f
//        );
//
//        String text3 = "This is a slightly longer text than the old school 30, 40, or 80 character green screen console pre rgb displays.";
//        effectFont.draw(batch, text1, 120, HEIGHT - 50, 120, 120, true); // wrap true
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        effectFont.dispose();
        uiFont.dispose();
    }
}
