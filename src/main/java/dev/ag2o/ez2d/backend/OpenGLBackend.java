package dev.ag2o.ez2d.backend;

import dev.ag2o.ez2d.backend.shader.ShaderProgram;
import dev.ag2o.ez2d.backend.shader.Shaders;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

@SuppressWarnings("SpellCheckingInspection")
public final class OpenGLBackend extends Backend {
    private final ShaderProgram shader;
    private final int uProjectionLoc;

    private final int vaoId;
    private final int vboId;

    public OpenGLBackend() {
        shader = new ShaderProgram(Shaders.vertex_shader, Shaders.fragment_shader);
        uProjectionLoc = shader.getUniformLocation("uProjection");
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
    public void begin(int width, int height) {
        shader.bind();

        float[] ortho = new float[16];
        ortho[0] = 2.0f / width;
        ortho[5] = -2.0f / height;
        ortho[10] = -1.0f;
        ortho[12] = -1.0f;
        ortho[13] = 1.0f;
        ortho[15] = 1.0f;

        GL20.glUniformMatrix4fv(uProjectionLoc, false, ortho);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void end() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
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
        shader.bind();
        GL30.glBindVertexArray(vaoId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    @Override
    public void vertexUpload(FloatBuffer buffer) {
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
    }

    @Override
    public void vertexPointer(int newPosition, int size, int stride) {
        long offset = (long) newPosition * 4;
        GL20.glVertexAttribPointer(0, size, GL11.GL_FLOAT, false, stride, offset);
    }

    @Override
    public void texCoordPointer(int newPosition, int size, int stride) {
        long offset = (long) newPosition * 4;
        GL20.glVertexAttribPointer(1, size, GL11.GL_FLOAT, false, stride, offset);
    }

    @Override
    public void colorPointer(int newPosition, int size, int stride) {
        long offset = (long) newPosition * 4;
        GL20.glVertexAttribPointer(2, size, GL11.GL_FLOAT, false, stride, offset);
    }

    @Override
    public void endVertexArray(int first, int count) {
        GL11.glDrawArrays(GL11.GL_QUADS, first, count);

        GL30.glBindVertexArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        shader.unbind();
    }
}
