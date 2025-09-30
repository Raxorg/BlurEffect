package com.epicness.neoneffect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Stuff {

    private final Sprite img;

    public Stuff() {
        img = new Sprite(new Texture("libgdx.png")); // Your game's texture
    }

    public Sprite getImg() {
        return img;
    }

    public void dispose() {
        img.getTexture().dispose();
    }
}