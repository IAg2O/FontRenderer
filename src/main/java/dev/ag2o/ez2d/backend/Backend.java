package dev.ag2o.ez2d.backend;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public sealed abstract class Backend permits OpenGLBackend, VulkanBackend {
    public abstract int genTextures();
    public abstract void bindTexture(int texture);
    public abstract void begin();
    public abstract void end();
    public abstract void texParameteri();
    public abstract void texImage2D(int width, int height, ByteBuffer buffer);
    public abstract void texSubImage2D(int x, int y, int width, int height, ByteBuffer buffer);
    public abstract void beginVertexArray();
    public abstract void vertexPointer(FloatBuffer buffer, int newPosition, int size, int stride);
    public abstract void texCoordPointer(FloatBuffer buffer, int newPosition, int size, int stride);
    public abstract void colorPointer(FloatBuffer buffer, int newPosition, int size, int stride);
    public abstract void endVertexArray(int first, int count);
}
