package com.staticvoid;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;
import com.staticvoid.utils.PerlinNoiseGenerator;

import java.util.Random;

public class PerlinNoiseSample extends SampleBase {

    private static final Logger log =
            new Logger(PerlinNoiseSample.class.getName(),
                    Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO =
            new SampleInfo(PerlinNoiseSample.class);

    float[][] perlinNoise;

    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Pixmap pixmap;

    @Override
    public void create() {
        setPerlinNoise();

        pixmap = PerlinNoiseGenerator.generatePixmap(random.nextInt(640), random.nextInt(480),
                random.nextInt(10), random.nextInt(120), 23);
        pixmap.setColor(Color.RED);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(480, 800, camera);
        renderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);
    }

    private void setPerlinNoise() {
        Random random = new Random();
        int randomX = random.nextInt(480);
        int randomY = random.nextInt(640);
        perlinNoise = PerlinNoiseGenerator.generatePerlinNoise(randomX, randomY, 3);
    }

    @Override
    public void resize(int width, int height) {
//        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void render() {
//        super.render();
        // GdxUtils.clearScreen();

        renderer.setProjectionMatrix(camera.combined);

        //setPerlinNoise();
        pixmap.drawPixmap(pixmap, 150, 200);

   //     drawPoints();
    }

    private void drawPoints() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.MAGENTA);
        renderer.point(-5, 0, 0);
      //  renderer.point(perlinNoise[0][0], perlinNoise[0][1], 0);
        System.out.println(perlinNoise);
        renderer.point(5, -3, 0);
        renderer.point(8 , 6, 1);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.x(-10, 0, 0.25f);

        renderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
