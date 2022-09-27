package com.staticvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.payne.games.piemenu.PieMenu;
import com.staticvoid.common.CustomActor;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

import com.payne.games.piemenu.RadialGroup;

public class PieMenuSample extends SampleBase {
    private static final Logger log = new Logger(PieMenuSample.class.getName(), Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(PieMenuSample.class);

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

       // initUi();
        createPieMenu();

        Gdx.input.setInputProcessor(stage);
    }

    private void createPieMenu() {
        PieMenu.PieMenuStyle style = new PieMenu.PieMenuStyle();
        style.sliceColor = new Color(.33f, .33f,.33f, 1);
        Texture whitePixel = new Texture(Gdx.files.internal("assets/white.png"));
        TextureRegion whitePixelRegion = new TextureRegion(whitePixel);
        PieMenu menu = new PieMenu(whitePixelRegion, style, 80);

        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Selected Index: " + menu.getSelectedIndex());
            }
        });

        BitmapFont myFont = new BitmapFont(Gdx.files.internal("assets/fonts/oswald-32.fnt"));
        Label.LabelStyle label1Style = new Label.LabelStyle();
        label1Style.font = myFont;
        final int PIE_SLICES = 8;
        for (int i = 0; i < PIE_SLICES; i++) {
            Label label = new Label(Integer.toString(i), label1Style);
            menu.addActor(label);
        }

        Table table = new Table();
        table.defaults().space(20);
        table.center();
        table.setFillParent(true);
        table.pack();

        table.add(menu);

        stage.addActor(table);
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
