package dev.ag2o.font;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class TextBuilder {
    private final Map<Character, CharData> charMap = new HashMap<>();
    private final BufferedImage bufferedImage;
    protected final int imageSize;
    protected final Font font;

    private int currentX = 2;
    private int currentY = 2;
    private int maxHeight = 0;

    public TextBuilder(Font font, int imageSize) {
        this.bufferedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        this.imageSize = imageSize;
        this.font = font;
    }

    protected CharData setupChar(char c) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setFont(font);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(Color.WHITE);

        FontMetrics fm = graphics2D.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(String.valueOf(c), graphics2D);

        int width = (int) bounds.getWidth() + 8;
        int height = (int) bounds.getHeight();

        if (currentX + width >= imageSize) {
            currentX = 2;
            currentY += maxHeight;
            maxHeight = 0;
        }

        graphics2D.drawString(String.valueOf(c), currentX, currentY + fm.getAscent());

        CharData data = new CharData();
        data.width = width;
        data.height = height;
        data.storedX = currentX;
        data.storedY = currentY;
        charMap.put(c, data);

        updateTextureRegion(currentX, currentY, width, height);

        if (height > maxHeight) {
            maxHeight = height;
        }
        currentX += width;

        graphics2D.dispose();
        return data;
    }

    protected ByteBuffer updateTextureRegion(int currentX, int currentY, int width, int height) {
        int[] pixels = new int[width * height];
        bufferedImage.getRGB(currentX, currentY, width, height, pixels, 0, width);

        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.nativeOrder());
        // RGBA
        for (int pixel : pixels) {
            buffer.put((byte) ((pixel >> 16) & 0xFF));
            buffer.put((byte) ((pixel >> 8) & 0xFF));
            buffer.put((byte) (pixel & 0xFF));
            buffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        buffer.flip();
        return buffer;
    }

    public CharData charData(char c) {
        return charMap.get(c);
    }
}