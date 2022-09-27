package com.staticvoid.cookbook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class TextureAtlasCookbook extends SampleBase implements InputProcessor {
    private static final Logger log =
            new Logger(TextureAtlasCookbook.class.getName(),
                    Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO =
            new SampleInfo(TextureAtlasCookbook.class);

    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private TextureRegion backgroundRegion;
    private TextureRegion cavemanRegion;
    private TextureRegion dinosaurRegion;

    public float cavemanRegionX;
    public float cavemanRegionY;

    public float gravity = .01f;
    public float jumpSpeed = 2.0f;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();

        // Load atlas and find regions
        atlas = new TextureAtlas(Gdx.files.internal("data/caveman.atlas"));
        backgroundRegion = atlas.findRegion("background");
        cavemanRegion = atlas.findRegion("caveman");
        dinosaurRegion = atlas.findRegion("trex");

        int maxSize = GL20.GL_MAX_TEXTURE_SIZE;
        Gdx.app.log("TextureAtlasSample", "Max texture size: " + maxSize + "x" + maxSize);

        cavemanRegion.flip(true, false);
        dinosaurRegion.flip(true, false);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jumpSpeed += .001f;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        cavemanRegionX = screenX;
        cavemanRegionY = screenY;

        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Render background at the center of the screen);
        float width = backgroundRegion.getRegionWidth();
        float height = backgroundRegion.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(backgroundRegion,
                -originX, -originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        width = cavemanRegion.getRegionWidth();
        height = cavemanRegion.getRegionHeight();
        originX = width * 0.5f;
        originY = height * 0.5f;

        // Render caveman on the left side
//        log.debug(cavemanRegionX + " , " + cavemanRegionY);
//        batch.draw(cavemanRegion,
//                cavemanRegionX, cavemanRegionY);
//                originX, originY,
//                width, height,
//                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
//                0.0f);
        log.debug("Caveman Width: " + cavemanRegion.getRegionWidth());
        batch.draw(cavemanRegion,
             -6.0f - originX - .5f, -2.0f - originY,
             //    - originX, -1.0f - originY + jumpSpeed,
                //-5.0f - originX, -2.0f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);



        width = dinosaurRegion.getRegionWidth();
        height = dinosaurRegion.getRegionHeight();
        originX = width * 0.5f;
        originY = height * 0.5f;

        // Render dinosaur on the right
        batch.draw(dinosaurRegion,
                1.5f - originX, 0.35f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}