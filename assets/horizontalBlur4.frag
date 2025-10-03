#version 100

#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_texture;
uniform float u_resolution_x; // Only need the texture width
uniform float u_radius;       // The blur radius in pixels

void main() {
    vec2 texelSize = vec2(1.0 / u_resolution_x, 0.0);
    vec4 color = vec4(0.0);

    // Center pixel
    color += texture2D(u_texture, v_texCoord0) * 0.227027;

    // 4 samples to the right
    color += texture2D(u_texture, v_texCoord0 + texelSize * 1.0 * u_radius) * 0.1945946;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 2.0 * u_radius) * 0.1216216;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 3.0 * u_radius) * 0.0540540;
    color += texture2D(u_texture, v_texCoord0 + texelSize * 4.0 * u_radius) * 0.0162162;

    // 4 samples to the left
    color += texture2D(u_texture, v_texCoord0 - texelSize * 1.0 * u_radius) * 0.1945946;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 2.0 * u_radius) * 0.1216216;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 3.0 * u_radius) * 0.0540540;
    color += texture2D(u_texture, v_texCoord0 - texelSize * 4.0 * u_radius) * 0.0162162;

    gl_FragColor = color * v_color;
}