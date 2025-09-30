package com.epicness.neoneffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class Renderer {

    private Stuff stuff;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private FrameBuffer fboA, fboB;
    private Shader horizontalBlurShader, verticalBlurShader;
    private float blurRadius;

    public Renderer() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        blurRadius = 4f;
        // Create the FrameBuffer
        // Use the screen dimensions
        fboA = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        fboB = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void init() {
        horizontalBlurShader = stuff.getHorizontalBlurShader();
        verticalBlurShader = stuff.getVerticalBlurShader();
    }

    public void render() {
        // 1. === Render your game scene to fboA ===
        fboA.begin();
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(null); // Use default shader for the scene
        batch.begin();

        stuff.getImg().draw(batch);

        batch.end();
        fboA.end();

        continueNormally();
    }

    private void continueNormally() {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 2. === Render fboA to fboB using the horizontal blur shader ===
        fboB.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setShader(horizontalBlurShader.getShaderProgram());
        horizontalBlurShader.bind();
        horizontalBlurShader.setUniformF("u_resolution_x", Gdx.graphics.getWidth());
        horizontalBlurShader.setUniformF("u_radius", blurRadius);

        batch.begin();
        Texture fboATexture = fboA.getColorBufferTexture();
        batch.draw(fboATexture,       // The texture from the FBO
            0,                        // x position
            0,                        // y position
            Gdx.graphics.getWidth(),  // destination width
            Gdx.graphics.getHeight(), // destination height
            0,                        // source x
            0,                        // source y
            fboATexture.getWidth(),   // source width
            fboATexture.getHeight(),  // source height
            false,                    // flip horizontally
            true);
        batch.end();
        fboB.end();

        // 3. === Render fboB to the screen using the vertical blur shader ===
        // (No FBO.begin() here, we're drawing to the actual screen)
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setShader(verticalBlurShader.getShaderProgram());
        verticalBlurShader.bind();
        verticalBlurShader.setUniformF("u_resolution_y", Gdx.graphics.getHeight());
        verticalBlurShader.setUniformF("u_radius", blurRadius);

        batch.begin();
        Texture fboBTexture = fboB.getColorBufferTexture();

        batch.draw(fboBTexture,       // The texture from the FBO
            0,                        // x position
            0,                        // y position
            Gdx.graphics.getWidth(),  // destination width
            Gdx.graphics.getHeight(), // destination height
            0,                        // source x
            0,                        // source y
            fboBTexture.getWidth(),   // source width
            fboBTexture.getHeight(),  // source height
            false,                    // flip horizontally
            true);
        batch.end();

        // Reset shader
        batch.setShader(null);

        renderNormally();
    }

    private void renderNormally() {
        batch.begin();
        stuff.getImg().draw(batch);
        batch.end();
    }

    public void resize(float width, float height) {
        // Update camera and FBO on resize
        camera.setToOrtho(false, width, height);
        if (fboA != null) fboA.dispose();
        if (fboB != null) fboB.dispose();

        fboA = new FrameBuffer(Pixmap.Format.RGBA8888, (int) width, (int) height, false);
        fboB = new FrameBuffer(Pixmap.Format.RGBA8888, (int) width, (int) height, false);
    }

    public void setBlurRadius(float blurRadius) {
        this.blurRadius = blurRadius;
    }

    public void dispose() {
        batch.dispose();
        fboA.dispose();
        fboB.dispose();
    }

    public void setStuff(Stuff stuff) {
        this.stuff = stuff;
    }
}
