package engine.models;

import lombok.Getter;
import org.lwjgl.opengl.GL30;

@Getter
public enum DrawTypeEnum {
    QUADS (GL30.GL_QUADS),
    TRIANGLES (GL30.GL_TRIANGLES),
    LINES (GL30.GL_LINES);

    DrawTypeEnum(int glEnum) {
        this.glEnum = glEnum;
    }

    /** what is defined in OpenGL enums */
    private final int glEnum;
}
