package ykw.engine.graph;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    private final int VAO;
    private final int VBO;
    private final int EBO;
    private final int colorID;
    private final int vertexCount;

    public Mesh(float[] vertices, float[] colors, int[] indices) {
        FloatBuffer verticesBuffer = null;
        FloatBuffer colorsBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            vertexCount = indices.length;

            VAO = GL30.glGenVertexArrays();
            VBO = GL15.glGenBuffers();
            EBO = GL15.glGenBuffers();
            colorID = GL15.glGenBuffers();

            GL30.glBindVertexArray(VAO);

            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            verticesBuffer.put(vertices).flip();
            GL15.glBindBuffer(GL30.GL_ARRAY_BUFFER, VBO);
            GL15.glBufferData(GL30.GL_ARRAY_BUFFER, verticesBuffer, GL30.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);

            colorsBuffer = MemoryUtil.memAllocFloat(colors.length);
            colorsBuffer.put(colors).flip();
            GL15.glBindBuffer(GL30.GL_ARRAY_BUFFER, colorID);
            GL15.glBufferData(GL30.GL_ARRAY_BUFFER, colorsBuffer, GL15.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);

            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            GL15.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, EBO);
            GL15.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL30.GL_STATIC_DRAW);

            GL30.glBindVertexArray(0);
            GL15.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
            if (indices != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
            if (colorsBuffer != null) {
                MemoryUtil.memFree(colorsBuffer);
            }
        }
    }

    public void cleanup() {
        GL30.glDisableVertexAttribArray(0);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(VBO);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(colorID);

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

    public void render() {
        GL30.glBindVertexArray(getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);

        GL11.glDrawElements(GL11.GL_TRIANGLES, getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
