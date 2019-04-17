package ykw.texture;

import ykw.engine.GameObject;
import ykw.engine.IGameLogic;
import ykw.engine.Window;
import ykw.engine.graph.Mesh;
import ykw.engine.graph.Texture;

import java.util.ArrayList;
import java.util.List;

public class TextureTest implements IGameLogic {

    private final Renderer renderer;
    private List<GameObject> gameObjects;


    private long last = 0;
    private boolean isFirst = true;

    public TextureTest() {
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
                -0.5f,  0.5f, -0.5f,
                 0.5f,  0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                 0.5f, -0.5f, -0.5f,
                // For text coords
                -0.5f,  0.5f, -0.5f,
                 0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f,  0.5f,
                 0.5f,  0.5f,  0.5f,
                 0.5f,  0.5f,  0.5f,
                 0.5f, -0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f, -0.5f,
                 0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f,  0.5f,
                 0.5f, -0.5f,  0.5f,
        };
//        float[] colours = new float[]{
//                0.5f, 0.0f, 0.0f,
//                0.0f, 0.5f, 0.0f,
//                0.0f, 0.0f, 0.5f,
//                0.0f, 0.5f, 0.5f,
//                0.5f, 0.0f, 0.0f,
//                0.0f, 0.5f, 0.0f,
//                0.0f, 0.0f, 0.5f,
//                0.0f, 0.5f, 0.5f,
//
//                0.5f, 0.0f, 0.0f,
//                0.0f, 0.5f, 0.0f,
//                0.5f, 0.0f, 0.0f,
//                0.0f, 0.5f, 0.5f,
//                0.0f, 0.5f, 0.5f,
//                0.0f, 0.0f, 0.5f,
//                0.5f, 0.0f, 0.0f,
//                0.0f, 0.5f, 0.0f,
//                0.0f, 0.0f, 0.5f,
//                0.0f, 0.5f, 0.5f,
//                0.0f, 0.5f, 0.0f,
//                0.0f, 0.0f, 0.5f,
//        };
        float[] colours = new float[]{
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,

                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
        };
        float[] textureCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                // For text coords
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                 0,  1,  3,  3,  1,  2,
                 8, 10, 11,  9,  8, 11,
                12, 13,  7,  5, 12,  7,
                14, 15,  6,  4, 14,  6,
                16, 18, 19, 17, 16, 19,
                 4,  6,  7,  5,  4,  7,
        };
        Texture texture = new Texture("/textures/grassblock.png");
        Mesh mesh = new Mesh(positions, colours, indices, textureCoords, texture);
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
        for (GameObject gameObject: gameObjects) {
            float rotatiion = gameObject.getRotation().x + 1.5F;
            if (rotatiion > 360) {
                rotatiion = 0;
            }
            gameObject.setRotation(rotatiion, rotatiion, rotatiion);
        }
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
