package ykw.engine.graph;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    private final int VAO;
    private final int VBO;
    private final int EBO;
    private final int vertexCount;

    public Mesh(float[] vertices, int[] indices) {
        FloatBuffer verticesBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            vertexCount = indices.length;

            VAO = GL30.glGenVertexArrays();
            VBO = GL30.glGenBuffers();
            EBO = GL30.glGenBuffers();

            GL30.glBindVertexArray(VAO);

            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            verticesBuffer.put(vertices).flip();
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, VBO);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);

            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, EBO);
            GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL30.GL_STATIC_DRAW);

            GL30.glBindVertexArray(0);
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
            GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
            if (indices != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public void cleanup() {
        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(VBO);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(EBO);

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
