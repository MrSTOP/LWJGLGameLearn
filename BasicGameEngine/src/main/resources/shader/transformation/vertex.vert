#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 inColour;

out vec3 colour;

uniform mat4 projection;

void main() {
    gl_Position = projection * vec4(position, 1.0);
    colour = inColour;
}