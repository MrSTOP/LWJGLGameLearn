package ykw.transformation.transform;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import ykw.engine.GameObject;
import ykw.engine.Utils;
import ykw.engine.Window;
import ykw.engine.graph.ShaderProgram;
import ykw.engine.graph.Transformation;

import java.util.List;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(60.0);
    private static final float Z_NEAR = 0.01F;
    private static final float Z_FAR = 1000.0F;
    private final Transformation transformation;
    private ShaderProgram shaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shader/transformation/transform/vertex.vert"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shader/transformation/transform/fragment.frag"));
        shaderProgram.link();

        float aspectRatio = (float) window.getWindowWidth() / window.getWindowHeight();
        shaderProgram.createUniform("projection");
        shaderProgram.createUniform("world");
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, List<GameObject> gameObjects) {
        clear();
        if (window.isResized()) {
            GL11.glViewport(0, 0, window.getWindowWidth(), window.getWindowHeight());
            window.setResized(false);
        }

        shaderProgram.bind();
        Matrix4f projection = transformation.getProjectionMatrix(FOV, window.getWindowWidth(), window.getWindowHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setMatrix4F("projection", projection);
        for (GameObject gameObject: gameObjects) {
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                    gameObject.getPosition(),
                    gameObject.getRotation(),
                    gameObject.getScale());
            shaderProgram.setMatrix4F("world", worldMatrix);
            gameObject.getMesh().render();
        }
        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}
