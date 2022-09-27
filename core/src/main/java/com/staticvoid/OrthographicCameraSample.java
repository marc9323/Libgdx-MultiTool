package com.staticvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class OrthographicCameraSample extends SampleBase {

    private static final Logger log = new Logger(OrthographicCameraSample.class.getName(), Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(OrthographicCameraSample.class);

    // a world unit is 100 x 100 pixels
    private static final float WORLD_WIDTH = 10.8f; // world units, 1080 / 100 = 10.8 so 100 pixels per world unit
    private static final float WORLD_HEIGHT = 7.2f; // world units, 720 / 100 = 7.2

    private static final float CAMERA_SPEED = 2.0f; // world units
    private static final float CAMERA_ZOOM_SPEED = 2.0f; // world units

    private OrthographicCamera camera;

    // viewport is - a strategy for scaling the world to the screen
    // manages the camera
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture texture;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/level-bg.png"));
    }

    @Override
    public void render() {

        queryInput();

        GdxUtils.clearScreen();

        // rendering
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void queryInput() {
        // deltaTime is time passed between two frames
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= CAMERA_SPEED * deltaTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += CAMERA_SPEED * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += CAMERA_SPEED * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= CAMERA_SPEED * deltaTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            log.debug("position= " + camera.position);
            log.debug("zoom= " + camera.zoom);
        }

        camera.update();
    }

    private void draw() {
        batch.draw(texture,
                0, 0, // x, y
                WORLD_WIDTH, WORLD_HEIGHT
        );
    }

    @Override
    public void resize(int width, int height) {
        // TODO:  whenever resize occurs viewport MUST be updated
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}



//import com.badlogic.gdx.Application;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.Logger;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//import com.staticvoid.common.SampleBase;
//import com.staticvoid.common.SampleInfo;
//import com.staticvoid.utils.GdxUtils;
//
//public class OrthographicCameraSample extends SampleBase {
//
//    private static final Logger log =
//            new Logger(OrthographicCamera.class.getName(), Logger.DEBUG);
//
//    public static final SampleInfo SAMPLE_INFO = new SampleInfo(OrthographicCameraSample.class);
//
//    private static final float WORLD_WIDTH = 10.8f; // world units, *not* pixels
//    private static final float WORLD_HEIGHT = 7.2f;
//
//    private static final float CAMERA_SPEED = 2.0f; // world units
//    private static final float CAMERA_ZOOM_SPEED = 2.0f;
//
//    private OrthographicCamera camera;
//    private Viewport viewport;
//    private SpriteBatch batch;
//
//    private Texture texture;
//
//    @Override
//    public void create() {
//        Gdx.app.setLogLevel(Application.LOG_DEBUG);
//
//        camera = new OrthographicCamera();
//
//        // workaround libgdx janky coordinate system
////        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0 );
////        camera.update();
//
//        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
//        batch = new SpriteBatch();
//
//        // 2048 x 1024 pixels
//        texture = new Texture(Gdx.files.internal("raw/level-bg.png"));
//    }
//
//    @Override
//    public void render() {
//        queryInput();
//
//        GdxUtils.clearScreen();
//
//        // rendering
//        // tells batch about location/rotation/zoom
//        batch.setProjectionMatrix(camera.combined);
//
//        // display
//        batch.begin();
//        draw();
//        batch.end();
//    }
//
//    private void queryInput() {
//        // deltaTime is time elapsed between frames
//        // 1/60 ...
//        float deltaTime = Gdx.graphics.getDeltaTime();
//
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            camera.position.x -= CAMERA_SPEED * deltaTime;
//        }
//
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            camera.position.x += CAMERA_SPEED * deltaTime;
//        }
//
//        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            camera.position.y += CAMERA_SPEED * deltaTime;
//        }
//
//        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            camera.position.y -= CAMERA_SPEED * deltaTime;
//        }
//
//        // zoom in and out
//        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
//            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
//        }
//
//        if(Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
//            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
//        }
//
//        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
//            log.debug("camera position: " + camera.position);
//            log.debug("zoom: " + camera.zoom);
//        }
//
//        camera.update();
//    }
//
//    private void draw() {
//        // texture, x and y location, size
//        batch.draw(texture,
//                0, 0,
//                WORLD_WIDTH, WORLD_HEIGHT);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        texture.dispose();
//    }
//
//
//}
