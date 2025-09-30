package com.epicness.neoneffect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class NeonEffectApp extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;

    // In your class members
    private FrameBuffer fboA, fboB;
    private ShaderProgram horizontalBlurShader, verticalBlurShader;
    private ShaderProgram horizontalBlurShader4, horizontalBlurShader8;
    private ShaderProgram verticalBlurShader4, verticalBlurShader8;
    private float blurRadius = 4.0f; // You can change this value!

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("libgdx.png"); // Your game's texture
        camera = new OrthographicCamera();

        // IMPORTANT: LibGDX needs this to not crash with custom shaders
        ShaderProgram.pedantic = false;

        loadShaderFiles();

        // Create the FrameBuffer
        // Use the screen dimensions.
        fboA = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        fboB = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    private void loadShaderFiles() {
        FileHandle vertex = Gdx.files.internal("vertex.vert");
        horizontalBlurShader4 = new ShaderProgram(vertex, Gdx.files.internal("horizontalBlur4.frag"));
        verticalBlurShader4 = new ShaderProgram(vertex, Gdx.files.internal("verticalBlur4.frag"));
        horizontalBlurShader8 = new ShaderProgram(vertex, Gdx.files.internal("horizontalBlur8.frag"));
        verticalBlurShader8 = new ShaderProgram(vertex, Gdx.files.internal("verticalBlur8.frag"));
        horizontalBlurShader = horizontalBlurShader4;
        verticalBlurShader = verticalBlurShader4;
    }

    @Override
    public void resize(int width, int height) {
        // Update camera and FBO on resize
        camera.setToOrtho(false, width, height);
        if (fboA != null) fboA.dispose();
        if (fboB != null) fboB.dispose();

        fboA = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        fboB = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
    }

    @Override
    public void render() {
        pollInput();
        // 1. === Render your game scene to fboA ===
        fboA.begin();
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(null); // Use default shader for the scene
        batch.begin();

        batch.draw(img, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

        batch.end();
        fboA.end();

        continueNormally();
    }

    private void pollInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            blurRadius = 1f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            blurRadius = 3f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            blurRadius = 6f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            blurRadius = 10f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            horizontalBlurShader = horizontalBlurShader4;
            verticalBlurShader = verticalBlurShader4;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            horizontalBlurShader = horizontalBlurShader8;
            verticalBlurShader = verticalBlurShader8;
        }
    }

    private void continueNormally() {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 2. === Render fboA to fboB using the horizontal blur shader ===
        fboB.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setShader(horizontalBlurShader);
        horizontalBlurShader.bind();
        horizontalBlurShader.setUniformf("u_resolution_x", Gdx.graphics.getWidth());
        horizontalBlurShader.setUniformf("u_radius", blurRadius);

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

        batch.setShader(verticalBlurShader);
        verticalBlurShader.bind();
        verticalBlurShader.setUniformf("u_resolution_y", Gdx.graphics.getHeight());
        verticalBlurShader.setUniformf("u_radius", blurRadius);

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
        batch.draw(img, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        fboA.dispose();
        fboB.dispose();
        horizontalBlurShader.dispose();
        verticalBlurShader.dispose();
    }
}