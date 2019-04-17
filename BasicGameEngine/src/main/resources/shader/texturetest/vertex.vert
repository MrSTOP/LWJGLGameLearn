#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 inColour;
layout (location = 2) in vec2 inTextureCoord;

out vec3 colour;
out vec2 textureCoord;

uniform mat4 projection;
uniform mat4 world;

void main() {
    gl_Position = projection * world * vec4(position, 1.0);
    colour = inColour;
    textureCoord = inTextureCoord;
}