package ykw.triangle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import ykw.engine.Utils;
import ykw.engine.Window;
import ykw.engine.graph.Mesh;
import ykw.engine.graph.ShaderProgram;

public class Renderer {

    private ShaderProgram shaderProgram;

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shader/vertex.vert"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shader/fragment.frag"));
        shaderProgram.link();
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Mesh mesh) {
        window.setGLClearColor(0.5F, 0.8F, 0.2F, 1.0F);
        clear();
        if (window.isResized()) {
            GL11.glViewport(0, 0, window.getWindowWidth(), window.getWindowHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
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

