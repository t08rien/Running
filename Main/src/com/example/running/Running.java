package com.example.running;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: tobrien
 * Date: 9/4/12
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Running extends Game {
    private ParallaxCamera      camera;
    private OrthoCamController  controller;
    private SpriteBatch         batch;
    private TextureRegion[]     layers;

    @Override
    public void create() {
        Texture texture = new Texture(Gdx.files.internal("layers.png"));
        layers = new TextureRegion[3];
        layers[0] = new TextureRegion(texture, 0, 0, 542, 363);
        layers[1] = new TextureRegion(texture, 0, 363, 1024, 149);
        layers[2] = new TextureRegion(texture, 547, 0, 224, 51);

        camera = new ParallaxCamera(480, 320);
        controller = new OrthoCamController(camera);
        Gdx.input.setInputProcessor(controller);
        batch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        layers[0].getTexture().dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(242 / 255.0f, 210 / 255.0f, 111 / 255.0f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        boolean updateCamera = false;
        if (camera.position.x < -1024 + camera.viewportWidth / 2) {
            camera.position.x = -1024 + (int)(camera.viewportWidth / 2);
            updateCamera = true;
        }
        else if (camera.position.x > 1024 - camera.viewportWidth / 2) {
            camera.position.x = 1024 - (int)(camera.viewportWidth / 2);
            updateCamera = true;
        }

        if (camera.position.y < 0) {
            camera.position.y = 0;
            updateCamera = true;
        }
        else if (camera.position.y > 400 - camera.viewportHeight / 2) {
            camera.position.y = 400 - (int)(camera.viewportHeight / 2);
            updateCamera = true;
        }

        // Background
        batch.setProjectionMatrix(camera.calculateParallaxMatrix(0,0));
        batch.disableBlending();
        batch.begin();
            batch.draw(layers[0], -(int)(layers[0].getRegionWidth() / 2), -(int)(layers[0].getRegionHeight() / 2));
        batch.end();
        batch.enableBlending();

        // Midground
        batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.5f, 1));
        batch.begin();
            batch.draw(layers[1], -512, -160);
        batch.end();

        // Foreground
        batch.setProjectionMatrix(camera.calculateParallaxMatrix(1f, 1));
        batch.begin();
            for (int i = 0; i < 9; i++) {
                batch.draw(layers[2], i * layers[2].getRegionWidth() - 1024, -160);
            }
        batch.end();

    }
}
