package engine.strategy;

import engine.Globals;
import engine.models.BufferEnum;
import engine.utils.GLUtils;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;

public class RectTextureRenderingStrategy {

    private int x;

    private int y;

    private int width;

    private int height;

    private FloatBuffer dataBuffer;

    public RectTextureRenderingStrategy(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void execute() {
        float[] coordinates = {
                (float) x / Globals.screenWidth,
                (float) y / Globals.screenHeight,
                (float) (x + width) / Globals.screenWidth,
                (float) (y + height) / Globals.screenHeight
        };
        dataBuffer = FloatBuffer.wrap(coordinates);
        GLUtils.prepareForObjectRendering(BufferEnum.SCENE_BUFFER);
        GL30.glEnableVertexAttribArray(0);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, coordinates, GL30.GL_STATIC_DRAW);
    }
}
