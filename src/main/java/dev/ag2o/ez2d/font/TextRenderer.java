package dev.ag2o.ez2d.font;

import dev.ag2o.ez2d.backend.Backend;

import java.awt.*;
import java.nio.ByteBuffer;

@SuppressWarnings("SpellCheckingInspection")
public class TextRenderer extends TextBuilder {
    private final TextTessellator tessellator;
    private final Backend backend;
    private final int textureId;

    public TextRenderer(Backend backend, Font font, int imgSize) {
        super(font, imgSize);

        this.backend = backend;

        this.tessellator = new TextTessellator(this.backend);
        this.textureId = this.backend.genTextures();
        this.backend.bindTexture(textureId);
        this.backend.texParameteri();
        this.backend.texImage2D(imgSize, imgSize, null);

        for (int i = 0; i < 256; i++) {
            setupChar((char) i);
        }
    }

    @Override
    protected ByteBuffer updateTextureRegion(int x, int y, int width, int height) {
        ByteBuffer buffer = super.updateTextureRegion(x, y, width, height);
        this.backend.bindTexture(textureId);
        this.backend.texSubImage2D(x, y, width, height, buffer);
        return buffer;
    }

    public void pushText(String text, float x, float y, Color color) {
        for (int i = 0; i < text.length(); i++) {
            if (tessellator.isRemainingMax()) {
                draw();
            }

            char c = text.charAt(i);

            CharData data = charData(c);
            if (data == null) {
                data = setupChar(c);
            }

            tessellator.pushChar(x, y, data, imageSize(), color);
            x += (data.width - 8);
        }
    }

    public void draw() {
        backend.bindTexture(textureId);
        tessellator.flush();
    }
}