package dev.ag2o.ez2d.backend;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

// holy shit
public final class VulkanBackend extends Backend {

    @Override
    public int genTextures() {
        return 0;
    }

    @Override
    public void bindTexture(int texture) {
    }

    @Override
    public void begin(int width, int height) {
    }

    @Override
    public void end() {

    }

    @Override
    public void texParameteri() {

    }

    @Override
    public void texImage2D(int width, int height, ByteBuffer buffer) {

    }

    @Override
    public void texSubImage2D(int x, int y, int width, int height, ByteBuffer buffer) {

    }

    @Override
    public void beginVertexArray() {

    }

    @Override
    public void vertexUpload(FloatBuffer buffer) {

    }

    @Override
    public void vertexPointer(int newPosition, int size, int stride) {

    }

    @Override
    public void texCoordPointer(int newPosition, int size, int stride) {

    }

    @Override
    public void colorPointer(int newPosition, int size, int stride) {

    }


    @Override
    public void endVertexArray(int first, int count) {

    }
}
