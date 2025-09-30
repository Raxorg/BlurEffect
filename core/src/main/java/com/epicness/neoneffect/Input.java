package com.epicness.neoneffect;

import static com.badlogic.gdx.Input.Keys.NUM_1;
import static com.badlogic.gdx.Input.Keys.NUM_2;
import static com.badlogic.gdx.Input.Keys.NUM_3;
import static com.badlogic.gdx.Input.Keys.NUM_4;
import static com.badlogic.gdx.Input.Keys.Q;
import static com.badlogic.gdx.Input.Keys.W;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class Input extends InputAdapter {

    private Renderer renderer;

    public Input() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case NUM_1:
                renderer.setBlurRadius(1f);
                return false;
            case NUM_2:
                renderer.setBlurRadius(3f);
                return false;
            case NUM_3:
                renderer.setBlurRadius(6f);
                return false;
            case NUM_4:
                renderer.setBlurRadius(10f);
                return false;
            case Q:
                renderer.useCheapShader();
                return false;
            case W:
                renderer.useBetterShader();
                return false;
        }
        return false;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}