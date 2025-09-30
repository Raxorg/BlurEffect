package com.epicness.neoneffect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class NeonEffectApp extends ApplicationAdapter {

    private Renderer renderer;
    private Stuff stuff;


    @Override
    public void create() {
        Input input = new Input();
        Logic logic = new Logic();
        renderer = new Renderer();
        stuff = new Stuff();

        logic.setStuff(stuff);
        input.setLogic(logic);
        input.setRenderer(renderer);
        renderer.setStuff(stuff);
        // IMPORTANT: LibGDX needs this to not crash with custom shaders
        ShaderProgram.pedantic = false;
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        stuff.dispose();
    }
}