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
    vec2 uv = texCoord0;
    float d = abs(uv.x - 0.5) * 10.;
    float col = .1/ d - 0.05;
    fragColor = vec4(1.0, 1.0, 1.0, col);
}
