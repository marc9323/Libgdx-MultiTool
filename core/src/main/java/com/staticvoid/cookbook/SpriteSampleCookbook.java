package com.staticvoid.cookbook;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class SpriteSampleCookbook extends SampleBase {

    private static final Logger log =
            new Logger(SpriteSampleCookbook.class.getName(),
                    Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO =
            new SampleInfo(SpriteSampleCookbook.class);

    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
//    private static final float SCENE_WIDTH = 12.80f;
//    private static final float SCENE_HEIGHT = 7.20f;
    private static final float SCENE_WIDTH = 1280f;
    private static final float SCENE_HEIGHT = 720f;

    private TextureAtlas atlas;
    private Sprite background;
    private Sprite dinosaur;
    private Sprite caveman;
    private Array<Color> colors;
    private int currentColor;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Vector3 tmp;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    private String temp = "Position: ";


    @Override
    public void create() {
        log.debug("create");
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
        batch = new SpriteBatch();

        atlas = new TextureAtlas(Gdx.files.internal("data/caveman.atlas"));
        background = new Sprite(atlas.findRegion("background"));
        caveman = new Sprite(atlas.findRegion("caveman"));
        dinosaur = new Sprite(atlas.findRegion("trex"));

        background.setPosition(-background.getWidth() * 0.5f,
                -background.getHeight() * 0.5f);
        caveman.setOrigin(caveman.getWidth() * 0.5f,
                caveman.getHeight() * 0.5f); // origin to center
        dinosaur.setPosition(100.0f, -85.0f);

        currentColor = 0;
        colors = new Array<Color>();
        colors.add(new Color(Color.WHITE));
        colors.add(new Color(0.0f, 0.0f, 0.0f, 1.0f));
        colors.add(new Color(1.0f, 0.0f, 0.0f, 1.0f));
        colors.add(new Color(0.0f, 1.0f, 0.0f, 1.0f));
        colors.add(new Color(0.0f, 0.0f, 1.0f, 1.0f));

        tmp = new Vector3();

        font = new BitmapFont(Gdx.files.internal("fonts/oswald-32.fnt"));
        font.setColor(Color.RED);

        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    // for every loop iteration, we transform mouse/touch coordinates into world space
    // using camera.unproject() to place caveman.
    @Override
    public void render() {
        Rectangle cavemanRect = caveman.getBoundingRectangle();
        Rectangle dinosaurRect = dinosaur.getBoundingRectangle();
        boolean overlap = cavemanRect.overlaps(dinosaurRect);

        tmp.set(Gdx.input.getX(),Gdx.input.getY(), 0.0f);
        camera.unproject(tmp);
        caveman.setPosition(tmp.x - caveman.getWidth() * 0.5f,
                tmp.y - caveman.getHeight() * 0.5f);

        GdxUtils.clearScreen();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        background.draw(batch);
        caveman.draw(batch);
        dinosaur.draw(batch);

        font.draw(batch, temp,
                -SCENE_WIDTH * 0.5f + 20.0f,
                SCENE_HEIGHT * 0.5f - 20.0f);

        //temp = caveman.getX() + " , " + caveman.getY();
//        font.draw(batch, temp,
//                -SCENE_WIDTH * 0.5f + 20.0f,
//                SCENE_HEIGHT * 0.5f - 20.0f);

        if(overlap) {
            font.draw(batch, "Collision between Caveman and Dinosaur!",
                    -SCENE_WIDTH * 0.5f + 20.0f,
                    SCENE_HEIGHT * 0.5f - 20.0f);
        }

        batch.end();

        if(overlap) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(
                    cavemanRect.x, cavemanRect.y,
                    cavemanRect.width, cavemanRect.height
            );
            shapeRenderer.rect(dinosaurRect.x, dinosaurRect.y,
                    dinosaurRect.width, dinosaurRect.height
            );
            shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            log.debug("currentColor: " + currentColor);
            currentColor = (currentColor + 1) % colors.size;
            log.debug("(currentColor + 1) % colors.size: " + currentColor);
            dinosaur.setColor(colors.get(currentColor));

            temp = "Position: " + screenX + " , " + screenY;

        }

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            caveman.scale(amountX * 0.5f);
        }
        else {
            caveman.rotate(amountY * 5.0f);
        }

        return true;
    }


//    @Override
//    public boolean scrolled (int amount) {
//       // System.exit(0);
//        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
//            caveman.scale(amount * 0.5f);
//        }
//        else {
//            caveman.rotate(amount * 5.0f);
//        }
//
//        return true;
//    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
