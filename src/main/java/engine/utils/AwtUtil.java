package engine.utils;

import java.awt.image.*;
import java.io.IOException;
import java.awt.*;

public class AwtUtil {

    public static BufferedImage createText(String text, int imageX, int imageY, int imageWidth, int imageHeight,
            float fontSize) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Font font;
        try {
            font = Font.createFont(Font.PLAIN, FileUtil.getFile("fonts/Knewave-Regular.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't load Fira Code font");
        }
        System.out.println("font before is: " + font.getSize());
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(255, 255, 255));
        g.setFont(font.deriveFont(fontSize));
        g.drawString(text, imageX, imageY);
        g.dispose();
        return image;
    }
}
