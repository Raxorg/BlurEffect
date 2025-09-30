package com.epicness.neoneffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Assets {

    private final ShaderProgram horizontalBlurShader4, horizontalBlurShader8;
    private final ShaderProgram verticalBlurShader4, verticalBlurShader8;
    private final Texture libGDXTexture;

    public Assets() {
        // libGDX needs this to not crash with custom shaders
        ShaderProgram.pedantic = false;

        FileHandle vertex = Gdx.files.internal("vertex.vert");
        horizontalBlurShader4 = new ShaderProgram(vertex, Gdx.files.internal("horizontalBlur4.frag"));
        verticalBlurShader4 = new ShaderProgram(vertex, Gdx.files.internal("verticalBlur4.frag"));
        horizontalBlurShader8 = new ShaderProgram(vertex, Gdx.files.internal("horizontalBlur8.frag"));
        verticalBlurShader8 = new ShaderProgram(vertex, Gdx.files.internal("verticalBlur8.frag"));

        libGDXTexture = new Texture("libgdx.png");
    }

    public ShaderProgram getHorizontalBlurShader4() {
        return horizontalBlurShader4;
    }

    public ShaderProgram getHorizontalBlurShader8() {
        return horizontalBlurShader8;
    }

    public ShaderProgram getVerticalBlurShader4() {
        return verticalBlurShader4;
    }

    public ShaderProgram getVerticalBlurShader8() {
        return verticalBlurShader8;
    }

    public Texture getLibGDXTexture() {
        return libGDXTexture;
    }

    public void dispose() {
        horizontalBlurShader4.dispose();
        horizontalBlurShader8.dispose();
        verticalBlurShader4.dispose();
        verticalBlurShader8.dispose();
        libGDXTexture.dispose();
    }
}