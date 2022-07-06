package engine.services;

import engine.Globals;
import engine.models.BufferEnum;
import engine.models.DrawTypeEnum;
import engine.models.ShaderProgram;
import org.lwjgl.opengl.GL30;

public class GlService {

    public static void renderObject(BufferEnum bufferType, DrawTypeEnum drawType, float[] coordinates,
                                    int dataCountPerCoordinate, ShaderProgram shaderProgram) {
        int program = shaderProgram.getProgram();
        GL30.glLinkProgram(program);
        GL30.glUseProgram(program);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, Globals.bufferMap.get(bufferType));
        GL30.glBindVertexArray(Globals.arrayMap.get(bufferType));
        GL30.glEnableVertexAttribArray(0);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, coordinates, GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0, 2, GL30.GL_FLOAT, false, Float.BYTES * dataCountPerCoordinate, 0);
        GL30.glDrawArrays(drawType.getGlEnum(), 0, coordinates.length / dataCountPerCoordinate);
    }

    public static void resetRenderingContext() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }
}
