package dev.ag2o.ez2d.backend;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public final class OpenGLBackend extends Backend {
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
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
    }

    @Override
    public void vertexPointer(FloatBuffer buffer, int newPosition, int size, int stride) {
        buffer.position(newPosition);
        GL11.glVertexPointer(size, GL11.GL_FLOAT, stride, buffer);
    }

    @Override
    public void texCoordPointer(FloatBuffer buffer, int newPosition, int size, int stride) {
        buffer.position(newPosition);
        GL11.glTexCoordPointer(size, GL11.GL_FLOAT, stride, buffer);
    }

    @Override
    public void colorPointer(FloatBuffer buffer, int newPosition, int size, int stride) {
        buffer.position(newPosition);
        GL11.glColorPointer(size, GL11.GL_FLOAT, stride, buffer);
    }

    @Override
    public void endVertexArray(int first, int count) {
        GL11.glDrawArrays(GL11.GL_QUADS, first, count);

        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }
}
