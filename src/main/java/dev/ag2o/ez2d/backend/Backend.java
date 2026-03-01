package dev.ag2o.ez2d.backend;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public sealed abstract class Backend permits OpenGLBackend, VulkanBackend {
    public abstract int genTextures();
    public abstract void bindTexture(int texture);
    public abstract void begin(int width, int height);
    public abstract void end();
    public abstract void texParameteri();
    public abstract void texImage2D(int width, int height, ByteBuffer buffer);
    public abstract void texSubImage2D(int x, int y, int width, int height, ByteBuffer buffer);
    public abstract void beginVertexArray();
    public abstract void vertexUpload(FloatBuffer buffer);
    public abstract void vertexPointer(int newPosition, int size, int stride);
    public abstract void texCoordPointer(int newPosition, int size, int stride);
    public abstract void colorPointer(int newPosition, int size, int stride);
    public abstract void endVertexArray(int first, int count);
}
