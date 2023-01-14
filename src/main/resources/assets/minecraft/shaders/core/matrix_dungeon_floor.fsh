#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0 * 75.0);

    float distanceFromCenter = length(texCoord0 - 0.5);
    if(distanceFromCenter <= 0.4) {
        fragColor = vec4(1.0, 1.0, 1.0, 0.1);
    } else if(distanceFromCenter <= 0.5) {
        fragColor = color;
    } else {
        discard;
    }
}
