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

    private Logic logic;
    private Renderer renderer;

    public Input() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        logic.updateSpritePosition(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
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
                logic.useCheapShader();
                return false;
            case W:
                logic.useBetterShader();
                return false;
        }
        return false;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}