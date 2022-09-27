package com.staticvoid.cookbook;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.TextureRegion;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

import java.util.Comparator;

public class AnimatedSpriteCookbook extends SampleBase implements ApplicationListener {

    private static final Logger log =
            new Logger(AnimatedSpriteCookbook.class.getName(),
                    Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO =
            new SampleInfo(AnimatedSpriteCookbook.class);

    private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
    private static final float SCENE_WIDTH = 12.80f;
    private static final float SCENE_HEIGHT = 7.20f;
    private static final float FRAME_DURATION = 1.0f / 30.0f;


    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private TextureAtlas cavemanAtlas;
    private TextureAtlas dinosaurAtlas;
    private Texture background;

    private TextureAtlas playerWalkLeftAtlas;

    private Animation<TextureRegion> playerWalkLeftAnimation;
    private Animation<TextureRegion> dinosaurWalk;
    private Animation<TextureRegion> cavemanWalk;
    private float animationTime;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);

        batch = new SpriteBatch();
        animationTime = 0.0f;

        // Load atlases and textures
        cavemanAtlas = new TextureAtlas(Gdx.files.internal("dataI/caveman.atlas"));
        dinosaurAtlas = new TextureAtlas(Gdx.files.internal("dataI/trex.atlas"));
        background = new Texture(Gdx.files.internal("dataI/jungle-level.png"));

        playerWalkLeftAtlas = new TextureAtlas(Gdx.files.internal("sokobantest/player_left.atlas"));

        // Load animations
        Array<TextureAtlas.AtlasRegion> playerWalkLeftRegions =
                new Array<>(playerWalkLeftAtlas.getRegions());

        Array<TextureAtlas.AtlasRegion> cavemanRegions = new Array<TextureAtlas.AtlasRegion>(cavemanAtlas.getRegions());
        cavemanRegions.sort(new RegionComparator());

        Array<TextureAtlas.AtlasRegion> dinosaurRegions = new Array<TextureAtlas.AtlasRegion>(dinosaurAtlas.getRegions());
        dinosaurRegions.sort(new RegionComparator());

        cavemanWalk = new Animation(FRAME_DURATION, cavemanRegions, Animation.PlayMode.LOOP);
        dinosaurWalk = new Animation(FRAME_DURATION, dinosaurRegions, Animation.PlayMode.LOOP);

        playerWalkLeftAnimation =
                new Animation(1/10f, playerWalkLeftRegions, Animation.PlayMode.LOOP);

