package ykw.engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import ykw.engine.graph.ShaderProgram;

import java.nio.FloatBuffer;

public class Renderer {

    private int VBOID;
    private int VAOID;
    private ShaderProgram shaderProgram;

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shader/vertex.vert"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shader/fragment.frag"));
        shaderProgram.link();

        float[] vertices = new float[]{
                 0.0F,  0.5F,  0.0F,
                -0.5F, -0.5F,  0.0F,
                 0.5F, -0.5F,  0.0F
        };

        FloatBuffer verticesBuffer = null;
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            verticesBuffer.put(vertices).flip();

            VAOID = GL30.glGenVertexArrays();
            VBOID = GL30.glGenBuffers();

            GL30.glBindVertexArray(VAOID);
            GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOID);
            GL30.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

            GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    public void render(Window window) {
        clear();
        if (window.isResized()) {
            GL11.glViewport(0, 0, window.getWindowWidth(), window.getWindowHeight());
            window.setResized(false);
        }
        shaderProgram.bind();
        GL30.glBindVertexArray(VAOID);
        GL30.glEnableVertexAttribArray(0);
        GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shaderProgram.unbind();

    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }

        GL30.glDisableVertexAttribArray(0);

        GL15.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(VBOID);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAOID);
    }
}
