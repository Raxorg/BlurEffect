package com.epicness.neoneffect;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Logic {

    private Stuff stuff;

    public void updateSpritePosition(float x, float y) {
        Sprite sprite = stuff.getImg();
        sprite.setPosition(x, y);
    }

    public void setStuff(Stuff stuff) {
        this.stuff = stuff;
    }
}