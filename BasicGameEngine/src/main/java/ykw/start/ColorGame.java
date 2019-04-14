package ykw.start;

import org.lwjgl.glfw.GLFW;
import ykw.engine.IGameLogic;
import ykw.engine.Window;

public class ColorGame implements IGameLogic {
    private int direction = 0;
    private float color = 0.0F;
    private float red = 0.0F;
    private float green = 0.0F;
    private float blue = 0.0F;
    private long last = 0;
    private long lastR = 0;
    private boolean isFirst = true;
    private boolean isFirstR = true;
    private final Renderer renderer;

    public ColorGame() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        this.renderer.init();
        last = System.currentTimeMillis();
    }

    @Override
    public void input(Window window) {
        if (window.isKeyPressed(GLFW.GLFW_KEY_UP)) {
            direction = 1;
        } else if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
            direction = -1;
        }
    }

    @Override
    public void update(int interval) {
        if (isFirst) {
            last = System.currentTimeMillis();
            isFirst = false;
        } else {
            long current = System.currentTimeMillis();
            long msPerTick = current - last;
            if (msPerTick != 50) {
                System.out.println("MS: " + msPerTick);
            }
            last = current;
            isFirst = true;
        }
        color += direction * 0.01F;
        if (color > 1) {
            color = 1.0F;
        } else if (color < 0) {
            color = 0.0F;
        }
        updateColor();
//        System.out.println("R: " + red + " G: " + green + " B: " + blue);
    }

    @Override
    public void render(Window window, double percentage) {
        if (isFirstR) {
            lastR = System.currentTimeMillis();
            isFirstR = false;
        } else {
            long current = System.currentTimeMillis();
            long msPerFrame = current - lastR;
            if (msPerFrame < 15 || msPerFrame > 18) {
                System.out.println("FMS: " + msPerFrame);
            }
            lastR = current;
            isFirstR = true;
        }
        window.setGLClearColor(red, green, blue, 0.0F);
        renderer.render(window);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
    }

    private void updateColor() {
        blue += 0.125F;
        if (blue > 1) {
            blue = 0.0F;
            green += 0.125F;
        }
        if (green > 1) {
            green = 0.0F;
            red += 0.125F;
        }
        if (red > 1) {
            red = 0.0F;
        }
    }
}
