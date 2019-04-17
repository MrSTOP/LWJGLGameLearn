package ykw.engine.graph;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgram {
    private final int programID;
    private int vertexShaderID;
    private int fragmentShaderID;
    private final Map<String, Integer> uniforms;

    public ShaderProgram() throws IllegalStateException {
        programID = GL20.glCreateProgram();
        if (programID == 0) {
            throw new IllegalStateException("Could not create Shader");
        }
        uniforms = new HashMap<>();
    }

    public void createVertexShader(String shaderCode) throws IllegalStateException {
        vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws IllegalStateException {
        fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }

    public void createUniform(String uniformName) throws IllegalArgumentException {
        int uniformLocation = GL20.glGetUniformLocation(programID, uniformName);
        if (uniformLocation < 0) {
            throw new IllegalArgumentException("Uniform: \"" + uniformName + "\" don\'t exist!");
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setMatrix4F(String uniformName, Matrix4f matrix4f) {
        try (MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer floatBuffer = stack.mallocFloat(16);
            matrix4f.get(floatBuffer);
            GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, floatBuffer);
        }
    }

    public void setUniform1I(String uniformName, int i) {
        GL20.glUniform1i(uniforms.get(uniformName), i);
    }

    public void link() throws IllegalStateException {
        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0) {
            throw new IllegalStateException("Error linking Shader code: " + GL20.glGetProgramInfoLog(programID));
        }

        if (vertexShaderID != 0) {
            GL20.glDetachShader(programID, vertexShaderID);
            GL20.glDeleteShader(vertexShaderID);
        }
        if (fragmentShaderID != 0) {
            GL20.glDetachShader(programID, fragmentShaderID);
            GL20.glDeleteShader(fragmentShaderID);
        }

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programID));
        }
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programID != 0) {
            GL20.glDeleteProgram(programID);
        }
    }

    protected int createShader(String shaderCode, int shaderType) throws IllegalStateException {
        int shaderID = GL20.glCreateShader(shaderType);
        if (shaderID == 0) {
            throw new IllegalStateException("Error creation shader. Type: " + shaderType);
        }
        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0) {
            throw new IllegalStateException("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderID));
        }
        GL20.glAttachShader(programID, shaderID);
        return shaderID;
    }
}
