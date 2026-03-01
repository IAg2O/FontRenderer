package dev.ag2o.ez2d.backend;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public final class OpenGLBackend extends Backend {
    private final int vaoId;
    private final int vboId;

    public OpenGLBackend() {
        vaoId = GL30.glGenVertexArrays();
        vboId = GL15.glGenBuffers();
    }

    @Override
    public int genTextures() {
        return GL11.glGenTextures();
    }

    @Override
    public void bindTexture(int texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }

    @Override
    public void begin() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public void end() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    @Override
    public void texParameteri() {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
    }

    @Override
    public void texImage2D(int width, int height, ByteBuffer buffer) {
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }

    @Override
    public void texSubImage2D(int x, int y, int width, int height, ByteBuffer buffer) {
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }

    @Override
    public void beginVertexArray() {
        GL30.glBindVertexArray(vaoId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
    }

    @Override
    public void vertexUpload(FloatBuffer buffer) {
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
    }

    @Override
    public void vertexPointer(int newPosition, int size, int stride) {
        long offset = (long) newPosition * 4;
        GL11.glVertexPointer(size, GL11.GL_FLOAT, stride, offset);
    }

    @Override
    public void texCoordPointer(int newPosition, int size, int stride) {
        long offset = (long) newPosition * 4;
        GL11.glTexCoordPointer(size, GL11.GL_FLOAT, stride, offset);
    }

    @Override
    public void colorPointer(int newPosition, int size, int stride) {
        long offset = (long) newPosition * 4;
        GL11.glColorPointer(size, GL11.GL_FLOAT, stride, offset);
    }

    @Override
    public void endVertexArray(int first, int count) {
        GL11.glDrawArrays(GL11.GL_QUADS, first, count);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
}
