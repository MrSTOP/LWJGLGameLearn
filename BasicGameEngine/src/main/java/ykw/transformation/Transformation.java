package ykw.transformation;

import ykw.engine.IGameLogic;
import ykw.engine.Window;
import ykw.engine.graph.Mesh;

public class Transformation implements IGameLogic {

    private final Renderer renderer;
    private Mesh mesh;

    public Transformation() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        float[] vertices = new float[]{
                -0.5F,  0.5F, -1.05F,
                -0.5F, -0.5F, -1.05F,
                 0.5F, -0.5F, -1.05F,
                 0.5F,  0.5F, -1.05F,
        };
        float[] color = new float[]{
                0.5F, 0.0F, 0.0F,
                0.0F, 0.5F, 0.0F,
                0.0F, 0.0F, 0.5F,
                0.0F, 0.5F, 0.5F,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };
        mesh = new Mesh(vertices, color, indices);
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void update(int interval) {

    }

    @Override
    public void render(Window window, double percentage) {
        renderer.render(window, mesh);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        mesh.cleanup();
    }
}
