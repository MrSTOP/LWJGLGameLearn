package ykw.engine.graph;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {
    private final int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public ShaderProgram() throws IllegalStateException {
        programID = GL20.glCreateProgram();
        if (programID == 0) {
            throw new IllegalStateException("Could not create Shader");
        }
    }

    public void createVertexShader(String shaderCode) throws IllegalStateException {
        vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws IllegalStateException {
        fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
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
