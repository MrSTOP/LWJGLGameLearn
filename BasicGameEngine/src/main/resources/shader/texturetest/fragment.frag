#version 330

in vec3 colour;
in vec2 textureCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;

void main() {
    fragColor = texture(textureSampler, textureCoord) * vec4(colour, 1);
}