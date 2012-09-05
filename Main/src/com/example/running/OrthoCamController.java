package com.example.running;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created with IntelliJ IDEA.
 * User: tobrien
 * Date: 9/4/12
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrthoCamController extends InputAdapter {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();

    public OrthoCamController (OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        camera.unproject(curr.set(x, y, 0));
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            camera.unproject(delta.set(last.x, last.y, 0));
            delta.sub(curr);
            camera.position.add(delta.x, delta.y, 0);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        return false;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.A || keyCode == Input.Keys.LEFT ) {
            curr.sub(camera.viewportWidth/20, 0, 0);
            camera.position.add(curr);
        }
        else if (keyCode == Input.Keys.D || keyCode == Input.Keys.RIGHT) {
            curr.add(camera.viewportWidth/20, 0, 0);
            camera.position.add(curr);
        }

        return false;
    }
}
