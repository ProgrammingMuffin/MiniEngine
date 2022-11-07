package engine.utils;

import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;

import engine.models.RGBWrapper;

import java.awt.*;

public class AwtUtil {

    public static BufferedImage createText(String text, int imageX, int imageY, int imageWidth, int imageHeight,
            InputStream fontFile, float fontSize, RGBWrapper fontColor) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Font font;
        try {
            font = Font.createFont(Font.PLAIN, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't load Fira Code font");
        }
        System.out.println("font before is: " + font.getSize());
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(fontColor.getR(), fontColor.getG(), fontColor.getB()));
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setFont(font.deriveFont(fontSize));
        g.drawString(text, imageX, imageY);
        g.dispose();
        return image;
    }
}
