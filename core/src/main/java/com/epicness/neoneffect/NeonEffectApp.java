package com.epicness.neoneffect;

import com.badlogic.gdx.ApplicationAdapter;

public class NeonEffectApp extends ApplicationAdapter {

    private Assets assets;
    private Renderer renderer;
    private Stuff stuff;

    @Override
    public void create() {
        assets = new Assets();
        Input input = new Input();
        Logic logic = new Logic();
        renderer = new Renderer();
        stuff = new Stuff();

        logic.setAssets(assets);
        logic.setStuff(stuff);
        input.setLogic(logic);
        input.setRenderer(renderer);
        renderer.setStuff(stuff);
        stuff.setAssets(assets);

        stuff.initStuff();
        renderer.init();
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
        assets.dispose();
        renderer.dispose();
        stuff.dispose();
    }
}