package com.epicness.neoneffect;

import com.badlogic.gdx.graphics.Texture;

public class Stuff {

    private final Texture img;

    public Stuff () {
        img = new Texture("libgdx.png"); // Your game's texture
    }

    public Texture getImg() {
        return img;
    }

    public void dispose() {
        img.dispose();
    }
}