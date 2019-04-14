package ykw.engine.graph;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Mesh {
    private final int VAO;
    private final int VBO;
    private final int vertexCount;

    public Mesh(float[] positions) {
        FloatBuffer verticesBuffer = null;
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
            vertexCount = positions.length / 3;
            verticesBuffer.put(positions).flip();
            VAO = GL30.glGenVertexArrays();
            VBO = GL30.glGenBuffers();

            GL30.glBindVertexArray(VAO);
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, VBO);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);

            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
            GL30.glBindVertexArray(0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    public void cleanup() {
        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(VBO);

        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO);
    }

    public int getVAO() {
        return VAO;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
