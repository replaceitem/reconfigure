#version 150

in vec4 vertexColor;
in vec2 texCoord;

uniform vec4 ColorModulator;

out vec4 fragColor;

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }
    vec3 hsv = vec3(color.r, clamp((color.gb-vec2(0.0,1.0)) * vec2(2.0,-2.0), 0.0, 1.0));
    fragColor = vec4(hsv2rgb(hsv), color.a) * ColorModulator;
}
