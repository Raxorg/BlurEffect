package com.epicness.neoneffect;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Stuff {

    private Assets assets;
    private Shader horizontalBlurShader, verticalBlurShader;
    private Sprite img;

    public void initStuff() {
        horizontalBlurShader = new Shader(assets.getHorizontalBlurShader4());
        verticalBlurShader = new Shader(assets.getVerticalBlurShader4());
        img = new Sprite(assets.getLibGDXTexture());
    }

    public Shader getHorizontalBlurShader() {
        return horizontalBlurShader;
    }

    public Shader getVerticalBlurShader() {
        return verticalBlurShader;
    }

    public Sprite getImg() {
        return img;
    }

    public void dispose() {
        img.getTexture().dispose();
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }
}