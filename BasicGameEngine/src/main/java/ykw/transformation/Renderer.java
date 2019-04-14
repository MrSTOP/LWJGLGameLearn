package ykw.transformation;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import ykw.engine.Utils;
import ykw.engine.Window;
import ykw.engine.graph.Mesh;
import ykw.engine.graph.ShaderProgram;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(60.0);
    private static final float Z_NEAR = 0.01F;
    private static final float Z_FAR = 1000.0F;
    private Matrix4f projectionMatrix;
    private ShaderProgram shaderProgram;

    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shader/transformation/vertex.vert"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shader/transformation/fragment.frag"));
        shaderProgram.link();

        float aspectRatio = (float) window.getWindowWidth() / window.getWindowHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shaderProgram.createUniform("projection");
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Mesh mesh) {
        clear();
        if (window.isResized()) {
            GL11.glViewport(0, 0, window.getWindowWidth(), window.getWindowHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        shaderProgram.setMatrix4F("projection", projectionMatrix);
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
