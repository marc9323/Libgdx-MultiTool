package com.staticvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.CustomActor;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

// imports all static methods, now they can be directly used
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class ActionsSample extends SampleBase {
    private static final Logger log = new Logger(ActionsSample.class.getName(), Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ActionsSample.class);

    private static final float WORLD_WIDTH = 1080f;
    private static final float WORLD_HEIGHT = 720f;

    private Viewport viewport;

    private Stage stage;
    private Texture texture;
    private CustomActor customActor;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //  Gdx.input.setInputProcessor(stage);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stage = new Stage(viewport);

        texture = new Texture(Gdx.files.internal("raw/custom-actor.png"));

        customActor = new CustomActor(new TextureRegion(texture));
        customActor.setSize(160, 80);
        customActor.setPosition(
                (WORLD_WIDTH - customActor.getWidth()) / 2f,
                (WORLD_HEIGHT - customActor.getHeight()) / 2f
        );

        stage.addActor(customActor);
        Gdx.input.setInputProcessor(this); // SampleBase implements InputProcessor

        String LS = "\n"; // System.getProperty("line-separator");
        String TAB = "\t";

        System.out.println(LS + "Press Keys." + "\n" +
                TAB + "1 - RotateBy Action" + LS +
                TAB + "2 - FadeOut Action" + LS +
                TAB + "3 - FadeIn Action" + LS +
                TAB + "4 - ScaleTo Action" + LS +
                TAB + "5 - MoveTo Action" + LS +
                TAB + "6 - Sequential Action" + LS +
                TAB + "7 - Parallel Action"
        );
    }

    @Override
    public boolean keyDown(int keycode) {
        customActor.clearActions();
        if (keycode == Input.Keys.NUM_1) {
            customActor.addAction(Actions.rotateBy(90f, 1f));
        } else if (keycode == Input.Keys.NUM_2) {
            customActor.addAction(Actions.fadeOut(2f));
        } else if (keycode == Input.Keys.NUM_3) {
            customActor.addAction(Actions.fadeIn(2f));
        } else if (keycode == Input.Keys.NUM_4) {
            log.debug("scaleTo Action");
            customActor.addAction(Actions.scaleTo(1.5f, 1.5f, 2f));
        } else if (keycode == Input.Keys.NUM_5) {
            log.debug("moveTo Action");
            customActor.addAction(moveTo(100, 100, 3f)); // note - direct import Actions
        } else if (keycode == Input.Keys.NUM_6) {
            log.debug("Sequential Action");
            Action action = sequence(
                    fadeOut(1f),
                    fadeIn(0.5f),
                    rotateBy(180),
                    moveBy(50, 50, 2f)
            );
            customActor.addAction(action);
        } else if (keycode == Input.Keys.NUM_7) {
            log.debug("Parallel Action");
            Action action = parallel(
                    rotateBy(270, 2f),
                    moveBy(150, 250, 2f),
                    fadeOut(2f)
            );
            customActor.addAction(action);

        }
        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        // no need to call setProjectionMatrix, begin/end, everything is handled internally
        // no need to call setProjectionMatrix, begin/end, handled internally
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        texture.dispose();
    }
}
