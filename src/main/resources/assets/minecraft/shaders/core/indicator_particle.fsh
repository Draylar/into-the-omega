#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;
in float progress;
in float fade;

out vec4 fragColor;

void main() {
    vec4 root = vec4(0.0, 0.0, 0.0, 0.0);
    float distanceFromCenter = length(texCoord0 - 0.5);

    // outer ring

    vec4 outer = vec4(1.0, 1.0, 1.0, 1.0);
    float radius = (progress / 2.0) - 0.05;
    if(distanceFromCenter >= radius) {
        outer *= 0.0;
    } else {
        outer *= (distanceFromCenter / progress) * 2.0;
    }

    vec4 ring = vec4(0.0);
    if(distanceFromCenter >= 0.45 && distanceFromCenter <= 0.455) {
        ring = vec4(1.0, 1.0, 1.0, 0.75);
    }

    // interior
    root += outer;
    root += ring;

    fragColor = root * vertexColor;
}
