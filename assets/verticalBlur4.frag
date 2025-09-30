#version 100

#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
uniform float u_resolution_y; // Only need the texture height
uniform float u_radius;       // The blur radius in pixels

void main() {
    vec2 texelSize = vec2(0.0, 1.0 / u_resolution_y);
    vec4 color = vec4(0.0);

    // Center pixel
    color += texture2D(u_sampler2D, v_texCoord0) * 0.227027;

    // 4 samples below
    color += texture2D(u_sampler2D, v_texCoord0 + texelSize * 1.0 * u_radius) * 0.1945946;
    color += texture2D(u_sampler2D, v_texCoord0 + texelSize * 2.0 * u_radius) * 0.1216216;
    color += texture2D(u_sampler2D, v_texCoord0 + texelSize * 3.0 * u_radius) * 0.0540540;
    color += texture2D(u_sampler2D, v_texCoord0 + texelSize * 4.0 * u_radius) * 0.0162162;

    // 4 samples above
    color += texture2D(u_sampler2D, v_texCoord0 - texelSize * 1.0 * u_radius) * 0.1945946;
    color += texture2D(u_sampler2D, v_texCoord0 - texelSize * 2.0 * u_radius) * 0.1216216;
    color += texture2D(u_sampler2D, v_texCoord0 - texelSize * 3.0 * u_radius) * 0.0540540;
    color += texture2D(u_sampler2D, v_texCoord0 - texelSize * 4.0 * u_radius) * 0.0162162;

    gl_FragColor = color * v_color;
}