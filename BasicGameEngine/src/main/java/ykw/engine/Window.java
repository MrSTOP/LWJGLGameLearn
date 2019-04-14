package ykw.engine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final String windowTitle;
    private int windowWidth;
    private int windowHeight;
    private long windowHandle;
    private boolean isResized;
    private boolean vSync;
    private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        }
    };
    private GLFWFramebufferSizeCallback framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            windowWidth = width;
            windowHeight = height;
            setResized(true);
        }
    };

    public Window(String windowTitle, int windowWidth, int windowHeight, boolean vSync) {
        this.windowTitle = windowTitle;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.vSync = vSync;
    }

    public void init() {

        //GLFW初始化开始
        GLFW.glfwSetErrorCallback(errorCallback);
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);//将GLFW窗口设为不可见
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);//将GLFW窗口设置为可重新设置大小
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        windowHandle = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        //设置窗口大小调整的回调函数
        GLFW.glfwSetFramebufferSizeCallback(windowHandle, framebufferSizeCallback);

        //设置按键回调函数
        GLFW.glfwSetKeyCallback(windowHandle, keyCallback);

        //获取显示器分辨率
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        //将窗口定位在显示器中心
        GLFW.glfwSetWindowPos(windowHandle,
                              (vidMode.width() - windowWidth) / 2,
                              (vidMode.height() - windowHeight) / 2);
        //OpenGL初始化开始
        GLFW.glfwMakeContextCurrent(windowHandle);

        if (isvSync()) {
            GLFW.glfwSwapInterval(1);
        }

        GLFW.glfwShowWindow(windowHandle);
        //GLFW初始化结束

        GL.createCapabilities();
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //OpenGL初始化结束
    }

    public void setGLClearColor(float red,float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return GLFW.glfwGetKey(windowHandle, keyCode) == GLFW.GLFW_PRESS;
    }

    public boolean isKeyRelease(int keyCode) {
        return GLFW.glfwGetKey(windowHandle, keyCode) == GLFW.GLFW_RELEASE;
    }

    public void update() {
        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
    }

    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void closeWindow() {
        GLFW.glfwTerminate();
        this.errorCallback.free();
        this.keyCallback.free();
        this.framebufferSizeCallback.free();
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public boolean isResized() {
        return isResized;
    }

    public void setResized(boolean resized) {
        isResized = resized;
    }

    public boolean isvSync() {
        return vSync;
    }
}
