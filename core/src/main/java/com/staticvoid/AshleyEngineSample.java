package com.staticvoid;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;
import com.staticvoid.utils.GdxUtils;

public class AshleyEngineSample extends SampleBase {

    private static final Logger log =
            new Logger(AshleyEngineSample.class.getName(),
                    Logger.DEBUG);

    public static final SampleInfo SAMPLE_INFO =
            new SampleInfo(AshleyEngineSample.class);

    private static final float SPAWN_TIME = 1f;
    private static final float REMOVE_TIME = 3f;

    private Engine engine;

    private Array<Entity> bullets = new Array<Entity>();

    private float spawnTimer;
    private float removeTimer;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // single engine per app
        engine = new Engine();

        // notifies when we add remove entities
        engine.addEntityListener(new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                log.debug("Entity Added: " + entity);
                log.debug("Entity Quantity: " + engine.getEntities().size());
            }

            @Override
            public void entityRemoved(Entity entity) {
                log.debug("Entity Removed: " + entity);
                log.debug("Entity Quantity: " + engine.getEntities().size());
            }
        });

        addBullet();
    }

    private void addBullet() {
        Entity bullet = new Entity();
        // add to array, add to engine
        bullets.add(bullet);
        engine.addEntity(bullet);
    }


    @Override
    public void render() {
        GdxUtils.clearScreen();
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.begin();
        renderer.rect(100, 200, 100, 200);
        renderer.end();

        // call update on the engine every tick
        // pass it deltaTime
        float delta = Gdx.graphics.getDeltaTime();
        engine.update(delta);

        // increment spawnTimer
        spawnTimer += delta;

        // if SPAWN_Time has elapsed, add a bullet
        if (spawnTimer > SPAWN_TIME) {
            spawnTimer = 0;
            addBullet();
        }

        // increment removeTimer
        removeTimer += delta;
        // if remove_time has elapsed then remove a bullet
        if (removeTimer > REMOVE_TIME) {
            removeTimer = 0;
            // bullet removal code
            if (bullets.size > 0) {
                // remove the first member of the libgdx Array
                Entity bullet = bullets.first();
                // fetch the first bullet and use the handle to it
                // in the Array .removeValue method
                // removes first instance of specified value
                bullets.removeValue(bullet, true);
                engine.removeEntity(bullet);
            }
        }
    }
}