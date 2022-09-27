package com.staticvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.common.CustomActor;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class TableSample extends SampleBase {
    private static final Logger log = new Logger(TableSample.class.getName(), Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(TableSample.class);

    private static final float WORLD_WIDTH = 1080f;
    private static final float WORLD_HEIGHT = 720f;

    private Viewport viewport;

    private Stage stage;
    private Texture texture;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //  Gdx.input.setInputProcessor(stage);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stage = new Stage(viewport);

        texture = new Texture(Gdx.files.internal("raw/custom-actor.png"));

        initUi();

        Gdx.input.setInputProcessor(stage);
    }

    private void initUi() {
        // all user interface is initialized in this method
        Table table = new Table();
        table.defaults().space(20);

        for (int i = 0; i < 6; i++) {
            CustomActor customActor =
                    new CustomActor(new TextureRegion(texture));

//            customActor.addAction(Actions.rotateBy(90f, 1f));
//            customActor.addAction(Actions.rotateBy(90f, 2f));
            // need to set size, default width/height is zero
            customActor.setSize(180, 60);
            table.add(customActor);
            table.row(); // creates a row, next actor on new row
        }

        table.row();
        CustomActor customActor = new CustomActor(new TextureRegion(texture));
        customActor.setSize(200, 40);
        table.add(customActor).expandX().fillX().left();
        table.row();

        table.center();
        table.setFillParent(true);
        table.pack(); // resize, re-layout the table

        stage.addActor(table);
        stage.setDebugAll(true);
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
