package engine.services;

import engine.Globals;
import engine.models.*;
import engine.models.Polygon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static java.sql.Types.FLOAT;

public class GlService {

    private static final int dataCountPerColor = 3;

    public static void renderObject(BufferEnum bufferType, DrawTypeEnum drawType, Polygon polygon,
            int dataCountPerCoordinate, ShaderProgram shaderProgram) {
        int program = shaderProgram.getProgram();
        GL30.glLinkProgram(program);
        GL30.glUseProgram(program);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, Globals.bufferMap.get(bufferType));
        GL30.glBindVertexArray(Globals.arrayMap.get(bufferType));
        GL30.glEnableVertexAttribArray(GL30.glGetAttribLocation(program, "vertexCoord"));
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, polygon.getCoordinates(), GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(GL30.glGetAttribLocation(program, "vertexCoord"), dataCountPerCoordinate,
                GL30.GL_FLOAT, false,
                Float.BYTES * dataCountPerCoordinate, 0);
        if (polygon.getTextureCoordinates() != null && polygon.getTextureCoordinates().length > 0) {
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, Globals.bufferMap.get(BufferEnum.TEXTURE_BUFFER));
            if (Globals.textureMap.get(polygon.getTextureFileName()) == null) {
                Globals.textureMap.put(polygon.getTextureFileName(), GL30.glGenTextures());
            }
            Integer texId = Globals.textureMap.get(polygon.getTextureFileName());
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, texId);
            BufferedImage image = getImage(polygon.getTextureFileName());
            ByteBuffer imageData = getImageBytes(image);
            GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
            GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_LINEAR);
            GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, image.getWidth(), image.getHeight(),
                    0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, imageData.flip());
            int uniformLocation = GL30.glGetUniformLocation(program, "tex");
            GL30.glUniform1i(uniformLocation, 0);
            GL30.glEnableVertexAttribArray(GL30.glGetAttribLocation(program, "textureCoord"));
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, polygon.getTextureCoordinates(), GL30.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(GL30.glGetAttribLocation(program, "textureCoord"), 2, GL30.GL_FLOAT, false,
                    Float.BYTES * 2, 0);
        } else if (polygon.getRgb() != null) {
            int uniformLocation = GL30.glGetUniformLocation(program, "colors");
            RGBWrapper rgb = polygon.getRgb();
            GL30.glUniform3f(uniformLocation, (float) rgb.getR() / 255, (float) rgb.getG() / 255,
                    (float) rgb.getB() / 255);
        }
        GL30.glDrawArrays(drawType.getGlEnum(), 0, polygon.getCoordinates().length / dataCountPerCoordinate);
    }

    public static void resetRenderingContext() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    private static BufferedImage getImage(String imageFileName) {
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(GlService.class.getClassLoader()
                    .getResourceAsStream(imageFileName)));
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer getImageBytes(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(width * height * 4);
        int[] rgbArray = new int[width * height];
        image.getRGB(0, 0, width, height, rgbArray, 0, width);
        for (int rgb : rgbArray) {
            byteBuffer.put((byte) ((rgb >> 16) & 0xFF));
            byteBuffer.put((byte) ((rgb >> 8) & 0xFF));
            byteBuffer.put((byte) (rgb & 0x0000FF));
            byteBuffer.put((byte) 0xFF);
        }
        return byteBuffer;
    }
}
