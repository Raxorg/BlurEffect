package com.epicness.neoneffect;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Assets {

    private final AssetManager assetManager;

    private static final String VERTEX = "vertex.vert";
    public static final String H_BLUR_4_FRAG = "horizontalBlur4.frag";
    public static final String V_BLUR_4_FRAG = "verticalBlur4.frag";
    public static final String H_BLUR_8_FRAG = "horizontalBlur8.frag";
    public static final String V_BLUR_8_FRAG = "verticalBlur8.frag";
    private final Texture libGDXTexture;

    public Assets() {
        assetManager = new AssetManager();

        loadShader(H_BLUR_4_FRAG);
        loadShader(V_BLUR_4_FRAG);
        loadShader(H_BLUR_8_FRAG);
        loadShader(V_BLUR_8_FRAG);
        assetManager.finishLoading();

        libGDXTexture = new Texture("libgdx.png");
    }

    private void loadShader(String fragment) {
        ShaderProgramLoader.ShaderProgramParameter params = new ShaderProgramLoader.ShaderProgramParameter();
        params.vertexFile = VERTEX;
        params.fragmentFile = fragment;
        assetManager.load(fragment, ShaderProgram.class, params);
    }

    public ShaderProgram getHorizontalBlurShader4() {
        return getShader(H_BLUR_4_FRAG);
    }

    public ShaderProgram getHorizontalBlurShader8() {
        return getShader(H_BLUR_8_FRAG);
    }

    public ShaderProgram getVerticalBlurShader4() {
        return getShader(V_BLUR_4_FRAG);
    }

    public ShaderProgram getVerticalBlurShader8() {
        return getShader(V_BLUR_8_FRAG);
    }

    private ShaderProgram getShader(String shader) {
        if (!assetManager.isLoaded(shader)) {
            loadShader(shader);
            assetManager.finishLoading();
        }
        return assetManager.get(shader);
    }

    public Texture getLibGDXTexture() {
        return libGDXTexture;
    }

    public void dispose() {
        assetManager.dispose();
        libGDXTexture.dispose();
    }

    public void dispose(String fileName) {
        if (assetManager.isLoaded(fileName)) assetManager.unload(fileName);
    }
}