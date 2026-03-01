package dev.ag2o.font;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.ByteBuffer;

public class GLFontRenderer extends TextBuilder {
    private final TextTessellator tess = new TextTessellator();
    private final int textureId;

    public GLFontRenderer(Font font) {
        super(font, 2048);

        this.textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, imageSize, imageSize, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

        for (int i = 0; i < 256; i++) {
            setupChar((char) i);
        }
    }

    @Override
    protected ByteBuffer updateTextureRegion(int x, int y, int width, int height) {
        ByteBuffer buffer = super.updateTextureRegion(x, y, width, height);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        return buffer;
    }

    public void pushText(String text, float x, float y, Color color) {
        for (int i = 0; i < text.length(); i++) {
            if (tess.isRemainingMax()) {
                flush();
            }

            char c = text.charAt(i);

            CharData data = charData(c);
            if (data == null) {
                data = setupChar(c);
            }

            tess.pushChar(x, y, data, imageSize, color);
            x += (data.width - 8);
        }
    }

    public void flush() {
        tess.flush();
    }

    public void beginTexture() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

    public void end() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}