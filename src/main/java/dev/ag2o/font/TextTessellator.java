package dev.ag2o.font;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.FloatBuffer;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class TextTessellator {
    private static final int MAX_CHARS = 4096;
    private static final int XYUV = 4;
    private static final int C = 8;

    private final FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(MAX_CHARS * XYUV * C);
    private int charCount = 0;

    public void pushChar(float x, float y, CharData data, float imageSize, Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        float u = (float) data.storedX / imageSize;
        float v = (float) data.storedY / imageSize;
        float u2 = (float) (data.storedX + data.width) / imageSize;
        float v2 = (float) (data.storedY + data.height) / imageSize;

        vertexBuffer.put(x).put(y).put(u).put(v)
                .put(r).put(g).put(b).put(a);
        vertexBuffer.put(x + data.width).put(y).put(u2).put(v)
                .put(r).put(g).put(b).put(a);
        vertexBuffer.put(x + data.width).put(y + data.height).put(u2).put(v2)
                .put(r).put(g).put(b).put(a);
        vertexBuffer.put(x).put(y + data.height).put(u).put(v2)
                .put(r).put(g).put(b).put(a);

        charCount++;
    }

    public void flush() {
        if (charCount <= 0) {
            return;
        }

        vertexBuffer.flip();

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

        vertexBuffer.position(0);
        GL11.glVertexPointer(2, GL11.GL_FLOAT, XYUV * C, vertexBuffer);

        vertexBuffer.position(2);
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, XYUV * C, vertexBuffer);

        vertexBuffer.position(4);
        GL11.glColorPointer(4, GL11.GL_FLOAT, XYUV * C, vertexBuffer);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, charCount * 4);

        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        vertexBuffer.clear();
        charCount = 0;
    }

    public boolean isRemainingMax() {
        return vertexBuffer.remaining() < XYUV * C;
    }
}
