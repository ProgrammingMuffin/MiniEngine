package engine.utils;

import engine.Globals;
import engine.models.BufferEnum;
import org.lwjgl.opengl.GL30;

public class GLUtils {

    public static void prepareForObjectRendering(BufferEnum bufferType) {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, Globals.bufferMap.get(bufferType));
        GL30.glBindVertexArray(Globals.bufferMap.get(bufferType));
    }

    public static void unPrepareForObjectRendering() {
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }
}
