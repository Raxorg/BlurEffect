#version 100

#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;
uniform float u_resolution_x;
uniform float u_radius;

void main() {
    vec2 texelSize = vec2(1.0 / u_resolution_x, 0.0);
    vec4 color = vec4(0.0);

    // 17-tap Gaussian weights
    // Center pixel
    color += texture2D(u_texture, v_texCoord0) * 0.19648255;

    // 8 samples to the right
    color += texture2D(u_texture, v_texCoord0 + texelSize * 1.0 * u_radius) * 0.17603266;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 2.0 * u_radius) * 0.12592076;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 3.0 * u_radius) * 0.07147346;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 4.0 * u_radius) * 0.03222013;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 5.0 * u_radius) * 0.01155998;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 6.0 * u_radius) * 0.00329018;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 7.0 * u_radius) * 0.00074213;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 8.0 * u_radius) * 0.00013322;

    // 8 samples to the left
    color += texture2D(u_texture, v_texCoord0 - texelSize * 1.0 * u_radius) * 0.17603266;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 2.0 * u_radius) * 0.12592076;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 3.0 * u_radius) * 0.07147346;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 4.0 * u_radius) * 0.03222013;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 5.0 * u_radius) * 0.01155998;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 6.0 * u_radius) * 0.00329018;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 7.0 * u_radius) * 0.00074213;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 8.0 * u_radius) * 0.00013322;

    gl_FragColor = color * v_color;
}