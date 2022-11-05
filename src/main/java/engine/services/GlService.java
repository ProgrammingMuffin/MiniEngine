package engine.services;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL30;

import engine.Globals;
import engine.models.BufferEnum;
import engine.models.DrawTypeEnum;
import engine.models.Polygon;
import engine.models.RGBWrapper;
import engine.models.ShaderProgram;

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
            GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
            GL30.glEnable(GL30.GL_BLEND);
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, Globals.bufferMap.get(BufferEnum.TEXTURE_BUFFER));
            if (Globals.textureMap.get(polygon.getTextureFileName()) == null) {
                Globals.textureMap.put(polygon.getTextureFileName(), GL30.glGenTextures());
            }
            Integer texId = Globals.textureMap.get(polygon.getTextureFileName());
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, texId);
            BufferedImage image = polygon.getTextureFile();
            if (Globals.assetCache.get(polygon.getAssetId()) == null) {
                AssetService.loadAsset(image, polygon.getAssetId(), polygon.getPerPixelProcessing());
            }
            ByteBuffer imageData = Globals.assetCache.get(polygon.getAssetId());
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
}
