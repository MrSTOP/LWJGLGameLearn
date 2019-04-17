package ykw.transformation.transform;

import ykw.engine.GameObject;
import ykw.engine.IGameLogic;
import ykw.engine.Window;
import ykw.engine.graph.Mesh;

import java.util.ArrayList;
import java.util.List;

public class Transform implements IGameLogic {

    private final Renderer renderer;
    private List<GameObject> gameObjects;


    private long last = 0;
    private boolean isFirst = true;

    public Transform() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        float[] positions = new float[]{
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                 0.5f, -0.5f,  0.5f,
                 0.5f,  0.5f,  0.5f,
        };
        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };
        Mesh mesh = new Mesh(positions, colours, indices, null, null);
        GameObject gameObject = new GameObject(mesh);
        gameObject.setPosition(0, 0, -2);
        gameObjects = new ArrayList<>();
        gameObjects.add(gameObject);
        window.setGLClearColor(0.5F, 0.2F, 0.4F, 1.0F);
    }

    @Override
    public void input(Window window) {
    }

    @Override
    public void update(int interval) {

    }

    @Override
    public void render(Window window, double percentage) {
        renderer.render(window, gameObjects);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameObject gameObject: gameObjects) {
            gameObject.getMesh().cleanup();
        }
    }
}
