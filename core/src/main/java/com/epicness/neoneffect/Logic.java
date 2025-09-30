package com.epicness.neoneffect;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Logic {

    private Assets assets;
    private Stuff stuff;

    public void updateSpritePosition(float x, float y) {
        Sprite sprite = stuff.getImg();
        sprite.setPosition(x, y);
    }

    public void useCheapShader() {
        stuff.getHorizontalBlurShader().setShaderProgram(assets.getHorizontalBlurShader4());
        stuff.getVerticalBlurShader().setShaderProgram(assets.getVerticalBlurShader4());
    }

    public void useBetterShader() {
        stuff.getHorizontalBlurShader().setShaderProgram(assets.getHorizontalBlurShader8());
        stuff.getVerticalBlurShader().setShaderProgram(assets.getVerticalBlurShader8());
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public void setStuff(Stuff stuff) {
        this.stuff = stuff;
    }
}