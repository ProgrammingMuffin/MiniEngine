package engine.models;

import engine.utils.ShaderUtil;
import lombok.Data;
import org.lwjgl.opengl.GL30;

import java.io.File;
import java.io.InputStream;

/**
 * In OpenGL terms, shader program is assembled
 * after they're individually separated based on
 * what kind of shader is being compiled.
 * This class represent the entire program which
 * can then be used to assemble the final program.
 */
@Data
public class ShaderProgram {

    private int program;

    private Integer vertexShader;

    private Integer fragmentShader;

    public static ShaderProgramBuilder builder() {
        return new ShaderProgramBuilder();
    }

    public static class ShaderProgramBuilder {

        private final ShaderProgram program;

        public ShaderProgramBuilder() {
            this.program = new ShaderProgram();
        }

        public ShaderProgramBuilder addVertexShader(InputStream file) {
            this.program.setVertexShader(ShaderUtil.compileAndAddShaderStage(GL30.GL_VERTEX_SHADER, file));
            return this;
        }

        public ShaderProgramBuilder addFragmentShader(InputStream file) {
            this.program.setFragmentShader(ShaderUtil.compileAndAddShaderStage(GL30.GL_FRAGMENT_SHADER, file));
            return this;
        }

        public ShaderProgram build() {
            int programId = GL30.glCreateProgram();
            this.program.setProgram(programId);
            attachShaderIfPresent(this.program.getVertexShader());
            attachShaderIfPresent(this.program.getFragmentShader());
            return this.program;
        }

        private void attachShaderIfPresent(Integer shader) {
            if (shader == null) {
                return;
            }
            GL30.glAttachShader(this.program.getProgram(), shader);
        }
    }
}
