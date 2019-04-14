#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 inColour;

out vec3 colour;

void main() {
    gl_Position = vec4(position, 1.0);
    colour = inColour;
}