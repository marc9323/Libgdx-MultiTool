package com.staticvoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;

public class PoolingSample extends SampleBase {

    private static final Logger log = new Logger(PoolingSample.class.getName(), Application.LOG_DEBUG);

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(PoolingSample.class);

    private static final float BULLET_ALIVE_TIME = 3f;
    private static final float BULLET_SPAWN_TIME = 1f;

    private Array<Bullet> bullets = new Array<Bullet>();
    private float timer;

    // returns a new or existing pool for the specified type and quantity
    // "reflection pool"
    //   private final Pool<Bullet> bulletPool = Pools.get(Bullet.class, 15);
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            log.debug("newObject");
            return new Bullet();
        }

        @Override
        public void free(Bullet object) {
            log.debug("before free object: " + object + " free= " + getFree());
            super.free(object);
            log.debug("after free object: " + object + " free= " + getFree());
        }

        @Override
        public Bullet obtain() {
            log.debug("before obtain free= " + getFree());
            Bullet ret = super.obtain();
            log.debug("after obtain free= " + getFree());
            return ret;
        }

        // free

        @Override
        protected void reset(Bullet object) {
            log.debug("resetting object= " + object);
            super.reset(object);
        }
    };

    // Pools.get() returns a reflection pool and all
    // that reflection pool does is return
    // ClassReflection.newInstance(Bullet.class);

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        timer += deltaTime;

        if (timer > BULLET_SPAWN_TIME) {
            timer = 0;
            Bullet bullet = bulletPool.obtain(); // fetch from the Pool
            bullets.add(bullet); // add to the array

            log.debug("Create Alive Bullets: " + bullets.size);
        }

        // update bullets
        for (int i = 0; i < bullets.size; i++) {
            Bullet bullet = bullets.get(i); // get from array
            bullet.update(deltaTime);

            if (!bullet.alive) {
                bullets.removeIndex(i);
                bulletPool.free(bullet);

                log.debug("after free alive bullets: " + bullets.size);
            }
        }
    }

    @Override
    public void dispose() {
        bulletPool.freeAll(bullets);
        bulletPool.clear();
        bullets.clear();
    }

    // == bullet class ========================
    public static class Bullet implements Pool.Poolable {
        boolean alive = true;
        float timer;

        public Bullet() {

        }

        public void update(float deltaTime) {
            timer += deltaTime;

            if (alive && timer > BULLET_ALIVE_TIME) {
                alive = false;
            }
        }

        @Override
        public void reset() {
            // reset must be properly performed for pooling to work
            alive = true;
            timer = 0;
            // this is the state in which object will be returned to pool
        }
    }
}
