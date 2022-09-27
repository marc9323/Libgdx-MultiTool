package com.staticvoid;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.ashley.component.PositionComponent;
import com.staticvoid.ashley.component.SizeComponent;
import com.staticvoid.ashley.component.TextureComponent;
import com.staticvoid.ashley.system.RenderSystem;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class AshleySystemSample extends SampleBase {

    private static final Logger log =
            new Logger(AshleySystemSample.class.getName(),
                    Logger.DEBUG);


    public static final SampleInfo SAMPLE_INFO =
            new SampleInfo(AshleySystemSample.class);


    // world units
    private static final float WORLD_WIDTH = 10.8f;
    private static final float WORLD_HEIGHT = 7.2f;

    private static final String LEVEL_BG =
            "raw/level-bg.png";

    private static final String CHARACTER =
            "raw/character.png";

    private static final Family FAMILY =
            Family.all(TextureComponent.class).get();

    private AssetManager assetManager;
    private Viewport viewport;
    private SpriteBatch batch;

    // center of framework... used to update all of the systems
    private Engine engine;

    private Entity entity;
    private TextureComponent texture;

    private boolean texturePresentFlag = true;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        engine = new Engine();

        assetManager.load(LEVEL_BG, Texture.class);
        assetManager.load(CHARACTER, Texture.class);
        assetManager.finishLoading();

        Gdx.input.setInputProcessor(this);

        addBackground();
        addCharacter();

        engine.addEntityListener(FAMILY, new EntityListener() {

            @Override
            public void entityAdded(Entity entity) {
                log.debug("family size: " + engine.getEntitiesFor(FAMILY).size());
                log.debug("total size: " + engine.getEntities().size());
                log.debug("entity added to family");
            }

            @Override
            public void entityRemoved(Entity entity) {
                log.debug("family size: " + engine.getEntitiesFor(FAMILY).size());
                log.debug("total size: " + engine.getEntities().size());
                log.debug("entity removed from family");

            }
        });

        // add system: RenderSystem to Engine
        engine.addSystem(new RenderSystem(viewport, batch));

    }

    @Override
    public boolean keyUp(int keycode) {
        // simulates item pickup in game
        if (keycode == Input.Keys.R) { // 'R'emove
            if (FAMILY.matches(entity)) {
                entity.remove(TextureComponent.class);
                // texturePresentFlag = false;
//            } else if(keycode == Input.Keys.A) {
//                if(!FAMILY.matches(entity)) {
//                    entity.add(texture);
//                }
            }
        }

        if (keycode == Input.Keys.A) {
            // A for Add
            System.out.println("Inside keyUp Listener");
            if (!FAMILY.matches(entity)) {
                entity.add(texture);
            }
        }
        return true;
    }

    private void addCharacter() {
        PositionComponent position = new PositionComponent();
        position.x = 0;
        position.y = 0;

        SizeComponent size = new SizeComponent();
        size.width = 2; // WORLD_WIDTH;
        size.height = 2; // WORLD_HEIGHT;

        texture = new TextureComponent();
        texture.texture = assetManager.get(CHARACTER);

        // assemble an entity, composing position size texture
        entity = new Entity();
        entity.add(position);
        entity.add(size);
        entity.add(texture);

        // add the entity to the engine
        engine.addEntity(entity);
    }

    private void addBackground() {
        PositionComponent position = new PositionComponent();
        position.x = 0;
        position.y = 0;

        SizeComponent size = new SizeComponent();
        size.width = WORLD_WIDTH;
        size.height = WORLD_HEIGHT;

        TextureComponent texture = new TextureComponent();
        texture.texture = assetManager.get(LEVEL_BG);

        // assemble an entity, composing position size texture
        Entity entity = new Entity();
        entity.add(position);
        entity.add(size);
        entity.add(texture);

        // add the entity to the engine
        engine.addEntity(entity);
    }

    @Override
    public void resize(int width, int height) {
        //
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen();

        float delta = Gdx.graphics.getDeltaTime();

        // loops through all the systems and calls update in each
        engine.update(delta); // runs every frame
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        batch.dispose();
    }
}