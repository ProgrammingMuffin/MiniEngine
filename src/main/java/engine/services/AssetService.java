package engine.services;

import java.nio.ByteBuffer;
import java.util.Objects;

import javax.imageio.ImageIO;

import engine.Globals;
import engine.models.IPerPixelProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class AssetService {

    /**
     * code to load asset, run post processing (perPixel) on it, and store
     * bytebuffer onto globals. Post processing can be null, in which case that will
     * be ignored
     */
    public static void loadAsset(BufferedImage image, String assetId, IPerPixelProcessing postProcessing) {
        ByteBuffer byteBuffer = getImageBytesProcessed(image, postProcessing);
        Globals.assetCache.putIfAbsent(assetId, byteBuffer);
    }

    public static BufferedImage getImage(String imageFileName) {
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(GlService.class.getClassLoader()
                    .getResourceAsStream(imageFileName)));
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer getImageBytesProcessed(BufferedImage image, IPerPixelProcessing postProcessing) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(width * height * 4);
        int[] rgbArray = new int[width * height];
        image.getRGB(0, 0, width, height, rgbArray, 0, width);
        /*
         * x,y = y*width + x;
         */
        int x = 0;
        int y = 0;
        for (int rgb : rgbArray) {
            byte r = (byte) ((rgb >> 16) & 0xFF);
            byte g = (byte) ((rgb >> 8) & 0xFF);
            byte b = (byte) (rgb & 0x0000FF);
            byte a = (byte) (0xFF);
            if (postProcessing != null) {
                postProcessing.process(byteBuffer, x, y, r, g, b, a);
            } else {
                byteBuffer.put(r).put(g).put(b).put(a);
            }
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }
        }
        return byteBuffer;
    }
}