        // Position the camera
       // camera.position.set(SCENE_WIDTH * 0.5f, SCENE_HEIGHT * 0.5f, 0.0f);
        camera.position.set(6.40f, 3.60f, 0);
    }

    @Override
    public void dispose() {
        batch.dispose();
        cavemanAtlas.dispose();
        dinosaurAtlas.dispose();
        background.dispose();
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update animationTime
        animationTime += Gdx.graphics.getDeltaTime();

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Render background
        int width = background.getWidth();
        int height = background.getHeight();

        batch.draw(background,
                0.0f, 0.0f,
                0.0f, 0.0f,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f,
                0, 0,
                width, height,
                false, false);

        // give animationTime to getKeyFrame and the Animation instance gives
        // you the frame to render
        TextureRegion cavemanFrame = cavemanWalk.getKeyFrame(animationTime);
       // cavemanFrame.flip(true, false);
        width = cavemanFrame.getRegionWidth();
        height = cavemanFrame.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(cavemanFrame,
                1.0f - originX, 3.70f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        // Again, pattern is use AnimationTime to get keyframe then render
        // via batch.draw() call
        batch.draw(cavemanWalk.getKeyFrame(animationTime), 100.0f, 275.0f);

        TextureRegion dinosaurFrame = dinosaurWalk.getKeyFrame(animationTime);
        width = dinosaurFrame.getRegionWidth();
        height = dinosaurFrame.getRegionHeight();
        originX = width * 0.5f;
        originY = height * 0.5f;

        batch.draw(dinosaurFrame,
                6.75f - originX, 4.70f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        TextureRegion playerWalkLeftFrame = playerWalkLeftAnimation.getKeyFrame(animationTime);
        width = playerWalkLeftFrame.getRegionWidth();
        height = playerWalkLeftFrame.getRegionHeight();
        originX = width * 0.5f;
        originY = height * 0.5f;

        batch.draw(playerWalkLeftFrame,
                5.0f - originX, 5.0f - originY,
                originX, originY,
                width, height,
                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
                0.0f);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {
        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }
}

//    // sort in alphabetical order
//    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion>{
//        @Override
//        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
//            return region1.name.compareTo(region2.name);
//        }
//    }
//
//    @Override
//    public void create() {
//        camera = new OrthographicCamera();
//        viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
//        batch = new SpriteBatch();
//        animationTime = 0.0f;
//
//        cavemanAtlas = new TextureAtlas(Gdx.files.internal("dataI/caveman.atlas"));
//        dinosaurAtlas = new TextureAtlas(Gdx.files.internal("dataI/trex.atlas"));
//        background = new Texture(Gdx.files.internal("dataI/jungle-level.png"));
//
//        Array<TextureAtlas.AtlasRegion> cavemanRegions =
//                new Array<TextureAtlas.AtlasRegion>(cavemanAtlas.getRegions());
//        cavemanRegions.sort(new RegionComparator());
//
//        Array<TextureAtlas.AtlasRegion> dinosaurRegions =
//                new Array<TextureAtlas.AtlasRegion>(dinosaurAtlas.getRegions());
//        dinosaurRegions.sort(new RegionComparator());
//
//        cavemanWalk = new Animation(FRAME_DURATION, cavemanRegions, Animation.PlayMode.LOOP);
//        dinosaurWalk = new Animation(FRAME_DURATION, dinosaurRegions, Animation.PlayMode.LOOP);
//
//        camera.position.set(SCENE_WIDTH * 0.5f, SCENE_HEIGHT * 0.5f, 0.0f);
//    }
//
//    @Override
//    public void render() {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        animationTime += Gdx.graphics.getDeltaTime();
//
//        camera.update();
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//
//        // Render background
//        int width = background.getWidth();
//        int height = background.getHeight();
//
//        batch.draw(background,
//                0.0f, 0.0f,
//                0.0f, 0.0f,
//                width, height,
//                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
//                0.0f,
//                0, 0,
//                width, height,
//                false, false);
//
////        TextureRegion cavemanFrame = (TextureRegion)cavemanWalk.getKeyFrame(animationTime);
////        System.out.println("cavemanFrame: " + cavemanFrame.toString());
////        width = cavemanFrame.getRegionWidth();
////        height = cavemanFrame.getRegionHeight();
////        float originX = width * 0.5f;
////        float originY = height * 0.5f;
////
////        batch.draw(cavemanFrame,
////                1.0f - originX, 3.70f - originY,
////                originX, originY,
////                width, height,
////                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
////                0.0f);
////
////
////        TextureRegion dinosaurFrame = (TextureRegion)dinosaurWalk.getKeyFrame(animationTime);
////        width = dinosaurFrame.getRegionWidth();
////        height = dinosaurFrame.getRegionHeight();
////        originX = width * 0.5f;
////        originY = height * 0.5f;
////
////        batch.draw(dinosaurFrame,
////                6.75f - originX, 4.70f - originY,
////                originX, originY,
////                width, height,
////                WORLD_TO_SCREEN, WORLD_TO_SCREEN,
////                0.0f);
//
//        batch.end();
//
//    }
//
//    @Override
//    public void dispose() {
//        batch.dispose();
//        cavemanAtlas.dispose();
//        dinosaurAtlas.dispose();
//        background.dispose();
//
//    }
//}
//
//
//
  