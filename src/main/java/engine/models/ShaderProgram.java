package engine.models;

import lombok.Data;

/**
 * In OpenGL terms, shader program is assembled
 * after they're individually separated based on
 * what kind of shader is being compiled.
 * This class represent the entire program which
 * can then be used to assemble the final program.
 */
@Data
public class ShaderProgram {

    private int vertexShader;

    private int fragmentShader;
}
