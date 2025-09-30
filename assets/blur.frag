#version 100

#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform vec2 u_resolution; // The width and height of the texture being blurred

void main() {
    // Calculate the size of a single pixel in texture coordinates
    vec2 onePixel = vec2(1.0, 1.0) / u_resolution;

    // Gaussian blur kernel weights
    // These weights determine how much influence each neighbor has.
    // The center has the most, corners have the least.
    //  [ 1, 2, 1 ]
    //  [ 2, 4, 2 ]  -> divided by 16 (the sum of all weights)
    //  [ 1, 2, 1 ]
    vec4 color = vec4(0.0);
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2(-1, -1)) * 1.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2( 0, -1)) * 2.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2( 1, -1)) * 1.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2(-1,  0)) * 2.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2( 0,  0)) * 4.0; // Center pixel
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2( 1,  0)) * 2.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2(-1,  1)) * 1.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2( 0,  1)) * 1.0;
    color += texture2D(u_sampler2D, v_texCoord0 + onePixel * vec2( 1,  1)) * 1.0;

    // Average the colors
    color /= 16.0;

    // Apply tinting and alpha from the SpriteBatch
    gl_FragColor = color * v_color;
}