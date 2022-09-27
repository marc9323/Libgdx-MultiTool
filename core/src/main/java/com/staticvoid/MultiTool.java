package com.staticvoid;

// TODO:  rename all instances of 'sampler' to Multi-Tool, tool, etc.
// TODO:  This class is no longer necessary
// marked for deletion
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.staticvoid.common.SampleBase;
import com.staticvoid.common.SampleInfo;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MultiTool extends SampleBase {

	public static final SampleInfo SAMPLE_INFO = new SampleInfo(MultiTool.class);

	private SpriteBatch batch;
	private Texture image;

	@Override
	public void create() {
		batch = new SpriteBatch();
		image = new Texture("libgdx.png");
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(image, 140, 210);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		image.dispose();
	}
}