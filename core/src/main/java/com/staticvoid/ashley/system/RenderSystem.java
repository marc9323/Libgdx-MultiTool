package com.staticvoid.ashley.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.ashley.component.PositionComponent;
import com.staticvoid.ashley.component.SizeComponent;
import com.staticvoid.ashley.component.TextureComponent;

import java.awt.TextComponent;

// only cares about Entities that have this FAMILY of COMPONENTS:
// POSITION, SIZE, TEXTURE
public class RenderSystem extends EntitySystem {

    private static final Logger log = new Logger(RenderSystem.class.getName(), Logger.DEBUG);

    // Family - a group that describes what our entities need to have
    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            SizeComponent.class,
            TextureComponent.class
    ).get();

    // offers really fast component retrieval from entities
    private static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);
    private static final ComponentMapper<SizeComponent> SIZE =
            ComponentMapper.getFor(SizeComponent.class);
    private static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    // these are passed into the constructor of this class
    // when engine.addSystem() is called from AshleySystemSample
    // engine.addSystem(new RenderSystem(viewport, batch));
    private Viewport viewport;
    private SpriteBatch batch;

    private ImmutableArray<Entity> entities;

    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        log.debug("added to engine");
        entities = engine.getEntitiesFor(FAMILY);

        log.debug("family entities: " + entities.size());
        // below returns all entities in game world
        log.debug("all entities: " + engine.getEntities().size());
    }

    // inside system gets called every frame
    // engine.update calls update on every system
    @Override
    public void update(float deltaTime) {
        viewport.apply();
        // using internal camera
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // do the actual rendering
        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {
        // loop through entities array .
        // entities array is populated when we add system to engine
        for(Entity entity : entities) {
            // slower method
           // entity.getComponent(PositionComponent.class);
            // use class:  ComponentMapper
            PositionComponent position = POSITION.get(entity);
            SizeComponent size = SIZE.get(entity);
            TextureComponent texture = TEXTURE.get(entity);
            // there is also .has() method which can be used to check
            // whether or not an entity has some such or other Component

            batch.draw(texture.texture,
                    position.x, position.y,
                    size.width, size.height);
        }
    }
}
