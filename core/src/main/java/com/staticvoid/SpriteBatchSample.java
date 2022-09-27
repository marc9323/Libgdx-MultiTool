package com.staticvoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class SpriteBatchSample extends SampleBase {

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(SpriteBatchSample.class);

    private static final float WORLD_WIDTH = 10.8f; // world units
    private static final float WORLD_HEIGHT = 7.2f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texture;
    private Color oldColor;

    private int width = 1; // world units
    private int height = 1;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        oldColor = new Color();

        texture = new Texture(Gdx.files.internal("raw/character.png"));

    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        // camera position, rotation, and zoom levels to take account
        // of in rendering this batch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {
        batch.draw(texture,
                0, 0, // x, y
                width / 2f, height / 2f, // origin
                width, height, // width, height
                1.0f, 1.0f, // scaleX, scaleY
                0.0f, // rotation
                0, 0, // source x and y
                texture.getWidth(), texture.getHeight(), // source width/height
                false, false // flipX, flipY
        );

        // save batch color
        oldColor.set(batch.getColor());
       batch.setColor(Color.GREEN);

       // render green character
        batch.draw(texture,
                8, 1,
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f,
                0.0f,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false
        );

        batch.setColor(oldColor);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
