package com.staticvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class ViewportSample extends SampleBase {

    private static final Logger log = new Logger(ViewportSample.class.getName(), Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ViewportSample.class);

    // virtual width, virtual height
    private static final float WORLD_WIDTH = 800.0f; // world units
    private static final float WORLD_HEIGHT = 600.0f; // aspect ratio here: 4:3

    // FitViewport will always maintain the aspect ratio
    // StretchViewport will stretch your shit but doesn't maintain aspect ratio
    // FillViewport also maintains aspect ratio but always fills whole screen, may cut off pieces
    // ScreenViewport doesn't have constant vis(rt)ual screen size, player w/big screen sees more
    // ExtendViewport keeps ratio without the black bars by first scaling to fit in viewport and then
    // extending world in one direction


    private OrthographicCamera camera;
    private Viewport currentViewport;
    private SpriteBatch batch;
    private Texture texture;
    private BitmapFont font;

    private ArrayMap<String, Viewport> viewports = new ArrayMap<>();

    private int currentViewportIndex;
    private String currentViewportName;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("raw/level-bg-small.png"));
        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));

        createViewports();
        selectNextViewport();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw() {
        batch.draw(texture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        font.draw(batch, currentViewportName, 50, 100);
    }

    private void createViewports() {
        viewports.put(StretchViewport.class.getSimpleName(),
                new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        );

        viewports.put(FitViewport.class.getSimpleName(),
                new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)

        );

        viewports.put(FillViewport.class.getSimpleName(),
                new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        );

        // ScreenViewport always the same size as the screen?
        viewports.put(ScreenViewport.class.getSimpleName(),
                new ScreenViewport(camera)
        );
        
        viewports.put(ExtendViewport.class.getSimpleName(),
                new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        );

        currentViewportIndex = -1;
    }

    private void selectNextViewport() {
        currentViewportIndex = (currentViewportIndex + 1) % viewports.size;

        currentViewport = viewports.getValueAt(currentViewportIndex);

        // true? to center?
        currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        currentViewportName = viewports.getKeyAt(currentViewportIndex);

        log.debug("selected viewport is: " + currentViewportName);
    }


    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectNextViewport();
        return true;
    }
}
