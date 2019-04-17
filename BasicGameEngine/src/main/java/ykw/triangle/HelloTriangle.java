package ykw.triangle;

import ykw.engine.IGameLogic;
import ykw.engine.Window;
import ykw.engine.graph.Mesh;

public class HelloTriangle implements IGameLogic {

    private final Renderer renderer;
    private Mesh mesh;

    public HelloTriangle() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init();
        float[] positions = new float[]{
                -0.5F,  0.5F,  0.0F,
                -0.5F, -0.5F,  0.0F,
                 0.5F, -0.5F,  0.0F,
                 0.5F,  0.5F,  0.0F
        };
        int[] indices = new int[]{
            0, 1, 3,
            3, 1, 2
        };
        float[] colors = new float[]{
                0.5F, 0.0F, 0.0F,
                0.0F, 0.5F, 0.0F,
                0.0F, 0.0F, 0.5F,
                0.0F, 0.5F, 0.5F
        };
        mesh = new Mesh(positions, colors, indices, null, null);
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
