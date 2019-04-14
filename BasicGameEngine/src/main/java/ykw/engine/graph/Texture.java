package ykw.engine.graph;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;

public class Texture {
    private final int ID;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

    public Texture(int ID) {
        this.ID = ID;
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
    }

    public int getID() {
        return ID;
    }

    private static int loadTexture(String fileName) throws URISyntaxException {
        int width;
        int height;
        ByteBuffer buffer;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            URL url = Texture.class.getResource(fileName);
            File file = Paths.get(url.toURI()).toFile();
            String filePath = file.getAbsolutePath();
            buffer = STBImage.stbi_load(filePath, w, h, channels, 4);
            if (buffer == null) {
                throw new IllegalStateException("Image file [" + filePath + "] not loaded: " + STBImage.stbi_failure_reason());
            }
            width = w.get();
            height = h.get();

            int textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0， GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

            GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
            STBImage.stbi_image_free(buffer);

            return textureID;
        }
    }

    public void cleanup() {
        GL11.glDeleteTextures(ID);
    }
}
